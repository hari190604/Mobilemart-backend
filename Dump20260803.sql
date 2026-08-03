-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: mobilemartdb
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `is_default` bit(1) NOT NULL,
  `mobile_number` varchar(255) NOT NULL,
  `postal_code` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `street_address` text NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,'Mumbai','India','2026-08-01 18:02:57.093471','John Doe',_binary '','9876543210','400001','Maharashtra','123 Main Street, Appt 4B','2026-08-01 18:02:57.093471',10);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `product_id` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  KEY `FK709eickf3kc0dujx3ub9i7btf` (`user_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK709eickf3kc0dujx3ub9i7btf` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (2,2,1,8);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `UK_41g4n0emuvcm3qyf1f6cn43c0` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (6,'Camera Phones'),(4,'Foldable Phones'),(3,'Gaming Phones'),(2,'Keypad Phones'),(5,'Refurbished Phones'),(1,'Smart Phones');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwt_tokens`
--

DROP TABLE IF EXISTS `jwt_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_tokens` (
  `token_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwt_tokens`
--

LOCK TABLES `jwt_tokens` WRITE;
/*!40000 ALTER TABLE `jwt_tokens` DISABLE KEYS */;
INSERT INTO `jwt_tokens` VALUES (4,'2026-08-01 16:27:30.075641','2026-08-01 17:27:30.075641','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGFuZHUiLCJpYXQiOjE3ODU2MDE2NDksImV4cCI6MTc4NTYwNTI0OX0.qj7yvEw6LuUQ0UadVmu6vmt8O97e2_s2yXB5GADI7UQ',4),(7,'2026-08-01 19:10:50.294515','2026-08-01 20:10:50.294515','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4NTYxMTQ1MCwiZXhwIjoxNzg1NjE1MDUwfQ.u3DT_lvFPsDijjYrHKhfgc0n_uoXKSlhyueGbxgdTps',8),(16,'2026-08-02 17:17:54.557684','2026-08-02 18:17:54.557684','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWtydXRhbWFsYS5oYXJlZXNoIiwiaWF0IjoxNzg1NjkxMDc0LCJleHAiOjE3ODU2OTQ2NzR9.77weAsicZ6JBq3yx5lN8FE7Q7G9m_UyfggT_lVeSjMU',19),(17,'2026-08-02 17:31:29.482890','2026-08-02 18:31:29.482890','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJpYXQiOjE3ODU2OTE4ODksImV4cCI6MTc4NTY5NTQ4OX0.9VomYjT1umGlLD7E7KlOd-q768Mb0KOPvQb2qnOwySE',10),(19,'2026-08-03 09:39:41.978346','2026-08-03 10:39:41.960694','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Zm9yMSIsImlhdCI6MTc4NTc0OTk4MSwiZXhwIjoxNzg1NzUzNTgxfQ.Hg_K0GdxDgNLRY7i8Q__wYSxuXKIuwkqKj5crlcCLro',20);
/*!40000 ALTER TABLE `jwt_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price_per_unit` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `order_id` varchar(255) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,64900.00,2,129800.00,'ORD202608012353',1),(2,64900.00,2,129800.00,'ORD202608011858',1);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `status` enum('PENDING','SUCCESS','FAILED') NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `razorpay_order_id` varchar(255) DEFAULT NULL,
  `razorpay_payment_id` varchar(255) DEFAULT NULL,
  `razorpay_signature` varchar(255) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  KEY `FKhlglkvf5i60dv6dn397ethgpt` (`address_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKhlglkvf5i60dv6dn397ethgpt` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('ORD202608011858','2026-08-01 18:23:07.801628','SUCCESS',129800.00,'2026-08-01 19:17:32.397411',10,'order_TKbRjolp1PPkHd','pay_TKbs8ewDBNYBWs','e9ba8d817768a4682492d7d2d8917a431e4192269a9b6ed990972961f23d8886',1),('ORD202608012353','2026-08-01 16:44:28.543356','PENDING',129800.00,'2026-08-01 16:44:28.579952',4,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productimages`
--

DROP TABLE IF EXISTS `productimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productimages` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `image_url` text NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKjji6hlkuywsthaocoslq3l621` (`product_id`),
  CONSTRAINT `FKjji6hlkuywsthaocoslq3l621` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productimages`
--

LOCK TABLES `productimages` WRITE;
/*!40000 ALTER TABLE `productimages` DISABLE KEYS */;
INSERT INTO `productimages` VALUES (1,'https://ik.imagekit.io/j4g4gygmjl/-original-imahgfmypevfehpc.webp',1),(2,'https://ik.imagekit.io/j4g4gygmjl/-original-imahft5gfchxyewy.webp',2),(3,'https://ik.imagekit.io/j4g4gygmjl/-original-imahng2ypvnsnqzq.webp',3),(4,'https://ik.imagekit.io/j4g4gygmjl/-original-imahk769tzesprwf.webp',4),(5,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfjsftzhymazf.webp',5),(6,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfjsfgu7vjkvw.webp',6),(7,'https://ik.imagekit.io/j4g4gygmjl/-original-imahhhfvqhmf7fgg.webp',7),(8,'https://ik.imagekit.io/j4g4gygmjl/-original-imahjzxkrfjzvgzy.webp',8),(9,'https://ik.imagekit.io/j4g4gygmjl/-original-imahm5cxajhxvhsk.webp',9),(10,'https://ik.imagekit.io/j4g4gygmjl/-enriched-transparent-original-imahdzg7rrpztry8.webp',10),(11,'https://ik.imagekit.io/j4g4gygmjl/-original-imahkr5epjgfrrde.webp',11),(12,'https://ik.imagekit.io/j4g4gygmjl/-original-imahpanmwdz2h3u3.webp',12),(13,'https://ik.imagekit.io/j4g4gygmjl/-original-imahpfrktxjyhqrg.webp',13),(14,'https://ik.imagekit.io/j4g4gygmjl/-original-imahzaxejjzhkxy8.webp',14),(15,'https://ik.imagekit.io/j4g4gygmjl/-original-imahgfnygwdeyrzp.webp',15),(16,'https://ik.imagekit.io/j4g4gygmjl/-original-imahmqga4sxzfnzn.webp?updatedAt=1785497630666',16),(17,'https://ik.imagekit.io/j4g4gygmjl/-original-imahhhkkvv7fhukk.webp?updatedAt=1785497654319',17),(18,'https://ik.imagekit.io/j4g4gygmjl/-enriched-transparent-original-imahfsgmmvhyfr8z.webp?updatedAt=1785498125091',18),(19,'https://ik.imagekit.io/j4g4gygmjl/-original-imahmx82vachdh9z.webp?updatedAt=1785498125130',19),(20,'https://ik.imagekit.io/j4g4gygmjl/-original-imahz7vnavgauv44.webp?updatedAt=1785498125217',20),(21,'https://ik.imagekit.io/j4g4gygmjl/-original-imahhngs3z46gnew.webp?updatedAt=1785498125410',21),(22,'https://ik.imagekit.io/j4g4gygmjl/-original-imahz7vn9qe6bp8k.webp?updatedAt=1785498125415',22),(23,'https://ik.imagekit.io/j4g4gygmjl/-enriched-transparent-original-imahgyq9bez6zq7g.webp?updatedAt=1785500143188',23),(24,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfz2tenzpsd3p.webp?updatedAt=1785500143308',24),(25,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfw4aasyhherc.webp?updatedAt=1785500143247',25),(26,'https://ik.imagekit.io/j4g4gygmjl/turbo-5-5g-turbo-5-redmi-original-imahzhqbrnpuzezx.webp',26),(27,'https://ik.imagekit.io/j4g4gygmjl/15c-5g-2508crn2bi-redmi-original-imahhyh3yy2yygjx.webp',27),(28,'https://ik.imagekit.io/j4g4gygmjl/-original-imahpf295bawzufj.webp',28),(29,'https://ik.imagekit.io/j4g4gygmjl/bold-n1-5g-bold-n1-5g-lava-original-imahhkfvdub7kvcg.webp',29),(30,'https://ik.imagekit.io/j4g4gygmjl/c01-plus-ta-1396-ds-nokia-original-imah54f97b9m4hnd.webp',30),(31,'https://ik.imagekit.io/j4g4gygmjl/-original-imagg9ujz7brfxk6.webp',31),(32,'https://ik.imagekit.io/j4g4gygmjl/n6-5g-n6-oneplus-original-imahzvfjb2q8uz3z.webp',32),(33,'https://ik.imagekit.io/j4g4gygmjl/nord-6-nord-6-one-plus-original-imahmbwsnuf5g5vh.webp?updatedAt=1785502270269',33),(34,'https://ik.imagekit.io/j4g4gygmjl/105-single-sim-keypad-mobile-phone-with-wireless-fm-radio-nokia-original-imah2xgc9z6cwcqv.webp?updatedAt=1785506634195',34),(35,'https://ik.imagekit.io/j4g4gygmjl/a100-a100-motorola-original-imahgr9fqwxmhzge.webp?updatedAt=1785506634251',35),(36,'https://ik.imagekit.io/j4g4gygmjl/-original-imagch26hyzhgn9v.webp?updatedAt=1785506634310',36),(37,'https://ik.imagekit.io/j4g4gygmjl/105-ta-1570-ds-nokia-original-imah3y73zfk9r9xj.webp?updatedAt=1785506634179',37),(38,'https://ik.imagekit.io/j4g4gygmjl/130-music-dual-sim-music-player-wireless-fm-radio-and-dedicated-original-imah3q6f4u5wqkug.webp?updatedAt=1785506634260',38),(39,'https://ik.imagekit.io/j4g4gygmjl/m28-keypad-mobile-phone-2-8-inch-display-with-3000-mah-big-original-imahjgzyntxwydf8.webp',39),(40,'https://ik.imagekit.io/j4g4gygmjl/super-2-super-2-jmax-original-imah6wkauu5ykzgd.webp',40),(41,'https://ik.imagekit.io/j4g4gygmjl/moto-a300-2026-a300-2026-motorola-original-imahh8v2rzdeawyf.webp',41),(42,'https://ik.imagekit.io/j4g4gygmjl/-original-imahjzpc9aghw25n.webp',42),(43,'https://ik.imagekit.io/j4g4gygmjl/jiobharat-v4-4g-jbv191m2-jio-original-imahjcj7zupb4xrq.webp',43),(44,'https://ik.imagekit.io/j4g4gygmjl/moto-a300-2026-a300-2026-motorola-original-imahhfzkmhz29uxu.webp',44),(45,'https://ik.imagekit.io/j4g4gygmjl/classic-ultra-classic-ultra-hotline-original-imahb9ghvhaxndmb.webp',45),(46,'https://ik.imagekit.io/j4g4gygmjl/royal-royal-new-hotline-original-imah8px9jhh6cas3.webp',46),(47,'https://ik.imagekit.io/j4g4gygmjl/x778-x778-micromax-original-imahdwq7qkcdueww.webp',47),(48,'https://ik.imagekit.io/j4g4gygmjl/guru-310-guru-310-rocktouch-original-imah9yjf98dphkgq.webp',48),(49,'https://ik.imagekit.io/j4g4gygmjl/a1-josh-lf1011-lava-original-imahhsx3ugthbzzx.webp',49),(50,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfqw9mmsrtjgh.webp',50),(51,'https://ik.imagekit.io/j4g4gygmjl/ace-3-shine-ace-3-itel-original-imahcx3dqegvnr4x.webp?updatedAt=1785517619618',51),(52,'https://ik.imagekit.io/j4g4gygmjl/keypad-mobile-2-4-inch-display-type-c-charging-it5032-itel-original-imahgeqdzvyvjkuk.webp?updatedAt=1785517619559',52),(53,'https://ik.imagekit.io/j4g4gygmjl/110-dual-sim-keypad-phone-with-fm-radio-auto-call-recording-original-imah53fzznnpntev.webp?updatedAt=1785520221310',53),(54,'https://ik.imagekit.io/j4g4gygmjl/m28-2mp-camera-dual-sim-keypad-mobile-phone-wireless-fm-radio-original-imahjgyzgarfmqfj.webp?updatedAt=1785517619668',54),(55,'https://ik.imagekit.io/j4g4gygmjl/super-guru-4g-max-128-it-9310-itel-64-original-imahhe95px4pyzmj.webp?updatedAt=1785517619351',55),(56,'https://ik.imagekit.io/j4g4gygmjl/x412-x412-micromax-original-imahyzcjqsjzjxyy.webp?updatedAt=1785517619235',56),(57,'https://ik.imagekit.io/j4g4gygmjl/k33-k33-kechaoda-original-imagt9qwaqmrhyww.webp?updatedAt=1785517619241',57),(58,'https://ik.imagekit.io/j4g4gygmjl/shark-new-shark-new-lime-green-gfive-original-imahbyr4u9ypq5ew.webp?updatedAt=1785517619347',58),(59,'https://ik.imagekit.io/j4g4gygmjl/a10v-ds-keypad-phone-with-voice-feature-800-mah-battery-wireless-original-imah3nztegjzcgyr.webp?updatedAt=1785517619398',59),(60,'https://ik.imagekit.io/j4g4gygmjl/a1-c-a1-c-gfive-original-imah5f5gzr2zwenr.webp?updatedAt=1785517619502',60),(61,'https://ik.imagekit.io/j4g4gygmjl/k190-k190-karbonn-original-imahhzr4wwvyh3sx.webp?updatedAt=178551761931961',61),(62,'https://ik.imagekit.io/j4g4gygmjl/guru-2173-guru-2173-snexian-original-imag6gb5eyskqrzp.webp?updatedAt=1785517619531',62),(63,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/y/8/i/-original-imah4zp7fgtezhsz.jpeg?q=70',63),(64,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/k/l/l/-original-imagtc5fz9spysyk.jpeg?q=70',64),(65,'https://rukminim2.flixcart.com/image/312/312/xif0q/shopsy-mobile/a/f/x/-original-imahjzpcbshtapqx.jpeg?q=70',65),(66,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/s/6/c/bharat-bharat-mtr-original-imahyc3nxde95mww.jpeg?q=70',66),(67,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/h/u/i/-original-imahgfmyczqxhtm2.jpeg?q=70',67),(68,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/d/0/0/-original-imahk769tzesprwf.jpeg?q=70',68),(69,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/p/x/5/bold-17-pro-bold-17-pro-ringme-original-imahmtt2ctpfafgx.jpeg?q=70',69),(70,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/y/m/o/-original-imahekyxgkvxb7a3.jpeg?q=70',70),(71,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/d/1/5/k33-k33-kechaoda-original-imahyc3zpgjvp2j9.jpeg?q=70',71),(72,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/z/v/1/-original-imahhua2ngfshkha.jpeg?q=70',72),(73,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/y/r/1/15-5g-25057rn09i-redmi-original-imahhyhbg7bdp7sv.jpeg?q=70',73),(74,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/p/c/3/mini-mini-caul-original-imaheyfdrftbzhkm.jpeg?q=70',74),(75,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/c/e/h/-original-imahhhhgvczktztw.jpeg?q=70',75),(76,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/n/v/e/-original-imahft5gfchxyewy.jpeg?q=70',76),(77,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/k/o/7/-original-imahn9f7bvqfehtt.jpeg?q=70',77),(78,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/v/u/j/13-5g-2406ern9ci-redmi-original-imahjspqzhuf7dgq.jpeg?q=70',78),(79,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/m/q/u/-original-imah34gx5euj4gqz.jpeg?q=70',79),(80,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/2/y/c/-original-imah4sssdf9pgz3e.jpeg?q=70',80),(81,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/y/v/f/-original-imahbr2cmpwexghw.jpeg?q=70',81),(82,'https://rukminim2.flixcart.com/image/312/312/xif0q/mobile/y/y/3/-original-imahncfvukqxvbm2.jpeg?q=70',82),(83,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/1.webp',83),(84,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/2.webp',84),(85,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/3.webp',85),(86,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/4.webp',86),(87,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/5.webp',87),(88,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/6.webp',88),(89,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/7.webp',89),(90,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/8.webp',90),(91,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/9.webp',91),(92,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/10.webp',92),(93,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/11.webp',93),(94,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/12.webp',94),(95,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/13.webp',95),(96,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/14.webp',96),(97,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/15.webp',97),(98,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/16.webp',98),(99,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/17.webp',99),(100,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/18.webp',100),(101,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/19.webp',101),(102,'https://ik.imagekit.io/StringStackNaveen/Gaming%20phones/20.webp',102),(103,'https://m.media-amazon.com/images/I/61rEc3pkrpL._SL1200_.jpg',103),(104,'https://ik.imagekit.io/j4g4gygmjl/-original-imahfay2zea8syuh.webp?updatedAt=1785577573827',104),(105,'https://m.media-amazon.com/images/I/61Q1AgNSO3L._AC_UY218_.jpg',105),(106,'https://m.media-amazon.com/images/I/51qDiwQ62HL._AC_UY218_.jpg',106),(107,'https://m.media-amazon.com/images/I/61rEc3pkrpL._SL1200_.jpg',107),(108,'https://m.media-amazon.com/images/I/71U+YdsvMPL._SX569_.jpg',108),(109,'https://m.media-amazon.com/images/I/716Nk3IKYNL._SX679_.jpg',109),(110,'https://m.media-amazon.com/images/I/714QNdK8BWL._SX679_.jpg',110),(111,'https://m.media-amazon.com/images/I/619Z1QHCy-L._SX679_.jpg',111),(112,'https://m.media-amazon.com/images/I/51qDiwQ62HL._SX679_.jpg',112),(113,'https://m.media-amazon.com/images/I/51GBUbLyW1L.jpg',113),(114,'https://m.media-amazon.com/images/I/81PJuksEE0L._SX679_.jpg',114),(115,'https://m.media-amazon.com/images/I/71xV34Au2dL._SY741_.jpg',115),(116,'https://m.media-amazon.com/images/I/71BifGqze0L._SY741_.jpg',116),(117,'https://m.media-amazon.com/images/I/71Zh7D0gNiL._SX569_.jpg',117),(118,'https://m.media-amazon.com/images/I/71sVdlxEceL._SX569_.jpg',118),(119,'https://m.media-amazon.com/images/I/71nu2-pG5IL._SX679_.jpg',119),(120,'https://m.media-amazon.com/images/I/61wbFD6KzmL._SX679_.jpg',120),(121,'https://m.media-amazon.com/images/I/61RBrScYUSL._SX679_.jpg',121),(122,'https://m.media-amazon.com/images/I/71NrptoTmlL._SY741_.jpg',122);
/*!40000 ALTER TABLE `productimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  KEY `idx_product_name` (`name`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,NULL,'1 year warranty for phone and 1 year warranty for in Box Accessories','Apple iPhone 16 (Pink, 128 GB)',64900.00,996,'2026-08-01 18:23:07.822449',1),(2,NULL,'1 year warranty for phone and 1 year warranty for in Box Accessories','Apple iPhone 17 (Sage, 256GB)',82900.00,2000,NULL,1),(3,NULL,'1 Year Warranty on Handset and 6 Months Warranty on Accessories','MOTOROLA g37 (PANTONE Impenetrable, 64 GB) (4 GB RAM)',17999.00,3000,NULL,1),(4,NULL,'1 Year Warranty on Handset and 6 Months Warranty on Accessories','MOTOROLA Signature (PANTONE Martini Olive, 512 GB) (16 GB RAM)',62500.00,2000,NULL,1),(5,NULL,'1 Year Domestic Warranty','Google Pixel 10 (Obsidian, 256 GB) (12 GB RAM)',74999.00,1000,NULL,1),(6,NULL,'1 Year Domestic Warranty','Google Pixel 10 (Frost, 256 GB) (12 GB RAM)',74999.00,500,NULL,1),(7,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','vivo T5x 5G (Cyber Green, 256 GB) (8 GB RAM)',30999.00,2000,NULL,1),(8,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','vivo X200T (Seaside Lilac, 512 GB) (12 GB RAM)',69999.00,300,NULL,1),(9,NULL,'1 Year Warranty on the Product and 6 Months Warranty on Inbox Accessories','OPPO K13 5G with 7000mAh and 80W SUPERVOOC Charger In-The-Box (Icy Purple, 128 GB) (8 GB RAM)',21999.00,4000,NULL,1),(10,NULL,'1 Year Warranty on the Product and 6 Months Warranty on Inbox Accessories','OPPO Reno14 5G (Forest Green, 256 GB) (8 GB RAM)',44999.00,1000,NULL,1),(11,NULL,'1 year for Handset 6 months for Accessories','Infinix Note edge (Silk Green, 128 GB) (6 GB RAM)',24999.00,500,NULL,1),(12,NULL,'1 year for Handset 6 months for Accessories','Infinix NOTE 60 Pro (Frost Silver, 128 GB) (8 GB RAM)',35999.00,100,NULL,1),(13,NULL,'1 Year Manufacturing Warranty','Nothing Phone (3) (Black, 256 GB) (12 GB RAM)',79999.00,200,NULL,1),(14,NULL,'1 Year Manufacturing Warranty','Nothing Phone (4a) (White, 256 GB) (12 GB RAM)',40999.00,300,NULL,1),(15,NULL,'1 Year Manufacturing Warranty','Nothing Phone (4b) (Black, 256 GB) (8 GB RAM)',38999.00,2100,NULL,1),(16,NULL,'1 Year Manufacturer Warranty for Phone and 6 Months Warranty for In the Box Accessories','POCO X8 Pro (Green, 256 GB) (8 GB RAM)',36999.00,200,NULL,1),(17,NULL,'1 Year Manufacturer Warranty for Phone and 6 Months Warranty for In the Box Accessories','POCO C85x (Elite Black, 64 GB) (4 GB RAM)',13499.00,300,NULL,1),(18,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','realme GT 7T (Racing Yellow, 256 GB) (12 GB RAM)',32249.00,300,NULL,1),(19,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','realme P4 Lite (Beach Gold, 64 GB) (4 GB RAM)',12999.00,430,NULL,1),(20,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','realme P4R 5G (Lavender Glare, 128 GB) (6 GB RAM)',21499.00,200,NULL,1),(21,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','realme P4 Lite 5G (Mosaic Blue, 64 GB) (4 GB RAM)',15499.00,230,NULL,1),(22,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories','realme P4R 5G (Titanium Glare, 128 GB) (6 GB RAM)',21499.00,150,NULL,1),(23,NULL,'1 Year Manufacturer Warranty for Device and 6 Months for In-Box Accessories','Samsung Galaxy A17 5G (Black, 128 GB) (8 GB RAM)',22499.00,200,NULL,1),(24,NULL,'1 Year Manufacturer Warranty for Device and 6 Months for In-Box Accessories','Samsung Galaxy S24 5G Snapdragon (Onyx Black, 128 GB) (8 GB RAM)',49999.00,460,NULL,1),(25,NULL,'1 Year Manufacturer Warranty for Device and 6 Months for In-Box Accessories','Samsung Galaxy S24 FE 5G (Mint, 128 GB) (8 GB RAM)',45000.00,340,NULL,1),(26,NULL,'1 Year Warranty','REDMI Turbo 5 5G (Nitro Blue, 256 GB) (8 GB RAM)',39499.00,200,NULL,1),(27,NULL,'1 Year Warranty','REDMI 15C 5G (Dusk Purple, 128 GB) (6 GB RAM)',19542.00,100,NULL,1),(28,NULL,'1 Year Manufacturer Warranty for Device and 6 Months for In-Box Accessories','LAVA Virat V1 (Himalayan Silver, 64 GB) (4 GB RAM)',8999.00,199,NULL,1),(29,NULL,'1 Year Manufacturer Warranty for Device and 6 Months for In-Box Accessories','LAVA Bold N1 5G (Royal Blue, 64 GB) (4 GB RAM)',11999.00,100,NULL,1),(30,NULL,'No Warranty on this Product','Nokia C01 Plus (Grey, 16 GB) (2 GB RAM)',6999.00,30,NULL,1),(31,NULL,'No Warranty on this Product','Nokia C21 Plus (Dark Cyan, 64 GB) (4 GB RAM)',12999.00,40,NULL,1),(32,NULL,'1 Year Warranty','OnePlus N6 5G (Midnight Green, 128 GB) (4 GB RAM)',22249.00,50,NULL,1),(33,NULL,'1 Year Warranty','OnePlus Nord CE6 (Pitch Black, 256 GB) (8 GB RAM)',38000.00,67,NULL,1),(34,NULL,'1 Month Company Domestic Warranty by Company Authorized Service Center.','Nokia 105 Classic Single Sim Keypad Phone, Without Charger (Charc...more',1050.00,50,NULL,2),(35,NULL,'2 Years Domestic Replacement Warranty','MOTOROLA Moto A100 (Blue)',1084.00,40,NULL,2),(36,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories Including Battery from the Date of Purchase','Nokia 105 Classic Single Sim Keypad Phone, Without Charger (Blue)',1050.00,50,NULL,2),(37,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories Including Battery from the Date of Purchase','Nokia 105 Dual Sim Keypad Phone with Built-in UPI Payments (Charc...more',1328.00,40,NULL,2),(38,NULL,'1 Year Manufacturer Warranty for Device and 6 Months Manufacturer Warranty for Inbox Accessories Including Battery from the Date of Purchase','Nokia 130 Music Dual Sim with Music Player, Dedicated Music Buttons (Purple)',1973.00,50,NULL,2),(39,NULL,'1 year manufacturer\'s warranty.','Pious M28 Keypad Mobile Phone 2.8 inch Display With 3000 mAh Big Battery & 2 MP Camera (Black)',1963.00,30,NULL,2),(40,NULL,'3 months Domestic Warranty','Jmax Super 2 (Purple)',599.00,20,NULL,2),(41,NULL,'2 Years Domestic Replacement Warranty','MOTOROLA Moto A300 2026 (Purple Elite)',1354.00,10,NULL,2),(42,NULL,'1 Year Manufacturer Warranty','Jio Bharat V4 JBV191M2 4G UPI Phone (Blue)',920.00,20,NULL,2),(43,NULL,'1 Year Manufacturer Warranty','JioBharat V4 4G (Black)',885.00,45,NULL,2),(44,NULL,'2 Years Domestic Replacement Warranty','MOTOROLA Moto A300 2026 (Black)',1354.00,30,NULL,2),(45,NULL,'6 Months domestic warranty','HOTLINE CLASSIC ULTRA (Blue)',583.00,47,NULL,2),(46,NULL,'6 Months domestic warranty','HOTLINE ROYAL (Black)',575.00,20,NULL,2),(47,NULL,'One Year manufacturer Warranty','Micromax X778 (Blue)',1355.00,34,NULL,2),(48,NULL,'NO WARRENTY','Snexian GURU 310 (Black)',699.00,50,NULL,2),(49,NULL,'1 Year Manufacturer Replacement Guarantee for Phone and 6 months Replacement for Accessories in the Box','LAVA A1 Josh (Blue)',1049.00,32,NULL,2),(50,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','itel Magic X Pro 4G| 2500mAh Big Battery| Expandable Storage upto...more',2480.00,23,NULL,2),(51,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','itel Ace 3 Shine (White)',849.00,30,NULL,2),(52,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','itel it5027 Slim Keypad Mobile | 2.4 inch Display| Type C Chargin...more',1149.00,50,NULL,2),(53,NULL,'1 Year Replacement','Nokia 110 Power Keypad Phone,Dual SIM, Camera, Long Lasting Battery,Phone Talker (Purple)',1779.00,40,NULL,2),(54,NULL,'1 year manufacturer\'s warranty.','Pious M28 2MP Camera Dual Sim Keypad Mobile Phone Wireless FM Radio Gift for Old Men\'s (GOLDEN)',2451.00,58,NULL,2),(55,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','itel Super Guru 4G Max (Blue)',3399.00,40,NULL,2),(56,NULL,'One Year manufacturer Warranty','Micromax X412 (Black&Red)',998.00,30,NULL,2),(57,NULL,'1 Year Manufacturer Warranty','Kechaoda K33 (Gold)',1098.00,20,NULL,2),(58,NULL,'3 Month Domestic Warranty.','GFive SHARK NEW (Lime Green)',799.00,40,NULL,2),(59,NULL,'2 Years Domestic Replacement Warranty','MOTOROLA A10V DS Keypad Phone with Voice Feature|800 mAh Battery|Wireless FM Recording (Teal Blue)',1186.00,30,NULL,2),(60,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','KARBONN K190 (Dark Blue, Black)',1152.00,20,NULL,2),(61,NULL,'3 Month Company Warranty.','GFive A1-C (Green)',570.00,25,NULL,2),(62,NULL,'3 Month Company Warranty.','Snexian GURU 2173 (Black)',629.00,30,NULL,2),(63,NULL,'1 year manufacturer\'s warranty.',' Samsung Galaxy S23 5G ',74999.00,10,NULL,5),(64,NULL,'6 Months domestic warranty','Apple iPhone 15 (128gb)    ',65000.00,200,NULL,5),(65,NULL,'1 year manufacturer\'s warranty.','Samsung Galaxy M07',30000.00,60,NULL,5),(66,NULL,'3 Month Company Warranty.',' MTR Bharat',2000.00,30,NULL,5),(67,NULL,'6 Months domestic warranty',' Apple iPhone 16',50000.00,50,NULL,5),(68,NULL,'6 Months domestic warranty','Motorola Signature',20000.00,30,NULL,5),(69,NULL,'NO WARRENTY','ringme BOLD 17 PRO(Silver,64GB) ',4000.00,60,NULL,5),(70,NULL,'6 Months domestic warranty','realme 15 5G(Silk Pink, 128GB)',2000.00,10,NULL,5),(71,NULL,'NO WARRENTY','Kechaoda K33',200.00,5,NULL,5),(72,NULL,'6 Months domestic warranty','realme P3 Ultra 5G(Orion red, 256GB)',5000.00,5,NULL,5),(73,NULL,'6 Months domestic warranty','REDMI 15 5G (Midnight Black, 128GB) ',2000.00,4,NULL,5),(74,NULL,'NO WARRENTY',' CAUL Mini',2000.00,3,NULL,5),(75,NULL,'6 Months Refurbished warranty','OPPO K13 Turbo 5G(Purple Phantom, 256GB)',3500.00,6,NULL,5),(76,NULL,'6 Months Refurbished warranty',' Apple iPhone 17(Sage,512GB)',40000.00,50,NULL,5),(77,NULL,'NO WARRENTY','Redmi note 15 SE 5G Snapdragon(Frosted White, 128GB)',2500.00,25,NULL,5),(78,NULL,'6 Months Refurbished warranty',' Redmi 13 5G(Hawaiian Blue, 128GB)',4000.00,20,NULL,5),(79,NULL,'6 Months domestic warranty','Xiaomi 14 CIVI (Hot Pink,512GB)',2999.00,23,NULL,5),(80,NULL,'NO WARRENTY','Samsung Galaxy A14 5G (Dark Red, 128GB)',14999.00,4,NULL,5),(81,NULL,'NO WARRENTY','POCO C75 5G(Enchanted Green, 64GB)',6999.00,12,NULL,5),(82,NULL,'6 Months Refurbished warranty',' Motorola Razr fold',3999.00,2,NULL,5),(83,NULL,'1 year manufacturer\'s warranty.','ASUS ROG Phone 9 Pro ',98499.00,200,NULL,3),(84,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','REDMAGIC 10 Pro',76499.00,350,NULL,3),(85,NULL,'1 Year Replacement','Lenovo Legion Y90',99799.00,400,NULL,3),(86,NULL,'1 year warranty for phone and 1 year warranty for in Box Accessories','Black Shark 5 Pro',79899.00,100,NULL,3),(87,NULL,'1 Year Replacement','OnePlus 13',65699.00,40,NULL,3),(88,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','iQOO 13',87900.00,356,NULL,3),(89,NULL,'1 year manufacturer\'s warranty.','POCO F7 Pro',76899.00,200,NULL,3),(90,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Realme GT 7 Pro',87999.00,170,NULL,3),(91,NULL,'1 Year Replacement','Samsung Galaxy S25 Ultra',79699.00,239,NULL,3),(92,NULL,'1 Year Replacement','Apple iPhone 16 Pro Max',198700.00,300,NULL,3),(93,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Xiaomi 15 Ultra',78900.00,500,NULL,3),(94,NULL,'1 Year Replacement','Vivo X200 pro',95769.00,200,NULL,3),(95,NULL,'1 year manufacturer\'s warranty.','Infinix GT 30 Pro',57899.00,300,NULL,3),(96,NULL,'1 year warranty for phone and 1 year warranty for in Box Accessories','HONOR Magic7 Pro',80999.00,500,NULL,3),(97,NULL,'1 Year Replacement','OPPO Find X8 Pro',56999.00,100,NULL,3),(98,NULL,'1 year warranty for phone and 1 year warranty for in Box Accessories','Motorola Edge 50 Ultra',79000.00,50,NULL,3),(99,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Nothing Phone (3)',56000.00,700,NULL,3),(100,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Google Pixel 9 Pro XL',90999.00,69,NULL,3),(101,NULL,'1 year manufacturer\'s warranty.','Sony Xperia 1 VI',89599.00,500,NULL,3),(102,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','ZTE nubia Z70 Ultra',79000.00,245,NULL,3),(103,NULL,'1 year manufacturer\'s warranty.','Samsung Galaxy Z Fold 8 ',67890.00,200,NULL,4),(104,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Samsung Galaxy Z Fold 4 ',78907.00,430,NULL,4),(105,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','Samsung Galaxy Z Fold 7',89086.00,540,NULL,4),(106,NULL,'1 year manufacturer\'s warranty.','Google Pixel 10 Pro Fold',89076.00,300,NULL,4),(107,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','vivo X Fold3 Pro 5G  ',98076.00,258,NULL,4),(108,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Samsung Galaxy Z Flip5  ',98760.00,100,NULL,4),(109,NULL,'1 year manufacturer\'s warranty.','Samsung Galaxy Z Fold8 Ultra 5G Graphite colour',78009.00,213,NULL,4),(110,NULL,'1 year manufacturer\'s warranty.','Samsung Galaxy Z Fold4 5G Beige colour',90890.00,850,NULL,4),(111,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','Samsung Galaxy Z Fold7 5G Mobile with Galaxy AI Blue Shadow colour',78900.00,700,NULL,4),(112,NULL,'1 year manufacturer\'s warranty.','Google Pixel 10 Pro Fold Moonstone, 256 GB 16 GB RAM',97808.00,780,NULL,4),(113,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Google Pixel 9 Pro Fold 5G Obsidian, 16GB RAM, 256GB',98760.00,543,NULL,4),(114,NULL,'1 year manufacturer\'s warranty.','Foneme All-New FOLD PRO Dual Display Keypad Mobile',78999.00,245,NULL,4),(115,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Snexian All-New Rock X Flip Dual Sim  Keypad Mobile Rose gold colour',88897.00,800,NULL,4),(116,NULL,'1 year manufacturer\'s warranty.','Snexian All-New Rock X Flip Dual Sim  Keypad Mobile cosmic black colour',90879.00,765,NULL,4),(117,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','Snexian All-New Rock FOLD MINI Dual Display white colour',67899.00,349,NULL,4),(118,NULL,'1 year manufacturer\'s warranty.','Snexian All-New Rock FOLD MINI Dual Display Blue colour',98999.00,507,NULL,4),(119,NULL,'2 Years Manufacturer Warranty on Device and 6 Months on Accessories','Snexian All-New Rock FOLD MINI Dual Display Orange colour',98765.00,780,NULL,4),(120,NULL,'12 month Handset & 6 Month Box Accessories Service Warrenty','Nokia 2660 Flip 4G Volte Blue colour',56789.00,980,NULL,4),(121,NULL,'1 year manufacturer\'s warranty.','Nokia 2660 Flip 4G Volte keypad Phone Black colour',35789.00,870,NULL,4),(122,NULL,'1 year manufacturer\'s warranty.','Foneme F17 FLIP Gold colour ',89999.00,580,NULL,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `session_id` bigint NOT NULL AUTO_INCREMENT,
  `active_status` bit(1) NOT NULL,
  `expiry_time` datetime(6) NOT NULL,
  `jwt_token` varchar(1024) NOT NULL,
  `login_time` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,_binary '\0','2026-07-28 13:04:00.576101','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJpQGV4YW1wbGUuY29tIiwiaWF0IjoxNzg1MjQwMjQwLCJleHAiOjE3ODUyNDM4NDB9.Z9knyGoRQUtWlxXDIUSJMJsc2cOD0vgze3wrnbhnfco','2026-07-28 12:04:00.576101',1);
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `mobile_number` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `role` enum('ADMIN','CUSTOMER') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r7c96a004bv8w16jgdm8imich` (`mobile_number`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-07-28 11:59:24.247139','hari@example.com','Hari Kumar','9876543210','$2a$10$.Jfz9L6msBhgLiL/TXKYeebbMzh/B4.0kSr4IYmHqMj1w.iO7kmmW','2026-07-28 11:59:24.247139',NULL,'ADMIN',NULL,''),(2,'2026-07-28 12:23:01.225586','chandra@gmail.com','vamsi','9876543219','$2a$10$UbeqfHdoO68ylUCiU/cZqeMYI/IOuCx/Y7hABlyXCY8BhQZYgTynG','2026-07-28 12:23:01.225586',NULL,'ADMIN',NULL,''),(4,NULL,'chandra1@gmail.com','vamsi','9875543219','$2a$10$4LYgdcdIBgb1UqRCELZPjeXLBLJBRtGBB5.BYaF1aFtIb6rgcXyiS',NULL,'2026-08-01 14:58:49.210197','CUSTOMER','2026-08-01 14:58:49.210197','chandu'),(5,NULL,'chandra2@gmail.com','MuniChandra','9603207344','$2a$10$t/ZaQ.lSNZTk/5PhplVZ1..k82df6nblEwX08sn3rWLRct8rJqOMq',NULL,'2026-08-01 15:40:45.443560','CUSTOMER','2026-08-01 15:40:45.443560','chandra'),(7,'2026-08-01 15:45:49.023584','chandra21@gmail.com','MuniChandra1','9603207244','$2a$10$skNw9GU5IQnzSogO9Rse3.AXPF6bmy/JC8gnFEFq9IkPrtsV293Ci',NULL,NULL,'CUSTOMER','2026-08-01 15:45:49.023584','chandra1'),(8,'2026-08-01 16:00:21.884882','admin@test.com','Admin User','9999999999','$2a$10$TDpCGUfD5mV.LsH04FF90unt03wDG/I7SgbfayYTYy5IBznrX8qT6',NULL,NULL,'ADMIN','2026-08-01 16:00:21.884882','admin'),(10,'2026-08-01 17:52:37.627540','admin1@test.com','Admin User','9999999990','$2a$10$yM6urg32i1f0dyrjEVrkfOmh19Py4wxr5qBARbdBiahmfho/VLBju',NULL,NULL,'CUSTOMER','2026-08-01 17:52:37.627540','admin1'),(13,'2026-08-02 16:48:28.402191','munichandra588@gmail.com','Polasani MuniChandra','9603207345','$2a$10$EDFuykXesi3nd6jYxJeSeeqxDu7z2fFSFVGcbZeLNY1hn3uLu5Dn.',NULL,NULL,'CUSTOMER','2026-08-02 16:48:28.402191','munichandra588'),(15,'2026-08-02 16:50:04.160820','munichandra58@gmail.com','Polasani MuniChandra222','9603207346','$2a$10$vpQA13s/.8nrGhzrKpj8v.y3tHTGN3xgMUixtBRBe9eaqYRVN/n76',NULL,NULL,'CUSTOMER','2026-08-02 16:50:04.160820','munichandra58'),(18,'2026-08-02 17:00:17.209301','munichandra548@gmail.com','Polasani MuniChandra','9603207343','$2a$10$gCttxBC9JDm6xAPXkQRz/eo6KBHv51SgY5OgSMLXfPL2RuYO9FGBq',NULL,NULL,'CUSTOMER','2026-08-02 17:00:17.209301','munichandra548'),(19,'2026-08-02 17:04:49.315882','vikrutamala.hareesh@gmail.com','Hareesh Ram','7095295681','$2a$10$8eaxpf33x4578DskskDrt.dxtEVnx5hPaZoxNgEx.yaq3tYRw3udK',NULL,NULL,'CUSTOMER','2026-08-02 17:04:49.315882','vikrutamala.hareesh'),(20,'2026-08-03 06:03:35.728895','testfor1@gmail.com','Test 1','1234567890','$2a$10$FYCQ2L0mwkZqPlpGjuefqOZsJJEviCPO76BdpYfWEiL/sTeoK4mlu',NULL,NULL,'CUSTOMER','2026-08-03 06:03:35.728895','testfor1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mobilemartdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-03 15:23:15
