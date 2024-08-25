package com.wuguoxing.saauthserver.convert;

import com.wuguoxing.saauthserver.dto.account.*;
import com.wuguoxing.saauthserver.dto.auth.AuthLoginResp;
import com.wuguoxing.saauthserver.entity.Account;
import com.wuguoxing.saauthserver.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AuthLoginResp toLoginResp(Account account);

    Account toEntity(AccountAddReq req);

    void toEntity(AccountUpdateReq req, @MappingTarget Account account);

    AccountDetailResp toDetailResp(Account account);

    AccountListResp toListResp(Account account);

    RoleListResp toListResp(Role role);
}
