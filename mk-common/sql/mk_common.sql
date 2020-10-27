/*
 Navicat Premium Data Transfer

 Source Server         : mymac
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : mk

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 27/10/2020 15:43:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_common
-- ----------------------------
DROP TABLE IF EXISTS `mk_common`;
CREATE TABLE `mk_common` (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `config_key` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '配置key',
  `config_value` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '配置值',
  `description` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of mk_common
-- ----------------------------
BEGIN;
INSERT INTO `mk_common` VALUES (1320630711165784064, '文件服务器路径', 'basePath', '/home/mk/data', '文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
