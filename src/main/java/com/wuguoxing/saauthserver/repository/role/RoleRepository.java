package com.wuguoxing.saauthserver.repository.role;

import com.wuguoxing.saauthserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByIdIn(Collection<Long> ids);
}
