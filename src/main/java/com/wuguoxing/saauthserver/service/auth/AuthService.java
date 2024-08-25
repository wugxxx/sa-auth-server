package com.wuguoxing.saauthserver.service.auth;

import com.wuguoxing.saauthserver.dto.auth.AuthLoginReq;
import com.wuguoxing.saauthserver.dto.auth.AuthLoginResp;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshReq;
import com.wuguoxing.saauthserver.dto.auth.AuthTokenRefreshResp;

import java.util.List;

public interface AuthService {

    /**
     * 登录
     */
    AuthLoginResp login(AuthLoginReq req);

    /**
     * 登出
     */
    void logout();

    /**
     * 获取角色列表
     */
    List<String> getRoleList(Object loginId, String loginType);

    /**
     * 获取权限列表
     */
    List<String> getPermissionList(Object loginId, String loginType);

    /**
     * 刷新令牌
     */
    AuthTokenRefreshResp refreshToken(AuthTokenRefreshReq req);
}
