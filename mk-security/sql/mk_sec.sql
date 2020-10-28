/*
 Navicat Premium Data Transfer

 Source Server         : mymac
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : mk

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 28/10/2020 23:24:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_permission`;
CREATE TABLE `mk_sec_permission` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '权限名',
  `url` varchar(1000) DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `type` int NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限表达式',
  `method` varchar(50) DEFAULT NULL COMMENT '后端接口访问方式',
  `sort` int NOT NULL COMMENT '排序',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of mk_sec_permission
-- ----------------------------
BEGIN;
INSERT INTO `mk_sec_permission` VALUES (1072806379288399872, '测试页面', '/test', 1, 'page:test', NULL, 1, 0, '2020-10-23 16:36:19', '2020-10-23 16:36:23');
INSERT INTO `mk_sec_permission` VALUES (1072806379313565696, '测试页面-查询', '/**/test', 2, 'btn:test:query', 'GET', 1, 1072806379288399872, '2020-10-23 16:36:19', '2020-10-23 16:36:19');
INSERT INTO `mk_sec_permission` VALUES (1072806379330342912, '测试页面-添加', '/**/test', 2, 'btn:test:insert', 'POST', 2, 1072806379288399872, '2020-10-23 16:36:19', '2020-10-23 16:36:19');
INSERT INTO `mk_sec_permission` VALUES (1072806379342925824, '监控在线用户页面', '/monitor', 1, 'page:monitor:online', NULL, 2, 0, '2020-10-23 16:36:19', '2020-10-23 16:36:19');
INSERT INTO `mk_sec_permission` VALUES (1072806379363897344, '在线用户页面-查询', '/**/api/monitor/online/user', 2, 'btn:monitor:online:query', 'GET', 1, 1072806379342925824, '2020-10-23 16:36:19', '2020-10-23 16:36:19');
INSERT INTO `mk_sec_permission` VALUES (1072806379384868864, '在线用户页面-踢出', '/**/api/monitor/online/user/kickout', 2, 'btn:monitor:online:kickout', 'DELETE', 2, 1072806379342925824, '2020-10-23 16:36:19', '2020-10-23 16:36:19');
COMMIT;

-- ----------------------------
-- Table structure for mk_sec_role
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_role`;
CREATE TABLE `mk_sec_role` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of mk_sec_role
-- ----------------------------
BEGIN;
INSERT INTO `mk_sec_role` VALUES (1072806379208708096, '管理员', '超级管理员', '2020-10-23 17:09:58', '2020-10-23 17:09:58');
INSERT INTO `mk_sec_role` VALUES (1072806379238068224, '普通用户', '普通用户', '2020-10-23 17:09:58', '2020-10-23 17:09:58');
INSERT INTO `mk_sec_role` VALUES (1321369049086693376, '123', 'dsa', '2020-10-28 16:31:46', '2020-10-28 16:32:11');
COMMIT;

-- ----------------------------
-- Table structure for mk_sec_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_role_permission`;
CREATE TABLE `mk_sec_role_permission` (
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `permission_id` bigint NOT NULL COMMENT '权限主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of mk_sec_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379288399872, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379313565696, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379330342912, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379342925824, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379363897344, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379208708096, 1072806379384868864, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379238068224, 1072806379288399872, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1072806379238068224, 1072806379313565696, '2020-10-23 16:37:35');
INSERT INTO `mk_sec_role_permission` VALUES (1321369049086693376, 1072806379288399872, '2020-10-28 17:20:23');
INSERT INTO `mk_sec_role_permission` VALUES (1321369049086693376, 1072806379384868864, '2020-10-28 17:20:23');
COMMIT;

-- ----------------------------
-- Table structure for mk_sec_user
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_user`;
CREATE TABLE `mk_sec_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态，启用-1，禁用-0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全用户表';

-- ----------------------------
-- Records of mk_sec_user
-- ----------------------------
BEGIN;
INSERT INTO `mk_sec_user` VALUES (1072806377661009920, 0, 1, '2020-10-23 17:08:47', '2020-10-23 17:08:47');
INSERT INTO `mk_sec_user` VALUES (1072806378780889088, 1, 1, '2020-10-23 17:08:47', '2020-10-23 17:08:47');
COMMIT;

-- ----------------------------
-- Table structure for mk_sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_user_role`;
CREATE TABLE `mk_sec_user_role` (
  `sec_user_id` bigint NOT NULL COMMENT '用户主键',
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`sec_user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of mk_sec_user_role
-- ----------------------------
BEGIN;
INSERT INTO `mk_sec_user_role` VALUES (1072806377661009920, 1072806379208708096, '2020-10-23 16:38:41');
INSERT INTO `mk_sec_user_role` VALUES (1072806378780889088, 1072806379238068224, '2020-10-23 16:38:43');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
