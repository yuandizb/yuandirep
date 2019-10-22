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

-- -------------------
-- 创建micro数据库
-- -------------------
CREATE DATABASE IF NOT EXISTS micro
  CHARACTER SET utf8;
USE micro;
-- upms用户权限管理系统

-- -------------------------
-- upms_log upms操作日志
-- -------------------------
DROP TABLE IF EXISTS upms_log;
CREATE TABLE `upms_log` (
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
DROP TABLE IF EXISTS upms_user;
CREATE TABLE upms_user (
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
DROP TABLE IF EXISTS upms_organization;
CREATE TABLE upms_organization (
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
DROP TABLE IF EXISTS upms_role;
CREATE TABLE upms_role (
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
DROP TABLE IF EXISTS upms_permission;
CREATE TABLE upms_permission (
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
DROP TABLE IF EXISTS upms_system;
CREATE TABLE upms_system (
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
DROP TABLE IF EXISTS upms_user_organization;
CREATE TABLE upms_user_organization (
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
DROP TABLE IF EXISTS upms_user_role;
CREATE TABLE upms_user_role (
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
# DROP TABLE IF EXISTS upms_user_permission;
# DROP TABLE IF EXISTS upms_user_permissioon;
# CREATE TABLE upms_user_permission (
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
DROP TABLE IF EXISTS upms_role_permission;
CREATE TABLE upms_role_permission (
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
