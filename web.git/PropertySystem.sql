-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: PropertySystem
-- ------------------------------------------------------
-- Server version	5.5.44-0ubuntu0.14.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `apk_log`
--

DROP TABLE IF EXISTS `apk_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apk_log` (
  `al_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `version_code` varchar(30) DEFAULT NULL,
  `apk_name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `upload_time` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`al_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apk_log`
--

LOCK TABLES `apk_log` WRITE;
/*!40000 ALTER TABLE `apk_log` DISABLE KEYS */;
INSERT INTO `apk_log` VALUES (4,'v1.1','test.apk','wu',1448181687503),(5,'v1.1.2','test.apk','wu',1448181708230);
/*!40000 ALTER TABLE `apk_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `phone` varchar(15) NOT NULL COMMENT '手机号码',
  `passwd` varchar(45) DEFAULT NULL,
  `salt` varchar(15) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `register_time` bigint(25) DEFAULT NULL COMMENT '时间戳',
  `status` tinyint(2) DEFAULT '0' COMMENT '-1为删除,1为正常,0为禁用',
  `last_login` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES ('13398485955','123456',NULL,'Ryon',1446084081053,1,NULL),('13981111434','1234567',NULL,'xiaozhang',155515151500,1,NULL),('15114052120','123456',NULL,'xiaoming',155212121000,1,NULL),('18144240528','123456',NULL,'kangbiao',144444505050,-1,NULL),('18224489342','1234567',NULL,'何科松',1446643906750,1,NULL);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brake`
--

DROP TABLE IF EXISTS `brake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brake` (
  `brake_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `village_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`brake_id`),
  KEY `FK_2ue9kp9ltdrva3k3ib2hgx0yh` (`village_id`),
  CONSTRAINT `FK_2ue9kp9ltdrva3k3ib2hgx0yh` FOREIGN KEY (`village_id`) REFERENCES `village` (`village_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brake`
--

LOCK TABLES `brake` WRITE;
/*!40000 ALTER TABLE `brake` DISABLE KEYS */;
INSERT INTO `brake` VALUES (1,'DZ001','道闸一','',5),(3,'DZ005','道闸二','',5);
/*!40000 ALTER TABLE `brake` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `building_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `village_id` int(10) unsigned DEFAULT NULL COMMENT '园区和楼栋构成主键',
  `description` varchar(45) DEFAULT NULL,
  `building_code` varchar(45) DEFAULT NULL,
  `building_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`building_id`),
  KEY `FK_6odky3rdk3fxudn5blwdw34ny` (`village_id`),
  CONSTRAINT `FK_6odky3rdk3fxudn5blwdw34ny` FOREIGN KEY (`village_id`) REFERENCES `village` (`village_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,5,'','G1B1','园区1的楼栋1'),(5,7,'独栋','LD001','楼栋一'),(9,5,NULL,'LD002','楼栋二');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complain`
--

DROP TABLE IF EXISTS `complain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `description` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `time` bigint(25) DEFAULT NULL,
  `image_id_list` varchar(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `cu_id` int(10) unsigned DEFAULT NULL,
  `remark` tinyint(4) DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL COMMENT '处理投诉人员填写处理说明',
  PRIMARY KEY (`id`),
  KEY `FK_axsavu84vcq195a7mqw9x2xj4` (`cu_id`),
  CONSTRAINT `FK_axsavu84vcq195a7mqw9x2xj4` FOREIGN KEY (`cu_id`) REFERENCES `console_user` (`cu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complain`
--

LOCK TABLES `complain` WRITE;
/*!40000 ALTER TABLE `complain` DISABLE KEYS */;
INSERT INTO `complain` VALUES (20,'楼下taichao','吵','吵','18144240528',1445700296382,'33',NULL,0,NULL,NULL,NULL),(21,'啊啊啊啊','擦擦擦','擦擦擦','13398485955',1446113215442,'34',NULL,0,NULL,NULL,NULL),(22,'第二次测试','么么哒','么么哒','13398485955',1446113385277,'',NULL,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `complain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `console_group`
--

DROP TABLE IF EXISTS `console_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `console_group` (
  `cg_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `authorization` varchar(255) DEFAULT NULL,
  `menu` text NOT NULL,
  PRIMARY KEY (`cg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `console_group`
--

LOCK TABLES `console_group` WRITE;
/*!40000 ALTER TABLE `console_group` DISABLE KEYS */;
INSERT INTO `console_group` VALUES (4,'sdas',NULL,'{\"property\":\"on\",\"parkLot\":\"on\",\"user\":\"on\",\"fee\":\"on\",\"notice\":\"on\",\"complainRepair\":\"on\",\"secret\":\"on\",\"admin\":\"on\"}'),(5,'普通用户',NULL,'{\"property\":\"on\",\"parkLot\":\"on\",\"user\":\"off\",\"fee\":\"off\",\"notice\":\"off\",\"complainRepair\":\"off\",\"secret\":\"off\",\"admin\":\"off\"}');
/*!40000 ALTER TABLE `console_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `console_user`
--

DROP TABLE IF EXISTS `console_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `console_user` (
  `cu_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `console_group_id` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `identity_type` tinyint(4) DEFAULT NULL,
  `identity_id` varchar(45) DEFAULT NULL,
  `last_login` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`cu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `console_user`
--

LOCK TABLES `console_user` WRITE;
/*!40000 ALTER TABLE `console_user` DISABLE KEYS */;
INSERT INTO `console_user` VALUES (1,'e10adc3949ba59abbe56e057f20f883e','admin','1316278289@qq.com',4,'超级管理员',NULL,NULL,1449324434467),(5,'e10adc3949ba59abbe56e057f20f883e','normal','',5,'普通管理员',NULL,NULL,1447326985511);
/*!40000 ALTER TABLE `console_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_item`
--

DROP TABLE IF EXISTS `fee_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_item` (
  `fi_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `decription` varchar(200) DEFAULT NULL,
  `fee_type` tinyint(2) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `unit_price` decimal(11,2) DEFAULT NULL COMMENT '单位价格',
  `overdue_unit_price` decimal(11,2) DEFAULT NULL,
  `overdue_unit` varchar(10) DEFAULT NULL,
  `pay_start_time` bigint(25) DEFAULT NULL,
  `pay_end_time` bigint(25) DEFAULT NULL,
  `effective_start_time` bigint(25) DEFAULT NULL,
  `effective_end_time` bigint(25) DEFAULT NULL,
  `is_periodic` tinyint(2) DEFAULT NULL,
  `village_id` int(10) unsigned DEFAULT NULL,
  `extend_info` varchar(255) DEFAULT NULL,
  `add_time` bigint(25) DEFAULT NULL,
  `is_effective` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`fi_id`),
  KEY `FK_dkxt8mdgrp38dbw0py16g89en` (`village_id`),
  CONSTRAINT `FK_dkxt8mdgrp38dbw0py16g89en` FOREIGN KEY (`village_id`) REFERENCES `village` (`village_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_item`
--

LOCK TABLES `fee_item` WRITE;
/*!40000 ALTER TABLE `fee_item` DISABLE KEYS */;
INSERT INTO `fee_item` VALUES (1,'2',NULL,2,'',NULL,10.22,'day',145440515150,155440515150,144440515150,154440515150,1,5,'{\"monthPrice\":\"300\",\"perTimePrice\":\"15\",\"managePrice\":\"200\"}',14405151510,1),(7,'1',NULL,2,NULL,NULL,10.22,'month',1447084800000,1448467200000,1446307200000,1451577600000,1,5,'{\"monthPrice\":\"100\",\"perTimePrice\":\"4\",\"managePrice\":\"200\"}',1446450581474,1),(8,'1',NULL,2,NULL,NULL,2.00,'day',1446307200000,1446652800000,1446307200000,1456761600000,1,7,'{\"monthPrice\":\"120\",\"perTimePrice\":\"5\",\"managePrice\":\"300\"}',1446450830751,1),(9,'2',NULL,2,NULL,NULL,2.00,'day',1446307200000,1446652800000,1446307200000,1456761600000,1,7,'{\"monthPrice\":\"130\",\"perTimePrice\":\"5.5\",\"managePrice\":\"300.00\"}',1446450860702,1),(11,'3',NULL,2,NULL,NULL,2.00,'day',1446307200000,1446652800000,1446307200000,1456761600000,1,5,'{\"monthPrice\":\"130\",\"perTimePrice\":\"5.5\",\"managePrice\":\"100.00\"}',1446450880651,1),(13,'清洁费','清洁',1,'per',100.00,NULL,NULL,NULL,NULL,1446307200000,1451577600000,0,5,NULL,1446452294030,1),(16,'所属',NULL,0,'family',22.00,22.00,'day',1447084800000,1447257600000,1446307200000,1451577600000,1,5,'1,9',1446453262641,1),(17,'费用二',NULL,0,'squre',99.20,5.00,'day',1446307200000,1451491200000,1446307200000,1451577600000,1,5,'1,9',1448875219661,1),(18,'费用三',NULL,0,'squre',9.20,5.00,'day',1446307200000,1451491200000,1446307200000,1451577600000,1,7,'5',1448875233610,1),(19,'费用四',NULL,0,'squre',10.20,5.00,'day',1446307200000,1451491200000,1446307200000,1451577600000,1,7,'5',1448875242080,1),(20,'费用五',NULL,0,'family',10.20,5.00,'day',1446307200000,1451491200000,1446307200000,1451577600000,1,5,'1,9',1448875251493,1);
/*!40000 ALTER TABLE `fee_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_item_order`
--

DROP TABLE IF EXISTS `fee_item_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_item_order` (
  `fio_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_id` int(10) unsigned DEFAULT NULL,
  `fee_item_id` int(10) unsigned DEFAULT NULL,
  `is_billed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`fio_id`),
  KEY `FK_2eom30ry9yaakqhlrplfvs9u9` (`fee_item_id`),
  CONSTRAINT `FK_2eom30ry9yaakqhlrplfvs9u9` FOREIGN KEY (`fee_item_id`) REFERENCES `fee_item` (`fi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_item_order`
--

LOCK TABLES `fee_item_order` WRITE;
/*!40000 ALTER TABLE `fee_item_order` DISABLE KEYS */;
INSERT INTO `fee_item_order` VALUES (1,28,16,NULL),(2,29,16,NULL),(3,31,16,NULL),(11,30,18,NULL),(12,36,18,NULL),(13,39,18,NULL),(14,43,18,NULL),(15,44,18,NULL),(16,45,18,NULL),(17,46,18,NULL),(18,30,19,NULL),(19,36,19,NULL),(20,39,19,NULL),(21,43,19,NULL),(22,44,19,NULL),(23,45,19,NULL),(24,46,19,NULL),(25,28,20,NULL),(26,29,20,NULL),(27,31,20,NULL),(28,28,17,NULL),(29,29,17,NULL),(30,31,17,NULL);
/*!40000 ALTER TABLE `fee_item_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `notice_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text COMMENT '通知图文信息',
  `time` bigint(25) DEFAULT NULL,
  `picture_id_list` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL COMMENT '1.',
  `type` tinyint(2) DEFAULT NULL,
  `expiretime` bigint(25) DEFAULT NULL,
  `cu_id` int(10) unsigned DEFAULT NULL COMMENT '发布者的id',
  PRIMARY KEY (`notice_id`),
  KEY `FK_1nyd0erg10vfu0etf8fc8h0va` (`cu_id`),
  CONSTRAINT `FK_1nyd0erg10vfu0etf8fc8h0va` FOREIGN KEY (`cu_id`) REFERENCES `console_user` (`cu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (4,'测试公告','<h1>内容</h1>',1444899774481,'19','描述',NULL,1545356800000,1),(6,'周本顺杨栋梁严重违纪被双开','<span class=\"img_descr\"></span> \n<div class=\"img_wrapper\" align=\"center\">\n	<img alt=\"周本顺。资料图\" src=\"http://n.sinaimg.cn/transform/20151016/9T3d-fxivsch3638703.jpg\" /> <span class=\"img_descr\">周本顺。资料图</span> \n</div>\n<p>\n	<strong>河北省委原书记、省人大常委会原主任周本顺严重违纪被开除党籍和公职</strong>\n</p>\n<p>\n	日前，经中共中央批准，中共中央纪委对第十八届中央委员，河北省委原书记、省人大常委会原主任<a href=\"http://news.sina.com.cn/2015-09-01/doc-ifxhkafe6245218.shtml\" target=\"_blank\">周本顺严重违纪问题</a>进行了立案审查。\n</p>\n<p>\n	经查，<span>周本顺</span>严重违反政治纪律和政治规矩，在重大问题上发表违背中央精神的言论，不认真落实党风廉政建设主体责任，\n干扰、妨碍组织审查；严重违反组织纪律，为提拔职务进行非组织活动，违规选拔任用干部，隐瞒不报个人有关事项；严重违反中央八项规定精神，超标准公务接\n待、公款吃喝，频繁出入私人会所，生活奢侈、挥霍浪费，违反中央精简会议文件、改进宣传报道的有关规定；严重违反廉洁纪律，利用职务上的便利在企业经营等\n方面为他人谋取利益并收受财物，收受礼金、礼品，为其子经营活动谋取利益，家风败坏、对配偶子女放任纵容；严重违反工作纪律，私存涉密资料，泄露党和国家\n秘密。其中，利用职务上的便利为他人谋取利益，收受财物问题涉嫌犯罪。\n</p>\n<p>\n	周本顺身为中央委员，理想信念丧失，严重违反党的纪律，且党的十八大后仍不收敛、不收手，性质恶劣、情节特别严重。依据《中国共产党纪律处分条\n例》等有关规定，经中央纪委常委会议研究并报中共中央政治局会议审议，决定给予周本顺开除党籍、开除公职处分；收缴其违纪所得；将其涉嫌犯罪问题、线索及\n所涉款物移送司法机关依法处理。给予其开除党籍的处分，待召开中央委员会全体会议时予以追认。\n</p>\n<p>\n	<strong>国家安全生产监督管理总局原党组书记、局长杨栋梁严重违纪被开除党籍和公职</strong>\n</p>\n<p>\n	日前，经中共中央批准，中共中央纪委对第十八届中央委员，国家安全生产监督管理总局原党组书记、局长<a href=\"http://news.sina.com.cn/c/nd/2015-08-26/doc-ifxhcvry1080625.shtml\" target=\"_blank\">杨栋梁严重违纪问题</a>进行了立案审查。\n</p>',1444977998184,'21,22','河北省委原书记、省人大常委会原主任周本顺严重违纪被开除党籍和公职',NULL,1545443200000,1);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `open_door_record`
--

DROP TABLE IF EXISTS `open_door_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `open_door_record` (
  `odr_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) DEFAULT NULL,
  `open_time` bigint(25) DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '1',
  `description` varchar(100) DEFAULT NULL,
  `level` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`odr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `open_door_record`
--

LOCK TABLES `open_door_record` WRITE;
/*!40000 ALTER TABLE `open_door_record` DISABLE KEYS */;
INSERT INTO `open_door_record` VALUES (14,'18144240528',1446128156307,NULL,0,'手机问题',0),(15,'18144240528',1446560966923,NULL,0,'description',1),(16,'18144240528',1446624038646,'ydsyfdsf',0,'description',1);
/*!40000 ALTER TABLE `open_door_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owner` (
  `owner_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `birthday` bigint(25) DEFAULT NULL,
  `urgent_name` varchar(45) DEFAULT NULL,
  `urgent_phone` varchar(20) DEFAULT NULL,
  `identity_type` tinyint(2) DEFAULT NULL COMMENT '证件类型',
  `identity_code` varchar(45) DEFAULT NULL COMMENT '证件代码',
  `vehicle_id_ist` varchar(50) DEFAULT NULL COMMENT '1-n个车牌用分隔符＃作为分隔符',
  `property_id_list` varchar(50) DEFAULT NULL COMMENT '1-n个物业，用，号作为分隔符',
  `authentication_time` bigint(25) DEFAULT NULL COMMENT '放到审批记录表',
  PRIMARY KEY (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES (1,NULL,'康彪',0,1441641600000,'何科松','18224489342',1,'510704199405281715','','',1445529600000),(10,NULL,'小明1',0,NULL,NULL,NULL,1,'510704199405281715',NULL,NULL,1445356800000),(11,'15182372278','小明',0,915206400000,'康彪','18144240528',0,'510704199905065698',NULL,NULL,1422720000000);
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parking_lot`
--

DROP TABLE IF EXISTS `parking_lot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_lot` (
  `pl_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL COMMENT '车位编号',
  `floor` varchar(45) DEFAULT NULL COMMENT '楼层',
  `location` varchar(100) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `brake_id` int(10) unsigned DEFAULT NULL,
  `village_id` int(10) unsigned DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`pl_id`),
  KEY `FK_4kgpj7bke3cswtrv8jh1vkb70` (`brake_id`),
  CONSTRAINT `FK_4kgpj7bke3cswtrv8jh1vkb70` FOREIGN KEY (`brake_id`) REFERENCES `brake` (`brake_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_lot`
--

LOCK TABLES `parking_lot` WRITE;
/*!40000 ALTER TABLE `parking_lot` DISABLE KEYS */;
INSERT INTO `parking_lot` VALUES (1,'CW001',NULL,'蓝光米兰香洲道闸一',2,NULL,1,6,0),(2,'CW002',NULL,'车位地址二',1,'巴拉巴拉',1,6,0),(3,'CW009',NULL,'园区一道闸一',2,'',1,5,0);
/*!40000 ALTER TABLE `parking_lot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parklot_owner_info`
--

DROP TABLE IF EXISTS `parklot_owner_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parklot_owner_info` (
  `poi_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pl_id` int(10) unsigned DEFAULT NULL,
  `owner_phone` varchar(15) DEFAULT NULL,
  `owner_type` tinyint(2) DEFAULT NULL,
  `brake_id` int(10) unsigned DEFAULT NULL,
  `enter_brake_allowed` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`poi_id`),
  KEY `FK_9ey4yw5vexd37wpqp8eeb6wbl` (`pl_id`),
  CONSTRAINT `FK_9ey4yw5vexd37wpqp8eeb6wbl` FOREIGN KEY (`pl_id`) REFERENCES `parking_lot` (`pl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parklot_owner_info`
--

LOCK TABLES `parklot_owner_info` WRITE;
/*!40000 ALTER TABLE `parklot_owner_info` DISABLE KEYS */;
INSERT INTO `parklot_owner_info` VALUES (7,1,'18144240528',1,NULL,1),(9,3,'18144240528',3,NULL,1),(12,3,'15114052120',3,NULL,1),(13,3,'18144240527',4,NULL,1),(14,2,'13398485955',1,NULL,1);
/*!40000 ALTER TABLE `parklot_owner_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `upload_time` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (15,'20151020185229_238.jpg',1445338349298),(16,'20151020185229_726.jpg',1445338349304),(17,'20151020200424_586.jpg',1445342664309),(18,'20151020200424_142.jpg',1445342664326),(19,'20151023171656_889.jpg',1445591816229),(20,'20151023171656_985.jpg',1445591816234),(21,'20151023191327_823.jpg',1445598807305),(22,'20151023191327_820.jpg',1445598807355),(23,'20151023195951_376.png',1445601591516),(24,'20151023195951_707.jpg',1445601591529),(25,'20151023200206_797.jpg',1445601726073),(26,'20151024135652_286.jpg',1445666212694),(27,'20151024135652_263.png',1445666212723),(28,'20151024222839_468.jpg',1445696919771),(29,'20151024222839_742.jpg',1445696919775),(30,'20151024232252_452.jpg',1445700172221),(31,'20151024232252_731.jpg',1445700172236),(32,'20151024232342_87.jpg',1445700222123),(33,'20151024232456_635.jpg',1445700296385),(34,'20151029180655_651.jpg',1446113215490),(35,'20151029181119_122.jpg',1446113479149);
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `property_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL COMMENT '房产证编码',
  `location` varchar(100) DEFAULT NULL COMMENT '房产证上位置信息',
  `type` tinyint(2) DEFAULT NULL COMMENT '1:普通住房；2:商铺；3:车位',
  `property_square` decimal(11,2) DEFAULT NULL,
  `owner_type` tinyint(2) DEFAULT NULL COMMENT '1：个人所有；2:物业所有',
  `village_id` int(10) unsigned DEFAULT NULL COMMENT '园区',
  `building_id` int(10) unsigned NOT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '1.自住 ／出租\n2.已入住／未入住\n3.已收房／未收房',
  `open_door_status` tinyint(2) DEFAULT '1',
  `modify_time` bigint(25) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `FK_jhl7ds2u1dndpgntggd4l1m5n` (`building_id`),
  CONSTRAINT `FK_jhl7ds2u1dndpgntggd4l1m5n` FOREIGN KEY (`building_id`) REFERENCES `building` (`building_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (28,'A001','半山蓝湾一单元',1,115.55,1,5,1,1,1,1445581460913),(29,'A002','半山蓝湾二单元',1,222.11,1,5,1,1,1,1345504275966),(30,'B001','万科城市花园二单元十号',2,192.22,NULL,7,5,-1,NULL,1245505275966),(31,'B009','园区一二单元10号',1,99.50,NULL,5,1,-1,NULL,1444405275966),(36,'A005','半山蓝湾二单元十号',2,115.22,NULL,7,5,-1,1,1445689691071),(39,'A010','半山蓝湾二单元十号',2,115.22,NULL,7,5,-1,1,1445692785840),(43,'A011','半山蓝湾二单元十号',2,115.22,NULL,7,5,-1,1,1445696375591),(44,'A023','半山蓝湾二单元十号',2,115.22,NULL,7,5,-1,1,1446007330778),(45,'A0012','地址一',2,121.22,NULL,7,5,-1,1,1448803672295),(46,'A0013','地址二',2,111.22,NULL,7,5,1,1,1448803672419);
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_bill`
--

DROP TABLE IF EXISTS `property_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_bill` (
  `pb_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_id` int(10) unsigned DEFAULT NULL,
  `fee_item_fee` varchar(255) DEFAULT NULL COMMENT '存放由fee_item_id《＝》fee_amount组成的键值对，以逗号间隔。',
  `pay_status` tinyint(2) DEFAULT '0',
  `pay_type` tinyint(2) DEFAULT NULL,
  `pay_time` bigint(25) DEFAULT NULL,
  `bill_generation_time` bigint(25) DEFAULT NULL,
  `overdue_fee` decimal(11,2) DEFAULT '0.00',
  `payer` varchar(45) DEFAULT NULL COMMENT '付款人',
  PRIMARY KEY (`pb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_bill`
--

LOCK TABLES `property_bill` WRITE;
/*!40000 ALTER TABLE `property_bill` DISABLE KEYS */;
INSERT INTO `property_bill` VALUES (24,28,'所属:22.00;费用五:10.20;费用二:11462.56',0,NULL,NULL,1449326571357,NULL,NULL),(25,29,'所属:22.00;费用五:10.20;费用二:22033.31',0,NULL,NULL,1449326571411,NULL,NULL),(26,30,'费用三:1768.42;费用四:1960.64',0,NULL,NULL,1449326571458,NULL,NULL),(27,31,'所属:22.00;费用五:10.20;费用二:9870.40',0,NULL,NULL,1449326571501,NULL,NULL),(28,36,'费用三:1060.02;费用四:1175.24',0,NULL,NULL,1449326571546,NULL,NULL),(29,39,'费用三:1060.02;费用四:1175.24',0,NULL,NULL,1449326571589,NULL,NULL),(30,43,'费用三:1060.02;费用四:1175.24',0,NULL,NULL,1449326571635,NULL,NULL),(31,44,'费用三:1060.02;费用四:1175.24',0,NULL,NULL,1449326571680,NULL,NULL),(32,45,'费用三:1115.22;费用四:1236.44',0,NULL,NULL,1449326571723,NULL,NULL),(33,46,'费用三:1023.22;费用四:1134.44',0,NULL,NULL,1449326571789,NULL,NULL);
/*!40000 ALTER TABLE `property_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_owner_info`
--

DROP TABLE IF EXISTS `property_owner_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property_owner_info` (
  `poi_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `property_id` int(10) unsigned DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0',
  `user_role` tinyint(2) DEFAULT NULL,
  `building_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`poi_id`),
  KEY `FK_7oq20rni7t96houjm1d7flnox` (`property_id`),
  KEY `FK_cmeyqayotl7586xjrqrlnvgm1` (`phone`),
  CONSTRAINT `FK_7oq20rni7t96houjm1d7flnox` FOREIGN KEY (`property_id`) REFERENCES `property` (`property_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_owner_info`
--

LOCK TABLES `property_owner_info` WRITE;
/*!40000 ALTER TABLE `property_owner_info` DISABLE KEYS */;
INSERT INTO `property_owner_info` VALUES (1,28,'18144240528',1,3,NULL),(2,29,'18144240528',1,3,NULL),(5,28,'13981111434',0,2,NULL),(7,31,'18144240528',1,3,NULL),(11,30,'15114052120',1,3,NULL),(12,29,'15114052120',1,1,NULL),(17,36,'13981111434',0,2,NULL),(18,28,'15182372278',1,3,1),(19,29,'15182372278',1,3,1);
/*!40000 ALTER TABLE `property_owner_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair`
--

DROP TABLE IF EXISTS `repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `description` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `repair_man_id` int(10) unsigned DEFAULT NULL,
  `submit_time` bigint(25) DEFAULT NULL,
  `process_time` bigint(25) DEFAULT NULL,
  `finish_time` bigint(25) DEFAULT NULL,
  `image_id_list` varchar(50) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '1.未提交2.已提交未处理3.处理中4处理完',
  `remark` varchar(10) DEFAULT NULL,
  `remark_text` varchar(100) DEFAULT NULL,
  `cu_id` int(10) unsigned DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL COMMENT '维修人员填写的处理说明',
  PRIMARY KEY (`id`),
  KEY `FK_oc5vf2v77386b82n98ejj4qcd` (`cu_id`),
  KEY `FK_cwawwq5yle93o0u418f6r9t2l` (`repair_man_id`),
  CONSTRAINT `FK_cwawwq5yle93o0u418f6r9t2l` FOREIGN KEY (`repair_man_id`) REFERENCES `repair_man` (`rp_id`),
  CONSTRAINT `FK_oc5vf2v77386b82n98ejj4qcd` FOREIGN KEY (`cu_id`) REFERENCES `console_user` (`cu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair`
--

LOCK TABLES `repair` WRITE;
/*!40000 ALTER TABLE `repair` DISABLE KEYS */;
INSERT INTO `repair` VALUES (17,'test','testcontent','testcontent','18144240528',1,1445601726069,1445601733972,1445601752302,'25',3,'22','很好',1,NULL),(18,'test','afdsfdfasfd','afdsfdfasfd','18144240528',2,1445696919683,1445696962017,1445697047070,'28,29',2,NULL,NULL,1,NULL),(19,'楼道扶手坏了','坏了','坏了','18144240528',1,1445700172158,1445700190803,NULL,'30,31',1,NULL,NULL,1,NULL),(20,'屋顶漏水','漏水','漏水','18144240528',NULL,1445700222119,NULL,NULL,'32',0,NULL,NULL,NULL,NULL),(21,'椅子','椅子坏了','椅子坏了','18144240528',2,1446105475363,1446115337155,NULL,'',1,NULL,NULL,1,NULL),(22,'第一次测试','啦啦啦','啦啦啦','13398485955',NULL,1446119325317,NULL,NULL,'',0,NULL,NULL,NULL,NULL),(23,'第四次测试','擦擦擦','擦擦擦','13398485955',NULL,1446119698642,NULL,NULL,'',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `repair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_man`
--

DROP TABLE IF EXISTS `repair_man`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair_man` (
  `rp_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL COMMENT '手机号码',
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`rp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_man`
--

LOCK TABLES `repair_man` WRITE;
/*!40000 ALTER TABLE `repair_man` DISABLE KEYS */;
INSERT INTO `repair_man` VALUES (1,'15114052120','小明'),(2,'18144240528','小张');
/*!40000 ALTER TABLE `repair_man` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ssid_secret`
--

DROP TABLE IF EXISTS `ssid_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ssid_secret` (
  `ss_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `control_id` int(10) unsigned DEFAULT NULL,
  `control_type` tinyint(2) DEFAULT NULL,
  `type` tinyint(2) NOT NULL,
  `village_id` int(10) unsigned DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  `secret` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ss_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ssid_secret`
--

LOCK TABLES `ssid_secret` WRITE;
/*!40000 ALTER TABLE `ssid_secret` DISABLE KEYS */;
INSERT INTO `ssid_secret` VALUES (9,5,1,1,NULL,'YC_D_001','123456789',NULL),(10,5,2,2,NULL,'YC_D_002','123456789','123456'),(11,1,3,1,NULL,'YC_D_003','12345678',NULL),(12,NULL,NULL,1,NULL,'YC_D_004','1236632',NULL);
/*!40000 ALTER TABLE `ssid_secret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bill`
--

DROP TABLE IF EXISTS `user_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bill` (
  `ub_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bill_seris` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `property_bill` varchar(255) DEFAULT NULL,
  `service_bill` varchar(255) DEFAULT NULL,
  `parklot_bill` varchar(255) DEFAULT NULL,
  `update_time` bigint(25) DEFAULT NULL,
  `pay_status` tinyint(2) DEFAULT NULL,
  `pay_type` tinyint(2) DEFAULT NULL,
  `pay_time` bigint(25) DEFAULT NULL,
  `overdue_fee` decimal(11,2) DEFAULT '0.00',
  PRIMARY KEY (`ub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bill`
--

LOCK TABLES `user_bill` WRITE;
/*!40000 ALTER TABLE `user_bill` DISABLE KEYS */;
INSERT INTO `user_bill` VALUES (52,'20151201213854553308','13398485955',NULL,NULL,'车位管理费(CW002):200',1449326571841,0,NULL,NULL,NULL),(53,'20151201213854651983','13981111434',NULL,NULL,'',1449326571890,0,NULL,NULL,NULL),(54,'20151201213854134420','15114052120',NULL,NULL,'车位月租费(CW009):300',1449326571935,0,NULL,NULL,NULL),(55,'20151201213854740490','18144240528',NULL,NULL,'车位管理费(CW001):200;车位月租费(CW009):300',1449326571982,0,NULL,NULL,NULL),(56,'20151201213854158452','18224489342',NULL,NULL,'',1449326572023,0,NULL,NULL,NULL),(57,'20151205212628494783','15182372278',NULL,NULL,'',1449326572069,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `ui_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL COMMENT '手机号码',
  `birthday` bigint(25) DEFAULT NULL,
  `name` varchar(15) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `urgent_name` varchar(15) DEFAULT NULL,
  `urgent_phone` varchar(15) DEFAULT NULL,
  `identity_type` tinyint(2) DEFAULT NULL,
  `identity_code` varchar(25) DEFAULT NULL,
  `vehicle_list` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ui_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `village`
--

DROP TABLE IF EXISTS `village`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `village` (
  `village_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`village_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `village`
--

LOCK TABLES `village` WRITE;
/*!40000 ALTER TABLE `village` DISABLE KEYS */;
INSERT INTO `village` VALUES (5,'园区一','YQ001',''),(7,'万科城市花园','wk001','川师附近');
/*!40000 ALTER TABLE `village` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-05 22:57:27
