package com.wuguoxing.saauthserver.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    ADMIN("role_admin", "管理员"),

    USER("role_user", "普通用户");

    private final String code;

    private final String desc;
}
