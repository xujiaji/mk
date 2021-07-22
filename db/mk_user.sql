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

 Date: 22/07/2021 22:53:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_user
-- ----------------------------
DROP TABLE IF EXISTS `mk_user`;
CREATE TABLE `mk_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `no` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'id编号',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` bigint DEFAULT NULL COMMENT '头像文件id',
  `sex` int NOT NULL DEFAULT '1' COMMENT '1男 2女',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '简介个性签名',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `qq_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'qq id 标识',
  `wx_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '微信id标识',
  `wx_mini_open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '微信小程序openId',
  `ios_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'iOS id标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no` (`no`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

-- ----------------------------
-- Table structure for mk_user_id_number
-- ----------------------------
DROP TABLE IF EXISTS `mk_user_id_number`;
CREATE TABLE `mk_user_id_number` (
  `id` bigint NOT NULL COMMENT 'id number编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `state` int NOT NULL DEFAULT '0' COMMENT '0未使用，1已使用',
  `good` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '靓号规则（如果是靓号的情况下）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户id编号列表';

-- ----------------------------
-- Table structure for mk_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `mk_user_login_log`;
CREATE TABLE `mk_user_login_log` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `login_ip` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录ip',
  `login_type` int NOT NULL COMMENT '1.QQ登录；2.微信登录；3.手机密码登录；4.手机验证码登录；5.邮箱登录；6.iOS登录；7.用户名密码登录；8.微信小程序；9.刷新登录',
  `login_location` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '登录地点',
  `login_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录设备',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  `update_time` datetime NOT NULL COMMENT '修改日志时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='认证用户日志';

SET FOREIGN_KEY_CHECKS = 1;
