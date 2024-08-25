package com.wuguoxing.saauthserver.common.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.wuguoxing.saauthserver.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {

    private AuthService authService;
    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = authService.getPermissionList(loginId, loginType);
        log.info("loginId: {}, 权限列表：{}", loginId, permissionList);
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = authService.getRoleList(loginId, loginType);
        log.info("loginId: {}, 角色列表：{}", loginId, roleList);
        return roleList;
    }

}

