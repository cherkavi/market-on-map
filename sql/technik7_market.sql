-- phpMyAdmin SQL Dump
-- version 4.1.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 17, 2014 at 06:33 AM
-- Server version: 5.5.36-cll
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `schema-name`
--

-- --------------------------------------------------------

--
-- Table structure for table `commodity`
--

CREATE TABLE IF NOT EXISTS `commodity` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id  of table',
  `name` varchar(100) COLLATE cp1251_general_cs NOT NULL COMMENT 'name of commodity',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=cp1251 COLLATE=cp1251_general_cs COMMENT='name of available commodity' AUTO_INCREMENT=61 ;

--
-- Dumping data for table `commodity`
--

INSERT INTO `commodity` (`id`, `name`) VALUES
(9, 'GPS навигаторы'),
(50, 'memory cards'),
(39, 'second hand'),
(48, 'USB flash'),
(47, 'автомобильные аксессуары'),
(46, 'автомобильные сигнализации'),
(26, 'аккумуляторы'),
(58, 'антенны авто'),
(54, 'антенны тв'),
(25, 'батарейки'),
(35, 'белье постельное'),
(52, 'блоки питания'),
(24, 'бытовая техника комплектующие'),
(55, 'видеонаблюдение'),
(10, 'видеорегистраторы'),
(33, 'жалюзи'),
(27, 'зарядные устройства'),
(8, 'интернет 3G'),
(44, 'кабельная продукция'),
(32, 'карнизы'),
(16, 'картриджи'),
(15, 'картриджи заправка'),
(19, 'компьютерные комплектующие'),
(59, 'компьютеры ремонт'),
(29, 'кухни под заказ'),
(41, 'магнитолы автомобильные'),
(28, 'мебель'),
(40, 'мобильные телефоны'),
(13, 'мобильные телефоны аксессуары'),
(42, 'мобильные телефоны БУ'),
(38, 'мобильные телефоны ремонт'),
(12, 'ноутбуки аксессуары'),
(11, 'ноутбуки б/у'),
(43, 'ноутбуки ремонт'),
(30, 'обои'),
(14, 'одежда спортивная'),
(56, 'охранные системы'),
(45, 'переходники и разъемы'),
(60, 'планшеты ремонт'),
(21, 'портьеры'),
(51, 'программное обеспечение'),
(37, 'пульты дистанционного управления'),
(53, 'радиодетали'),
(34, 'ролеты'),
(20, 'сетевое оборудование'),
(7, 'телевидение спутниковое'),
(57, 'телевидение цифровое'),
(23, 'тюль'),
(17, 'фотобумага'),
(18, 'чернила'),
(31, 'шторы');

-- --------------------------------------------------------

--
-- Table structure for table `point2commodity`
--

CREATE TABLE IF NOT EXISTS `point2commodity` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key of table',
  `idpoint` int(11) NOT NULL COMMENT 'FK to point',
  `idcommodity` int(11) NOT NULL COMMENT 'FK to commodity',
  `percent` float DEFAULT NULL COMMENT 'value of commodity for concrete point',
  PRIMARY KEY (`id`),
  KEY `idpoint` (`idpoint`,`idcommodity`)
) ENGINE=InnoDB  DEFAULT CHARSET=cp1251 COLLATE=cp1251_general_cs COMMENT='describe many commodity on point ' AUTO_INCREMENT=112 ;

--
-- Dumping data for table `point2commodity`
--

