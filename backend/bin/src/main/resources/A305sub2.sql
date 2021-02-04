-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: subdb
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `commentNo` int NOT NULL AUTO_INCREMENT,
  `content` varchar(100) NOT NULL,
  `email` varchar(45) NOT NULL,
  `postNo` int NOT NULL,
  `uploadDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `modifyDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `likeCnt` int DEFAULT '0',
  PRIMARY KEY (`commentNo`),
  KEY `FK_PostNum_Comment_idx` (`postNo`),
  KEY `FK_commentWriter_Comment_idx` (`email`),
  CONSTRAINT `FK_COMMENT_EMAIL` FOREIGN KEY (`email`) REFERENCES `member` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_PostNum_Comment` FOREIGN KEY (`postNo`) REFERENCES `post` (`postNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (68,'test','admin',99,'2021-01-29 00:52:47','2021-01-29 00:52:47',0),(69,'test','admin',99,'2021-01-29 00:53:48','2021-01-29 00:53:48',0),(70,'test','admin',99,'2021-01-29 00:53:48','2021-01-29 00:53:48',0),(71,'test','admin',99,'2021-01-29 00:53:48','2021-01-29 00:53:48',0),(72,'test','admin',99,'2021-01-29 00:53:48','2021-01-29 00:53:48',0),(73,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(74,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(75,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(76,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(77,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(78,'test','admin',99,'2021-01-29 00:53:49','2021-01-29 00:53:49',0),(79,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(80,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(81,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(82,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(83,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(84,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(85,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(86,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(87,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(88,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(89,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(90,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(91,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(92,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(93,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(94,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(95,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(96,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(97,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(98,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(99,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(100,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(101,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(102,'test','admin',99,'2021-01-29 00:54:19','2021-01-29 00:54:19',0),(103,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(104,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(105,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(106,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(107,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(108,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(109,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(110,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(111,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(112,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(113,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(114,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(115,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(116,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(117,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0),(118,'test','admin',99,'2021-01-29 00:54:20','2021-01-29 00:54:20',0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_member_like`
--

DROP TABLE IF EXISTS `comment_member_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_member_like` (
  `cmLikeNo` int NOT NULL AUTO_INCREMENT,
  `commentNo` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `likeCheck` char(1) DEFAULT 'N',
  PRIMARY KEY (`cmLikeNo`),
  KEY `FK_USERNO_idx` (`email`),
  KEY `FK_COMMENTNO_idx` (`commentNo`),
  CONSTRAINT `FK_CMLIKE_COMMENTNO` FOREIGN KEY (`commentNo`) REFERENCES `comment` (`commentNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_CMLIKE_USERNO` FOREIGN KEY (`email`) REFERENCES `member` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_member_like`
--

LOCK TABLES `comment_member_like` WRITE;
/*!40000 ALTER TABLE `comment_member_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_member_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `email` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `joinDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `role` varchar(10) DEFAULT 'user',
  `introduce` varchar(100) DEFAULT NULL,
  `profileImg` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('a@a.com','123','name','2021-01-25 07:42:03','user','dafasdfadsf','C:\\SSAFY\\uploaded\\숨바꼭질 3.jpeg'),('admin','admin','관리자','2021-01-22 11:35:19','admin',NULL,'C:\\SSAFY\\uploaded\\tree-276014_1920.jpg'),('laugh4mile@naver.com','qwer1234','김영록','2021-01-29 01:08:33','user','내가 싸피의 자랑 김영록이다. 알겠나?','C:\\SSAFY\\uploaded\\bird-1045954_1920.jpg');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `postNo` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `uploadDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `modifyDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `likeCnt` int DEFAULT '0',
  `email` varchar(45) NOT NULL,
  `temp` int DEFAULT '0',
  PRIMARY KEY (`postNo`),
  KEY `FK_Writer_idx` (`email`),
  CONSTRAINT `FK_POST_EMAIL` FOREIGN KEY (`email`) REFERENCES `member` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (50,'환영합니다','자유롭게 활동해 주세요!','2021-01-29 00:45:44','2021-01-29 00:45:44',0,'admin',0),(51,'2번글','','2021-01-29 00:46:19','2021-01-29 00:46:19',0,'admin',0),(52,'3번글','','2021-01-29 00:46:26','2021-01-29 00:46:26',0,'admin',0),(53,'4번글','','2021-01-29 00:46:31','2021-01-29 00:46:31',0,'admin',0),(54,'5번글','','2021-01-29 00:46:34','2021-01-29 00:46:34',0,'admin',0),(55,'6번글','','2021-01-29 00:46:38','2021-01-29 00:46:38',0,'admin',0),(56,'7번글','','2021-01-29 00:46:43','2021-01-29 00:46:43',0,'admin',0),(57,'8번글','테스트','2021-01-29 00:48:08','2021-01-29 00:48:08',0,'admin',0),(58,'9번글','테스트','2021-01-29 00:48:37','2021-01-29 00:48:37',0,'admin',0),(59,'10번글','테스트','2021-01-29 00:48:37','2021-01-29 00:48:37',0,'admin',0),(60,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(61,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(62,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(63,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(64,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(65,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(66,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(67,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(68,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(69,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(70,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(71,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(72,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(73,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(74,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(75,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(76,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(77,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(78,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(79,'test','테스트','2021-01-29 00:49:15','2021-01-29 00:49:15',0,'admin',0),(80,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(81,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(82,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(83,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(84,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(85,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(86,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(87,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(88,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(89,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(90,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(91,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(92,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(93,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(94,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(95,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(96,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(97,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(98,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',0,'admin',0),(99,'test','테스트','2021-01-29 00:49:43','2021-01-29 00:49:43',1,'admin',0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_member_like`
--

DROP TABLE IF EXISTS `post_member_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_member_like` (
  `pmLikeNo` int NOT NULL AUTO_INCREMENT,
  `postNo` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `likeCheck` char(1) DEFAULT 'N',
  PRIMARY KEY (`pmLikeNo`),
  KEY `FK_POSTNO_idx` (`postNo`),
  KEY `FK_USERNO_idx` (`email`),
  CONSTRAINT `FK_PMLIKE_POSTNO` FOREIGN KEY (`postNo`) REFERENCES `post` (`postNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_PMLIKE_USERNO` FOREIGN KEY (`email`) REFERENCES `member` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_member_like`
--

LOCK TABLES `post_member_like` WRITE;
/*!40000 ALTER TABLE `post_member_like` DISABLE KEYS */;
INSERT INTO `post_member_like` VALUES (7,99,'admin','N'),(8,99,'laugh4mile@naver.com','Y');
/*!40000 ALTER TABLE `post_member_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_pics`
--

DROP TABLE IF EXISTS `post_pics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_pics` (
  `picNo` int NOT NULL AUTO_INCREMENT,
  `postNo` int NOT NULL,
  `oriPicName` varchar(260) NOT NULL,
  `modPicName` varchar(36) NOT NULL,
  `picSize` int DEFAULT NULL,
  `uploadDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`picNo`),
  KEY `FK_PostNo_idx` (`postNo`),
  CONSTRAINT `FK_PostNo` FOREIGN KEY (`postNo`) REFERENCES `post` (`postNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_pics`
--

LOCK TABLES `post_pics` WRITE;
/*!40000 ALTER TABLE `post_pics` DISABLE KEYS */;
INSERT INTO `post_pics` VALUES (30,50,'cat-2536662_1920.jpg','PmC4YwOGWYdhGC131my7pxLaGJDXPuvr.jpg',484544,'2021-01-29 00:45:44'),(31,99,'zz.jpg','tree-736885_1280.jpg',484544,'2021-01-29 01:14:58'),(32,99,'z','tree-276014_1920.jpg',NULL,'2021-01-29 01:17:08'),(33,99,'zz','mountain-landscape-2031539_1920.jpg',NULL,'2021-01-29 01:17:37'),(34,99,'zzz','egret-5937499_1920.jpg',NULL,'2021-01-29 01:18:32'),(35,99,'zzzz','forest-5903010_1920.jpg',NULL,'2021-01-29 01:19:45'),(36,99,'zzzzz','purple-5935577_1920.jpg',NULL,'2021-01-29 01:20:31');
/*!40000 ALTER TABLE `post_pics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_voted`
--

DROP TABLE IF EXISTS `post_voted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_voted` (
  `votedNo` int NOT NULL AUTO_INCREMENT,
  `picNo` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `postNo` int NOT NULL,
  PRIMARY KEY (`votedNo`),
  KEY `FK_PICNO_idx` (`picNo`),
  KEY `FK_USERNO_idx` (`email`),
  KEY `FK_POSTNO_idx` (`postNo`),
  CONSTRAINT `FK_VOTED_PICNO` FOREIGN KEY (`picNo`) REFERENCES `post_pics` (`picNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_VOTED_POSTNO` FOREIGN KEY (`postNo`) REFERENCES `post` (`postNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_VOTED_USERNO` FOREIGN KEY (`email`) REFERENCES `member` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_voted`
--

LOCK TABLES `post_voted` WRITE;
/*!40000 ALTER TABLE `post_voted` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_voted` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-29 11:01:47
