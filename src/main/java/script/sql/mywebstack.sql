/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306(Mysql8)
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : mywebstack

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 01/09/2023 09:14:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_category
-- ----------------------------
DROP TABLE IF EXISTS `db_category`;
CREATE TABLE `db_category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '目录分类id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目录分类名',
  `category_engname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '目录英文名',
  `category_jpname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '目录日文名',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'icon样式名',
  `pid` int(0) DEFAULT NULL COMMENT '父目录',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_category
-- ----------------------------
INSERT INTO `db_category` VALUES (1, '常用推荐', 'Recommended', 'おすすめ', 'linecons-star', NULL);
INSERT INTO `db_category` VALUES (2, '社区资讯', 'Information', 'コミュニティ情報', 'linecons-doc', NULL);
INSERT INTO `db_category` VALUES (3, '灵感采集', 'Inspiration', 'インスピレーション', 'linecons-lightbulb', NULL);
INSERT INTO `db_category` VALUES (4, '发现产品', 'Product', 'プロダクト', NULL, 3);
INSERT INTO `db_category` VALUES (5, '界面灵感', 'UI Inspiration', 'UI インスピレーション', NULL, 3);
INSERT INTO `db_category` VALUES (6, '网页灵感', 'WEB Inspiration', 'Web インスピレーション', NULL, 3);
INSERT INTO `db_category` VALUES (7, '素材资源', 'Resources', '素材リソース', 'linecons-thumbs-up', NULL);
INSERT INTO `db_category` VALUES (8, '图标素材', 'Icon', 'アイコン', NULL, 7);
INSERT INTO `db_category` VALUES (9, 'LOGO设计', 'Logo Design', 'ロゴデザイン', NULL, 7);
INSERT INTO `db_category` VALUES (10, '平面素材', 'Graphic Design', 'グラフィックデザイン', NULL, 7);
INSERT INTO `db_category` VALUES (11, 'UI资源', 'UI Design', 'UI デザイン', NULL, 7);
INSERT INTO `db_category` VALUES (12, 'Sketch资源', 'Sketch', 'スケッチ', NULL, 7);
INSERT INTO `db_category` VALUES (13, '字体资源', 'Fonts', 'フォント', NULL, 7);
INSERT INTO `db_category` VALUES (14, 'Mockup', 'Mockup', 'モックアップ', NULL, 7);
INSERT INTO `db_category` VALUES (15, '摄影图库', 'Free Photos', '写真撮影', NULL, 7);
INSERT INTO `db_category` VALUES (16, 'PPT资源', 'PPT', 'PPT', NULL, 7);
INSERT INTO `db_category` VALUES (17, '常用工具', 'Design Tools', 'デザインツール', 'linecons-diamond', NULL);
INSERT INTO `db_category` VALUES (18, '图形创意', 'Creative Graphics', 'クリエイティブグラフィックス', NULL, 17);
INSERT INTO `db_category` VALUES (19, '界面设计', 'User Interface', 'インターフェースデザイン', NULL, 17);
INSERT INTO `db_category` VALUES (20, '交互动效', 'Motion Design', 'モーションデザイン', NULL, 17);
INSERT INTO `db_category` VALUES (21, '在线配色', 'Colors', '色合い', NULL, 17);
INSERT INTO `db_category` VALUES (22, '在线工具', 'Online Tools', 'オンラインツール', NULL, 17);
INSERT INTO `db_category` VALUES (23, 'Chrome插件', 'Chrome Plug-ins', 'Chromeプラグイン', NULL, 17);
INSERT INTO `db_category` VALUES (24, '学习教程', 'Tutorial', 'チュートリアル', 'linecons-pencil', NULL);
INSERT INTO `db_category` VALUES (25, '设计规范', 'Design Guidelines', 'デザインガイドライン', NULL, 24);
INSERT INTO `db_category` VALUES (26, '视频教程', 'Video Tutorial', '動画チュートリアル', NULL, 24);
INSERT INTO `db_category` VALUES (27, '设计文章', 'Design Article', 'デザイン記事', NULL, 24);
INSERT INTO `db_category` VALUES (28, '设计电台', 'Design FM', 'デザイン FM', NULL, 24);
INSERT INTO `db_category` VALUES (29, '交互设计', 'UX', 'インタラクションデザイン', NULL, 24);
INSERT INTO `db_category` VALUES (30, 'UED团队', 'UED Team', 'UED チーム', 'linecons-user', NULL);
INSERT INTO `db_category` VALUES (31, '关于本站', 'About This Site', 'このサイトについて', 'linecons-heart', NULL);

-- ----------------------------
-- Table structure for db_comment
-- ----------------------------
DROP TABLE IF EXISTS `db_comment`;
CREATE TABLE `db_comment`  (
  `comment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `rate` decimal(10, 1) DEFAULT NULL COMMENT '网站评分',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `web_id` int(0) NOT NULL COMMENT '业务模块的id，用于区分不同网站',
  `target` int(0) DEFAULT NULL COMMENT '回复对象',
  `pid` int(0) DEFAULT NULL COMMENT '\r\n父级评论id',
  `createtime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_comment
-- ----------------------------
INSERT INTO `db_comment` VALUES (1, 1, 1.0, '哈哈哈哈哈，好有意思', 1, NULL, NULL, '2023-07-07 17:05:05');
INSERT INTO `db_comment` VALUES (2, 2, 1.0, '好好好', 1, NULL, NULL, '2023-07-07 17:10:57');
INSERT INTO `db_comment` VALUES (3, 3, 4.5, '阿大声道阿萨德阿萨德as大三啊的', 2, NULL, NULL, '2023-07-08 09:13:28');
INSERT INTO `db_comment` VALUES (4, 4, 4.5, '一般而已', 2, NULL, NULL, '2023-07-08 10:15:50');
INSERT INTO `db_comment` VALUES (5, 1, 3.0, '一般一般', 1, NULL, NULL, '2023-07-08 10:17:55');
INSERT INTO `db_comment` VALUES (6, 3, 0.5, '啊啊啊啊啊啊啊啊啊', 3, NULL, NULL, '2023-07-08 10:19:09');
INSERT INTO `db_comment` VALUES (7, 2, 5.0, '去玩儿', 3, NULL, NULL, '2023-07-08 10:22:21');
INSERT INTO `db_comment` VALUES (8, 1, 5.0, '很有帮助的一个网站', 1, NULL, NULL, '2023-07-08 10:50:53');
INSERT INTO `db_comment` VALUES (9, 2, NULL, '真的吗？？', 1, NULL, NULL, '2023-07-10 10:33:48');
INSERT INTO `db_comment` VALUES (10, 2, NULL, '是这样的', 1, 1, 1, '2023-07-10 10:35:38');
INSERT INTO `db_comment` VALUES (11, 2, NULL, '确实很好', 1, 2, 2, '2023-07-10 13:58:55');
INSERT INTO `db_comment` VALUES (12, 2, NULL, '太好了', 1, 2, 1, '2023-07-10 14:07:47');
INSERT INTO `db_comment` VALUES (13, 1, 5.0, '好评好评好评', 4, NULL, NULL, '2023-07-10 16:08:55');
INSERT INTO `db_comment` VALUES (14, 1, 0.0, '很好的网站', 5, NULL, NULL, '2023-07-10 16:10:44');
INSERT INTO `db_comment` VALUES (15, 2, NULL, '确实', 5, 1, 14, '2023-07-10 16:10:54');
INSERT INTO `db_comment` VALUES (16, 1, 0.0, '阿萨德', 1, NULL, NULL, '2023-07-10 16:13:53');
INSERT INTO `db_comment` VALUES (17, 7, NULL, '还可以', 1, 1, 5, '2023-07-10 19:45:50');
INSERT INTO `db_comment` VALUES (18, 7, 5.0, '没人吗？？？', 7, NULL, NULL, '2023-07-10 19:47:24');
INSERT INTO `db_comment` VALUES (19, 7, 0.0, '产品经理网真厉害！', 16, NULL, NULL, '2023-07-10 19:48:10');
INSERT INTO `db_comment` VALUES (20, 1, 0.0, '啊啊啊啊啊', 1, NULL, NULL, '2023-07-11 10:06:19');
INSERT INTO `db_comment` VALUES (21, 1, NULL, '来了！', 7, 7, 18, '2023-07-11 11:31:01');
INSERT INTO `db_comment` VALUES (22, 7, 5.0, '谷歌！！！好用！！！', 11, NULL, NULL, '2023-07-12 16:09:21');
INSERT INTO `db_comment` VALUES (23, 7, 3.0, '马马虎虎', 2, NULL, NULL, '2023-07-12 16:25:11');
INSERT INTO `db_comment` VALUES (24, 3, NULL, '确实', 5, 2, 14, '2023-07-13 10:57:38');
INSERT INTO `db_comment` VALUES (25, 2, 3.0, '有一定帮助', 12, NULL, NULL, '2023-07-13 11:15:26');
INSERT INTO `db_comment` VALUES (26, 2, NULL, '谢谢推荐~~~', 4, 1, 13, '2023-07-13 11:20:25');
INSERT INTO `db_comment` VALUES (28, 2, NULL, '我觉得也不错', 1, 7, 5, '2023-07-13 11:28:40');
INSERT INTO `db_comment` VALUES (29, 2, 5.0, '很好用的网站！！！', 8, NULL, NULL, '2023-07-31 16:22:46');
INSERT INTO `db_comment` VALUES (30, 2, NULL, '真的好用！', 8, 2, 29, '2023-07-31 16:22:58');
INSERT INTO `db_comment` VALUES (31, 2, 3.5, '还可以！', 2, NULL, NULL, '2023-07-31 16:23:46');
INSERT INTO `db_comment` VALUES (32, 2, 0.5, '很不好！', 7, NULL, NULL, '2023-07-31 16:29:17');
INSERT INTO `db_comment` VALUES (33, 2, 5.0, '很多有用的内容', 10, NULL, NULL, '2023-08-01 10:14:09');
INSERT INTO `db_comment` VALUES (34, 2, NULL, '的确很有用', 11, 7, 22, '2023-08-01 10:14:30');
INSERT INTO `db_comment` VALUES (36, 2, 5.0, '很好', 17, NULL, NULL, '2023-08-01 11:02:25');
INSERT INTO `db_comment` VALUES (37, 2, 1.0, '很差', 17, NULL, NULL, '2023-08-01 11:02:36');
INSERT INTO `db_comment` VALUES (38, 11, 1.0, '1223', 11, NULL, NULL, '2023-08-01 14:40:44');
INSERT INTO `db_comment` VALUES (39, 11, NULL, '123', 11, 7, 22, '2023-08-01 14:41:15');
INSERT INTO `db_comment` VALUES (41, 2, 4.0, '很好的', 12, NULL, NULL, '2023-08-27 16:17:05');
INSERT INTO `db_comment` VALUES (42, 2, 2.5, '234234', 12, NULL, NULL, '2023-08-27 16:20:01');
INSERT INTO `db_comment` VALUES (43, 2, 0.0, '很好的', 20, NULL, NULL, '2023-08-27 16:24:02');
INSERT INTO `db_comment` VALUES (44, 2, 4.0, '123', 22, NULL, NULL, '2023-08-27 16:33:28');
INSERT INTO `db_comment` VALUES (45, 2, 0.0, '测试2', 22, NULL, NULL, '2023-08-27 16:34:53');
INSERT INTO `db_comment` VALUES (46, 2, 0.0, '测试3', 22, NULL, NULL, '2023-08-27 16:37:53');
INSERT INTO `db_comment` VALUES (47, 2, 0.0, '测试4', 22, NULL, NULL, '2023-08-27 16:39:43');
INSERT INTO `db_comment` VALUES (48, 2, 0.0, '测试5', 22, NULL, NULL, '2023-08-27 16:40:33');
INSERT INTO `db_comment` VALUES (49, 2, 0.0, '1', 20, NULL, NULL, '2023-08-27 16:45:43');
INSERT INTO `db_comment` VALUES (50, 2, 0.0, '2', 20, NULL, NULL, '2023-08-27 16:49:21');
INSERT INTO `db_comment` VALUES (51, 2, 0.0, '', 5, NULL, NULL, '2023-08-27 19:18:44');
INSERT INTO `db_comment` VALUES (52, 2, 0.0, '', 5, NULL, NULL, '2023-08-27 19:19:17');
INSERT INTO `db_comment` VALUES (53, 2, 0.0, '123', 5, 1, 14, '2023-08-27 19:20:02');
INSERT INTO `db_comment` VALUES (54, 2, 0.0, '456', 5, 1, 14, '2023-08-27 19:20:28');
INSERT INTO `db_comment` VALUES (55, 2, 4.0, '123', 6, NULL, NULL, '2023-08-30 17:18:41');

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(0) DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `disable` int(0) DEFAULT 0,
  `is_admin` int(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `db_user` VALUES (1, 'admin', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', 'superAdmin@aliyun.com', '18677779999', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 1);
INSERT INTO `db_user` VALUES (2, 'zhangsan', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', 'zhangsan123@gmail.com', '13966667777', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 1);
INSERT INTO `db_user` VALUES (3, 'lisi', '***', 'lisi@gmail.com', '13966667778', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (4, 'wangwu', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', 'wangwu@gmail.com', '13966667773', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (5, 'zhaoer', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', 'zhaoer@gmail.com', '13966667776', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (6, 'songliu', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', 'songliu@gmail.com', '13966667771', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (7, 'Leo', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', '1010@qq.com', '15823568888', 1, NULL, 0, 0);
INSERT INTO `db_user` VALUES (8, 'jackz', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', '123666@qq.com', '15888888888', 1, NULL, 1, 0);
INSERT INTO `db_user` VALUES (9, 'TestUser', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', '123321@cq.cn', '15888888888', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (10, 'xiaozhang', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', '1111@qq.com', '15888888888', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);
INSERT INTO `db_user` VALUES (11, 'xiexiexie', '$2a$10$eZ./ewYDC.8Q/owRmex21.axBWmDrJnX5Gh0gsQXL9Pk1h323G7Ky', '123@qq.com', '15888888888', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0);

-- ----------------------------
-- Table structure for db_web
-- ----------------------------
DROP TABLE IF EXISTS `db_web`;
CREATE TABLE `db_web`  (
  `web_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '网站id',
  `web_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站名',
  `web_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '网页描述',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT 'url路径',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'logo路径',
  `category_id` int(0) NOT NULL COMMENT '目录id',
  `status` int(0) DEFAULT 1 COMMENT '网站状态（1可见，0不可见）',
  PRIMARY KEY (`web_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_web
-- ----------------------------
INSERT INTO `db_web` VALUES (1, 'Dribbble', '全球UI设计师作品分享平台。', 'https://dribbble.com/', 'assets/images/logos/dribbble.png', 1, 1);
INSERT INTO `db_web` VALUES (2, 'Behance', 'Adobe旗下的设计师交流平台，来自世界各地的设计师在这里分享自己的作品。', 'https://behance.net/', 'assets/images/logos/behance.png', 1, 1);
INSERT INTO `db_web` VALUES (3, 'UI中国', '图形交互与界面设计交流、作品展示、学习平台。', 'https://www.ui.cn/', 'assets/images/logos/uicn.png', 1, 1);
INSERT INTO `db_web` VALUES (4, '站酷', '中国人气设计师互动平台', 'http://www.zcool.com.cn/', 'assets/images/logos/zcool.png', 1, 1);
INSERT INTO `db_web` VALUES (5, 'Pinterest', '全球美图收藏采集站', 'https://www.pinterest.com/', 'assets/images/logos/pinterest.png', 1, 1);
INSERT INTO `db_web` VALUES (6, '花瓣', '收集灵感,保存有用的素材', 'http://huaban.com/', 'assets/images/logos/huaban.png', 1, 1);
INSERT INTO `db_web` VALUES (7, 'Medium', '高质量设计文章', 'https://medium.com/', 'assets/images/logos/medium.png', 1, 1);
INSERT INTO `db_web` VALUES (8, '优设', '设计师交流学习平台', 'http://www.uisdc.com/', 'assets/images/logos/uisdc.png', 1, 1);
INSERT INTO `db_web` VALUES (9, 'Producthunt', '发现新鲜有趣的产品', 'https://www.producthunt.com', 'assets/images/logos/producthunt.png', 1, 1);
INSERT INTO `db_web` VALUES (10, 'Youtube', '全球最大的学习分享平台', 'https://www.youtube.com', 'assets/images/logos/youtube.png', 1, 1);
INSERT INTO `db_web` VALUES (11, 'Google', '全球最大的UI学习分享平台', 'https://www.google.com', 'assets/images/logos/google.png', 1, 1);
INSERT INTO `db_web` VALUES (12, '雷锋网', '人工智能和智能硬件领域的互联网科技媒体', 'https://www.leiphone.com/', 'assets/images/logos/leiphone.png', 2, 1);
INSERT INTO `db_web` VALUES (13, '36kr', '创业资讯、科技新闻', 'http://36kr.com/', 'assets/images/logos/36kr.png', 2, 1);
INSERT INTO `db_web` VALUES (14, '数英网', '数字媒体及职业招聘网站', 'https://www.digitaling.com/', 'assets/images/logos/digitaling.png', 2, 1);
INSERT INTO `db_web` VALUES (15, '猎云网', '互联网创业项目推荐和创业创新资讯', 'http://www.lieyunwang.com/', 'assets/images/logos/lieyunwang.png', 2, 1);
INSERT INTO `db_web` VALUES (16, '人人都是产品经理', '产品经理、产品爱好者学习交流平台', 'http://www.woshipm.com/', 'assets/images/logos/woshipm.png', 2, 1);
INSERT INTO `db_web` VALUES (17, '互联网早读课', '互联网行业深度阅读与学习平台', 'https://www.zaodula.com/', 'assets/images/logos/zaodula.png', 2, 1);
INSERT INTO `db_web` VALUES (18, '产品壹佰', '为产品经理爱好者提供最优质的产品资讯、原创内容和相关视频课程', 'http://www.chanpin100.com/', 'assets/images/logos/chanpin100.png', 2, 1);
INSERT INTO `db_web` VALUES (19, 'PMCAFF', '中国第一产品经理人气组织，专注于研究互联网产品', 'http://www.pmcaff.com/', 'assets/images/logos/pmcaff.png', 2, 1);
INSERT INTO `db_web` VALUES (20, '爱运营', '网站运营人员学习交流，专注于网站产品运营管理、淘宝运营。', 'http://www.iyunying.org/', 'assets/images/logos/iyunying.png', 2, 1);
INSERT INTO `db_web` VALUES (21, '鸟哥笔记', '移动互联网第一干货平台', 'http://www.niaogebiji.com/', 'assets/images/logos/niaogebiji.png', 2, 1);
INSERT INTO `db_web` VALUES (22, '古田路9号', '国内专业品牌创意平台', 'http://www.gtn9.com/', 'assets/images/logos/gtn9.png', 2, 1);
INSERT INTO `db_web` VALUES (23, '优阁网', 'UI设计师学习交流社区', 'http://www.uigreat.com/', 'assets/images/logos/uigreat.png', 2, 1);
INSERT INTO `db_web` VALUES (24, 'Producthunt', '发现新鲜有趣的产品', 'https://www.producthunt.com/', 'assets/images/logos/producthunt.png', 4, 1);
INSERT INTO `db_web` VALUES (25, 'NEXT', '不错过任何一个新产品', 'http://next.36kr.com/posts', 'assets/images/logos/NEXT.png', 4, 1);
INSERT INTO `db_web` VALUES (26, '少数派', '高品质数字消费指南', 'https://sspai.com/', 'assets/images/logos/sspai.png', 4, 1);

-- ----------------------------
-- Table structure for db_webinfo
-- ----------------------------
DROP TABLE IF EXISTS `db_webinfo`;
CREATE TABLE `db_webinfo`  (
  `web_id` int(0) NOT NULL,
  `click_count` int(0) DEFAULT NULL,
  PRIMARY KEY (`web_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_webinfo
-- ----------------------------
INSERT INTO `db_webinfo` VALUES (1, 62);
INSERT INTO `db_webinfo` VALUES (2, 32);
INSERT INTO `db_webinfo` VALUES (3, 45);
INSERT INTO `db_webinfo` VALUES (4, 14);
INSERT INTO `db_webinfo` VALUES (5, 56);
INSERT INTO `db_webinfo` VALUES (6, 70);
INSERT INTO `db_webinfo` VALUES (7, 20);
INSERT INTO `db_webinfo` VALUES (8, 14);
INSERT INTO `db_webinfo` VALUES (9, 33);
INSERT INTO `db_webinfo` VALUES (10, 11);
INSERT INTO `db_webinfo` VALUES (11, 12);

SET FOREIGN_KEY_CHECKS = 1;
