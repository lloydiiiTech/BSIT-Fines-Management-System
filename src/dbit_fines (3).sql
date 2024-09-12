-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 07, 2024 at 04:25 PM
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
(26, 2, 21, 0, 0, 0, 0),
(27, 3, 21, 0, 0, 0, 100),
(29, 2, 22, 0, 0, 0, 0),
(30, 3, 22, 0, 0, 0, 100),
(32, 2, 23, 0, 0, 0, 100),
(33, 3, 23, 0, 0, 0, 100),
(35, 5, 21, 3, 6, 4, 0),
(36, 5, 22, 2, 4, 4, 0),
(37, 5, 23, 0, 0, 0, 100),
(38, 6, 21, 2, 4, 4, 50),
(39, 6, 22, 2, 4, 2, 50),
(40, 6, 23, 0, 0, 0, 100),
(41, 7, 21, 3, 6, 4, 25),
(42, 7, 22, 1, 2, 0, 75),
(43, 7, 23, 0, 0, 0, 100),
(44, 8, 21, 1, 4, 0, 75),
(45, 8, 22, 2, 2, 2, 50),
(46, 8, 23, 0, 0, 0, 100),
(47, 9, 21, 1, 4, 0, 0),
(48, 9, 22, 0, 0, 0, 0),
(49, 9, 23, 0, 0, 0, 100),
(50, 2, 24, 1, 4, 0, 75),
(51, 3, 24, 0, 0, 0, 100),
(52, 5, 24, 0, 0, 0, 0),
(53, 6, 24, 0, 0, 0, 100),
(54, 7, 24, 0, 0, 0, 100),
(55, 8, 24, 0, 0, 0, 100),
(56, 9, 24, 0, 0, 0, 100),
(57, 5, 25, 0, 0, 0, 75),
(58, 6, 25, 0, 0, 0, 75),
(59, 7, 25, 0, 0, 0, 75),
(60, 8, 25, 0, 0, 0, 75),
(61, 9, 25, 0, 0, 0, 75),
(64, 10, 21, 4, 6, 6, 0),
(65, 10, 22, 0, 0, 0, 100),
(66, 10, 23, 3, 6, 4, 25),
(67, 10, 24, 0, 0, 0, 100),
(68, 10, 25, 0, 0, 0, 75),
(69, 11, 21, 0, 0, 0, 100),
(70, 11, 22, 0, 0, 0, 100),
(71, 11, 23, 0, 0, 0, 100),
(72, 11, 24, 0, 0, 0, 100),
(73, 11, 25, 0, 0, 0, 75),
(74, 5, 26, 1, 4, 0, 50),
(75, 6, 26, 1, 4, 0, 50),
(76, 7, 26, 1, 4, 0, 50),
(77, 8, 26, 1, 4, 0, 50),
(78, 9, 26, 0, 0, 0, 75),
(79, 10, 26, 3, 6, 4, 0),
(80, 11, 26, 0, 0, 0, 75),
(81, 2, 27, 0, 0, 0, 50),
(82, 5, 27, 0, 0, 0, 50),
(83, 6, 27, 0, 0, 0, 50),
(84, 7, 27, 0, 0, 0, 50),
(85, 8, 27, 0, 0, 0, 50),
(86, 9, 27, 0, 0, 0, 50),
(87, 10, 27, 0, 0, 0, 50);

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
(21, 1, 'Sportfest', 'sport', 'sport', '2024-09-09', 4, '01:00:00', '05:00:00', 'ongoing', 6, 6),
(22, 1, 'Sportfest Day2', 'sport', 'sport', '2024-09-10', 4, '01:00:00', '05:00:00', 'ongoing', 6, 6),
(23, 1, 'Sportfest Day3', 'sport', 'sport', '2024-09-11', 4, '01:00:00', '05:00:00', 'ongoing', 6, 6),
(24, 1, 'fdgd', 'ghfhg', 'gfdfg', '2024-09-08', 4, '12:00:00', '01:00:00', 'done', 6, 6),
(25, 1, 'try', 'try', 'try', '2024-09-09', 3, '01:00:00', '05:00:00', 'ongoing', 6, 4),
(26, 1, 'BCRV', 'assessment', 'presentation', '2024-09-07', 3, '08:00:00', '12:00:00', 'ongoing', 6, 4),
(27, 1, 'BCRV', 'SIL', 'OJT', '2024-09-16', 2, '01:00:00', '05:00:00', 'ongoing', 4, 4);

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
(3, 5, 275),
(4, 6, 375),
(5, 7, 375),
(6, 8, 400),
(7, 9, 300),
(8, 10, 250),
(9, 11, 450);

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
(55, 9, 100, '2024-09-07', '07:56:32', 1, 22),
(56, 9, 100, '2024-09-07', '07:56:32', 1, 23),
(57, 2, 100, '2024-09-07', '08:05:15', 1, 21),
(58, 2, 100, '2024-09-07', '08:05:15', 1, 22),
(59, 2, 100, '2024-09-07', '08:05:15', 1, 23),
(60, 5, 25, '2024-09-07', '08:08:14', 1, 21),
(61, 5, 50, '2024-09-07', '08:08:14', 1, 22),
(62, 5, 100, '2024-09-07', '08:08:14', 1, 23),
(63, 5, 100, '2024-09-07', '08:08:14', 1, 24),
(64, 9, 75, '2024-09-07', '11:26:52', 1, 21);

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
(1, 'admin', 'admin', 'admin', NULL, NULL, NULL, '09279504023', 'admin@gmail.com', 'admin', 'admin', 'admin', '', 'active'),
(2, 'MCC2022-0347', 'Dan Lloyd', 'Clanor', 'Male', '3rd Year', 'F2', '09279504023', 'danlloydclanor29@gmail.com', 'dan', 'student', 'lloydiii', NULL, 'deactivated'),
(3, 'MCC2022-0457', 'Paul Janssen', 'Martinez', 'Male', '3rd Year', 'F2', '0912345789', 'jnssn@gmail.com', 'jan', 'student', 'jnssn', NULL, 'deactivated'),
(4, 'MCC_admin', 'Dan', 'Clanor', NULL, NULL, NULL, '0987654329', 'dan@gmail.com', 'dan', 'admin', 'dan', 'admin', 'active'),
(5, 'MCC2022-0354', 'Rica', 'Crisporo', 'Female', '4th Year', 'F2', '0995616995', 'crispororicamae15@gmail.com', '1234', 'student', 'ricamae', NULL, 'active'),
(6, 'MCC2022-1209', 'Evangel', 'Fajardo', 'Female', '3rd Year', 'F2', '09559676695', 'evangel@gmail.com', 'evangel', 'student', 'vangel', NULL, 'active'),
(7, 'MCC2022-0533 ', 'Paul Jerome', 'Martinez', 'Male', '3rd Year', 'F2', '09982863340', 'firedown1231@gmail.com', 'jem', 'student', 'jerome', NULL, 'active'),
(8, 'MCC2022-0850 ', 'Mean', 'Aldea', 'Female', '3rd Year', 'F2', '09634056453', 'aldeamean13@gmail.com', 'mean', 'student', 'mean', NULL, 'active'),
(9, 'MCC2022-1314', 'Mark Jenssen', 'Bahia', 'Male', '3rd Year', 'F2', '09388030032', 'bmarkjenssen@gmail.com ', 'mark', 'student', 'mark', NULL, 'active'),
(10, 'MCC2022-4056', 'Glesie', 'Maranan', 'Female', '3rd Year', 'F2', '09634056451', 'marananglesie@gmail.com', '123456789', 'student', 'Glesie', NULL, 'active'),
(11, 'MCC2022-037', 'Dan Lloyd', 'Clanor', 'Male', '3rd Year', 'F2', '09279504023', 'danlloyd@gmail.com', '1234', 'student', 'danlloyddan', NULL, 'deactivated'),
(12, '12345', 'ange', 'perez', NULL, NULL, NULL, '09123456789', 'perez@gmail.com', 'ange', 'admin', 'angelica', 'admin', 'active');

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
  MODIFY `Attendance_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;

--
-- AUTO_INCREMENT for table `tblevent`
--
ALTER TABLE `tblevent`
  MODIFY `Event_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `tblfines`
--
ALTER TABLE `tblfines`
  MODIFY `Fines_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tblpayment`
--
ALTER TABLE `tblpayment`
  MODIFY `Payment_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT for table `tblstudent`
--
ALTER TABLE `tblstudent`
  MODIFY `Student_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

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
