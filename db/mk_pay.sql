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

 Date: 22/07/2021 22:52:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_pay
-- ----------------------------
DROP TABLE IF EXISTS `mk_pay`;
CREATE TABLE `mk_pay` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `money` bigint NOT NULL COMMENT '多少钱',
  `type` int NOT NULL COMMENT '支付类型1 QQ，2微信，3支付宝，4iOS',
  `state` int NOT NULL DEFAULT '1' COMMENT '支付状态0未支付，1已支付，2已退款',
  `trade_type` int NOT NULL COMMENT '交易类型1app，2h5，3快应用，4微信小程序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='支付';

SET FOREIGN_KEY_CHECKS = 1;
