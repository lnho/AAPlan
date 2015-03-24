/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : aaplan

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2015-03-24 10:29:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(255) DEFAULT NULL,
  `user_id` int(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `money` float(255,2) DEFAULT NULL,
  `addtime` bigint(13) DEFAULT NULL,
  `left_money` float(255,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=620 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('38', '2', '1', '现金', '100.00', '1405918800000', '100.00');
INSERT INTO `log` VALUES ('39', '2', '2', '现金', '60.00', '1405918800000', '60.00');
INSERT INTO `log` VALUES ('40', '1', '1', '', '13.00', '1405918800000', '87.00');
INSERT INTO `log` VALUES ('41', '1', '1', '', '13.00', '1406005200000', '74.00');
INSERT INTO `log` VALUES ('42', '1', '1', '', '13.00', '1406091600000', '61.00');
INSERT INTO `log` VALUES ('43', '1', '2', '', '15.00', '1405918800000', '45.00');
INSERT INTO `log` VALUES ('44', '1', '2', '', '15.00', '1406005200000', '30.00');
INSERT INTO `log` VALUES ('45', '1', '2', '', '12.00', '1406091600000', '18.00');
INSERT INTO `log` VALUES ('46', '2', '3', '现金', '50.00', '1406263083490', '50.00');
INSERT INTO `log` VALUES ('47', '1', '3', '香酥鸡腿', '12.00', '1406263275844', '38.00');
INSERT INTO `log` VALUES ('48', '1', '2', '奥尔良烤翅', '15.00', '1406263301814', '3.00');
INSERT INTO `log` VALUES ('49', '1', '1', '梅菜扣肉', '13.00', '1406263323069', '48.00');
INSERT INTO `log` VALUES ('50', '2', '4', '支付宝', '100.00', '1406263718605', '100.00');
INSERT INTO `log` VALUES ('51', '1', '4', '鸡腿饭', '12.00', '1406263769445', '88.00');
INSERT INTO `log` VALUES ('52', '1', '1', '', '12.00', '1406521359607', '36.00');
INSERT INTO `log` VALUES ('53', '1', '4', '', '13.00', '1406521370857', '75.00');
INSERT INTO `log` VALUES ('54', '2', '2', '现金', '50.00', '1406697444000', '53.00');
INSERT INTO `log` VALUES ('55', '1', '2', '一品便当', '15.00', '1406697444000', '38.00');
INSERT INTO `log` VALUES ('56', '1', '1', '一品便当', '15.00', '1406697444000', '21.00');
INSERT INTO `log` VALUES ('57', '1', '1', '', '13.00', '1406867564625', '8.00');
INSERT INTO `log` VALUES ('58', '1', '2', '', '12.00', '1406867572882', '26.00');
INSERT INTO `log` VALUES ('59', '1', '4', '', '12.00', '1406867586004', '63.00');
INSERT INTO `log` VALUES ('60', '1', '3', '', '13.00', '1406867597340', '25.00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `money` float(255,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '郑小妹', '13.00');
INSERT INTO `user` VALUES ('2', '陈晓', '12.00');
INSERT INTO `user` VALUES ('3', '林志玲', '13.00');
INSERT INTO `user` VALUES ('4', '王小明', '12.00');
