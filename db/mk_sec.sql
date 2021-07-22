/*
 Navicat Premium Data Transfer

 Source Server         : mymac
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : mk

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/07/2021 22:52:45
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
  `path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `component` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '前端所需元件',
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `type` int NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限表达式',
  `method` varchar(50) DEFAULT NULL COMMENT '后端接口访问方式',
  `sort` int NOT NULL COMMENT '排序',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

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
-- Table structure for mk_sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mk_sec_user_role`;
CREATE TABLE `mk_sec_user_role` (
  `sec_user_id` bigint NOT NULL COMMENT '用户主键',
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`sec_user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

SET FOREIGN_KEY_CHECKS = 1;
