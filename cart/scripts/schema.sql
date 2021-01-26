CREATE DATABASE `cartsdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
CREATE TABLE `cartsdb`.`cart_prd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prd_des` varchar(255) DEFAULT NULL,
  `prd_icon` varchar(255) DEFAULT NULL,
  `prd_id` varchar(45) NOT NULL,
  `prd_name` varchar(255) DEFAULT NULL,
  `prd_price` double unsigned zerofill DEFAULT NULL,
  `prd_stock` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
