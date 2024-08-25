package com.wuguoxing.saauthserver.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "刷新令牌请求")
public class AuthTokenRefreshReq {

    @Schema(description = "Refresh Token")
    @NotBlank(message = "Refresh Token 不能为空")
    @Length(max = 512, message = "Refresh Token 不能超过 512 个字符")
    private String refreshToken;
}
