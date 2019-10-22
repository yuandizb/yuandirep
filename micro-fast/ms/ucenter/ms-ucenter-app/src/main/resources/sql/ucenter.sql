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
# 外键检查，不允许有外键
SET FOREIGN_KEY_CHECKS = 0;
-- ucenter用户中心
-- ------------------------------------
-- ucenter_user_details ucenter用户详情表
-- ------------------------------------
CREATE TABLE IF NOT EXISTS ucenter_user_details (
  `id`        INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `signature` VARCHAR(300) DEFAULT NULL
  COMMENT '个性签名',
  `real_name` VARCHAR(20)  DEFAULT NULL
  COMMENT '真实姓名',
  `birthday`  DATETIME   DEFAULT NULL
  COMMENT '出生日期',
  `question`  VARCHAR(100) DEFAULT NULL
  COMMENT '账号安全问题',
  `answer`    VARCHAR(100) DEFAULT NULL
  COMMENT '账号安全答案',
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
-- ------------------------------------
-- ucenter_user ucenter用户表
-- ------------------------------------
CREATE TABLE IF NOT EXISTS ucenter_user (
  `id`              INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `password`        VARCHAR(200)   NOT NULL
  COMMENT '密码(MD5(密码+salt))',
  `salt`            VARCHAR(32)  DEFAULT NULL
  COMMENT '盐值',
  `nickname`        VARCHAR(32)  DEFAULT NULL
  COMMENT '昵称',
  `sex`             TINYINT(4)   DEFAULT NULL
  COMMENT '性别 0未知 1男 2女',
  `avatar`          VARCHAR(100) DEFAULT NULL
  COMMENT '头像',
  `create_ip`       VARCHAR(50)  DEFAULT NULL
  COMMENT '注册ip',
  `last_login_time` DATETIME    DEFAULT NULL
  COMMENT '最后登陆时间',
  `last_login_ip`   VARCHAR(50)  DEFAULT NULL
  COMMENT '最后登录的ip',
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
-- -----------------------------
-- ucenter_user_log 用户操作日志表
-- -----------------------------
CREATE TABLE IF NOT EXISTS ucenter_user_log (
  `id`          INT(10) UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `user_id`     INT(10)   UNSIGNED     DEFAULT NULL
  COMMENT '用户编号',
  `content`     VARBINARY(100) DEFAULT NULL
  COMMENT '内容',
  `ip`          VARCHAR(20)    DEFAULT NULL
  COMMENT '操作ip',
  `agent`       VARBINARY(200) DEFAULT NULL
  COMMENT '操作环境',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `ucenter_user_log_user_id_index` (`user_id`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ------------------------------
-- ucenter_user_oauth 用户认证方式表
-- -------------------------------
CREATE TABLE IF NOT EXISTS ucenter_user_oauth (
  `id`          INT(10)  UNSIGNED AUTO_INCREMENT NOT NULL
  COMMENT '编号',
  `user_id`     INT(10)    UNSIGNED   DEFAULT NULL
  COMMENT '用户编号',
  `oauth_id`    INT(10)     UNSIGNED  DEFAULT NULL
  COMMENT '认证方式编号',
  `open_id`     VARBINARY(50) DEFAULT NULL
  COMMENT '第三方id',
  `status`      TINYINT(4)    DEFAULT NULL
  COMMENT '绑定状态 0绑定 1解绑',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`),
  KEY `ucenter_user_oauth_user_id_index` (`user_id`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- ------------------------
-- ucenter_oauth 认证方式表
-- ------------------------
CREATE TABLE IF NOT EXISTS ucenter_oauth(
  `id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '编号',
  `name` VARCHAR(20) NOT NULL COMMENT '认证方式名称',
  `ctime`   BIGINT(20)  UNSIGNED DEFAULT NULL
  COMMENT '创建时间',
  `uptime`  BIGINT(20) UNSIGNED DEFAULT NULL
  COMMENT '修改时间',
  `version` INT(10) UNSIGNED DEFAULT 0
  COMMENT '数据版本',
  PRIMARY KEY (`id`)
)ENGINE =InnoDB DEFAULT CHARSET =utf8;
