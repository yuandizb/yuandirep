/*
  Source Server : 127.0.0.1
  Source Host : localhost:3317
  Source Database : micro

  Target Server Type : MYSQL
  Target Server Version : 5.7
  File Encoding : utf-8
  Author ： lishouyu
  Email : 18332763730@163.com
*/
#  外键检查，不能有外键
SET FOREIGN_KEY_CHECKS = 0;
-- upms用户权限管理系统

-- -------------------------
-- upms_log upms操作日志
-- -------------------------
CREATE TABLE IF NOT EXISTS `upms_log` (
  `id`          INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '日志编号',
  `descirption` VARCHAR(100)        DEFAULT NULL
  COMMENT '操作描述',
  `username`    VARCHAR(20)         DEFAULT NULL
  COMMENT '操作用户',
  `start_time`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '操作时间',
  `spend_time`  INT UNSIGNED        DEFAULT NULL
  COMMENT '消耗时间',
  `base_path`   VARCHAR(100)        DEFAULT NULL
  COMMENT '根路径',
  `uri`         VARCHAR(200)        DEFAULT NULL
  COMMENT 'URI',
  `url`         VARCHAR(200)        DEFAULT NULL
  COMMENT 'URL',
  `method`      VARCHAR(10)         DEFAULT NULL
  COMMENT '请求的类型',
  `parameter`   MEDIUMTEXT          DEFAULT NULL
  COMMENT '请求参数',
  `user_agent`  VARCHAR(200)        DEFAULT NULL
  COMMENT '用户标识',
  `ip`          VARCHAR(30)         DEFAULT NULL
  COMMENT 'ip地址',
  `result`      MEDIUMTEXT          DEFAULT NULL
  COMMENT '响应结果',
  `permissions` VARCHAR(100)        DEFAULT NULL
  COMMENT '权限值',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ------------------
-- upms_user upms用户表
-- ------------------
CREATE TABLE IF NOT EXISTS upms_user (
  `id`       INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '用户的编号',
  `username` VARCHAR(20)                     NOT NULL
  COMMENT '用户名',
  `password` VARCHAR(200)                     NOT NULL
  COMMENT '密码加密后的md5值(密码+盐值）',
  `salt`     VARCHAR(32)  DEFAULT NULL
  COMMENT '盐值',
  `realname` VARCHAR(20)  DEFAULT NULL
  COMMENT '真实姓名',
  `avatar`   VARCHAR(150) DEFAULT NULL
  COMMENT '头像',
  `phone`    VARCHAR(20)  DEFAULT NULL
  COMMENT '电话',
  `email`    VARCHAR(50)  DEFAULT NULL
  COMMENT '电子邮箱',
  `sex`      TINYINT(4)   DEFAULT NULL
  COMMENT '性别',
  `locked`   TINYINT(4)   DEFAULT NULL
  COMMENT '账号状态',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------
-- upms_organization upms组织表
-- ---------------------------
CREATE TABLE IF NOT EXISTS upms_organization (
  `id`          INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '组织的编号',
  `pid`         INT(10) UNSIGNED    DEFAULT NULL
  COMMENT '所属上级',
  `name`        VARCHAR(20)         DEFAULT NULL
  COMMENT '组织名称',
  `description` VARCHAR(1000)       DEFAULT NULL
  COMMENT '组织描述',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------
-- upms_role upms角色表
-- ---------------------
CREATE TABLE IF NOT EXISTS upms_role (
  `id`          INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '角色编号',
  `name`        VARCHAR(20)         DEFAULT NULL
  COMMENT '角色名称',
  `description` VARCHAR(1000)       DEFAULT NULL
  COMMENT '角色描述',
  `orders`      BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '排序',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------------
-- upms_permission upms权限
-- ---------------------------
CREATE TABLE IF NOT EXISTS upms_permission (
  `id`               INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '权限编号',
  `system_id`        INT(10) UNSIGNED    DEFAULT NULL
  COMMENT '所属系统',
  `pid`              INT(10) UNSIGNED    DEFAULT NULL
  COMMENT '所属上级',
  `name`             VARCHAR(20)         DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(200) DEFAULT  NULL
  COMMENT '权限描述',
  `type`             TINYINT(4)          DEFAULT NULL
  COMMENT '类型 1.视图授权2.数据授权',
  `permission_value` VARCHAR(50)         DEFAULT NULL
  COMMENT '权限值',
  `uri`              VARCHAR(100)        DEFAULT NULL
  COMMENT '路径',
  `icon`             VARCHAR(50)         DEFAULT NULL
  COMMENT '图标',
  `status`           TINYINT(4)          DEFAULT NULL
  COMMENT '状态 0 禁止 1 正常',
  `orders`           BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '排序',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `upms_permission_system_id_index` (`system_id`) USING BTREE COMMENT 'B+树索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- -------------------------
-- upms_system upms系统表
-- -------------------------
CREATE TABLE IF NOT EXISTS upms_system (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '系统编号',
  `icon`        VARCHAR(50)               DEFAULT NULL
  COMMENT '图标',
  `banner`      VARCHAR(150)              DEFAULT NULL
  COMMENT '背景',
  `theme`       VARCHAR(50)               DEFAULT NULL
  COMMENT '主题',
  `basepath`    VARCHAR(100)              DEFAULT NULL
  COMMENT '根目录',
  `status`      TINYINT(4)                DEFAULT NULL
  COMMENT '状态 0 黑名单 1 正常',
  `name`        VARCHAR(20)               DEFAULT NULL
  COMMENT '系统名称',
  `route`       VARCHAR(20)               DEFAULT NULL
  COMMENT '系统路由',
  `title`       VARCHAR(20)               DEFAULT NULL
  COMMENT '系统标题',
  `description` VARCHAR(300)              DEFAULT NULL
  COMMENT '系统描述',
  `orders`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
# 关联关系表
-- --------------------------
-- upms_user_organization 用户_组织关联表
-- --------------------------
CREATE TABLE IF NOT EXISTS upms_user_organization (
  `id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `user_id`              INT(10) UNSIGNED                NOT NULL
  COMMENT '用户编号',
  `organization_id`      INT(10) UNSIGNED                NOT NULL
  COMMENT '组织编号',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `upms_user_organization_user_id_index` (`user_id`) USING BTREE COMMENT 'B+树索引',
  KEY `upms_user_organization_organization_id_index` (`organization_id`) USING BTREE COMMENT 'B+树索引'
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- ----------------------------
-- upms_user_role 用户_角色关联表
-- ----------------------------
CREATE TABLE IF NOT EXISTS upms_user_role (
  `id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `user_id`      INT(10) UNSIGNED                NOT NULL
  COMMENT '用户编号',
  `role_id`      INT(10) UNSIGNED                NOT NULL
  COMMENT '角色编号',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY ` upms_user_role_user_id_index` (`user_id`) USING BTREE COMMENT 'B+树索引',
  KEY ` upms_user_role_role_id_index` (`role_id`) USING BTREE COMMENT 'B+树索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# -- ----------------------------------
# -- upms_user_permission 用户_权限关联表
# -- ----------------------------------
# CREATE TABLE IF NOT EXISTS upms_user_permission (
#   `id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
#   COMMENT '编号',
#   `user_id`            INT(10) UNSIGNED                NOT NULL
#   COMMENT '用户编号',
#   `permission_id`      INT(10) UNSIGNED                NOT NULL
#   COMMENT '权限编号',
#   `type`               TINYINT(4)                      NOT NULL
#   COMMENT '权限类型 -1 减权限',
#   `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
#   COMMENT '创建时间',
#   `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
#   COMMENT '修改时间',
#   `version` INT(10) UNSIGNED DEFAULT 0
#   COMMENT '数据版本',
#   PRIMARY KEY (`id`),
#   KEY `upms_user_permission_user_id_index` (`user_id`) USING BTREE COMMENT 'B+树索引',
#   KEY `upms_user_permission_permission_id_index` (`permission_id`) USING BTREE COMMENT 'B+树索引'
# )
#   ENGINE = InnoDB
#   DEFAULT CHARSET utf8;

-- ----------------------------------
-- upms_role_permission 角色_权限关联表
-- ----------------------------------
CREATE TABLE IF NOT EXISTS upms_role_permission (
  `id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `role_id`            INT(10) UNSIGNED                NOT NULL
  COMMENT '角色编号',
  `permission_id`      INT(10) UNSIGNED                NOT NULL
  COMMENT '权限编号',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `upms_role_permission_role_id_index` (`role_id`) USING BTREE COMMENT 'B+树索引',
  KEY `upms_role_permission_permission_id_index` (`permission_id`) USING BTREE COMMENT 'B+树索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# ##########################
# 初始化数据
# ##########################

# 初始化管理员数据
INSERT INTO upms_user VALUE (1,'admin','$2a$10$Tqf5dVNDPBLwGSKrtK4zeuxMHY54qoJgqRQiLQZsLSsFmsWqY/gOa',NULL ,'micro-fast'
  ,NULL ,'18332751256','18332763133@163.com',0,0,unix_timestamp(now()),unix_timestamp(now()) ,0);

# 初始化组织数据
INSERT INTO upms_organization VALUE (1,0,'micro-fast项目部','负责micro-fast项目开源工作',unix_timestamp(now()),unix_timestamp(now()),0);

# 初始化系统数据
INSERT  INTO upms_system (id,basepath,status,name,route,description,title,orders,ctime,version) VALUES
  (1,'www.upms.com',1,'upms','/upms','权限管理系统，对后台的用户权限进行控制','权限管理系统',1,unix_timestamp(now()),1),
  (2,'www.config.com',1,'config-server','/config-server','配置中心，负责配置管理配置管理','配置管理系统',2,unix_timestamp(now()),1),
  (3,'www.zipkin.com',1,'monitor-zipkin','/monitor-zipkin','服务调用监控','服务调用监控系统',3,unix_timestamp(now()),1),
  (4,'www.turbine.com',1,'monitor-turbine','/monitor-turbine','服务熔断监控，请求监控和','请求监控系统',4,unix_timestamp(now()),1),
  (5,'www.oauth.com',1,'oauth','/oauth','认证中心','认证中心系统',5,unix_timestamp(now()),1),
  (6,'www.register1.com',1,'eureka-server','/eureka-server','注册中心','高可用注册中心',6,unix_timestamp(now()),1),
  (7,'www.ucenter.com',1,'ucenter','/ucenter','用户管理系统','用户管理系统',7,unix_timestamp(now()),1);
# 初始化权限系统数据
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (1, 2, null, '配置中心查询权限', '配置中心查询权限', 0, 'get', '/**', null, 1, 1, 1523699790457, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (2, 2, null, '配置中心修改权限', '配置中心修改权限', 0, 'put', '/**', '', 1, 2, 1523795985918, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (3, 2, null, '配置中心添加权限', '配置中心添加权限', 0, 'post', '/**', null, 1, 3, 1523796400250, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (4, 2, null, '配置中心删除权限', '配置中心删除权限', 0, 'delete', '/**', null, 1, 4, 1523798075581, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (5, 1, null, '权限系统查询权限', '后台权限系统查询权限', 0, 'get', '/**', null, 1, 5, 1523798198805, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (6, 2, null, '权限系统添加权限', '后台权限系统添加权限', 0, 'post', '/**', null, 1, 6, 1523798305795, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (8, 1, null, '权限系统修改权限', '后台权限系统修改权限', 0, 'put', '/**', null, 1, 7, 1523798580296, null, 0);
INSERT INTO upms_permission (id, system_id, pid, name, description, type, permission_value, uri, icon, status, orders, ctime, uptime, version) VALUES (9, 1, null, '权限系统删除权限', '后台权限系统删除权限', 0, 'delete', '/**', null, 1, 8, 1523798659826, null, 0);
# 初始化角色系统数据
INSERT INTO upms_role (id, name, description, orders, ctime, uptime, version) VALUES (2, '超级管理员', '超级管理员', 1, 1523687867805, null, 0);
# 初始化角色权限关系
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (6, 2, 3, 1523797807, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (7, 2, 2, 1523797807, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (8, 2, 1, 1523797807, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (9, 2, 9, 1523798684, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (10, 2, 8, 1523798684, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (11, 2, 6, 1523798684, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (12, 2, 5, 1523798684, null, 0);
INSERT INTO upms_role_permission (id, role_id, permission_id, ctime, uptime, version) VALUES (13, 2, 4, 1523798684, null, 0);
# 初始化角色用户关系
INSERT INTO upms_user_role (id, user_id, role_id, ctime, uptime, version) VALUES (2, 1, 2, 1523791458, null, 0);