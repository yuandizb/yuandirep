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

-- cms内容管理系统

-- ----------------------
-- cms_system cms系统管理
-- ----------------------
DROP TABLE IF EXISTS cms_system;
CREATE TABLE `cms_system` (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `alias`       VARCHAR(20)               DEFAULT NULL
  COMMENT '系统名称',
  `code`        VARCHAR(20)               DEFAULT NULL
  COMMENT '别名',
  `description` VARCHAR(300)              DEFAULT NULL
  COMMENT '描述',
  `ctime`       BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  `orders`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------------
-- cms_system cms网站配置
-- ---------------------------
DROP TABLE IF EXISTS cms_setting;
CREATE TABLE cms_setting (
  `id`            INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `setting_key`   VARCHAR(10)               DEFAULT NULL
  COMMENT '键',
  `setting_value` VARCHAR(500)              DEFAULT NULL
  COMMENT '值',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------------
-- cms_category cms类目表
-- ---------------------------
DROP TABLE IF EXISTS cms_category;
CREATE TABLE cms_category (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `pid`         INT(10) UNSIGNED          DEFAULT 0
  COMMENT '上级编号 0表示的是最顶级',
  `level`       TINYINT(4)                DEFAULT NULL
  COMMENT '层级',
  `name`        VARCHAR(20)               DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(200)              DEFAULT NULL
  COMMENT '描述',
  `icon`        VARCHAR(50)               DEFAULT NULL
  COMMENT '图标',
  `type`        TINYINT(3)                DEFAULT NULL
  COMMENT '类型 1 普通 2 热门',
  `alias`       VARCHAR(20)               DEFAULT NULL
  COMMENT '别名',
  `system_id`   INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '所属系统',
  `ctime`       BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  `orders`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `cms_category_pid_index` (`pid`) USING BTREE COMMENT '父亲分类索引',
  KEY `cms_category_level_index` (`level`) USING BTREE COMMENT '类目层级索引',
  KEY `cms_category_alias_index`(`alias`) USING BTREE COMMENT '类目别名索引',
  KEY `cms_category_orders_index` (`orders`) USING BTREE COMMENT '类目排序索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------------------
-- cms_tag cms标签表
-- ---------------------------------
DROP TABLE IF EXISTS cms_tag;
CREATE TABLE cms_tag (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `name`        VARCHAR(20)               DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(200)              DEFAULT NULL
  COMMENT '描述',
  `icon`        VARCHAR(50)               DEFAULT NULL
  COMMENT '图标',
  `type`        TINYINT(4)                DEFAULT NULL
  COMMENT '类型',
  `alias`       VARCHAR(20)               DEFAULT NULL
  COMMENT '别名',
  `system_id`   INT(10)                   DEFAULT NULL
  COMMENT '系统id',
  `ctime`       BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  `orders`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `cms_tag_alias_index` (`alias`) USING BTREE COMMENT '标签别名索引',
  KEY `cms_tag_orders_index` (`orders`) USING BTREE COMMENT '标签排序索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------
-- cms_comment cms评论表
-- -------------------------
DROP TABLE IF EXISTS cms_comment;
CREATE TABLE cms_comment (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `pid`        INT(10) UNSIGNED          DEFAULT 0
  COMMENT '回复楼中楼编号',
  `article_id` INT(10)                   DEFAULT NULL
  COMMENT '文章编号',
  `user_id`    INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '用户编号',
  `content`    TEXT                      DEFAULT NULL
  COMMENT '评论内容',
  `status`     TINYINT(4)                DEFAULT NULL
  COMMENT '状态 -1 不通过 0 未审核 1 通过',
  `ip`         VARCHAR(30)               DEFAULT NULL
  COMMENT '评论人的ip',
  `agent`      VARCHAR(200)              DEFAULT NULL
  COMMENT '评论人的终端信息',
  `syatem_id`  INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '所属系统',
  `ctime`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `cms_comment_pid_index` (`pid`) USING BTREE COMMENT '回复楼中楼编号索引',
  KEY `cms_comment_article_id_index` (`article_id`) USING BTREE COMMENT '文章编号索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------------
-- cms_topic 专题
-- ---------------------------
DROP TABLE IF EXISTS cms_topic;
CREATE TABLE cms_topic (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `title`       VARCHAR(100)              DEFAULT NULL
  COMMENT '标题',
  `description` VARCHAR(300)              DEFAULT NULL
  COMMENT '描述',
  `url`         VARCHAR(100)              DEFAULT NULL
  COMMENT '链接',
  `ctime`       BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ---------------------
-- cms_article 文章表
-- ---------------------
DROP TABLE IF EXISTS cms_article;
CREATE TABLE cms_article (
  `id`            INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `topic_id`      INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '所属专题',
  `title`         VARCHAR(200)              DEFAULT NULL
  COMMENT '文章标题',
  `author`        VARCHAR(50)               DEFAULT NULL
  COMMENT '文章原作者',
  `fromurl`       VARCHAR(300)              DEFAULT NULL
  COMMENT '文章来源网址',
  `image`         VARCHAR(300)              DEFAULT NULL
  COMMENT '封面图',
  `keywords`      VARCHAR(100)              DEFAULT NULL
  COMMENT '关键字',
  `description`   VARCHAR(500)              DEFAULT NULL
  COMMENT '简介',
  `type`          TINYINT(4)                DEFAULT NULL
  COMMENT '类型 1 普通 2 热门',
  `allowcomments` TINYINT(4)                DEFAULT NULL
  COMMENT '是否允许评论 0 不允许 1允许',
  `status`        TINYINT(4)                DEFAULT NULL
  COMMENT '状态 -1 不通过 0 未审核 1通过',
  `content`       MEDIUMTEXT                DEFAULT NULL
  COMMENT '内容',
  `user_id`       INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '发布人id',
  `readnumber`    INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '阅读数量',
  `top`           INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '置顶等级',
  `system_id`     INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '所属系统',
  `ctime`         BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  `orders`        BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `cms_article_topic_id_index` (`topic_id`) USING BTREE COMMENT '所属话题索引',
  KEY `cms_article_user_id_index` (`user_id`) USING BTREE COMMENT '所属用户索引',
  KEY `cms_article_system_id_index` (`system_id`) USING BTREE COMMENT '所属系统索引',
  KEY `cms_article_orders_index` (`orders`) USING BTREE COMMENT '排序索引'

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------
-- cms_menu 菜单
-- ----------------------
DROP TABLE IF EXISTS cms_menu;
CREATE TABLE cms_menu (
  `id`     INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `pid`    INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '父菜单',
  `name`   VARCHAR(20)               DEFAULT NULL
  COMMENT '名称',
  `url`    VARCHAR(100)              DEFAULT NULL
  COMMENT '链接',
  `target` VARCHAR(10)               DEFAULT NULL
  COMMENT '打开方式',
  `orders` BIGINT(20)                DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `cms_menu_orders` (`orders`) USING BTREE COMMENT '排序索引',
  KEY `cms_menu_pid` (`pid`) USING BTREE COMMENT '父类索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------
-- cms_page 页面
-- --------------------------
DROP TABLE IF EXISTS cms_page;
CREATE TABLE cms_page (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `pid`         INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '父页面',
  `title`       VARCHAR(20)               DEFAULT NULL
  COMMENT '标题',
  `alias`       VARCHAR(20)               DEFAULT NULL
  COMMENT '别名',
  `content`     MEDIUMTEXT                DEFAULT NULL
  COMMENT '页面内容',
  `keywords`    VARCHAR(100)              DEFAULT NULL
  COMMENT '关键字',
  `description` VARCHAR(300)              DEFAULT NULL
  COMMENT '描述',
  `ctime`       BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '创建时间',
  `orders`      BIGINT(20) UNSIGNED       DEFAULT NULL
  COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `cms_menu_pid` (`pid`) USING BTREE COMMENT '父类索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- -----------------------------
-- cms_category_tag 类目标签关联表
-- -----------------------------
DROP TABLE IF EXISTS cms_category_tag;
CREATE TABLE cms_category_tag (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `category_id` INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '类目编号',
  `tag_id`      INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '标签编号',
  PRIMARY KEY (`id`),
  KEY `cms_category_tag_category_id_index` (`category_id`) USING BTREE COMMENT '类目编号索引',
  KEY `cms_category_tag_tag_id_index` (`tag_id`) USING BTREE COMMENT '标签编号索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- -----------------------------------
-- cms_article_category 文章类目关联表
-- -----------------------------------
DROP TABLE IF EXISTS cms_article_category;
CREATE TABLE cms_article_category (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `article_id`  INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '文章编号',
  `category_id` INT(10) UNSIGNED          DEFAULT NULL
  COMMENT '类目编号',
  PRIMARY KEY (`id`),
  KEY `cms_article_category_article_id_index` (`article_id`) USING BTREE COMMENT '文章编号索引',
  KEY `cms_article_category_category_id_index` (`category_id`) USING BTREE COMMENT '标签编号索引'
)
   ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------------
-- cms_article_category 文章标签关联表
-- ----------------------------------
DROP TABLE IF EXISTS cms_article_tag;
CREATE TABLE cms_article_tag (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '编号',
  `article_id` INT(10) UNSIGNED DEFAULT NULL  COMMENT '文章编号',
  `tag_id` INT(10) UNSIGNED DEFAULT NULL COMMENT '标签编号',
  PRIMARY KEY (`id`),
  KEY `cms_article_tag_article_id_index` (`article_id`) USING BTREE COMMENT '文章编号索引',
  KEY `cms_article_tag_tag_id_index` (`tag_id`) USING BTREE COMMENT '标签编号索引'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;