package com.wuguoxing.saauthserver.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wuguoxing.saauthserver.common.group.SearchOperation;
import com.wuguoxing.saauthserver.dto.account.*;
import com.wuguoxing.saauthserver.dto.common.PageEntity;
import com.wuguoxing.saauthserver.dto.common.ResponseEntity;
import com.wuguoxing.saauthserver.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Tag(name = "账户管理", description = "账户管理相关接口")
public class AccountController {

    private AccountService accountService;
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @SaCheckPermission("account:add")
    @PostMapping()
    @Operation(summary = "新增账户")
    public ResponseEntity<Void> add(@Valid @RequestBody AccountAddReq req) {
        accountService.add(req);
        return ResponseEntity.ok();
    }

    @SaCheckPermission("account:update")
    @PutMapping()
    @Operation(summary = "更新账户")
    public ResponseEntity<Void> update(@Valid @RequestBody AccountUpdateReq req) {
        accountService.update(req);
        return ResponseEntity.ok();
    }

    @SaCheckPermission("account:delete")
    @DeleteMapping("/{id}")
    @Operation(summary = "删除账户")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.ok();
    }

    @SaCheckPermission("account:get")
    @GetMapping("/{id}")
    @Operation(summary = "查询账户")
    public ResponseEntity<AccountDetailResp> get(@PathVariable Long id) {
        AccountDetailResp detailResp = accountService.getDetail(id);
        return ResponseEntity.ok(detailResp);
    }

    @SaCheckPermission("account:search")
    @PostMapping("/search")
    @Operation(summary = "查询账户列表")
    public ResponseEntity<PageEntity<AccountListResp>> list(
            @Validated(SearchOperation.class) @RequestBody AccountSearchReq req) {
        Page<AccountListResp> respPage = accountService.searchList(req);
        return ResponseEntity.ok(new PageEntity<>(respPage));
    }

    @SaCheckPermission("account:roles")
    @GetMapping("/roles")
    @Operation(summary = "查询角色列表")
    public ResponseEntity<List<RoleListResp>> getRoleList() {
        List<RoleListResp> roleList = accountService.getRoleList();
        return ResponseEntity.ok(roleList);
    }
}
