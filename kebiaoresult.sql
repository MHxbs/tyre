/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : tyre

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 21/09/2018 15:07:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kebiaoresult
-- ----------------------------
DROP TABLE IF EXISTS `kebiaoresult`;
CREATE TABLE `kebiaoresult`  (
  `status` int(255) NOT NULL,
  `success` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `version` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `stuNum` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nowWeek` int(255) NOT NULL,
  PRIMARY KEY (`stuNum`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
