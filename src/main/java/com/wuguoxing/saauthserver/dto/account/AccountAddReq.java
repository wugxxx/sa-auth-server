package com.wuguoxing.saauthserver.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "新增账号")
public class AccountAddReq {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(max = 32, message = "用户名不能超过32个字符")
    private String username;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    @Length(max = 32, message = "昵称不能超过32个字符")
    private String nickname;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])^.{8,20}$", message = "密码需同时包含字母和数字，支持特殊字符，长度为：8~20")
    private String password;

    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
}
