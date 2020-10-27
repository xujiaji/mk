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

 Date: 27/10/2020 15:44:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_auth_user_log
-- ----------------------------
DROP TABLE IF EXISTS `mk_auth_user_log`;
CREATE TABLE `mk_auth_user_log` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录ip',
  `login_type` int NOT NULL COMMENT '1.QQ登录；2.微信登录；3.手机密码登录；4.手机验证码登录；5.邮箱登录；6.iOS登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='认证用户日志';

SET FOREIGN_KEY_CHECKS = 1;
