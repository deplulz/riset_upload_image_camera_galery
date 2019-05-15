-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 15, 2019 at 08:45 AM
-- Server version: 10.2.3-MariaDB-log
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `multipart`
--

-- --------------------------------------------------------

--
-- Table structure for table `img`
--

CREATE TABLE `img` (
  `file` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `img`
--

INSERT INTO `img` (`file`) VALUES
('localhost/multipart_api/img/background.png'),
('localhost/multipart_api/img/1557823527950.png'),
('localhost/multipart_api/img/1557823799342.png'),
('localhost/multipart_api/img/1557823852597.png'),
('http://192.168.100.144/multipart_api/img/background.png'),
('http://192.168.100.144/multipart_api/img/1557823996556.png'),
('http://192.168.100.144/multipart_api/img/1557824159844.png'),
('http://192.168.100.144/multipart_api/img/1557824177421.png'),
('http://192.168.100.144/multipart_api/img/1557834933894.png'),
('http://192.168.100.144/multipart_api/img/1557900723399.png'),
('http://192.168.100.144/multipart_api/img/1557900907750.png'),
('http://192.168.100.144/multipart_api/img/1557901023630.png'),
('http://192.168.100.144/multipart_api/img/1557901091263.png'),
('http://192.168.100.144/multipart_api/img/1557901283636.png'),
('http://192.168.100.144/multipart_api/img/1557901643139.png'),
('http://192.168.100.144/multipart_api/img/1557907697632.png'),
('http://192.168.100.144/multipart_api/img/1557908333267.png');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
