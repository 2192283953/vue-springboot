/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.62.131
 Source Server Type    : MySQL
 Source Server Version : 100428
 Source Host           : 192.168.62.131:3306
 Source Schema         : wms

 Target Server Type    : MySQL
 Target Server Version : 100428
 File Encoding         : 65001

 Date: 15/05/2023 22:23:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (2, '数学', '太难了');
INSERT INTO `course` VALUES (3, '英语', '确实太南了\n');
INSERT INTO `course` VALUES (5, '俄语', '以后很重要');
INSERT INTO `course` VALUES (6, '计算机网络', '网络方面课程');
INSERT INTO `course` VALUES (7, 'c++', '编程');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bus_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL,
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '管理员管理', '1', NULL, 'Admin', '0', 'admin/AdminManage.vue', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (2, '002', '用户管理', '1', NULL, 'User', '0,1', 'user/UserManage.vue', 'el-icon-user-solid');
INSERT INTO `menu` VALUES (3, '003', '课程管理', '1', NULL, 'Course', '0,1', 'course/CourseManage', 'el-icon-office-building');
INSERT INTO `menu` VALUES (4, '004', '作业分类管理', '1', NULL, 'Workstype', '0,1', 'workstype/WorkstypeManage', 'el-icon-notebook-2');
INSERT INTO `menu` VALUES (5, '005', '作业管理', '1', NULL, 'Works', '0,1,2', 'works/WorksManage', 'el-icon-s-platform');
INSERT INTO `menu` VALUES (6, '006', '记录管理', '1', NULL, 'Record', '0,1,2', 'record/RecordManage', 'el-icon-s-order');
INSERT INTO `menu` VALUES (7, '007', '公告管理', '1', NULL, 'Notice', '0,1,2', 'notice/index', 'el-icon-date');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (2, '1', '11111111111111111111', '2023-05-09 11:53:58', NULL);
INSERT INTO `notice` VALUES (3, '2', '2222222', '2023-05-09 11:54:02', NULL);

-- ----------------------------
-- Table structure for notice_user
-- ----------------------------
DROP TABLE IF EXISTS `notice_user`;
CREATE TABLE `notice_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nid` int NULL DEFAULT NULL,
  `uid` int NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态： 0 未读，1已读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice_user
-- ----------------------------
INSERT INTO `notice_user` VALUES (1, 3, 9, '1');
INSERT INTO `notice_user` VALUES (2, 2, 9, '1');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `works` int NOT NULL COMMENT '作业id',
  `userId` int NULL DEFAULT NULL COMMENT '提交者/撤销者',
  `admin_id` int NULL DEFAULT NULL COMMENT '操作人同意id',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `createtime` timestamp(0) NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `score` int NULL DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 16, 15, 16, 2, '2023-04-10 16:36:57', NULL, NULL, NULL);
