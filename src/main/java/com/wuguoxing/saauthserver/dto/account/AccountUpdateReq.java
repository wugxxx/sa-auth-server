package com.wuguoxing.saauthserver.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "更新账号请求")
public class AccountUpdateReq {

    @Schema(description = "账户ID")
    @NotNull(message = "账户ID不能为空")
    private Long id;

    @Schema(description = "昵称")
    @Length(max = 32, message = "昵称不能超过32个字符")
    private String nickname;

    @Schema(description = "密码")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])^.{8,20}$", message = "密码需同时包含字母和数字，支持特殊字符，长度为：8~20")
    private String password;

    @Schema(description = "角色ID")
    private Long roleId;
}
