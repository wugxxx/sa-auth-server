package com.wuguoxing.saauthserver.service.auth;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.wuguoxing.saauthserver.common.constant.CommonConstant;
import com.wuguoxing.saauthserver.common.exception.BusinessException;
import com.wuguoxing.saauthserver.common.utils.JwtUtils;
import com.wuguoxing.saauthserver.convert.AccountMapper;
import com.wuguoxing.saauthserver.dto.auth.AuthLoginReq;
import com.wuguoxing.saauthserver.dto.auth.AuthLoginResp;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshReq;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshResp;
import com.wuguoxing.saauthserver.entity.*;
import com.wuguoxing.saauthserver.repository.account.AccountRepository;
import com.wuguoxing.saauthserver.repository.permission.PermissionRepository;
import com.wuguoxing.saauthserver.repository.role.RoleRepository;
import com.wuguoxing.saauthserver.repository.rs.AccountRoleRepository;
import com.wuguoxing.saauthserver.repository.rs.RolePermissionRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private RedisTemplate<String, String> stringRedisTemplate;
    @Autowired
    public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private RedisTemplate<String, List<String>> listRedisTemplate;
    @Autowired
    public void setListRedisTemplate(RedisTemplate<String, List<String>> listRedisTemplate) {
        this.listRedisTemplate = listRedisTemplate;
    }

    private AccountRepository accountRepository;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private PermissionRepository permissionRepository;
    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    private AccountRoleRepository accountRoleRepository;
    @Autowired
    public void setAccountRoleRepository(AccountRoleRepository accountRoleRepository) {
        this.accountRoleRepository = accountRoleRepository;
    }

    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    public void setRolePermissionRepository(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public AuthLoginResp login(AuthLoginReq req) {
        Account account = accountRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));

        if (!BCrypt.checkpw(req.getPassword(), account.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        AuthLoginResp loginResp = AccountMapper.INSTANCE.toLoginResp(account);

        StpUtil.login(account.getId());
        String refreshToken = JwtUtils.createJWT("refreshToken",
                Map.of("id", account.getId(), "username", account.getUsername()),
                CommonConstant.REFRESH_TOKEN_EXPIRE);
        stringRedisTemplate.opsForValue().set(CommonConstant.REFRESH_TOKEN_KEY_PREFIX + account.getId(),
                refreshToken, CommonConstant.REFRESH_TOKEN_EXPIRE, TimeUnit.SECONDS);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        loginResp.setRefreshToken(refreshToken);
        loginResp.setAccessToken(tokenInfo.getTokenValue());
        loginResp.setRoleList(this.getRoleList(StpUtil.getLoginId(), StpUtil.getLoginType()));

        return loginResp;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String roleKey = CommonConstant.ROLE_KEY_PREFIX + loginId;
        if (Boolean.TRUE.equals(listRedisTemplate.hasKey(roleKey))) {
            return listRedisTemplate.opsForValue().get(roleKey);
        } else {
            List<AccountRole> accountRoleList = accountRoleRepository.findAllByAccountId(Long.valueOf(loginId.toString()));
            List<Long> roleIdList = accountRoleList.stream().map(AccountRole::getRoleId).toList();
            List<Role> roleList = roleRepository.findAllByIdIn(roleIdList);
            List<String> roList = roleList.stream().map(Role::getName).distinct().toList();
            listRedisTemplate.opsForValue().set(roleKey, roList, CommonConstant.ROLE_EXPIRE, TimeUnit.SECONDS);
            return roList;
        }
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String permissionKey = CommonConstant.PERMISSION_KEY_PREFIX + loginId;
        if (Boolean.TRUE.equals(listRedisTemplate.hasKey(permissionKey))) {
            return listRedisTemplate.opsForValue().get(permissionKey);
        } else {
            List<AccountRole> accountRoleList = accountRoleRepository.findAllByAccountId(Long.valueOf(loginId.toString()));
            List<Long> roleIdList = accountRoleList.stream().map(AccountRole::getRoleId).toList();
            List<RolePermission> rolePermissionList = rolePermissionRepository.findAllByRoleIdIn(roleIdList);
            List<Long> permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).distinct().toList();
            List<Permission> permissionList = permissionRepository.findAllByIdIn(permissionIdList);
            List<String> perList = permissionList.stream().map(Permission::getName).distinct().toList();
            listRedisTemplate.opsForValue().set(permissionKey, perList, CommonConstant.PERMISSION_EXPIRE, TimeUnit.SECONDS);
            return perList;
        }
    }

    @Override
    public AuthTokenRefreshResp refreshToken(AuthTokenRefreshReq req) {
        String refreshToken = req.getRefreshToken();
        Claims claims = JwtUtils.parseJWT(refreshToken);
        if (claims != null) {
            if (claims.get("id") != null && claims.get("username") != null) {
                Long accountId = Long.valueOf(claims.get("id").toString());
                String username = claims.get("username").toString();
                String refreshTokenKey = CommonConstant.REFRESH_TOKEN_KEY_PREFIX + accountId;
                String refreshTokenValue = stringRedisTemplate.opsForValue().get(refreshTokenKey);
                if (refreshTokenValue != null && refreshTokenValue.equals(refreshToken)) {
                    StpUtil.login(accountId);
                    String newRefreshToken = JwtUtils.createJWT("refreshToken",
                            Map.of("id", accountId, "username", username),
                            CommonConstant.REFRESH_TOKEN_EXPIRE);
                    stringRedisTemplate.opsForValue().set(CommonConstant.REFRESH_TOKEN_KEY_PREFIX + accountId,
                            newRefreshToken, CommonConstant.REFRESH_TOKEN_EXPIRE, TimeUnit.MILLISECONDS);

                    return AuthTokenRefreshResp.builder().accessToken(StpUtil.getTokenInfo().getTokenValue())
                            .refreshToken(newRefreshToken).build();
                }
            }
        }

        throw new BusinessException("Refresh Token 无效");
    }
}
