package com.wuguoxing.saauthserver.service.account;

import com.wuguoxing.saauthserver.dto.account.*;
import com.wuguoxing.saauthserver.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    /**
     * 新增账户
     */
    void add(AccountAddReq req);

    /**
     * 更新账户
     */
    void update(AccountUpdateReq req);

    /**
     * 删除账户
     */
    void delete(Long id);

    /**
     * 获取账户
     */
    Account get(Long id);

    /**
     * 获取账户详情
     */
    AccountDetailResp getDetail(Long id);

    /**
     * 搜索账户列表
     */
    Page<Account> search(AccountSearchReq req);

    /**
     * 搜索账户列表
     */
    Page<AccountListResp> searchList(AccountSearchReq req);

    /**
     * 获取角色列表
     */
    List<RoleListResp> getRoleList();
}
