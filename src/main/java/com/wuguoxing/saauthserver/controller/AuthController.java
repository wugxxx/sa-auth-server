package com.wuguoxing.saauthserver.controller;

import com.wuguoxing.saauthserver.dto.auth.AuthLoginReq;
import com.wuguoxing.saauthserver.dto.auth.AuthLoginResp;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshReq;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshResp;
import com.wuguoxing.saauthserver.dto.common.ResponseEntity;
import com.wuguoxing.saauthserver.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "认证管理相关接口")
public class AuthController {

    private AuthService authService;
    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "账户登录")
    public ResponseEntity<AuthLoginResp> login(@Valid @RequestBody AuthLoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/logout")
    @Operation(summary = "账户登出")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok();
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌")
    public ResponseEntity<AuthTokenRefreshResp> refresh(@Valid @RequestBody AuthTokenRefreshReq req) {
        AuthTokenRefreshResp refreshResp = authService.refreshToken(req);
        return ResponseEntity.ok(refreshResp);
    }
}
