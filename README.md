# onlineshopmall
Springboot + thymeleaf
sql table:
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `productId` (`product_id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `user_id` int DEFAULT NULL ,
  `login_name` varchar(255) DEFAULT NULL ,
  `user_address` varchar(255) DEFAULT NULL ,
  `cost` float DEFAULT NULL ,
  `serialnumber` varchar(255) DEFAULT NULL ,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL ,
  PRIMARY KEY (`id`)
) ;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL ,
  `product_id` int NOT NULL ,
  `quantity` int NOT NULL,
  `cost` float NOT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___66E1BD8E2F10007B` (`id`)
) ;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `name` varchar(200) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `price` float NOT NULL,
  `stock` int NOT NULL ,
  `categorylevelone_id` int DEFAULT NULL ,
  `categoryleveltwo_id` int DEFAULT NULL,
  `categorylevelthree_id` int DEFAULT NULL,
  `file_name` varchar(200) DEFAULT NULL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___94F6E55132E0915F` (`id`)
) ；

CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL ,
  `parent_id` int NOT NULL COMMENT 'parent's id',
  `type` int DEFAULT NULL COMMENT 'levels(1:level1 2：level2 3 level3)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___9EC2A4E236B12243` (`id`)
);
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL ,
  `gender` int NOT NULL DEFAULT '1' COMMENT 'gender(1:male 0：female)',
  `identity_code` varchar(60) DEFAULT NULL ,
  `email` varchar(80) DEFAULT NULL ,
  `mobile` varchar(11) DEFAULT NULL ,
  `file_name` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___C96109CC3A81B327` (`login_name`)
);
CREATE TABLE `user_address` (
  `id` int NOT NULL AUTO_INCREMENT ,
  `user_id` int DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL ,
  `remark` varchar(255) DEFAULT NULL COMMENT 'if user wants to leave some comments for their order',
  `isdefault` int DEFAULT '0' COMMENT 'default address',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
);
