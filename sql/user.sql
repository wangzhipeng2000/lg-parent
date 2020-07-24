/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.42.131
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.42.131:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 19/07/2020 18:19:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册手机号',
  `created` datetime(0) NOT NULL COMMENT '创建时间',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码加密的salt值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (28, 'zhangsan', 'e21d44f200365b57fab2641cd31226d4', '13600527634', '2018-05-25 17:52:03', '05b0f203987e49d2b72b20b95e0e57d9');
INSERT INTO `tb_user` VALUES (30, 'leyou', '4de9a93b3f95d468874a3c1bf3b25a48', '15855410440', '2018-09-30 11:37:30', '4565613d4b0e434cb496d4eb87feb45f');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'hrID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` tinyint(4) NULL DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住宅电话',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否可用：1：启用，0：停用',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` int(255) NULL DEFAULT NULL COMMENT '省',
  `city` int(255) NULL DEFAULT NULL COMMENT '城市',
  `district` int(255) NULL DEFAULT NULL COMMENT '区县',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (25, '管理员', 1, 'http://localhost:8888/img/52711ed4-cc21-458f-8494-61ee36ca050e_ChMkJ1bKyT-IVb8hAB4Y76rKwTQAALILgAM78YAHhkH536.png', '13811189999', NULL, NULL, 1, 'admin', 'admin', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (26, '张三', 2, 'http://localhost:8888/img/52711ed4-cc21-458f-8494-61ee36ca050e_ChMkJ1bKyT-IVb8hAB4Y76rKwTQAALILgAM78YAHhkH536.png', '13811189999', NULL, NULL, 1, 'zhangsan', 'zhangsan', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (27, '李四', 1, 'http://localhost:8888/img/52711ed4-cc21-458f-8494-61ee36ca050e_ChMkJ1bKyT-IVb8hAB4Y76rKwTQAALILgAM78YAHhkH536.png', '18618109887', NULL, NULL, 1, 'lisi', '123456', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
