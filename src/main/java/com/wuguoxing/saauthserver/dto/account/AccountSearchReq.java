package com.wuguoxing.saauthserver.dto.account;

import com.wuguoxing.saauthserver.dto.common.BaseSearchReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "账户查询请求")
public class AccountSearchReq extends BaseSearchReq {

    @Schema(description = "用户名")
    @Length(max = 32, message = "用户名不能超过32个字符")
    private String username;

    @Schema(description = "昵称")
    @Length(max = 32, message = "昵称不能超过32个字符")
    private String nickname;
}
