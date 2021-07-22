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

 Date: 22/07/2021 22:47:37
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
  `config_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '配置值',
  `description` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='通用配置表';

-- ----------------------------
-- Records of mk_common
-- ----------------------------
BEGIN;
INSERT INTO `mk_common` VALUES (1320630711165784040, 'api接口url', 'baseApiUrl', 'https://api.xxx.xxx', 'api接口基本url', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784050, '服务器ip', 'serverIp', '192.168.1.2', '服务器的ip地址', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784060, '文本文件路径', 'baseTextPath', '/text', '文本文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784061, '音频文件路径', 'baseAudioPath', '/audio', '音频文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784062, '视频文件路径', 'baseVideoPath', '/video', '视频文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784063, '图片文件路径', 'baseImagePath', '/image', '图片文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784064, '文件服务器路径', 'basePath', '/Users/jiajixu/IdeaProjects/diary/home/mk/data', '文件存放在服务器中的父路径', '2020-10-26 15:37:52', '2020-10-26 21:08:55');
INSERT INTO `mk_common` VALUES (1320630711165784065, 'IP城市映射数据库路径', 'ipCityDBPath', './db/ip2region.db', '通过该数据库获取用户的所在城市', '2020-11-03 14:52:54', '2020-11-03 14:52:58');
INSERT INTO `mk_common` VALUES (1320630711165784066, '微信ID', 'wxAppId', 'wxxxxx', '微信开发平台上的应用标示', '2020-11-04 13:31:04', '2020-11-04 13:31:08');
INSERT INTO `mk_common` VALUES (1320630711165784067, '微信支付密钥', 'wxPayKey', '99999999xxxxxx', '微信支付要用到的key', '2020-11-04 13:32:08', '2020-11-04 13:32:11');
INSERT INTO `mk_common` VALUES (1320630711165784068, '微信Secret', 'wxSecret', '111111111111111', '微信开发平台上对应应用的secret', '2020-11-04 13:33:16', '2020-11-04 13:33:19');
INSERT INTO `mk_common` VALUES (1320630711165784069, '微信小程序appId', 'wxMiniAppId', 'wxd66df', '微信的小程序后台中找到，用于登录等', '2020-11-04 13:54:02', '2020-11-04 13:54:08');
INSERT INTO `mk_common` VALUES (1320630711165784070, '微信小程序secret', 'wxMiniSecret', '3c1954f03873b77485aaaaaaaaaa', '微信小程序的secret，用于登录等', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784071, '短信模版-普通', 'smsTemplateNormal', 'SMS_182415', '普通短信的短信模版', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784072, '短信模版-注册', 'smsTemplateRegister', 'SMS-aaa', '注册时的短信模版', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784073, '短信模版-登录', 'smsTemplateLogin', 'SMS-aaa', '手机号登录时的短信模版', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784074, '短信模版-信息变更', 'smsTemplateModify', 'SMS-aaa', '修改个人安全信息时的短信模版', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784075, '短信模版-手机号变更', 'smsTemplateModifyPhone', 'SM-aa', '修改个人手机号时的短信模版', '2020-11-04 13:55:10', '2020-11-04 13:55:16');
INSERT INTO `mk_common` VALUES (1320630711165784076, '阿里云短信key', 'aliSmsKey', 'LTntiy1WVtvP4aZ', '阿里云短信key', '2020-11-04 15:13:01', '2020-11-04 15:13:08');
INSERT INTO `mk_common` VALUES (1320630711165784077, '阿里云短信secret', 'aliSmsSecret', 'CjBmTz5rxxetWbZ0yd', '阿里云短信secret', '2020-11-04 15:13:48', '2020-11-04 15:13:52');
INSERT INTO `mk_common` VALUES (1320630711165784078, '阿里云短信sign name', 'aliSmsSignName', 'xxxx', '阿里云短信签名的名字（应用名）', '2020-11-04 15:13:48', '2020-11-04 15:13:52');
INSERT INTO `mk_common` VALUES (1320630711165784079, '文件域名链接', 'baseFileUrl', 'http://file.xxxx.com', '文件域名链接', '2020-11-09 17:01:52', '2020-11-09 17:01:56');
INSERT INTO `mk_common` VALUES (1320630711165784080, 'QQ app id', 'qqAppId', '10198', 'QQ认证分享等的app id', '2020-11-09 17:41:23', '2020-11-09 17:41:27');
INSERT INTO `mk_common` VALUES (1320630711165784081, 'QQ APP Key', 'qqSecret', '21f19586428353b082', 'qq app key', '2020-11-09 17:42:17', '2020-11-09 17:42:21');
INSERT INTO `mk_common` VALUES (1320630711165784082, 'iOS包名', 'iOSBundleId', 'com.dia.ppi', 'iOS包名', '2020-11-16 17:33:51', '2020-11-16 17:33:55');
INSERT INTO `mk_common` VALUES (1320630711165784083, 'android包名', 'androidBundleId', 'com.dia.ppi', 'android包名', '2020-11-16 17:33:51', '2020-11-16 17:33:55');
INSERT INTO `mk_common` VALUES (1320630711165784084, '253短信账号', 'sms253Account', 'N7400', '253短信平台账号', '2020-11-25 10:58:02', '2020-11-25 10:58:05');
INSERT INTO `mk_common` VALUES (1320630711165784085, '253短信密码', 'sms253Password', 'SD4512', '253短信平台配置密码', '2020-11-25 11:01:14', '2020-11-25 11:01:18');
INSERT INTO `mk_common` VALUES (1320630711165784086, '253短信发送请求地址', 'sms253SendUrl', 'http://smssh1.253.com', '253短信平台给予的用于发送短信的请求', '2020-11-25 11:02:29', '2020-11-25 11:02:33');
INSERT INTO `mk_common` VALUES (1320630711165784087, '微信商户号', 'wxMerchant', '1600305257', '微信商户号', '2020-11-27 17:36:25', '2020-11-27 17:36:29');
INSERT INTO `mk_common` VALUES (1320630711165784100, '支付宝appid', 'aliAppId', '2021001168650656', '支付宝appid', '2020-11-27 17:36:25', '2020-11-27 17:36:29');
INSERT INTO `mk_common` VALUES (1320630711165784101, '支付宝私钥', 'aliPrivateKey', 'MIIEvCEXBe4FQKbVR+JrHK4aAUyZE7Dv7Bfc8CNXAgMBAAECggEALWLJCzkqXELeDBAb39wnJ+m9GbSyHBR1w==', '生成公钥时对应的私钥（填自己的）', '2020-11-27 17:36:25', '2020-11-27 17:36:29');
INSERT INTO `mk_common` VALUES (1320630711165784102, '支付宝公钥', 'aliPublicKey', 'MIIBIWj0cSfHVr5MxdDmfruG4KUa0Xnms3rkKxNWyAOqQW1QYP2B6i7K9UNVt8EoReWU6iyc8oczdIMFMyEztI6/Uekyu3Vi2gT1AhxZC/6IPUu/ry7Igxw/3mjFOjVa5mGregv8v0ArBfH+0otzQIDAQAB', '支付宝公钥', '2020-11-27 17:36:25', '2020-11-27 17:36:29');
INSERT INTO `mk_common` VALUES (1320630711165784103, '支付宝网关', 'aliGatewayUrl', 'https://openapi.alipay.com/gateway.do', '支付宝网关（注意沙箱alipaydev，正式则为 alipay）不需要修改', '2020-11-27 17:36:25', '2020-11-27 17:36:29');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
