package com.wuguoxing.saauthserver.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "账户详情返回")
public class AccountDetailResp {

    @Schema(description = "账户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "角色ID")
    private Long roleId;
}
