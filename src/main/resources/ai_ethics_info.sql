/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80013 (8.0.13)
 Source Host           : localhost:3306
 Source Schema         : ai_ethics_info

 Target Server Type    : MySQL
 Target Server Version : 80013 (8.0.13)
 File Encoding         : 65001

 Date: 12/05/2025 15:55:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员唯一标识，自增主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登录名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '管理员创建时间',
  `last_login` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------

-- ----------------------------
-- Table structure for answer_records
-- ----------------------------
DROP TABLE IF EXISTS `answer_records`;
CREATE TABLE `answer_records`  (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答题记录唯一标识，自增主键',
  `user_id` int(11) NOT NULL COMMENT '答题用户ID，关联users表',
  `quiz_id` int(11) NOT NULL COMMENT '题目ID，关联quizzes表',
  `correct` tinyint(1) NULL DEFAULT 0 COMMENT '是否答对：0-错误，1-正确',
  `score` int(11) NULL DEFAULT 0 COMMENT '答题得分（根据题目难度计算）',
  `user_answer` varchar(255) NOT NULL COMMENT '用户提交的答案内容', -- 新增逗号
  `answer_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '答题时间',
  PRIMARY KEY (`answer_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `quiz_id`(`quiz_id` ASC) USING BTREE,
  CONSTRAINT `answer_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `answer_records_ibfk_2` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '答题记录数据表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of answer_records
-- ----------------------------

-- ----------------------------
-- Table structure for cases
-- ----------------------------
DROP TABLE IF EXISTS `cases`;
CREATE TABLE `cases`  (
  `case_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '案例唯一标识，自增主键',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '案例标题',
  `category` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '案例分类，用于检索',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '案例详细内容',
  `solution` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解决方案',
  `impact_analysis` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '影响分析',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '案例浏览次数',
  `image_urls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案例图片URL，多个用逗号分隔',
  `admin_id` int(11) NOT NULL COMMENT '创建或修改该案例的管理员ID，关联admins表',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '案例创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '案例更新时间，自动更新',
  PRIMARY KEY (`case_id`) USING BTREE,
  INDEX `admin_id`(`admin_id` ASC) USING BTREE,
  CONSTRAINT `cases_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cases
-- ----------------------------

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论唯一标识，自增主键',
  `user_id` int(11) NOT NULL COMMENT '评论用户ID，关联users表',
  `forum_id` int(11) NULL DEFAULT NULL COMMENT '关联的帖子ID，可为空',
  `knowledge_id` int(11) NULL DEFAULT NULL COMMENT '关联的知识ID，可为空',
  `case_id` int(11) NULL DEFAULT NULL COMMENT '关联的案例ID，可为空',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父评论ID，用于评论嵌套',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `comment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '评论点赞数',
  `status` enum('active','deleted') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'active' COMMENT '评论状态：active-正常，deleted-已删除',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `forum_id`(`forum_id` ASC) USING BTREE,
  INDEX `knowledge_id`(`knowledge_id` ASC) USING BTREE,
  INDEX `case_id`(`case_id` ASC) USING BTREE,
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`forum_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`knowledge_id`) REFERENCES `knowledge` (`knowledge_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comments_ibfk_4` FOREIGN KEY (`case_id`) REFERENCES `cases` (`case_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for expert_application
-- ----------------------------
DROP TABLE IF EXISTS `expert_application`;
CREATE TABLE `expert_application`  (
  `application_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '专家申请唯一标识，自增主键',
  `user_id` int(11) NOT NULL COMMENT '申请用户ID，关联users表',
  `application_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `application_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请内容，如专业领域、相关经验等',
  `approval_status` enum('pending','approved','rejected') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'pending' COMMENT '审批状态：pending-待审核，approved-已通过，rejected-已拒绝',
  `approval_time` timestamp NULL DEFAULT NULL COMMENT '审批时间',
  `approval_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审批原因',
  PRIMARY KEY (`application_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `expert_application_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of expert_application
-- ----------------------------

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `favorite_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏记录唯一标识，自增主键',
  `user_id` int(11) NOT NULL COMMENT '收藏用户ID，关联users表',
  `knowledge_id` int(11) NULL DEFAULT NULL COMMENT '收藏的知识ID，关联knowledge表',
  `case_id` int(11) NULL DEFAULT NULL COMMENT '收藏的案例ID，关联cases表',
  `forum_id` int(11) NULL DEFAULT NULL COMMENT '收藏的帖子ID，关联forums表',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`favorite_id`) USING BTREE,
  UNIQUE INDEX `unique_favorite`(`user_id` ASC, `knowledge_id` ASC, `case_id` ASC, `forum_id` ASC) USING BTREE,
  INDEX `knowledge_id`(`knowledge_id` ASC) USING BTREE,
  INDEX `case_id`(`case_id` ASC) USING BTREE,
  INDEX `forum_id`(`forum_id` ASC) USING BTREE,
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`knowledge_id`) REFERENCES `knowledge` (`knowledge_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `favorites_ibfk_3` FOREIGN KEY (`case_id`) REFERENCES `cases` (`case_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `favorites_ibfk_4` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`forum_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户收藏记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorites
-- ----------------------------

-- ----------------------------
-- Table structure for forums
-- ----------------------------
DROP TABLE IF EXISTS `forums`;
CREATE TABLE `forums`  (
  `forum_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '论坛帖子唯一标识，自增主键',
  `topic` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子主题',
  `post_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子内容',
  `user_id` int(11) NOT NULL COMMENT '发帖用户ID，关联users表',
  `post_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发帖时间',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '帖子点赞数',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '帖子评论数',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '帖子浏览次数',
  `image_urls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子图片URL，多个用逗号分隔',
  PRIMARY KEY (`forum_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `forums_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forums
-- ----------------------------

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge`  (
  `knowledge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识内容唯一标识，自增主键',
  `topic` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识主题，用于分类和检索',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识详细内容，支持富文本格式',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '知识浏览次数',
  `image_urls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识内容图片URL，多个用逗号分隔',
  `admin_id` int(11) NOT NULL COMMENT '创建或修改该知识的管理员ID，关联admins表',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '知识更新时间，自动更新',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '知识创建时间',
  PRIMARY KEY (`knowledge_id`) USING BTREE,
  INDEX `admin_id`(`admin_id` ASC) USING BTREE,
  CONSTRAINT `knowledge_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for quizzes
-- ----------------------------
DROP TABLE IF EXISTS `quizzes`;
CREATE TABLE `quizzes`  (
  `quiz_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测验题目唯一标识，自增主键',
  `question` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测验题目内容',
  `options` json NOT NULL COMMENT '测验选项，JSON格式存储：{选项A: 内容, 选项B: 内容...}',
  `answer` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '正确答案，对应选项中的键',
  `explanation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '答案解析',
  `difficulty` enum('easy','medium','hard') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'medium' COMMENT '题目难度级别',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '题目创建时间',
  PRIMARY KEY (`quiz_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of quizzes
-- ----------------------------

-- ----------------------------
-- Table structure for user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `user_feedback`;
CREATE TABLE `user_feedback`  (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈唯一标识，自增主键',
  `user_id` int(11) NOT NULL COMMENT '反馈用户ID，关联users表',
  `feedback_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '反馈内容',
  `submission_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈提交时间',
  `processing_status` enum('pending','processing','resolved','rejected') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'pending' COMMENT '反馈处理状态：pending-待处理，processing-处理中，resolved-已解决，rejected-已拒绝',
  `admin_id` int(11) NULL DEFAULT NULL COMMENT '处理该反馈的管理员ID，关联admins表',
  `admin_reply` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '管理员回复内容',
  `reply_time` timestamp NULL DEFAULT NULL COMMENT '管理员回复时间',
  PRIMARY KEY (`feedback_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `admin_id`(`admin_id` ASC) USING BTREE,
  CONSTRAINT `user_feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_feedback_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识，自增主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录名，用于识别用户身份',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱，用于注册和通知',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default_avatar.png' COMMENT '用户头像URL，默认为系统提供的默认头像',
  `bio` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户个人简介，可包含专业领域等信息',
  `is_expert` tinyint(1) NULL DEFAULT 0 COMMENT '是否为认证专家：0-普通用户，1-专家',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息更新时间，自动更新',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Triggers structure for table expert_application
-- ----------------------------
DROP TRIGGER IF EXISTS `update_user_expert_status`;
delimiter ;;
CREATE TRIGGER `update_user_expert_status` AFTER UPDATE ON `expert_application` FOR EACH ROW BEGIN
    IF NEW.approval_status = 'approved' THEN
        UPDATE users SET is_expert = 1 WHERE user_id = NEW.user_id;
    ELSEIF NEW.approval_status = 'rejected' THEN
        UPDATE users SET is_expert = 0 WHERE user_id = NEW.user_id;
    END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