INSERT INTO `point2commodity` (`id`, `idpoint`, `idcommodity`, `percent`) VALUES
(8, 3, 7, NULL),
(9, 3, 8, NULL),
(10, 4, 9, NULL),
(11, 4, 10, NULL),
(12, 4, 11, NULL),
(13, 4, 12, NULL),
(14, 4, 13, NULL),
(15, 5, 14, NULL),
(16, 6, 13, NULL),
(17, 7, 16, NULL),
(18, 7, 15, NULL),
(19, 8, 21, NULL),
(20, 8, 23, NULL),
(22, 10, 24, NULL),
(23, 11, 26, NULL),
(24, 11, 25, NULL),
(25, 11, 27, NULL),
(26, 12, 28, NULL),
(27, 12, 29, NULL),
(28, 13, 30, NULL),
(29, 14, 23, NULL),
(30, 14, 31, NULL),
(31, 14, 32, NULL),
(32, 14, 33, NULL),
(33, 14, 34, NULL),
(34, 15, 32, NULL),
(35, 15, 33, NULL),
(36, 15, 34, NULL),
(37, 16, 35, NULL),
(38, 17, 37, NULL),
(39, 18, 38, NULL),
(40, 19, 39, NULL),
(41, 20, 40, NULL),
(42, 21, 41, NULL),
(43, 22, 19, NULL),
(44, 23, 19, NULL),
(45, 24, 7, NULL),
(46, 25, 38, NULL),
(47, 26, 13, NULL),
(48, 26, 42, NULL),
(49, 27, 43, NULL),
(50, 27, 12, NULL),
(51, 28, 38, NULL),
(52, 28, 44, NULL),
(53, 28, 45, NULL),
(54, 29, 41, NULL),
(55, 29, 46, NULL),
(56, 29, 47, NULL),
(57, 30, 38, NULL),
(58, 30, 42, NULL),
(59, 31, 13, NULL),
(60, 32, 42, NULL),
(61, 32, 13, NULL),
(62, 33, 19, NULL),
(63, 34, 26, NULL),
(64, 34, 25, NULL),
(65, 34, 27, NULL),
(66, 35, 40, NULL),
(67, 35, 7, NULL),
(68, 36, 12, NULL),
(69, 36, 48, NULL),
(70, 36, 50, NULL),
(71, 37, 42, NULL),
(72, 37, 13, NULL),
(75, 38, 19, NULL),
(76, 38, 20, NULL),
(77, 38, 51, NULL),
(78, 39, 42, NULL),
(79, 39, 13, NULL),
(80, 40, 42, NULL),
(81, 40, 13, NULL),
(82, 41, 37, NULL),
(83, 41, 52, NULL),
(84, 42, 53, NULL),
(85, 43, 37, NULL),
(86, 43, 54, NULL),
(87, 44, 19, NULL),
(88, 45, 54, NULL),
(89, 46, 7, NULL),
(90, 46, 54, NULL),
(91, 47, 42, NULL),
(92, 47, 13, NULL),
(93, 48, 42, NULL),
(94, 49, 53, NULL),
(95, 50, 38, NULL),
(96, 51, 53, NULL),
(97, 51, 52, NULL),
(98, 52, 55, NULL),
(99, 52, 56, NULL),
(100, 53, 13, NULL),
(101, 53, 44, NULL),
(102, 54, 7, NULL),
(103, 54, 57, NULL),
(104, 55, 54, NULL),
(105, 55, 58, NULL),
(106, 56, 13, NULL),
(107, 57, 59, NULL),
(108, 58, 41, NULL),
(109, 59, 54, NULL),
(110, 60, 42, NULL),
(111, 60, 38, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `points`
--

CREATE TABLE IF NOT EXISTS `points` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id for table',
  `pointnum` varchar(10) COLLATE cp1251_general_cs NOT NULL COMMENT 'unique number of point',
  `active` int(11) NOT NULL COMMENT '0 - not active; 1 - active',
  `pos_x` int(11) NOT NULL COMMENT 'position X on image',
  `pos_y` int(11) NOT NULL COMMENT 'position Y on image',
  `html` varchar(2048) COLLATE cp1251_general_cs NOT NULL COMMENT 'text of html for display point',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pointnum` (`pointnum`)
) ENGINE=InnoDB  DEFAULT CHARSET=cp1251 COLLATE=cp1251_general_cs COMMENT='table with all points in system' AUTO_INCREMENT=61 ;

--
-- Dumping data for table `points`
--

INSERT INTO `points` (`id`, `pointnum`, `active`, `pos_x`, `pos_y`, `html`) VALUES
(3, 'K5', 1, 1472, 321, ''),
(4, 'K7', 1, 1441, 535, ''),
(5, 'K9', 1, 1400, 752, ''),
(6, 'K11', 1, 1374, 912, ''),
(7, 'K12', 1, 1363, 1020, 'points/k12.html'),
(8, 'K13', 1, 1339, 1177, ''),
(9, 'K15', 1, 1303, 1346, ''),
(10, 'K17', 1, 1257, 1618, ''),
(11, 'K19', 1, 1225, 1830, ''),
(12, 'K21', 1, 1175, 2038, ''),
(13, 'K23', 1, 1006, 2254, ''),
(14, 'K27', 1, 765, 2462, ''),
(15, 'K29', 1, 661, 2553, ''),
(16, 'K31', 1, 527, 2670, ''),
(17, 'K32', 1, 459, 2730, ''),
(18, 'K33', 1, 390, 2787, ''),
(19, 'K37', 1, 117, 3031, ''),
(20, '89', 1, 3340, 990, ''),
(21, '91', 1, 3212, 990, ''),
(22, '93', 1, 3074, 997, ''),
(23, '97', 1, 2830, 992, 'points/97.html'),
(24, '99', 1, 2702, 991, ''),
(25, '101', 1, 2574, 991, 'points/101.html'),
(26, '103', 1, 2450, 994, ''),
(27, '105', 1, 2321, 993, ''),
(28, '107', 1, 2193, 991, ''),
(29, '111', 1, 1939, 990, ''),
(30, '113', 1, 3226, 1178, ''),
(31, '115', 1, 3098, 1177, ''),
(32, '117', 1, 2972, 1175, 'points/117.html'),
(33, '119', 1, 2846, 1176, ''),
(34, '121', 1, 2718, 1177, ''),
(35, '125', 1, 2464, 1175, ''),
(36, '127', 1, 2339, 1176, ''),
(37, '129', 1, 2211, 1177, ''),
(38, '131', 1, 2085, 1175, 'points/131.html'),
(39, '133', 1, 1956, 1175, 'points/133.html'),
(40, '135', 1, 1830, 1177, ''),
(41, '63', 1, 2264, 651, ''),
(42, '61', 1, 2388, 654, ''),
(43, '59', 1, 2543, 650, ''),
(44, '58', 1, 2608, 627, ''),
(45, '57', 1, 2669, 644, ''),
(46, '56', 1, 2736, 631, ''),
(47, '51', 1, 3047, 644, ''),
(48, '65', 1, 3364, 882, ''),
(49, '66', 1, 3305, 873, ''),
(50, '67', 1, 3238, 881, ''),
(51, '68', 1, 3181, 868, ''),
(52, '69', 1, 3083, 888, ''),
(53, '71', 1, 2955, 891, ''),
(54, '73', 1, 2831, 888, ''),
(55, '75', 1, 2701, 889, ''),
(56, '79', 1, 2444, 889, ''),
(57, '81', 1, 2318, 890, ''),
(58, '83', 1, 2192, 886, ''),
(59, '85', 1, 2060, 889, ''),
(60, '87', 1, 1936, 890, '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
