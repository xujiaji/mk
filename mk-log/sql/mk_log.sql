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

 Date: 27/10/2020 15:44:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_log
-- ----------------------------
DROP TABLE IF EXISTS `mk_log`;
CREATE TABLE `mk_log` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '创建者id',
  `content` text COLLATE utf8mb4_bin NOT NULL COMMENT '日志内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `log_type` int NOT NULL DEFAULT '0' COMMENT '日志类型：1.访问；2.操作；3.错误；',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='日志记录';

SET FOREIGN_KEY_CHECKS = 1;
