-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 03, 2024 at 07:34 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbit_fines`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblattendance`
--

CREATE TABLE `tblattendance` (
  `Attendance_ID` int(11) NOT NULL,
  `Student_ID` int(11) DEFAULT NULL,
  `Event_ID` int(11) DEFAULT NULL,
  `Attendance` int(11) DEFAULT NULL,
  `In` int(11) DEFAULT NULL,
  `Out` int(11) DEFAULT NULL,
  `Balance` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblattendance`
--

INSERT INTO `tblattendance` (`Attendance_ID`, `Student_ID`, `Event_ID`, `Attendance`, `In`, `Out`, `Balance`) VALUES
(1, 3, 1, 0, 0, 0, 0),
(2, 4, 1, 0, 0, 0, 0),
(3, 5, 1, 0, 0, 0, 0),
(4, 6, 1, 0, 0, 0, 0),
(5, 7, 1, 0, 0, 0, 0),
(6, 8, 1, 0, 0, 0, 0),
(7, 9, 1, 0, 0, 0, 0),
(8, 3, 2, 0, 0, 0, 0),
(9, 4, 2, 0, 0, 0, 0),
(10, 5, 2, 0, 0, 0, 0),
(11, 6, 2, 0, 0, 0, 0),
(12, 7, 2, 0, 0, 0, 0),
(13, 8, 2, 0, 0, 0, 0),
(14, 9, 2, 0, 0, 0, 0),
(15, 3, 3, 0, 0, 0, 0),
(16, 4, 3, 0, 0, 0, 0),
(17, 5, 3, 0, 0, 0, 0),
(18, 6, 3, 0, 0, 0, 0),
(19, 7, 3, 0, 0, 0, 0),
(20, 8, 3, 0, 0, 0, 0),
(21, 9, 3, 0, 0, 0, 0),
(25, 14, 1, 0, 0, 0, 0),
(26, 14, 2, 0, 0, 0, 0),
(27, 14, 3, 0, 0, 0, 0),
(28, 15, 1, 0, 0, 0, 0),
(29, 15, 2, 0, 0, 0, 0),
(30, 15, 3, 0, 0, 0, 0),
(31, 16, 1, 0, 0, 0, 0),
(32, 16, 2, 0, 0, 0, 0),
(33, 16, 3, 0, 0, 0, 0),
(34, 3, 4, 0, 0, 0, 0),
(35, 4, 4, 0, 0, 0, 0),
(36, 5, 4, 0, 0, 0, 0),
(37, 6, 4, 0, 0, 0, 0),
(38, 7, 4, 0, 0, 0, 0),
(39, 8, 4, 0, 0, 0, 0),
(40, 9, 4, 0, 0, 0, 0),
(41, 14, 4, 0, 0, 0, 0),
(42, 15, 4, 0, 0, 0, 0),
(43, 16, 4, 0, 0, 0, 0),
(49, 17, 1, 0, 0, 0, 0),
(50, 17, 2, 0, 0, 0, 0),
(51, 17, 3, 0, 0, 0, 0),
(52, 17, 4, 0, 0, 0, 0),
(53, 3, 5, 0, 0, 0, 0),
(54, 4, 5, 0, 0, 0, 0),
(55, 5, 5, 0, 0, 0, 0),
(56, 6, 5, 0, 0, 0, 0),
(57, 7, 5, 0, 0, 0, 0),
(58, 8, 5, 0, 0, 0, 0),
(59, 9, 5, 0, 0, 0, 0),
(60, 14, 5, 0, 0, 0, 0),
(61, 15, 5, 0, 0, 0, 0),
(62, 16, 5, 0, 0, 0, 0),
(63, 17, 5, 0, 0, 0, 0),
(68, 3, 6, 0, 0, 0, 0),
(69, 4, 6, 0, 0, 0, 0),
(70, 5, 6, 0, 0, 0, 0),
(71, 6, 6, 0, 0, 0, 0),
(72, 7, 6, 0, 0, 0, 0),
(73, 8, 6, 0, 0, 0, 0),
(74, 9, 6, 0, 0, 0, 0),
(75, 14, 6, 0, 0, 0, 0),
(76, 15, 6, 0, 0, 0, 0),
(77, 16, 6, 0, 0, 0, 0),
(78, 17, 6, 0, 0, 0, 0),
(83, 3, 7, 0, 0, 0, 0),
(84, 4, 7, 0, 0, 0, 0),
(85, 5, 7, 0, 0, 0, 0),
(86, 6, 7, 0, 0, 0, 0),
(87, 7, 7, 0, 0, 0, 0),
(88, 8, 7, 0, 0, 0, 0),
(89, 9, 7, 0, 0, 0, 0),
(90, 14, 7, 0, 0, 0, 0),
(91, 15, 7, 0, 0, 0, 0),
(92, 16, 7, 0, 0, 0, 0),
(93, 17, 7, 0, 0, 0, 0),
(98, 3, 8, 0, 0, 0, 0),
(99, 4, 8, 0, 0, 0, 0),
(100, 5, 8, 0, 0, 0, 0),
(101, 6, 8, 0, 0, 0, 0),
(102, 7, 8, 0, 0, 0, 0),
(103, 8, 8, 0, 0, 0, 0),
(104, 9, 8, 0, 0, 0, 0),
(105, 14, 8, 0, 0, 0, 0),
(106, 15, 8, 0, 0, 0, 0),
(107, 16, 8, 0, 0, 0, 0),
(108, 17, 8, 0, 0, 0, 0),
(113, 3, 9, 0, 0, 0, 0),
(114, 4, 9, 0, 0, 0, 0),
(115, 5, 9, 0, 0, 0, 0),
(116, 6, 9, 0, 0, 0, 0),
(117, 7, 9, 0, 0, 0, 0),
(118, 8, 9, 0, 0, 0, 0),
(119, 9, 9, 0, 0, 0, 0),
(120, 14, 9, 0, 0, 0, 0),
(121, 15, 9, 0, 0, 0, 0),
(122, 16, 9, 0, 0, 0, 0),
(123, 17, 9, 0, 0, 0, 0),
(128, 3, 10, 0, 0, 0, 0),
(129, 4, 10, 3, 6, 4, 0),
(130, 5, 10, 0, 0, 0, 0),
(131, 6, 10, 0, 0, 0, 0),
(132, 7, 10, 0, 0, 0, 0),
(133, 8, 10, 0, 0, 0, 0),
(134, 9, 10, 0, 0, 0, 0),
(135, 14, 10, 0, 0, 0, 0),
(136, 15, 10, 0, 0, 0, 0),
(137, 16, 10, 0, 0, 0, 0),
(138, 17, 10, 0, 0, 0, 0),
(143, 3, 11, 0, 0, 0, 0),
(144, 4, 11, 3, 6, 4, 0),
(145, 5, 11, 0, 0, 0, 0),
(146, 6, 11, 0, 0, 0, 0),
(147, 7, 11, 0, 0, 0, 0),
(148, 8, 11, 0, 0, 0, 0),
(149, 9, 11, 0, 0, 0, 0),
(150, 14, 11, 0, 0, 0, 0),
(151, 15, 11, 0, 0, 0, 0),
(152, 16, 11, 0, 0, 0, 0),
(153, 17, 11, 0, 0, 0, 0),
(158, 3, 12, 3, 6, 4, 0),
(159, 4, 12, 0, 0, 0, 0),
(160, 5, 12, 0, 0, 0, 0),
(161, 6, 12, 0, 0, 0, 0),
(162, 7, 12, 0, 0, 0, 0),
(163, 8, 12, 0, 0, 0, 0),
(164, 9, 12, 0, 0, 0, 0),
(165, 14, 12, 0, 0, 0, 0),
(166, 15, 12, 0, 0, 0, 0),
(167, 16, 12, 0, 0, 0, 0),
(168, 17, 12, 0, 0, 0, 0),
(173, 3, 13, 3, 6, 4, 0),
(174, 4, 13, 0, 0, 0, 0),
(175, 5, 13, 0, 0, 0, 0),
(176, 6, 13, 0, 0, 0, 0),
(177, 7, 13, 0, 0, 0, 0),
(178, 8, 13, 0, 0, 0, 0),
(179, 9, 13, 0, 0, 0, 0),
(180, 14, 13, 0, 0, 0, 0),
(181, 15, 13, 0, 0, 0, 0),
(182, 16, 13, 0, 0, 0, 0),
(183, 17, 13, 0, 0, 0, 0),
(188, 3, 14, 2, 4, 4, 0),
(189, 4, 14, 0, 0, 0, 0),
(190, 5, 14, 0, 0, 0, 0),
(191, 6, 14, 0, 0, 0, 0),
(192, 7, 14, 0, 0, 0, 0),
(193, 8, 14, 0, 0, 0, 0),
(194, 9, 14, 0, 0, 0, 0),
(195, 14, 14, 0, 0, 0, 0),
(196, 15, 14, 0, 0, 0, 0),
(197, 16, 14, 0, 0, 0, 0),
(198, 17, 14, 0, 0, 0, 0),
(203, 3, 15, 2, 4, 4, 0),
(204, 4, 15, 0, 0, 0, 0),
(205, 5, 15, 0, 0, 0, 0),
(206, 6, 15, 0, 0, 0, 0),
(207, 7, 15, 0, 0, 0, 0),
(208, 8, 15, 0, 0, 0, 0),
(209, 9, 15, 0, 0, 0, 0),
(210, 14, 15, 0, 0, 0, 0),
(211, 15, 15, 0, 0, 0, 0),
(212, 16, 15, 0, 0, 0, 0),
(213, 17, 15, 0, 0, 0, 0),
(218, 3, 16, 3, 6, 4, 0),
(219, 4, 16, 0, 0, 0, 0),
(220, 5, 16, 0, 0, 0, 0),
(221, 6, 16, 0, 0, 0, 0),
(222, 7, 16, 0, 0, 0, 0),
(223, 8, 16, 0, 0, 0, 0),
(224, 9, 16, 0, 0, 0, 0),
(225, 14, 16, 0, 0, 0, 0),
(226, 15, 16, 0, 0, 0, 0),
(227, 16, 16, 0, 0, 0, 0),
(228, 17, 16, 0, 0, 0, 0),
(233, 3, 17, 1, 4, 0, 0),
(234, 4, 17, 0, 0, 0, 0),
(235, 5, 17, 0, 0, 0, 0),
(236, 6, 17, 0, 0, 0, 0),
(237, 7, 17, 0, 0, 0, 0),
(238, 8, 17, 0, 0, 0, 0),
(239, 9, 17, 0, 0, 0, 0),
(240, 14, 17, 0, 0, 0, 0),
(241, 15, 17, 0, 0, 0, 0),
(242, 16, 17, 0, 0, 0, 0),
(243, 17, 17, 3, 6, 4, 0),
(248, 3, 18, 3, 6, 4, 25),
(249, 4, 18, 0, 0, 0, 100),
(250, 5, 18, 0, 0, 0, 100),
(251, 6, 18, 0, 0, 0, 100),
(252, 7, 18, 0, 0, 0, 100),
(253, 8, 18, 0, 0, 0, 100),
(254, 9, 18, 0, 0, 0, 100),
(255, 14, 18, 0, 0, 0, 100),
(256, 15, 18, 1, 4, 0, 75),
(257, 16, 18, 1, 4, 0, 75),
(258, 17, 18, 3, 6, 4, 25),
(263, 3, 19, 2, 4, 4, 50),
(264, 4, 19, 2, 4, 4, 50),
(265, 5, 19, 2, 4, 4, 50),
(266, 6, 19, 1, 4, 0, 75),
(267, 7, 19, 1, 4, 0, 75),
(268, 8, 19, 1, 4, 0, 75),
(269, 9, 19, 1, 4, 0, 75),
(270, 14, 19, 1, 4, 0, 75),
(271, 15, 19, 2, 4, 4, 50),
(272, 16, 19, 2, 4, 4, 50),
(273, 17, 19, 2, 4, 4, 50),
(278, 3, 20, 1, 4, 0, 25),
(279, 4, 20, 1, 4, 0, 25),
(280, 5, 20, 1, 4, 0, 25),
(281, 6, 20, 1, 4, 0, 25),
(282, 7, 20, 1, 4, 0, 25),
(283, 8, 20, 1, 4, 0, 25),
(284, 9, 20, 1, 4, 0, 25),
(285, 14, 20, 1, 4, 0, 25),
(286, 15, 20, 1, 4, 0, 25),
(287, 16, 20, 1, 4, 0, 25),
(288, 17, 20, 1, 4, 0, 25),
(293, 18, 1, 0, 0, 0, 100),
(294, 18, 2, 0, 0, 0, 100),
(295, 18, 3, 0, 0, 0, 50),
(296, 18, 4, 0, 0, 0, 25),
(297, 18, 5, 0, 0, 0, 100),
(298, 18, 6, 0, 0, 0, 100),
(299, 18, 7, 0, 0, 0, 0),
(300, 18, 8, 0, 0, 0, 100),
(301, 18, 9, 0, 0, 0, 75),
(302, 18, 10, 0, 0, 0, 75),
(303, 18, 11, 0, 0, 0, 100),
(304, 18, 12, 0, 0, 0, 75),
(305, 18, 13, 0, 0, 0, 75),
(306, 18, 14, 0, 0, 0, 50),
(307, 18, 15, 0, 0, 0, 75),
(308, 18, 16, 0, 0, 0, 75),
(309, 18, 17, 0, 0, 0, 100),
(310, 18, 18, 0, 0, 0, 100),
(311, 18, 19, 0, 0, 0, 100),
(312, 18, 20, 0, 0, 0, 50),
(313, 3, 21, 0, 0, 0, 50),
(314, 4, 21, 0, 0, 0, 50),
(315, 5, 21, 2, 4, 4, 0),
(316, 6, 21, 2, 4, 4, 0),
(317, 7, 21, 1, 4, 0, 25),
(318, 8, 21, 1, 4, 0, 25),
(319, 9, 21, 0, 0, 0, 50),
(320, 14, 21, 0, 0, 0, 50),
(321, 15, 21, 0, 0, 0, 50),
(322, 16, 21, 0, 0, 0, 50),
(323, 17, 21, 0, 0, 0, 0),
(324, 18, 21, 0, 0, 0, 50),
(328, 19, 1, 0, 0, 0, 100),
(329, 19, 2, 0, 0, 0, 100),
(330, 19, 3, 0, 0, 0, 50),
(331, 19, 4, 0, 0, 0, 25),
(332, 19, 5, 0, 0, 0, 100),
(333, 19, 6, 0, 0, 0, 100),
(334, 19, 7, 0, 0, 0, 0),
(335, 19, 8, 0, 0, 0, 100),
(336, 19, 9, 0, 0, 0, 75),
(337, 19, 10, 0, 0, 0, 75),
(338, 19, 11, 0, 0, 0, 100),
(339, 19, 12, 0, 0, 0, 75),
(340, 19, 13, 0, 0, 0, 75),
(341, 19, 14, 0, 0, 0, 50),
(342, 19, 15, 0, 0, 0, 75),
(343, 19, 16, 0, 0, 0, 75),
(344, 19, 17, 0, 0, 0, 100),
(345, 19, 18, 0, 0, 0, 100),
(346, 19, 19, 0, 0, 0, 100),
(347, 19, 20, 0, 0, 0, 50),
(348, 19, 21, 0, 0, 0, 50),
(349, 4, 22, 0, 0, 0, 50),
(350, 5, 22, 2, 4, 4, 0),
(351, 6, 22, 0, 0, 0, 0),
(352, 7, 22, 0, 0, 0, 0),
(353, 8, 22, 0, 0, 0, 50),
(354, 9, 22, 0, 0, 0, 50),
(355, 14, 22, 0, 0, 0, 50),
(356, 15, 22, 0, 0, 0, 50),
(357, 16, 22, 0, 0, 0, 50),
(358, 17, 22, 0, 0, 0, 0),
(359, 18, 22, 0, 0, 0, 50),
(360, 19, 22, 0, 0, 0, 50),
(364, 6, 23, 0, 0, 0, 50),
(365, 7, 23, 0, 0, 0, 50),
(366, 8, 23, 0, 0, 0, 50),
(367, 9, 23, 0, 0, 0, 50),
(368, 14, 23, 0, 0, 0, 50),
(369, 15, 23, 0, 0, 0, 50),
(370, 16, 23, 0, 0, 0, 50),
(371, 17, 23, 0, 0, 0, 50),
(372, 18, 23, 0, 0, 0, 50),
(373, 19, 23, 0, 0, 0, 50);

-- --------------------------------------------------------

--
-- Table structure for table `tblevent`
--

CREATE TABLE `tblevent` (
  `Event_ID` int(11) NOT NULL,
  `Admin_ID` int(11) DEFAULT NULL,
  `Event_Name` varchar(50) DEFAULT NULL,
  `Event_Type` varchar(50) DEFAULT NULL,
  `Event_Description` varchar(100) DEFAULT NULL,
  `Event_Date` date DEFAULT NULL,
  `Attendance` int(11) DEFAULT NULL,
  `Start` time DEFAULT NULL,
  `End` time DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `In` int(11) NOT NULL,
  `Out` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblevent`
--

INSERT INTO `tblevent` (`Event_ID`, `Admin_ID`, `Event_Name`, `Event_Type`, `Event_Description`, `Event_Date`, `Attendance`, `Start`, `End`, `Status`, `In`, `Out`) VALUES
(1, 1, 'Sportest', 'sport', 'none', '2024-09-13', 4, '08:00:00', '05:00:00', 'done', 0, 0),
(2, 1, 'Sportest', 'sport', 'none', '2024-09-14', 4, '08:00:00', '05:00:00', 'done', 6, 6),
(3, 1, 'kjhkjh', 'kjhjkh', 'jhjghg', '2024-02-02', 2, '05:00:00', '06:00:00', 'done', 4, 4),
(4, 1, 'hghjg', 'ghjgjh', 'ghjgj', '2024-09-03', 1, '01:00:00', '05:00:00', 'done', 0, 2),
(5, 1, 'kjhj', 'hjkh', 'kjjhjkh', '2024-12-02', 4, '01:00:00', '02:00:00', 'done', 6, 6),
(6, 1, 'hgjh', 'ggy', 'jhg', '2024-09-09', 4, '09:00:00', '10:00:00', 'done', 6, 6),
(7, 1, 'h', 'ghgh', 'ghj', '2025-02-20', 0, '02:00:00', '02:00:00', 'done', 0, 0),
(8, 1, 'admin', 'damin', 'adm', '2023-12-20', 4, '01:00:00', '01:00:00', 'done', 6, 6),
(9, 1, 'none', 'none', 'none', '2024-09-03', 3, '08:00:00', '05:00:00', 'done', 6, 4),
(10, 1, 'try', 'try', 'try', '2024-09-03', 3, '08:00:00', '05:00:00', 'done', 6, 4),
(11, 1, 'new', 'new', 'new', '2024-09-09', 4, '01:00:00', '05:00:00', 'done', 6, 6),
(12, 1, 'none', 'none', 'none', '2024-09-09', 3, '05:00:00', '05:00:00', 'done', 6, 4),
(13, 1, 'none', 'none', 'none', '2024-09-09', 3, '01:00:00', '05:00:00', 'done', 6, 4),
(14, 1, 'none', 'none', 'none', '2024-09-09', 2, '01:00:00', '05:00:00', 'done', 4, 4),
(15, 1, 'none', 'none', 'none', '2024-09-07', 3, '01:00:00', '02:00:00', 'done', 6, 4),
(16, 1, 'none', 'none', 'none', '2024-09-09', 3, '01:00:00', '05:00:00', 'done', 6, 4),
(17, 1, 'none', 'none', 'none', '2024-09-09', 4, '05:00:00', '05:00:00', 'done', 6, 6),
(18, 1, 'none', 'none', 'none', '2024-09-09', 4, '01:00:00', '05:00:00', 'done', 6, 6),
(19, 1, 'admin', 'admin', 'admin', '2024-09-09', 4, '01:00:00', '05:00:00', 'done', 6, 6),
(20, 1, 'none', 'none', 'none', '2024-08-17', 2, '08:00:00', '05:00:00', 'done', 4, 4),
(21, 1, 'none', 'none', 'none', '2024-09-09', 2, '01:00:00', '05:00:00', 'ongoing', 4, 4),
(22, 21, 'none', 'none', 'none', '2024-09-09', 2, '01:00:00', '03:00:00', 'ongoing', 4, 4),
(23, 1, 'hgui', 'gg', 'ugu', '2024-09-09', 2, '02:00:00', '03:00:00', 'ongoing', 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tblfines`
--

CREATE TABLE `tblfines` (
  `Fines_ID` int(11) NOT NULL,
  `Student_ID` int(11) DEFAULT NULL,
  `Amount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblfines`
--

INSERT INTO `tblfines` (`Fines_ID`, `Student_ID`, `Amount`) VALUES
(43, 3, 50),
(44, 4, 50),
(45, 5, 0),
(46, 6, 50),
(47, 7, 75),
(48, 8, 75),
(49, 9, 100),
(50, 14, 100),
(51, 15, 100),
(52, 16, 100),
(53, 17, 50),
(57, 18, 100),
(58, 19, 100);

-- --------------------------------------------------------

--
-- Table structure for table `tblpayment`
--

CREATE TABLE `tblpayment` (
  `Payment_ID` int(11) NOT NULL,
  `Student_ID` int(11) DEFAULT NULL,
  `Amount` double DEFAULT NULL,
  `Payment_Date` date DEFAULT curdate(),
  `Payment_Time` time DEFAULT curtime(),
  `Admin_ID` int(11) DEFAULT NULL,
  `Event_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblpayment`
--

INSERT INTO `tblpayment` (`Payment_ID`, `Student_ID`, `Amount`, `Payment_Date`, `Payment_Time`, `Admin_ID`, `Event_ID`) VALUES
(1, 7, 50, '2024-09-03', '23:32:22', 1, NULL),
(2, 17, 50, '2024-09-03', '23:42:52', 1, 21),
(3, 17, 50, '2024-09-03', '23:42:52', 1, 22);

-- --------------------------------------------------------

--
-- Table structure for table `tblstudent`
--

CREATE TABLE `tblstudent` (
  `Student_ID` int(11) NOT NULL,
  `School_ID` varchar(50) NOT NULL,
  `First_Name` varchar(50) NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `Gender` varchar(50) DEFAULT NULL,
  `Year_Level` varchar(10) DEFAULT NULL,
  `Section` varchar(5) DEFAULT NULL,
  `Contact_Number` varchar(15) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` varchar(10) DEFAULT NULL,
  `Username` varchar(50) NOT NULL,
  `Added_by` varchar(50) DEFAULT NULL,
  `Status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblstudent`
--

INSERT INTO `tblstudent` (`Student_ID`, `School_ID`, `First_Name`, `Last_Name`, `Gender`, `Year_Level`, `Section`, `Contact_Number`, `Email`, `Password`, `Role`, `Username`, `Added_by`, `Status`) VALUES
(1, 'admins', 'admin', 'admin', NULL, NULL, NULL, '09279504023', 'admin@gmail.com', 'admins', 'admin', 'admin', '', 'active'),
(3, 'mcc2022', 'dan', 'clanor', 'Male', '3rd Year', 'F3', '0912345678', 'dan@gmail.com', 'dan', 'student', 'dan', '', ''),
(4, 'mcc', 'dan', 'dan', 'Male', '3rd Year', 'F2', '0912345678', 'danny@gmail.com', 'dan', 'student', '', '', ''),
(5, 'MCC2022_0347', 'Dan Lloyd', 'Clanor', 'Male', '3rd Year', 'F2', '09279504023', 'danlloyd@gmail.com', 'lloyd', 'student', '', '', 'deactivated'),
(6, 'MCC2020', 'DanDan', 'Clanor', 'Male', '3rd Year', 'F2', '09123456789', 'email@gmail.com', 'eyy', 'student', 'DANDANDAN', '', 'active'),
(7, 'pogi1', 'none', 'none', 'LGBTQIA+', '4th Year', 'F6', '0987654321', 'none@gmail.com', 'none', 'student', 'DAN', '', 'active'),
(8, 'pogi', 'pogi', 'pogi', 'LGBTQIA+', '4th Year', 'F5', '098765432', 'pogi@gmail.com', 'pogi', 'student', 'POGI', '', 'active'),
(9, 'MCC2022_0347', 'Dan Lloyd', 'Clanor', 'Male', '3rd Year', 'F2', '0912345689', 'dandan@gmail.com', 'pogi', 'student', 'lloyd', '', 'active'),
(14, 'MCC2021', 'Gessile', 'De Castro', 'Female', '4th Year', 'F3', '0912345678', 'fye@gmail.com', 'gessile', 'student', 'fye', NULL, 'active'),
(15, 'hghjg', 'hghg', 'hjghjgh', 'Male', '1st Year', 'F3', '1234', '1234@gmail.com', '1234', 'student', '1234', NULL, 'active'),
(16, 'rtyyuh', 'ftuyiu', 'fyuyui', 'Female', '1st Year', 'F2', 'qwerty', 'qwerty', 'qwerty', 'student', 'qwerty', NULL, 'active'),
(17, 'qwerty', 'qwerty', 'qwerty', 'Male', '2nd Year', 'F2', 'qwerty', 'qwert', 'qwerty', 'student', 'qwert', NULL, 'active'),
(18, 'mcc2022', 'dan', 'clanor', 'Male', '3rd Year', 'F2', '091234578', 'dan@gmail.com', 'dan', 'student', 'danl', NULL, 'active'),
(19, 'sdgf', 'jhg', 'jhghg', 'Female', '1st Year', 'F2', '1234567890', 'da@gmail.com', 'da', 'student', 'da', NULL, 'active'),
(20, 'me', 'me', 'me', NULL, NULL, NULL, '98765432', 'me', 'me', 'admin', 'me', 'admin', 'deactivated'),
(21, 'dan', 'dan', 'dan', NULL, NULL, NULL, '12345678', 'dan', 'dan', 'admin', 'danlloyd', 'admin', 'active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblattendance`
--
ALTER TABLE `tblattendance`
  ADD PRIMARY KEY (`Attendance_ID`),
  ADD KEY `Student_ID` (`Student_ID`),
  ADD KEY `Event_ID` (`Event_ID`);

--
-- Indexes for table `tblevent`
--
ALTER TABLE `tblevent`
  ADD PRIMARY KEY (`Event_ID`),
  ADD KEY `fk_admin` (`Admin_ID`);

--
-- Indexes for table `tblfines`
--
ALTER TABLE `tblfines`
  ADD PRIMARY KEY (`Fines_ID`),
  ADD KEY `Student_ID` (`Student_ID`);

--
-- Indexes for table `tblpayment`
--
ALTER TABLE `tblpayment`
  ADD PRIMARY KEY (`Payment_ID`),
  ADD KEY `Student_ID` (`Student_ID`),
  ADD KEY `Admin_ID` (`Admin_ID`),
  ADD KEY `fk_event` (`Event_ID`);

--
-- Indexes for table `tblstudent`
--
ALTER TABLE `tblstudent`
  ADD PRIMARY KEY (`Student_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblattendance`
--
ALTER TABLE `tblattendance`
  MODIFY `Attendance_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=379;

--
-- AUTO_INCREMENT for table `tblevent`
--
ALTER TABLE `tblevent`
  MODIFY `Event_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `tblfines`
--
ALTER TABLE `tblfines`
  MODIFY `Fines_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `tblpayment`
--
ALTER TABLE `tblpayment`
  MODIFY `Payment_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tblstudent`
--
ALTER TABLE `tblstudent`
  MODIFY `Student_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tblattendance`
--
ALTER TABLE `tblattendance`
  ADD CONSTRAINT `tblattendance_ibfk_1` FOREIGN KEY (`Student_ID`) REFERENCES `tblstudent` (`Student_ID`),
  ADD CONSTRAINT `tblattendance_ibfk_2` FOREIGN KEY (`Event_ID`) REFERENCES `tblevent` (`Event_ID`);

--
-- Constraints for table `tblevent`
--
ALTER TABLE `tblevent`
  ADD CONSTRAINT `fk_admin` FOREIGN KEY (`Admin_ID`) REFERENCES `tblstudent` (`Student_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblfines`
--
ALTER TABLE `tblfines`
  ADD CONSTRAINT `tblfines_ibfk_1` FOREIGN KEY (`Student_ID`) REFERENCES `tblstudent` (`Student_ID`);

--
-- Constraints for table `tblpayment`
--
ALTER TABLE `tblpayment`
  ADD CONSTRAINT `fk_event` FOREIGN KEY (`Event_ID`) REFERENCES `tblevent` (`Event_ID`),
  ADD CONSTRAINT `tblpayment_ibfk_1` FOREIGN KEY (`Student_ID`) REFERENCES `tblstudent` (`Student_ID`),
  ADD CONSTRAINT `tblpayment_ibfk_2` FOREIGN KEY (`Admin_ID`) REFERENCES `tblstudent` (`Student_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
