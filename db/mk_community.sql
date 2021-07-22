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

 Date: 22/07/2021 22:51:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mk_community_article
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_article`;
CREATE TABLE `mk_community_article` (
  `id` bigint NOT NULL COMMENT '帖子表id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `category_id` bigint NOT NULL COMMENT '分类id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `state` int NOT NULL DEFAULT '1' COMMENT '状态 0 待审核 1 审核成功 2审核未通过 3屏蔽',
  `priority` bigint NOT NULL DEFAULT '0' COMMENT '优先级排序',
  `collect_num` bigint NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `praise_num` bigint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `display` int NOT NULL DEFAULT '1' COMMENT '1 显示 0隐藏',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—帖子表';

-- ----------------------------
-- Table structure for mk_community_article_file
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_article_file`;
CREATE TABLE `mk_community_article_file` (
  `id` bigint NOT NULL COMMENT '主键',
  `article_id` bigint NOT NULL COMMENT '帖子id',
  `file_id` bigint NOT NULL COMMENT '文件id',
  `type` int NOT NULL COMMENT '类型：0图片缩略图，1原图片，2视频缩略图，3视频',
  `display_num` bigint NOT NULL DEFAULT '0' COMMENT '展示次数',
  `order_num` int NOT NULL DEFAULT '0' COMMENT '排序编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文件使用';

-- ----------------------------
-- Table structure for mk_community_category
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_category`;
CREATE TABLE `mk_community_category` (
  `id` bigint NOT NULL COMMENT '分类主键',
  `display` int NOT NULL DEFAULT '1' COMMENT '1 显示 0 隐藏',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '分类介绍',
  `thumb` bigint NOT NULL DEFAULT '0' COMMENT '封面',
  `priority` int NOT NULL DEFAULT '0' COMMENT '优先级排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—分类表';

-- ----------------------------
-- Table structure for mk_community_collect
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_collect`;
CREATE TABLE `mk_community_collect` (
  `id` bigint NOT NULL COMMENT '主键',
  `collected_id` bigint NOT NULL COMMENT '帖子id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `type` int NOT NULL DEFAULT '0' COMMENT '0收藏帖子 1收藏留言',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—收藏帖子表';

-- ----------------------------
-- Table structure for mk_community_comment
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_comment`;
CREATE TABLE `mk_community_comment` (
  `id` bigint NOT NULL COMMENT '主键',
  `root_id` bigint NOT NULL COMMENT '顶级id',
  `parent_id` bigint DEFAULT NULL COMMENT '父级评论id',
  `reply_id` bigint DEFAULT NULL COMMENT '回复的评论id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '评论内容',
  `praise_num` bigint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—留言表';

-- ----------------------------
-- Table structure for mk_community_follow
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_follow`;
CREATE TABLE `mk_community_follow` (
  `id` bigint NOT NULL COMMENT '用户关注表主键',
  `followed_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '关注的用户id  或者是关注的帖子id',
  `user_id` bigint NOT NULL COMMENT '用户',
  `type` int NOT NULL DEFAULT '0' COMMENT '0 关注的用户 1关注的话题',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—关注表';

-- ----------------------------
-- Table structure for mk_community_notice
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_notice`;
CREATE TABLE `mk_community_notice` (
  `id` bigint NOT NULL COMMENT '主键',
  `from_user_id` bigint NOT NULL COMMENT '来自哪个用户',
  `to_user_id` bigint NOT NULL COMMENT '到达哪个用户',
  `article_id` bigint DEFAULT NULL COMMENT '帖子id',
  `comment_id` bigint DEFAULT NULL COMMENT '评论id',
  `state` int NOT NULL DEFAULT '0' COMMENT '0未读，1已读',
  `type` int NOT NULL COMMENT '0帖子评论，1评论回复，2帖子点赞，3回复点赞，4帖子收藏，5评论收藏',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='社区动态-消息通知';

-- ----------------------------
-- Table structure for mk_community_praise
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_praise`;
CREATE TABLE `mk_community_praise` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `praised_id` bigint NOT NULL COMMENT '被点赞的id',
  `type` int NOT NULL COMMENT '0点赞帖子 1点赞评论',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—点赞';

-- ----------------------------
-- Table structure for mk_community_topic
-- ----------------------------
DROP TABLE IF EXISTS `mk_community_topic`;
CREATE TABLE `mk_community_topic` (
  `id` bigint NOT NULL COMMENT '话题主键',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级id',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `image` bigint NOT NULL DEFAULT '0' COMMENT '话题图片',
  `thumb` bigint NOT NULL DEFAULT '0' COMMENT '略缩图',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '话题描述',
  `priority` int NOT NULL DEFAULT '0' COMMENT '优先级排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='社区动态—话题表';

SET FOREIGN_KEY_CHECKS = 1;
