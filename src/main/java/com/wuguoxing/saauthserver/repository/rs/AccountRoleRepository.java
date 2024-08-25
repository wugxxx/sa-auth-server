package com.wuguoxing.saauthserver.repository.rs;

import com.wuguoxing.saauthserver.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

    List<AccountRole> findAllByAccountId(Long accountId);

    @Transactional
    @Modifying
    void deleteAllByAccountId(Long accountId);
}
