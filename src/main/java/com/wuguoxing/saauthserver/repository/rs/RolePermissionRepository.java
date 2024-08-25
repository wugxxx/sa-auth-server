package com.wuguoxing.saauthserver.repository.rs;

import com.wuguoxing.saauthserver.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findAllByRoleIdIn(Collection<Long> roleIds);
}
