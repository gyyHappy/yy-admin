/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : yy-admin

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 20/07/2020 11:20:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `dept_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父级id',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '为了维护更深层级关系(规则：父级关系编码+自己的编码)',
  `dept_manager_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('06e8066d-fbb9-4904-b71a-e75fc06b19a6', 'YXD0000006', '迎学教育总公司', '0', 1, 'YXD0000006', NULL, '小霍', '13866666666', '2020-01-07 19:33:24', NULL, 1);
INSERT INTO `sys_dept` VALUES ('34843160-cfe7-48a4-8e4d-c54653217636', 'YXD0000008', '迎学教育广州分部', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', 1, 'YXD0000006YXD0000008', NULL, '小刘', '13866666666', '2020-01-07 19:36:19', '2020-01-08 12:20:41', 0);
INSERT INTO `sys_dept` VALUES ('7610b9c4-9348-4102-afc3-0e598216ccb6', 'YXD0000007', '迎学教育深圳分部', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', 1, 'YXD0000006YXD0000007', NULL, '小庄', '13866666667', '2020-01-07 19:34:49', '2020-01-08 12:04:42', 1);
INSERT INTO `sys_dept` VALUES ('da5b02b4-1fb1-4907-aaf3-3fa617f875a8', 'YXD0000009', '广州分部白云区', '34843160-cfe7-48a4-8e4d-c54653217636', 1, 'YXD0000006YXD0000008YXD0000009', NULL, '小黄', '13899999999', '2020-01-08 11:28:17', '2020-01-08 12:20:41', 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '删除菜单', 'com.gyy.modules.sys.controller.SysMenuController.delete()', NULL, 505, '127.0.0.1', '2020-07-15 22:58:45');
INSERT INTO `sys_log` VALUES (3, 'admin', '新增用户', 'com.gyy.modules.sys.controller.SysUserController.save()', NULL, 214, '127.0.0.1', '2020-07-17 17:26:21');
INSERT INTO `sys_log` VALUES (4, 'admin', '删除用户', 'com.gyy.modules.sys.controller.SysUserController.delete()', NULL, 0, '127.0.0.1', '2020-07-17 17:40:09');
INSERT INTO `sys_log` VALUES (5, 'admin', '删除用户', 'com.gyy.modules.sys.controller.SysUserController.delete()', NULL, 7, '127.0.0.1', '2020-07-17 17:43:27');
INSERT INTO `sys_log` VALUES (6, 'admin', '删除用户', 'com.gyy.modules.sys.controller.SysUserController.delete()', NULL, 0, '127.0.0.1', '2020-07-17 17:51:29');
INSERT INTO `sys_log` VALUES (7, 'admin', '新增用户', 'com.gyy.modules.sys.controller.SysUserController.save()', NULL, 166, '127.0.0.1', '2020-07-17 18:10:17');
INSERT INTO `sys_log` VALUES (8, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 632, '127.0.0.1', '2020-07-17 22:58:11');
INSERT INTO `sys_log` VALUES (9, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 58933, '127.0.0.1', '2020-07-17 23:01:49');
INSERT INTO `sys_log` VALUES (10, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 20032, '127.0.0.1', '2020-07-17 23:09:51');
INSERT INTO `sys_log` VALUES (11, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 27472, '127.0.0.1', '2020-07-17 23:30:53');
INSERT INTO `sys_log` VALUES (12, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 6337, '127.0.0.1', '2020-07-17 23:35:12');
INSERT INTO `sys_log` VALUES (13, 'admin', '修改菜单', 'com.gyy.modules.sys.controller.SysMenuController.update()', NULL, 125, '127.0.0.1', '2020-07-18 11:39:54');
INSERT INTO `sys_log` VALUES (14, 'admin', '修改菜单', 'com.gyy.modules.sys.controller.SysMenuController.update()', NULL, 97, '127.0.0.1', '2020-07-18 11:40:22');
INSERT INTO `sys_log` VALUES (15, 'admin', '保存角色', 'com.gyy.modules.sys.controller.SysRoleController.save()', NULL, 232, '127.0.0.1', '2020-07-18 23:15:49');
INSERT INTO `sys_log` VALUES (16, 'admin', '保存角色', 'com.gyy.modules.sys.controller.SysRoleController.save()', NULL, 851, '127.0.0.1', '2020-07-18 23:21:45');
INSERT INTO `sys_log` VALUES (17, 'admin', '删除角色', 'com.gyy.modules.sys.controller.SysRoleController.delete()', NULL, 976, '127.0.0.1', '2020-07-19 16:55:50');
INSERT INTO `sys_log` VALUES (18, 'admin', '保存角色', 'com.gyy.modules.sys.controller.SysRoleController.save()', NULL, 3074, '127.0.0.1', '2020-07-19 16:59:06');
INSERT INTO `sys_log` VALUES (19, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 492, '127.0.0.1', '2020-07-19 16:59:27');
INSERT INTO `sys_log` VALUES (20, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 207, '127.0.0.1', '2020-07-19 16:59:28');
INSERT INTO `sys_log` VALUES (21, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 763, '127.0.0.1', '2020-07-19 17:14:04');
INSERT INTO `sys_log` VALUES (22, 'admin', '删除角色', 'com.gyy.modules.sys.controller.SysRoleController.delete()', NULL, 1292, '127.0.0.1', '2020-07-19 17:33:14');
INSERT INTO `sys_log` VALUES (23, 'admin', '保存角色', 'com.gyy.modules.sys.controller.SysRoleController.save()', NULL, 1539, '127.0.0.1', '2020-07-19 17:48:05');
INSERT INTO `sys_log` VALUES (24, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 477, '127.0.0.1', '2020-07-19 17:48:14');
INSERT INTO `sys_log` VALUES (25, 'admin', '删除角色', 'com.gyy.modules.sys.controller.SysRoleController.delete()', NULL, 142353, '127.0.0.1', '2020-07-19 17:50:51');
INSERT INTO `sys_log` VALUES (26, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 51277, '127.0.0.1', '2020-07-19 17:53:07');
INSERT INTO `sys_log` VALUES (27, 'admin', '保存角色', 'com.gyy.modules.sys.controller.SysRoleController.save()', NULL, 1605, '127.0.0.1', '2020-07-19 17:54:45');
INSERT INTO `sys_log` VALUES (28, 'admin', '修改用户信息', 'com.gyy.modules.sys.controller.SysUserController.update()', NULL, 30920, '127.0.0.1', '2020-07-19 17:58:53');
INSERT INTO `sys_log` VALUES (29, 'admin', '删除角色', 'com.gyy.modules.sys.controller.SysRoleController.delete()', NULL, 112250, '127.0.0.1', '2020-07-19 18:03:43');
INSERT INTO `sys_log` VALUES (30, 'admin', '修改角色', 'com.gyy.modules.sys.controller.SysRoleController.update()', NULL, 3778, '127.0.0.1', '2020-07-19 20:59:28');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(如：sys:user:add)',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址URL',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级菜单权限id',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '排序',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态1:正常 0：禁用',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('013095aa-0f4d-4c32-b30e-229a587e52ad', '新增部门', 'sys:dept:add', '', '8f393e44-b585-4875-866d-71f88fea9659', 100, 3, 1, '2020-07-18 11:38:16', '2020-07-18 11:38:16', NULL);
INSERT INTO `sys_menu` VALUES ('0545d9d1-c82c-44c2-85e5-0b6ac4042515', '日志管理', 'sys:log:list', 'sys/log', '4caeb861-354c-45f6-98b4-59f486beb511', 7, 2, 1, '2020-07-15 22:39:04', '2020-07-15 22:39:04', 'log');
INSERT INTO `sys_menu` VALUES ('145cb90b-d205-40f6-8a2d-703f41ed1feb', '删除用户', 'sys:user:delete', '', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', 100, 3, 1, '2020-07-18 11:38:13', '2020-07-18 11:38:13', NULL);
INSERT INTO `sys_menu` VALUES ('24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', '接口管理', '', 'http://localhost:8080/swagger-ui.html', '4caeb861-354c-45f6-98b4-59f486beb511', 6, 2, 1, '2020-07-20 10:55:34', '2020-07-20 10:55:34', 'mudedi');
INSERT INTO `sys_menu` VALUES ('27acf73b-2fcb-451b-bdc4-e11e5ab41e2a', '删除部门', 'sys:dept:delete', '', '8f393e44-b585-4875-866d-71f88fea9659', 100, 3, 1, '2020-07-18 11:38:19', '2020-07-18 11:38:19', NULL);
INSERT INTO `sys_menu` VALUES ('290c0240-0914-487c-b4e9-6635bf5ebfec', '菜单权限管理', '', 'sys/menu', '4caeb861-354c-45f6-98b4-59f486beb511', 3, 2, 1, '2020-07-15 12:24:12', '2020-07-15 12:24:12', 'menu');
INSERT INTO `sys_menu` VALUES ('2ae13993-9501-46d5-8473-fe45fee57f3b', '新增用户', 'sys:user:save,sys:role:select', '', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', 100, 3, 1, '2020-07-18 11:38:23', '2020-07-18 11:38:23', NULL);
INSERT INTO `sys_menu` VALUES ('2eeaa020-74d5-4c4b-9849-2cf4bd68fed9', '更新角色', 'sys:role:update,sys:menu:list', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:43:27', '2020-07-18 11:43:27', NULL);
INSERT INTO `sys_menu` VALUES ('2f9a3f67-6ef3-4eac-b9a1-c0e898718d0c', '删除菜单', 'sys:menu:delete', '', '290c0240-0914-487c-b4e9-6635bf5ebfec', 100, 3, 1, '2020-07-17 21:05:20', '2020-07-17 21:05:20', NULL);
INSERT INTO `sys_menu` VALUES ('390ded0e-9f48-40a7-a841-791c203f22ae', '查询菜单权限列表', 'sys:menu:list,sys:menu:info', '', '290c0240-0914-487c-b4e9-6635bf5ebfec', 100, 3, 1, '2020-07-18 11:38:34', '2020-07-18 11:38:34', NULL);
INSERT INTO `sys_menu` VALUES ('39313e6a-14ed-4224-a91e-ef6a10ba54cd', '查询部门信息列表', 'sys:dept:list', '', '8f393e44-b585-4875-866d-71f88fea9659', 100, 3, 1, '2020-07-18 11:38:37', '2020-07-18 11:38:37', NULL);
INSERT INTO `sys_menu` VALUES ('47697e92-e199-4420-a2c2-09ec1b08cb9d', '查询用户信息列表', 'sys:user:list,sys:user:info', '', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', 100, 3, 1, '2020-07-18 11:38:39', '2020-07-18 11:38:39', NULL);
INSERT INTO `sys_menu` VALUES ('4caeb861-354c-45f6-98b4-59f486beb511', '系统管理', '', '', '0', 0, 1, 1, '2020-07-15 12:23:25', '2020-07-15 12:23:25', 'system');
INSERT INTO `sys_menu` VALUES ('65734896-90c5-4b48-b9e8-dee47a74a297', '删除角色', 'sys:role:delete', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:38:43', '2020-07-18 11:38:43', NULL);
INSERT INTO `sys_menu` VALUES ('7141c2e9-6d50-46b6-94e8-100466b7249f', 'SQL监控', '', 'http://localhost:8080/druid/sql.html', '4caeb861-354c-45f6-98b4-59f486beb511', 5, 2, 1, '2020-07-20 10:55:28', '2020-07-20 10:55:28', 'sql');
INSERT INTO `sys_menu` VALUES ('84b9b525-aa44-4b16-9900-adca26115a37', '新增角色', 'sys:role:add', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:38:48', '2020-07-18 11:38:48', NULL);
INSERT INTO `sys_menu` VALUES ('8f393e44-b585-4875-866d-71f88fea9659', '部门管理', '', 'sys/depts', '4caeb861-354c-45f6-98b4-59f486beb511', 4, 2, 1, '2020-07-15 12:24:03', '2020-07-15 12:24:03', 'shouye');
INSERT INTO `sys_menu` VALUES ('90b3be91-5e9d-42f8-81fb-0c9ef3014faa', '角色详情', 'sys:role:detail', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:38:52', '2020-07-18 11:38:52', NULL);
INSERT INTO `sys_menu` VALUES ('9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '用户管理', '', 'sys/user', '4caeb861-354c-45f6-98b4-59f486beb511', 1, 2, 1, '2020-07-15 12:23:42', '2020-07-15 12:23:42', 'admin');
INSERT INTO `sys_menu` VALUES ('b7348d63-c4d3-406d-9e46-543346674275', '更新部门信息', 'sys:dept:update', '', '8f393e44-b585-4875-866d-71f88fea9659', 100, 3, 1, '2020-07-18 11:38:56', '2020-07-18 11:38:56', NULL);
INSERT INTO `sys_menu` VALUES ('bb5ca869-0303-4fc0-b067-936cba7d1cc8', '更新菜单', 'sys:menu:update,sys:menu:select', '', '290c0240-0914-487c-b4e9-6635bf5ebfec', 100, 3, 1, '2020-07-18 11:39:01', '2020-07-18 11:39:01', NULL);
INSERT INTO `sys_menu` VALUES ('c198d1cb-ad4d-4001-9375-9ec8ee04053d', '角色管理', '', 'sys/role', '4caeb861-354c-45f6-98b4-59f486beb511', 2, 2, 1, '2020-07-15 12:23:51', '2020-07-15 12:23:51', 'role');
INSERT INTO `sys_menu` VALUES ('d60faf3e-9a72-49d5-b02d-a67bfeff07fa', '更新用户信息', 'sys:user:update,sys:role:select', '', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', 100, 3, 1, '2020-07-18 11:39:54', '2020-07-18 11:39:54', NULL);
INSERT INTO `sys_menu` VALUES ('de8e2328-4313-477e-9644-3ca93799cc76', '新增角色', 'sys:role:save,sys:menu:list', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:42:38', '2020-07-18 11:42:38', NULL);
INSERT INTO `sys_menu` VALUES ('e136cc74-9817-4ef1-b181-8f1afd7e102c', '新增菜单', 'sys:menu:save,sys:menu:select', '', '290c0240-0914-487c-b4e9-6635bf5ebfec', 100, 3, 1, '2020-07-18 11:40:21', '2020-07-18 11:40:22', NULL);
INSERT INTO `sys_menu` VALUES ('f9f4d9f4-a2f5-430c-9f2d-6c8e650a8c39', '查询角色列表', 'sys:role:list,sys:role:info', '', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', 100, 3, 1, '2020-07-18 11:42:30', '2020-07-18 11:42:30', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('b125dbb8-459e-409f-bd1f-d17966568eda', 'admin', '我是超级管理员', '2020-01-06 23:37:45', '2020-07-17 12:46:26', 'fcf34b56-a7a2-4719-9236-867495e74c31');
INSERT INTO `sys_role` VALUES ('fc674bde-29ee-4160-bd97-00761357f019', 'test', '测试角色', '2020-01-08 10:45:31', '2020-07-17 12:46:32', 'fcf34b56-a7a2-4719-9236-867495e74c31');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('04090248-59d5-4909-99a8-25b71bcefb42', 'b125dbb8-459e-409f-bd1f-d17966568eda', '39313e6a-14ed-4224-a91e-ef6a10ba54cd', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('07e593cb-b238-4bcc-b35c-090de648ee87', 'b125dbb8-459e-409f-bd1f-d17966568eda', '290c0240-0914-487c-b4e9-6635bf5ebfec', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('140c4dbf-114c-4a8e-8dae-710ac28f38ea', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2ae13993-9501-46d5-8473-fe45fee57f3b', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('1c310548-7ccf-40b9-b169-0d821c40d524', 'b125dbb8-459e-409f-bd1f-d17966568eda', '90b3be91-5e9d-42f8-81fb-0c9ef3014faa', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('218987256f21002ab5f18909bfac8aff', 'fc674bde-29ee-4160-bd97-00761357f019', '4caeb861-354c-45f6-98b4-59f486beb511', '2020-07-19 20:59:28');
INSERT INTO `sys_role_menu` VALUES ('2522ef2c-369a-469f-bb45-68d3c0317e4a', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'e136cc74-9817-4ef1-b181-8f1afd7e102c', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('31d041e9-7e00-4e8f-901d-576ad66ade34', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2f9a3f67-6ef3-4eac-b9a1-c0e898718d0c', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('364c8d50-6edf-4a0b-b5ed-a1e4be1c0e0c', 'b125dbb8-459e-409f-bd1f-d17966568eda', '24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('3c00cd7e2e17e742c78f8f467dfb5cdc', 'fc674bde-29ee-4160-bd97-00761357f019', '24b7b13c-f00f-4e6b-a221-fe2d780e4d4f', '2020-07-19 20:59:27');
INSERT INTO `sys_role_menu` VALUES ('3fc20ea6-a2c6-4bdb-847e-3a83a11a2961', 'b125dbb8-459e-409f-bd1f-d17966568eda', '145cb90b-d205-40f6-8a2d-703f41ed1feb', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('448b5a65-42c9-48a3-8a3e-615eb5cdb1dc', 'b125dbb8-459e-409f-bd1f-d17966568eda', '8f393e44-b585-4875-866d-71f88fea9659', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('526d063b-3ef0-40c7-8880-ff580e009ba0', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'c198d1cb-ad4d-4001-9375-9ec8ee04053d', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('5601b594-549d-4e25-914a-0341593f1b0b', 'b125dbb8-459e-409f-bd1f-d17966568eda', '84b9b525-aa44-4b16-9900-adca26115a37', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('5b860e04-0f0d-4f31-bdc2-059539c07859', 'b125dbb8-459e-409f-bd1f-d17966568eda', '9ce621a0-ee2c-4cf6-b7bd-012a1a01ba63', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('77a95086-d196-402e-9363-14e66ae5b01f', 'b125dbb8-459e-409f-bd1f-d17966568eda', '013095aa-0f4d-4c32-b30e-229a587e52ad', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('89c3f1b3-9ca5-4764-a4ee-ee514b895f14', 'b125dbb8-459e-409f-bd1f-d17966568eda', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('8ba8b35f-371a-4913-8b90-8b940aa99327', 'b125dbb8-459e-409f-bd1f-d17966568eda', '27acf73b-2fcb-451b-bdc4-e11e5ab41e2a', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('93e6e9ec-6296-409d-b424-889db695df92', 'b125dbb8-459e-409f-bd1f-d17966568eda', '65734896-90c5-4b48-b9e8-dee47a74a297', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('9db74eaa-ff2c-4239-ba10-123bee138698', 'b125dbb8-459e-409f-bd1f-d17966568eda', '7141c2e9-6d50-46b6-94e8-100466b7249f', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('a3988c61-039b-4949-b0b3-33e3178eda34', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2eeaa020-74d5-4c4b-9849-2cf4bd68fed9', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('a5fbc470-da7d-4b5e-bc95-0a003f7548bd', 'b125dbb8-459e-409f-bd1f-d17966568eda', '390ded0e-9f48-40a7-a841-791c203f22ae', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('ab6d9d33023acc056ed389b387124d62', 'fc674bde-29ee-4160-bd97-00761357f019', '-666666', '2020-07-19 20:59:28');
INSERT INTO `sys_role_menu` VALUES ('b9f9b1f6-e74d-4e2e-81b0-d5f6e0a34deb', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'b7348d63-c4d3-406d-9e46-543346674275', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('be41d718-8409-46bb-92f6-819b98bb7fc5', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'f9f4d9f4-a2f5-430c-9f2d-6c8e650a8c39', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('c4d70d4f-df35-48db-a8e6-984bb016c3a1', 'b125dbb8-459e-409f-bd1f-d17966568eda', '47697e92-e199-4420-a2c2-09ec1b08cb9d', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('d1db53e8-e877-43b2-8526-5e85f546f043', 'b125dbb8-459e-409f-bd1f-d17966568eda', '4caeb861-354c-45f6-98b4-59f486beb511', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('eab81c13e348b697501a458ae0291192', 'fc674bde-29ee-4160-bd97-00761357f019', '0545d9d1-c82c-44c2-85e5-0b6ac4042515', '2020-07-19 20:59:27');
INSERT INTO `sys_role_menu` VALUES ('f337606d-8c29-46ec-b0b0-36904a9c6be7', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'd60faf3e-9a72-49d5-b02d-a67bfeff07fa', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('f7ac2256-d3b7-43fa-a6b4-0caff31f7471', 'b125dbb8-459e-409f-bd1f-d17966568eda', 'bb5ca869-0303-4fc0-b067-936cba7d1cc8', '2020-01-08 15:31:57');
INSERT INTO `sys_role_menu` VALUES ('f9c99d518d451aefadd2d46d9f123dbe', 'fc674bde-29ee-4160-bd97-00761357f019', '7141c2e9-6d50-46b6-94e8-100466b7249f', '2020-07-19 20:59:27');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名称',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) NULL DEFAULT 1 COMMENT '性别(1.男 2.女)',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('081d0497-819d-4f04-b258-e7e079f7578f', 'test', 'e58d51325fdb4d9e9f34', '0ebd177f7648d37f1bc1e6224494abc2', '13866666666', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', 'test', 'gyyHappy02@163.com', 1, 1, NULL, '2020-01-08 10:46:41');
INSERT INTO `sys_user` VALUES ('4790485ae1e7e380e07aff36e1c04427', 'GYY', 'bcba48a7c62d4dabb220', 'da9bdc67f19ace52ff97ebafca6e7b48', '13710043303', NULL, '大叔', '244160422@qq.com', 1, 1, 'fcf34b56-a7a2-4719-9236-867495e74c31', '2020-07-17 18:10:17');
INSERT INTO `sys_user` VALUES ('fcf34b56-a7a2-4719-9236-867495e74c31', 'admin', 'c1f4a8a78e7d4186ac0b', '79533cf45e1cb76fa2ff58fead3ea319', '13888888888', '06e8066d-fbb9-4904-b71a-e75fc06b19a6', 'GYY', 'gyyHappy01@163.com', 1, 1, 'fcf34b56-a7a2-4719-9236-867495e74c31', '2019-09-22 19:38:05');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0e03a014a2959b207742e0e2757afd32', '4790485ae1e7e380e07aff36e1c04427', 'fc674bde-29ee-4160-bd97-00761357f019', '2020-07-19 17:58:53');
INSERT INTO `sys_user_role` VALUES ('342cdcf7-27c7-4ac6-b599-35810a2e38a0', '081d0497-819d-4f04-b258-e7e079f7578f', 'fc674bde-29ee-4160-bd97-00761357f019', '2020-01-08 16:47:48');
INSERT INTO `sys_user_role` VALUES ('4dc98adae1fe68f6a68d27f4681c2818', 'fcf34b56-a7a2-4719-9236-867495e74c31', 'b125dbb8-459e-409f-bd1f-d17966568eda', '2020-07-17 23:35:12');
INSERT INTO `sys_user_role` VALUES ('b492eb30516fefcec3e0ddeeda5d6323', 'fcf34b56-a7a2-4719-9236-867495e74c31', 'fc674bde-29ee-4160-bd97-00761357f019', '2020-07-17 23:35:12');

SET FOREIGN_KEY_CHECKS = 1;
