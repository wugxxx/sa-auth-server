package com.wuguoxing.saauthserver.repository.permission;

import com.wuguoxing.saauthserver.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findAllByIdIn(List<Long> ids);
}
