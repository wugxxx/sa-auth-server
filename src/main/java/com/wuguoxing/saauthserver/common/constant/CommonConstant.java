package com.wuguoxing.saauthserver.common.constant;

public interface CommonConstant {

    /**
     * 登录验证码 key 前缀
     */
    String CAPTCHA_KEY_PREFIX = "auth:captcha:";

    /**
     * 角色 key 前缀
     */
    String ROLE_KEY_PREFIX = "auth:role:";

    /**
     * 权限 key 前缀
     */
    String PERMISSION_KEY_PREFIX = "auth:permission:";

    /**
     * Refresh Token key 前缀
     */
    String REFRESH_TOKEN_KEY_PREFIX = "auth:refresh:";

    /**
     * 角色过期时间（1 天，单位：秒）
     */
    Long ROLE_EXPIRE = 86400L;

    /**
     * 权限过期时间（1 天，单位：秒）
     */
    Long PERMISSION_EXPIRE = 86400L;

    /**
     * Refresh Token 过期时间（7 天，单位：秒）
     */
    Long REFRESH_TOKEN_EXPIRE = 604800L;

    /**
     * 账户状态：正常
     */
    Integer ACCOUNT_STATUS_NORMAL = 1;

    /**
     * 账户状态：禁用
     */
    Integer ACCOUNT_STATUS_DISABLE = 0;
}
