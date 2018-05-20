CREATE DATABASE  IF NOT EXISTS `matchpoint` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `matchpoint`;
-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: matchpoint
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_date` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nofication_date` datetime DEFAULT NULL,
  `registration_last_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9ks4rm4534e6bxu7gxovvojpb` (`name`,`start_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'2017-08-06 00:00:00','Matchpoint','Matchpoint Internal league','2017-07-30 00:00:00','2017-07-03 00:00:00','2017-08-04 00:00:00'),(2,'2017-08-20 00:00:00','Malleshwaram','Canara Union','2017-07-31 00:00:00','2017-08-15 00:00:00','2017-08-16 00:00:00');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_registration`
--

DROP TABLE IF EXISTS `event_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_registration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cb` bit(1) DEFAULT NULL,
  `cg` bit(1) DEFAULT NULL,
  `jb` bit(1) DEFAULT NULL,
  `jg` bit(1) DEFAULT NULL,
  `mcb` bit(1) DEFAULT NULL,
  `mcg` bit(1) DEFAULT NULL,
  `ms` bit(1) DEFAULT NULL,
  `nmd` bit(1) DEFAULT NULL,
  `nms` bit(1) DEFAULT NULL,
  `sjb` bit(1) DEFAULT NULL,
  `sjg` bit(1) DEFAULT NULL,
  `user_dob` datetime NOT NULL,
  `ws` bit(1) DEFAULT NULL,
  `yb` bit(1) DEFAULT NULL,
  `yg` bit(1) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4luonmc0xuogbj97rm90k5r8b` (`user_id`,`event_id`),
  KEY `FK2ra3ocp6gfw2vbwybbooxxuml` (`event_id`),
  CONSTRAINT `FK2ra3ocp6gfw2vbwybbooxxuml` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `FKsiqqwnfa1t1rybxsi424kfxh1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_registration`
--

LOCK TABLES `event_registration` WRITE;
/*!40000 ALTER TABLE `event_registration` DISABLE KEYS */;
INSERT INTO `event_registration` VALUES (1,'\0','\0','\0','\0','\0','\0','\0','\0','','\0','\0','1992-03-27 00:00:00','\0','\0','\0',1,1);
/*!40000 ALTER TABLE `event_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `payment_date` datetime NOT NULL,
  `payment_mode` varchar(255) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `transcation_id` varchar(255) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK95mdx4gcoy5aacmes6h5fxhwr` (`product_id`),
  KEY `FK4spfnm9si9dowsatcqs5or42i` (`user_id`),
  CONSTRAINT `FK4spfnm9si9dowsatcqs5or42i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK95mdx4gcoy5aacmes6h5fxhwr` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,750.00,'Matchpoint new member registration fee','2017-09-24 00:28:34','online','initialized',NULL,1,3,NULL),(2,750.00,'Matchpoint new member registration fee','2017-09-24 00:30:49','online','initialized','d2940403-6ede-49a4-a2ef-b517cdad43da2',1,3,NULL),(3,750.00,'Matchpoint new member registration fee','2017-09-24 00:47:03','online','initialized','bcf656ee-f3fc-4e37-9524-5e74674412f33',1,3,NULL),(4,750.00,'Matchpoint new member registration fee','2017-09-24 17:15:59','online','initialized','e313d4a1-7e8b-4571-9c67-2071b12e68c64',1,3,NULL),(5,750.00,'Matchpoint new member registration fee','2017-09-24 17:17:57','online','initialized','c0dadbe0-92fd-48cf-bcae-9d944858786f5',1,3,NULL),(6,1700.00,'Matchpoint monthly fee','2017-09-24 17:23:09','online','completed','647336db-800f-4f0b-be0d-37e8a4b335e26',2,3,'ce0f4d135515450f86b28970ed097276');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,750.00,'Matchpoint new member registration fee','ClubRegistryFee'),(2,1700.00,'Matchpoint monthly fee','MonthlyFee');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'member');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `dob` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'\0','1992-03-27 00:00:00','rprithviprakash@gmail.com','Prithviprakash',NULL,'8015972163','$2a$10$bHoje5XN6zpJ17IMd6QP/utYTRFDe9J.5ETMT/688/0on.ioOhc2e'),(2,'','2017-07-12 00:00:00','prithviprakash@gmail.com','Gokul','R','8015972163','$2a$10$t7jAiVt8L2O0mUqsWNqL4e2UQ8tCiw5co7ZgOPPTcuwcyo8gfkg/W'),(3,'','1972-07-15 00:00:00','matchpointtta@gmail.com','Sandeep','Kannambadi','9538925439','$2a$10$ucaWtdh7VPDAcA6WgN9FWemVXjOP3Xp49AOP6zXD8XbkZh2cCHjq6'),(10,'','1992-03-27 00:00:00','prithviprakashr@gmail.com','Prithviprakash','R','8015972163','$2a$10$lXYH1FNTcdFr66Wu6Yc3H.2Y33sZpwn5B8ZnHUQOB6brEaPSmeu1.');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_queries`
--

DROP TABLE IF EXISTS `user_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_queries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_queries`
--

LOCK TABLES `user_queries` WRITE;
/*!40000 ALTER TABLE `user_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKeog8p06nu33ihk13roqnrp1y6` (`roles_id`),
  CONSTRAINT `FKeog8p06nu33ihk13roqnrp1y6` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKip2yfnw7nx55paaa4i18j7moj` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-20 10:13:09
