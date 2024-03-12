-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.29 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for wickrama_store
CREATE DATABASE IF NOT EXISTS `wickrama_store` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wickrama_store`;

-- Dumping structure for table wickrama_store.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `email` varchar(50) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.admin: ~0 rows (approximately)
INSERT INTO `admin` (`email`, `password`) VALUES
	('admin@gmail.com', '123');

-- Dumping structure for table wickrama_store.customer_registration
CREATE TABLE IF NOT EXISTS `customer_registration` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mobile` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `balance_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.customer_registration: ~3 rows (approximately)
INSERT INTO `customer_registration` (`id`, `mobile`, `name`, `balance_amount`) VALUES
	(1, 'unkown', 'unkown', 0),
	(2, '0766923221', 'Sachira', 0),
	(3, '0766923220', 'rashan2', 0);

-- Dumping structure for table wickrama_store.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` varchar(20) NOT NULL,
  `date` datetime NOT NULL,
  `paid_amount` double NOT NULL,
  `customer_registration_id` int NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_customer_registration1_idx` (`customer_registration_id`),
  CONSTRAINT `fk_invoice_customer_registration1` FOREIGN KEY (`customer_registration_id`) REFERENCES `customer_registration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.invoice: ~15 rows (approximately)

-- Dumping structure for table wickrama_store.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `products_product_id` varchar(50) NOT NULL DEFAULT '',
  `qty` double DEFAULT NULL,
  `our_price` double DEFAULT NULL,
  `invoice_id` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_products1_idx` (`products_product_id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `FK_invoice_item_products` FOREIGN KEY (`products_product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.invoice_item: ~23 rows (approximately)

-- Dumping structure for table wickrama_store.products
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(50) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `buying_price` double DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `our_price` double DEFAULT NULL,
  `img_url` text,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.products: ~10 rows (approximately)
INSERT INTO `products` (`product_id`, `name`, `brand`, `company`, `qty`, `buying_price`, `selling_price`, `our_price`, `img_url`) VALUES
	('0001900000', 'උළු හාල්', 'No brand', 'No company', 46, 500, 1000, 800, 'no_product.png'),
	('0005300000', 'Margarin', 'No brand', 'No company', 100, 1000, 1200, 1100, 'no_product.png'),
	('100000004', 'Bread', 'Keells', 'Keells Supermarket', 36, 80, 100, 90, 'bread.jpg'),
	('100000005', 'Eggs (Dozen)', 'Keells', 'Keells Supermarket', 17, 130, 170, 150, 'eggs.jpg'),
	('100000006', 'Cooking Oil', 'Mareena', 'Keells Supermarket', 98, 230, 300, 250, 'oil.jpg'),
	('100000007', 'Samba rice', 'Keells ', 'Keells Supermarket', 247, 180, 220, 200, 'rice.jpg'),
	('100000008', 'Demerara Sugar', 'MDG Exports', 'Lanka Supermart', 147, 130, 180, 150, 'sugar.jpg'),
	('100000009', 'Ceylon tea', 'Dilmah', 'Green Leaf Traders', 58, 350, 420, 400, 'tea.jpeg'),
	('100000010', 'Tuna canned fish', 'Cargills', 'Cargills supermarcket', 95, 160, 200, 180, 'tuna.jpg'),
	('100000011', 'Mangoes', 'No brand', 'No company', 999998, 20, 40, 30, 'no_product.png'),
	('111', 'Clogard Tooth paste', 'Clogard', 'Uneliever', 51, 200, 300, 250, 'clogard.jpg'),
	('222', 'Milk chocolate', 'Kandos', 'Kandos choocolate company', 12, 100, 200, 160, 'kandos.jpg'),
	('333', 'රත්මල් සබන්', 'Panda baby', 'Uneliever', 69, 60, 100, 80, 'rathmal.jpg');

-- Dumping structure for table wickrama_store.whole_sale_invoice
CREATE TABLE IF NOT EXISTS `whole_sale_invoice` (
  `id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `datetime` datetime NOT NULL,
  `payment` double NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.whole_sale_invoice: ~8 rows (approximately)

-- Dumping structure for table wickrama_store.whole_sale_invoice_item
CREATE TABLE IF NOT EXISTS `whole_sale_invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `quantity` double NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `invoice_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `invoice_id` (`invoice_id`),
  CONSTRAINT `FK_whole_sale_invoice_item_whole_sale_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `whole_sale_invoice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table wickrama_store.whole_sale_invoice_item: ~13 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
