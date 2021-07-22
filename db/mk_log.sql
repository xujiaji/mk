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

 Date: 22/07/2021 22:52:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_log
-- ----------------------------
DROP TABLE IF EXISTS `mk_log`;
CREATE TABLE `mk_log` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '日志内容',
  `type` int NOT NULL DEFAULT '0' COMMENT '日志类型',
  `ua` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'user-agent',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'ip地址',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '请求方式',
  `header` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '头部信息',
  `param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '参数',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '路径',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '位置',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='日志记录';

SET FOREIGN_KEY_CHECKS = 1;
