-- 用户列表(admin/abc123456)
INSERT INTO `account`
(`id`, `create_time`, `update_time`, `username`, `nickname`, `password`, `status`)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'root', '超级管理员', '$2a$10$QWGTcDfHLyyq.U5SDL9m2.ko/wgGln0/BZRpA.2xcdtKoSzHSJgTW', 1);

-- 角色列表
INSERT INTO `role`
(`id`, `create_time`, `update_time`, `name`, `description`)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'role_root', '超级管理员'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'role_admin', '管理员'),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'role_user', '普通用户');

-- 权限列表
INSERT INTO `permission`
(`id`, `create_time`, `update_time`, `name`, `description`)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:*', '账户管理权限'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:add', '新增权限'),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:update', '账户更新权限'),
    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:delete', '账户删除权限'),
    (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:get', '账户详情权限'),
    (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:search', '账户查询权限'),
    (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'account:roles', '账户角色查询权限');

-- 用户角色列表
INSERT INTO `rs_account_role`
(`create_time`, `update_time`, `account_id`, `role_id`)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);

-- 角色权限列表
INSERT INTO `rs_role_permission`
(`create_time`, `update_time`, `role_id`, `permission_id`)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 5),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 6),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 7),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 5);