INSERT INTO `record` VALUES (2, 17, 15, 16, 1, '2023-04-03 22:17:48', NULL, NULL, NULL);
INSERT INTO `record` VALUES (3, 16, 16, 1, 2, '2023-04-20 22:35:54', '', NULL, NULL);
INSERT INTO `record` VALUES (4, 17, 16, 1, 2, '2023-04-12 22:35:58', '', NULL, NULL);
INSERT INTO `record` VALUES (5, 16, NULL, 1, 1, '2023-04-12 22:36:05', '', NULL, NULL);
INSERT INTO `record` VALUES (6, 17, 13, 1, 1, '2023-04-18 22:36:08', '', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `age` int NULL DEFAULT NULL COMMENT '性别',
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话',
  `role_id` int NULL DEFAULT NULL COMMENT '角色 ，0超级管理员，1管理员，2普通用户',
  `idValid` varchar(4) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'Y' COMMENT '是否有效，Y有效其他无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '5656', '超级管理员', '5656', 18, 1, '14552454525', 0, 'Y');
INSERT INTO `user` VALUES (2, 'admin', '管理员', '1234', 19, 1, '455', 1, 'Y');
INSERT INTO `user` VALUES (4, 'asdkl', 'xu', '456465', 65, 0, '565', 0, 'Y');
INSERT INTO `user` VALUES (5, '1231231', 'sdas', '4564564', 12, 0, '455646546', NULL, 'Y');
INSERT INTO `user` VALUES (6, 'sdasd', 'dads', '45654', 12, 1, '464564', NULL, 'Y');
INSERT INTO `user` VALUES (7, '1', 'sda', '1', 100, 1, '456456456', 1, 'Y');
INSERT INTO `user` VALUES (8, '211', '徐哥', '2', 66, 1, '56525', 1, 'Y');
INSERT INTO `user` VALUES (9, '2', '454dasd', '2', 444, 1, 'sads', 2, 'Y');
INSERT INTO `user` VALUES (10, 'ssad', 'jdkj', '123', 22, 1, '15648747656', 2, 'Y');
INSERT INTO `user` VALUES (12, 'sdas', 'dsad', '123', 22, 0, '15647847635', 2, 'Y');
INSERT INTO `user` VALUES (13, 'dfgdgd', 'kkkjdj', 'dsfsd', 66, 1, '15648787898', 2, 'Y');
INSERT INTO `user` VALUES (14, 'dfdf', 'dffds', '4544564', 55, 1, '17866824287', 2, 'Y');
INSERT INTO `user` VALUES (15, 'bai', 'bo', '1234', 23, 1, '15648747636', 2, 'Y');
INSERT INTO `user` VALUES (16, 'admin', 'bobo', '1234', 33, 1, '17856454852', 1, 'Y');
INSERT INTO `user` VALUES (17, 'test', 'test', '1234', 22, 1, '15648966356', 1, 'Y');
INSERT INTO `user` VALUES (18, 'test22', 'test22', '00000', 11, 1, '15046356585', 1, 'Y');
INSERT INTO `user` VALUES (19, 'bb', '用户', '1234', 11, 0, '15078956412', 2, 'Y');

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货名',
  `course` int NOT NULL COMMENT '鐠囧墽鈻?',
  `worksType` int NOT NULL COMMENT '娴ｆ粈绗熼崚鍡欒',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `score` int NULL DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES (16, '二分问题', 7, 2, 1, '作业', NULL, 14);
INSERT INTO `works` VALUES (17, 'learning', 3, 4, 1, '作业', NULL, NULL);
INSERT INTO `works` VALUES (18, '计算机网络作业', 6, 2, 1, '作业', NULL, NULL);
INSERT INTO `works` VALUES (21, '二分问题', 7, 2, 1, '作业', NULL, 1);
INSERT INTO `works` VALUES (22, '二分问题', 7, 2, 1, '作业', NULL, 1);
INSERT INTO `works` VALUES (23, '二分问题', 7, 2, 1, '作业', NULL, 1);
INSERT INTO `works` VALUES (24, '二分问题', 7, 2, 1, '作业', NULL, 12);
INSERT INTO `works` VALUES (25, '二分问题', 7, 2, 1, '作业', NULL, 212);
INSERT INTO `works` VALUES (26, '二分问题', 7, 2, 1, '作业', NULL, 1);
INSERT INTO `works` VALUES (27, '二分问题', 7, 2, 1, '作业', NULL, 12);
INSERT INTO `works` VALUES (28, '二分问题', 7, 2, 1, '作业', NULL, 10);
INSERT INTO `works` VALUES (29, '二分问题', 7, 2, 1, '作业', NULL, 10);

-- ----------------------------
-- Table structure for workstype
-- ----------------------------
DROP TABLE IF EXISTS `workstype`;
CREATE TABLE `workstype`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of workstype
-- ----------------------------
INSERT INTO `workstype` VALUES (2, '编程类', '666666');
INSERT INTO `workstype` VALUES (3, '计算类', '的撒');
INSERT INTO `workstype` VALUES (4, '听说类', ' 啊的');
INSERT INTO `workstype` VALUES (5, '实践类', '爱耍和');

SET FOREIGN_KEY_CHECKS = 1;
