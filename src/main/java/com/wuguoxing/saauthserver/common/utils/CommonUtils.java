package com.wuguoxing.saauthserver.common.utils;

/**
 * 通用工具类
 */
public class CommonUtils {

    /**
     * 生成UUID
     */
    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

}
