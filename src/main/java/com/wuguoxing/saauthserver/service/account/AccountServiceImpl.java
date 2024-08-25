package com.wuguoxing.saauthserver.service.account;

import cn.dev33.satoken.secure.BCrypt;
import com.wuguoxing.saauthserver.common.constant.CommonConstant;
import com.wuguoxing.saauthserver.common.exception.ResourceNotFoundException;
import com.wuguoxing.saauthserver.convert.AccountMapper;
import com.wuguoxing.saauthserver.dto.account.*;
import com.wuguoxing.saauthserver.entity.Account;
import com.wuguoxing.saauthserver.entity.AccountRole;
import com.wuguoxing.saauthserver.entity.Role;
import com.wuguoxing.saauthserver.repository.account.AccountRepository;
import com.wuguoxing.saauthserver.repository.account.AccountSpecification;
import com.wuguoxing.saauthserver.repository.role.RoleRepository;
import com.wuguoxing.saauthserver.repository.rs.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private AccountRoleRepository accountRoleRepository;
    @Autowired
    public void setAccountRoleRepository(AccountRoleRepository accountRoleRepository) {
        this.accountRoleRepository = accountRoleRepository;
    }

    private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(AccountAddReq req) {
        Account account = AccountMapper.INSTANCE.toEntity(req);
        account.setPassword(BCrypt.hashpw(req.getPassword()));
        account.setStatus(CommonConstant.ACCOUNT_STATUS_NORMAL);
        Account accountSaved = accountRepository.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(accountSaved.getId());
        accountRole.setRoleId(req.getRoleId());
        accountRoleRepository.save(accountRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AccountUpdateReq req) {
        Account account = this.get(req.getId());
        AccountMapper.INSTANCE.toEntity(req, account);

        if (StringUtils.hasText(req.getPassword())) {
            account.setPassword(BCrypt.hashpw(req.getPassword()));
        }

        if (null != req.getRoleId()) {
            accountRoleRepository.deleteAllByAccountId(account.getId());
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(account.getId());
            accountRole.setRoleId(req.getRoleId());
            accountRoleRepository.save(accountRole);
        }

        accountRepository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account get(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("账户不存在"));
    }

    @Override
    public AccountDetailResp getDetail(Long id) {
        Account account = this.get(id);
        return AccountMapper.INSTANCE.toDetailResp(account);
    }

    @Override
    public Page<Account> search(AccountSearchReq req) {
        Specification<Account> specification = Stream.of(
                        StringUtils.hasText(req.getUsername()) ? AccountSpecification.usernameLike(req.getUsername()) : null,
                        StringUtils.hasText(req.getNickname()) ? AccountSpecification.nicknameLike(req.getNickname()) : null
                )
                .filter(Objects::nonNull) // 过滤掉 null
                .reduce(Specification::and) // 将所有的 Specification 组合成一个
                .orElse(null); // 如果没有 Specification，则使用 null

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), req.getSort());

        return specification != null
                ? accountRepository.findAll(specification, pageable)
                : accountRepository.findAll(pageable);
    }

    @Override
    public Page<AccountListResp> searchList(AccountSearchReq req) {
        Page<Account> accountPage = this.search(req);
        return accountPage.map(AccountMapper.INSTANCE::toListResp);
    }

    @Override
    public List<RoleListResp> getRoleList() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(AccountMapper.INSTANCE::toListResp).toList();
    }
}
