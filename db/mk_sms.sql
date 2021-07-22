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

 Date: 22/07/2021 22:52:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_sms
-- ----------------------------
DROP TABLE IF EXISTS `mk_sms`;
CREATE TABLE `mk_sms` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `code` int NOT NULL COMMENT '验证码',
  `type` int NOT NULL DEFAULT '1' COMMENT '1普通短信，2注册，3登录，4信息变更，5修改绑定手机号',
  `create_time` datetime NOT NULL COMMENT '发送时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mobile，type` (`mobile`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1324181647012270081 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='短信验证码表';

SET FOREIGN_KEY_CHECKS = 1;
