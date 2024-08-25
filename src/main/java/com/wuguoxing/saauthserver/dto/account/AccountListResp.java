package com.wuguoxing.saauthserver.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户列表返回")
public class AccountListResp {

    @Schema(description = "用户UUID")
    private String uuid;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;
}
