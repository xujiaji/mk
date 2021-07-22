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

 Date: 22/07/2021 22:52:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_file
-- ----------------------------
DROP TABLE IF EXISTS `mk_file`;
CREATE TABLE `mk_file` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '创建者',
  `path` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '路径',
  `file_type` int NOT NULL COMMENT '文件类型：1.图片；2.视频；3.音频；4.文本',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `state` int NOT NULL DEFAULT '0' COMMENT '状态：0可使用，1已禁用，2附件已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文件管理';

SET FOREIGN_KEY_CHECKS = 1;
