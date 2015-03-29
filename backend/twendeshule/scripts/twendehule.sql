-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: twendeshule
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.12.04.1

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `car_owner` int(11) NOT NULL,
  `car_model` varchar(255) NOT NULL,
  `car_reg` varchar(20) NOT NULL,
  `car_capacity` int(11) NOT NULL,
  PRIMARY KEY (`car_id`),
  KEY `car_ibfk_1` (`car_owner`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`car_owner`) REFERENCES `parent` (`parent_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (1,1,'Toyota Premio','KBQ123F',5),(2,2,'Toyota Hilux','KBX 012B',4);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grp`
--

DROP TABLE IF EXISTS `grp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grp` (
  `grp_id` int(11) NOT NULL AUTO_INCREMENT,
  `grp_name` varchar(255) NOT NULL,
  `grp_leader` int(11) NOT NULL,
  `grp_desc` varchar(255) DEFAULT NULL,
  `grp_image` varchar(255) DEFAULT NULL,
  `date_formed` datetime DEFAULT NULL,
  PRIMARY KEY (`grp_id`),
  KEY `grp_leader` (`grp_leader`),
  CONSTRAINT `grp_ibfk_1` FOREIGN KEY (`grp_leader`) REFERENCES `parent` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grp`
--

LOCK TABLES `grp` WRITE;
/*!40000 ALTER TABLE `grp` DISABLE KEYS */;
INSERT INTO `grp` VALUES (1,'Pipeline Hustlers',1,'All hustles from Pipeline are welcome to this group.',NULL,NULL),(2,'obajo',1,'obadia joash',NULL,NULL);
/*!40000 ALTER TABLE `grp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kid`
--

DROP TABLE IF EXISTS `kid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kid` (
  `kid_id` int(11) NOT NULL AUTO_INCREMENT,
  `kid_name` varchar(255) NOT NULL,
  `kid_gender` varchar(255) NOT NULL,
  `kid_age` int(11) NOT NULL,
  `kid_sch` int(11) NOT NULL,
  `kid_parent` int(11) NOT NULL,
  `kid_status` smallint(6) DEFAULT '0',
  PRIMARY KEY (`kid_id`),
  KEY `kid_sch` (`kid_sch`),
  KEY `kid_parent` (`kid_parent`),
  CONSTRAINT `kid_ibfk_1` FOREIGN KEY (`kid_sch`) REFERENCES `school` (`school_id`),
  CONSTRAINT `kid_ibfk_2` FOREIGN KEY (`kid_parent`) REFERENCES `parent` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kid`
--

LOCK TABLES `kid` WRITE;
/*!40000 ALTER TABLE `kid` DISABLE KEYS */;
INSERT INTO `kid` VALUES (1,'Liy Ndinda','GIRL',9,2,1,0),(2,'Chris','BOY',8,2,2,0);
/*!40000 ALTER TABLE `kid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `members_id` int(11) NOT NULL AUTO_INCREMENT,
  `grp_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  PRIMARY KEY (`members_id`),
  KEY `parent_id` (`parent_id`),
  KEY `grp_id` (`grp_id`),
  CONSTRAINT `members_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`parent_id`),
  CONSTRAINT `members_ibfk_2` FOREIGN KEY (`grp_id`) REFERENCES `grp` (`grp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,1,1),(2,2,1);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_name` varchar(255) NOT NULL,
  `parent_email` varchar(255) DEFAULT NULL,
  `parent_password` varchar(255) DEFAULT NULL,
  `parent_phone` varchar(255) DEFAULT NULL,
  `parent_residence` varchar(255) DEFAULT NULL,
  `lat` varchar(20) DEFAULT NULL,
  `lon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (1,'Joash Musyoka','muthangyamusyoka@gmail.com','joash@2014','0726910857','Pipeline',NULL,NULL),(2,'Obadia Vundi','vkangweobadiah@gmail.com','obad','0728450362','Kobil - Embakasi',NULL,NULL);
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `grp_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `reminder` int(11) NOT NULL,
  `num_days` int(11) NOT NULL,
  `event_id` varchar(255) NOT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `parent_id` (`parent_id`),
  KEY `grp_id` (`grp_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`parent_id`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`grp_id`) REFERENCES `grp` (`grp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `school_id` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(255) NOT NULL,
  `school_address` varchar(255) NOT NULL,
  `school_location` varchar(20) NOT NULL,
  `school_classes` int(11) NOT NULL,
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
INSERT INTO `school` VALUES (1,'Peponi School','1254 - 00200 Nairobi','Nairobi - Kenya',10),(2,'Hilcrest Academy','3562 - 00200 Nairobi','Nairobi - Kenya',10),(3,'Light Academy','85642 - 00200 Nairobi','Nairobi - Kenya',10);
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-23 23:11:03
