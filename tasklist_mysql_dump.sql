-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: tasklist
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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `completed_count` bigint DEFAULT '0',
  `uncompleted_count` bigint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Family',1,0),(2,'Work',1,1),(3,'Recreation',1,0),(4,'Travels',0,0),(5,'Sport',0,0),(6,'Health',0,0),(7,'Development',NULL,NULL),(8,'Finance',NULL,NULL),(9,'Study',NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `priority`
--

DROP TABLE IF EXISTS `priority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `priority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `color` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `priority`
--

LOCK TABLES `priority` WRITE;
/*!40000 ALTER TABLE `priority` DISABLE KEYS */;
INSERT INTO `priority` VALUES (1,'Low','#caffdd'),(2,'Medium','#883bdc'),(3,'High','#f05f5f'),(4,'Very High','#dfff00'),(5,'Extremely High','#000000');
/*!40000 ALTER TABLE `priority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stat`
--

DROP TABLE IF EXISTS `stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `completed_total` bigint DEFAULT '0',
  `uncompleted_total` bigint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stat`
--

LOCK TABLES `stat` WRITE;
/*!40000 ALTER TABLE `stat` DISABLE KEYS */;
INSERT INTO `stat` VALUES (1,3,1);
/*!40000 ALTER TABLE `stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `completed` int DEFAULT '0',
  `date` datetime DEFAULT NULL,
  `priority_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_title` (`title`),
  KEY `index_completed` (`completed`),
  KEY `index_date` (`date`),
  KEY `fk_category_idx` (`category_id`),
  KEY `fk_priority_idx` (`priority_id`),
  CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_priority` FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Call parents',1,NULL,3,1),(2,'Watch cartoons',1,NULL,2,3),(3,'Learn Java',1,NULL,1,2),(4,'Call boss',0,NULL,NULL,2);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `task_AFTER_INSERT` AFTER INSERT ON `task` FOR EACH ROW BEGIN

	/* the category is NON-EMPTY          and      the task status is COMPLETED */
	 if (ifnull(NEW.category_id, 0)>0      &&      ifnull(NEW.completed, 0)=1) then
		update tasklist.category set completed_count = (ifnull(completed_count, 0)+1) where id = NEW.category_id;
	end if;

	/* the category is NON-EMPTY         and      the task status is NOT-COMPLETED */
    if (ifnull(NEW.category_id, 0)>0      &&      ifnull(NEW.completed, 0)=0) then
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count, 0)+1) where id = NEW.category_id;
	end if;

	/* general statistics */
	if ifnull(NEW.completed, 0)=1 then
		update tasklist.stat set completed_total = (ifnull(completed_total, 0)+1)  where id=1;
	else
		update tasklist.stat set uncompleted_total = (ifnull(uncompleted_total, 0)+1)  where id=1;
    end if;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `task_AFTER_UPDATE` AFTER UPDATE ON `task` FOR EACH ROW BEGIN

	/* changed completed to 1, DIDN'T change the category */
	IF (   ifnull(old.completed,0) <> ifnull(new.completed,0)  &&   new.completed=1      &&   ifnull(old.category_id,0) = ifnull(new.category_id,0)     ) THEN    
		
        /* for a category, the count of uncompleted will decrease by 1, the count of completed  will increase by 1 */
        update tasklist.category set uncompleted_count = (ifnull(uncompleted_count, 0)-1), completed_count = (ifnull(completed_count,0)+1) where id = old.category_id; 
		
        /* general statistics */
		update tasklist.stat set uncompleted_total = (ifnull(uncompleted_total,0)-1), completed_total = (ifnull(completed_total,0)+1)  where id=1;

	END IF;
    
    /* changed completed to 0, DIDN'T change the category */
    IF (   ifnull(old.completed,0) <> ifnull(new.completed,0)    &&   new.completed=0       &&   ifnull(old.category_id,0) = ifnull(new.category_id,0)   ) THEN    
    
		/* for a category, the count of completed will decrease by 1, the count of uncompleted  will increase by 1 */
		update tasklist.category set completed_count = (ifnull(completed_count,0)-1), uncompleted_count = (ifnull(uncompleted_count,0)+1) where id = old.category_id; 
       
       /* general statistics */
		update tasklist.stat set completed_total = (ifnull(completed_total,0)-1), uncompleted_total = (ifnull(uncompleted_total,0)+1)  where id=1;

	END IF;
    
    /* changed the category for the unchanged completed=1 */
    IF (   ifnull(old.completed,0) = ifnull(new.completed,0)    &&   new.completed=1       &&   ifnull(old.category_id,0) <> ifnull(new.category_id,0)    ) THEN    
    
		/* for the old category, the count of completed will decrease by 1 */
		update tasklist.category set completed_count = (ifnull(completed_count,0)-1) where id = old.category_id; 
		
        /* for the new category, the count of completed will increase by 1 */
		update tasklist.category set completed_count = (ifnull(completed_count,0)+1) where id = new.category_id; 
        
        /* general statistics do not change */
       
	END IF;
    
    /* changed the category for the unchanged completed=0 */
    IF (   ifnull(old.completed,0) = ifnull(new.completed,0)     &&   new.completed=0      &&   ifnull(old.category_id,0) <> ifnull(new.category_id,0)     ) THEN    
    
		/* for the old category, the count of uncompleted will decrease by 1 */
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count,0)-1) where id = old.category_id; 

		/* for the new category, the count of uncompleted will increase by 1 */
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count,0)+1) where id = new.category_id;
        
        /* general statistics do not change */
       
	END IF;
   
    /* changed the category, changed completed from 1 to 0 */
    IF (   ifnull(old.completed,0) <> ifnull(new.completed,0)     &&   new.completed=0      &&   ifnull(old.category_id,0) <> ifnull(new.category_id,0)     ) THEN    
    
		/* for the old category, the count of completed will decrease by 1 */
		update tasklist.category set completed_count = (ifnull(completed_count,0)-1) where id = old.category_id; 
        
        /* for the new category, the count of uncompleted will increase by 1 */
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count,0)+1) where id = new.category_id; 

		/* general statistics */
		update stat set uncompleted_total = (ifnull(uncompleted_total,0)+1), completed_total = (ifnull(completed_total,0)-1)  where id=1;

	END IF;
    
    /* changed the category, changed completed from 0 to 1 */
    IF (   ifnull(old.completed,0) <> ifnull(new.completed,0)     &&   new.completed=1      &&   ifnull(old.category_id,0) <> ifnull(new.category_id,0)     ) THEN    
    
		/* for the old category, the count of uncompleted will decrease by 1 */
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count,0)-1) where id = old.category_id; 
        
        /* for the new category, the count of completed will increase by 1 */
		update tasklist.category set completed_count = (ifnull(completed_count,0)+1) where id = new.category_id; 
        
        /* general statistics */
		update tasklist.stat set uncompleted_total = (ifnull(uncompleted_total,0)-1), completed_total = (ifnull(completed_total,0)+1)  where id=1;
	
	END IF;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `task_AFTER_DELETE` AFTER DELETE ON `task` FOR EACH ROW BEGIN
	
    /* the category is NON-EMPTY          and      the task status is COMPLETED */
    if (ifnull(old.category_id, 0)>0       &&       ifnull(old.completed, 0)=1) then
		update tasklist.category set completed_count = (ifnull(completed_count, 0)-1) where id = old.category_id;
	end if;
    
    /* the category is NON-EMPTY         and      the task status is NOT-COMPLETED */
    if (ifnull(old.category_id, 0)>0      &&        ifnull(old.completed, 0)=0) then
		update tasklist.category set uncompleted_count = (ifnull(uncompleted_count, 0)-1) where id = old.category_id;
	end if;
    
    /* general statistics */
	if ifnull(old.completed, 0)=1 then
		update tasklist.stat set completed_total = (ifnull(completed_total, 0)-1)  where id=1;
	else
		update tasklist.stat set uncompleted_total = (ifnull(uncompleted_total, 0)-1)  where id=1;
    end if;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'tasklist'
--

--
-- Dumping routines for database 'tasklist'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-16 20:57:26
