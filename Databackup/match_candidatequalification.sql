CREATE DATABASE  IF NOT EXISTS `match` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `match`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: match
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `candidatequalification`
--

DROP TABLE IF EXISTS `candidatequalification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidatequalification` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Qualification` varchar(45) NOT NULL,
  `levelOfExperience` int NOT NULL,
  `candidateID` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidatequalification`
--

LOCK TABLES `candidatequalification` WRITE;
/*!40000 ALTER TABLE `candidatequalification` DISABLE KEYS */;
INSERT INTO `candidatequalification` VALUES (1,'MS Access',5,1),(2,'Visual Basic',5,1),(3,'SQL Server Admin',3,1),(4,'T-SQL',3,1),(5,'C#',4,1),(6,'MVC',5,2),(7,'HTML',5,2),(8,'JAVA',5,2),(9,'MySQL',3,2),(10,'HTML',5,3),(11,'SCRUM Master',5,3),(12,'JQUERY',3,3),(13,'T-SQL',4,3),(14,'LINQ',5,3),(15,'Fortran',5,4),(16,'LISP',5,4),(17,'JAVA',5,4),(18,'C#',5,4),(19,'RPG',5,4),(20,'COBOL',5,4),(21,'MS Access',5,2),(22,'Visual Basic',5,2);
/*!40000 ALTER TABLE `candidatequalification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-23 12:20:30
