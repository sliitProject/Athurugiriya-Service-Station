-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 28, 2015 at 01:09 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `voltage`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE IF NOT EXISTS `appointment` (
  `appointmentID` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(20) NOT NULL,
  `Time` varchar(50) NOT NULL,
  `TimeStatus` varchar(3) NOT NULL,
  `duration` int(11) NOT NULL,
  `portNo` int(11) NOT NULL,
  `CustID` int(11) NOT NULL,
  `ContactNo` int(11) NOT NULL,
  `custName` varchar(50) NOT NULL,
  `VehiRegNO` varchar(50) NOT NULL,
  `VehiModel` varchar(20) NOT NULL,
  `emailNotification` varchar(50) NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`appointmentID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`appointmentID`, `date`, `Time`, `TimeStatus`, `duration`, `portNo`, `CustID`, `ContactNo`, `custName`, `VehiRegNO`, `VehiModel`, `emailNotification`) VALUES
(6, '2015-08-26', '1.00', 'PM', 1, 1, 1, 768379257, 'Devin Amarasekara', '5462', 'Nissan -leaf', 'Pending'),
(7, '2015-08-27', '2.00', 'PM', 4, 2, 2, 765894521, 'Chathura Pathmasooriya', '5886', 'Tesla Model S', 'Pending'),
(9, '2015-08-29', '4.00', 'PM', 1, 2, 2, 770530173, 'Chathura Pathmasooriya', '5678', 'Nissan Sunny', 'Pending'),
(10, '2015-08-30', '7.00', 'PM', 1, 1, 2, 712313465, 'Chathura Pathmasooriya', '6784', 'BMW 520d', 'Pending'),
(11, '2015-08-28', '4.00', 'PM', 1, 3, 2, 47390, 'Chathura Pathmasooriya', '47390', 'Nissan-leaf', 'Pending'),
(12, '2015-08-28', '3.00', 'PM', 1, 1, 2, 756845958, 'Chathura Pathmasooriya', '65432', 'Leaf', 'Pending'),
(13, '2015-09-26', '1.00', 'AM', 2, 1, 2, 543534, 'Chathura Pathmasooriya', '34534', 'fgd', 'Pending'),
(14, '2015-09-26', '2.00', 'AM', 3, 2, 2, 432, 'Chathura Pathmasooriya', '234', 'gdfg', 'Pending'),
(15, '2015-09-26', '1.00', 'AM', 3, 1, 2, 545, 'Chathura Pathmasooriya', '4353', 'fd', 'Pending'),
(16, '2015-09-25', '1.00', 'AM', 2, 2, 1, 435, 'Devin Amarasekara', '234', 'fsd', 'Pending'),
(17, '2015-09-26', '1.00', 'AM', 3, 2, 2, 342, 'Chathura Pathmasooriya', '234', 'rwe', 'Pending'),
(18, '2015-09-26', '2.00', 'AM', 3, 1, 2, 4234, 'Chathura Pathmasooriya', '423', '234', 'Pending'),
(19, '2015-09-29', '3.00', 'PM', 1, 1, 1, 765893217, 'Devin Amarasekara', 'WD-3478', 'Nissan Leaf', 'Pending'),
(20, '2015-09-30', '3.00', 'PM', 1, 1, 1, 778696113, 'Devin Amarasekara', 'WD-3478', 'Nissan Leaf', 'Pending'),
(21, '2015-09-30', '3.00', 'PM', 1, 2, 1, 777515363, 'Devin Amarasekara', 'WD-3478', 'Nissan Leaf', 'Pending'),
(22, '2015-09-28', '1.00', 'PM', 1, 1, 1, 774569872, 'Devin Amarasekara', 'WE-9087', 'Nissan Leaf', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE IF NOT EXISTS `bills` (
  `billid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `date` varchar(15) NOT NULL,
  PRIMARY KEY (`billid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`billid`, `description`, `type`, `amount`, `date`) VALUES
(1, 'Paid by check', 'Electricity', 2500, '2015-08-01'),
(2, 'Monthly water bill, paid in cash by Kasun.', 'Water', 2000, '2015-08-15'),
(3, 'Repaired a pc.', 'Other', 500, '2015-08-26'),
(4, 'payed by amla', 'Salary', 4500, '2015-09-28'),
(5, 'test', 'Salary', 4500, '2015-09-28');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `CustID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Contact_Person` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `NIC` varchar(10) NOT NULL,
  `Registered_Date` varchar(100) NOT NULL,
  PRIMARY KEY (`CustID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CustID`, `Name`, `Address`, `Contact_Person`, `Email`, `NIC`, `Registered_Date`) VALUES
(1, 'Devin Amarasekara', 'Rajagiriya', 'Mewanka Roshali', 'devin.sanduka@gmail.com', '943513147V', ''),
(2, 'Chathura Pathmasooriya', 'Chilaw', 'Pasan', 'chathurapa93@gmail.com', '931840576V', ''),
(3, 'Diyath Nelaka', 'Kotte', 'Dulmi Hasara', 'diyathni7@gmail.com', '931340050V', ''),
(4, 'Nipuni Lokuhettiarachchi', 'Galle			', 'Nipuna', 'nipuniakka@gmial.com', '945648752V', '2015-09-11'),
(5, 'Ayesha Shamindi', 'Katunayake', 'Nadeeshani', 'ayeshanadeeshani@gmail.com', '9', 'Sunday,September 27,2015');

-- --------------------------------------------------------

--
-- Table structure for table `customer_contact`
--

CREATE TABLE IF NOT EXISTS `customer_contact` (
  `CustID` varchar(10) NOT NULL,
  `Mobile` int(11) NOT NULL,
  `Office` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_contact`
--

INSERT INTO `customer_contact` (`CustID`, `Mobile`, `Office`) VALUES
('C001', 1234567890, 1234567890),
('C002', 717664301, 912246544),
('1', 768379257, 112792570),
('4', 718965412, 114587654),
('5', 771234565, 115489966);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `empID` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `dob` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `contactNo` varchar(10) NOT NULL,
  `department` varchar(20) NOT NULL,
  `halfDay` int(11) NOT NULL DEFAULT '5',
  `fullDay` int(11) NOT NULL DEFAULT '5',
  `sickDay` int(11) NOT NULL DEFAULT '5',
  `otherLeaves` int(11) NOT NULL DEFAULT '0',
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`empID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`empID`, `fname`, `lname`, `dob`, `address`, `contactNo`, `department`, `halfDay`, `fullDay`, `sickDay`, `otherLeaves`, `username`, `password`) VALUES
(2, 'Harshie', 'Edirisinghe', '1994-04-01', 'Borella', '0754586248', 'Financial Division', 5, 4, 5, 0, 'hashu', 'bandi'),
(3, 'Malsha', 'Ruwandi', '1992-03-16', 'Wennappuwa', '077568463', 'Service Division', 5, 5, 5, 0, 'malsha', 'akka'),
(4, 'Nipuni', 'Lokuhettiarachchi', '1992-01-16', 'Galle', '0758965423', 'Customer Relations', 5, 8, 5, 0, 'nipuni', 'akka'),
(5, 'Harshie', 'Fernando', '1992-08-20', 'Borella', '0712345678', 'Service Division', 4, 5, 5, 0, '', ''),
(6, 'Binesh', 'Amarasekara', '1991-07-18', 'Colombo', '0713259245', 'Financial Division', 5, 5, 5, 0, 'binesh', 'minoka');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `event_descrip` varchar(100) NOT NULL,
  `event_address` varchar(100) NOT NULL,
  `vehicle_approved_emp` varchar(50) NOT NULL,
  `event_handler` varchar(50) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`event_id`, `event_name`, `from_date`, `to_date`, `event_descrip`, `event_address`, `vehicle_approved_emp`, `event_handler`) VALUES
(1, 'AutoVisioin', '2015-08-27', '2015-08-30', 'Hosted by Sirasa TV			', 'BMICH', 'Kasun', 'Pasindu'),
(2, 'Motor Show', '2015-09-30', '2015-10-03', 'Car Exhibition', 'BMICH', 'Ravi Liyanage', 'Mahesh Perera');

-- --------------------------------------------------------

--
-- Table structure for table `event_vehicle`
--

CREATE TABLE IF NOT EXISTS `event_vehicle` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `chassis_no` varchar(20) NOT NULL,
  `vehicle_model` varchar(50) NOT NULL,
  `manufac_year` int(4) NOT NULL,
  `vehicle_colour` varchar(20) NOT NULL,
  `vehicle_status` varchar(50) NOT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `event_vehicle`
--

INSERT INTO `event_vehicle` (`vehicle_id`, `chassis_no`, `vehicle_model`, `manufac_year`, `vehicle_colour`, `vehicle_status`) VALUES
(1, '43536', 'Nissan Leaf', 2345, 'Red', 'fdgg'),
(2, '3789TYI8GJ7807', 'Tesla Model S', 2015, 'Black', 'Brand New'),
(3, '5789RWE78U12HJ3', 'BMW i8', 2015, 'White+Blue Neon', 'Brand New');

-- --------------------------------------------------------

--
-- Table structure for table `evtable`
--

CREATE TABLE IF NOT EXISTS `evtable` (
  `event_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`,`vehicle_id`),
  KEY `vehicle_id` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `evtable`
--

INSERT INTO `evtable` (`event_id`, `vehicle_id`) VALUES
(2, 1),
(2, 2),
(2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `itemCode` int(11) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(50) NOT NULL,
  `category` varchar(50) NOT NULL,
  `stock` int(11) NOT NULL,
  `unitPrice` float NOT NULL,
  `reOrderLevel` int(11) NOT NULL,
  PRIMARY KEY (`itemCode`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`itemCode`, `itemName`, `category`, `stock`, `unitPrice`, `reOrderLevel`) VALUES
(1, 'Havoline Super900', 'Lubricants', 10, 870, 5),
(2, 'Dash Carwash Shampoo', 'Chemicals', 15, 500, 25),
(3, 'Caltex Rando HD32', 'Lubricants', 15, 800, 25),
(4, 'Caltex Meropa 220', 'Lubricants', 15, 750, 20),
(5, 'Caltex Regal 32', 'Lubricants', 15, 1000, 15),
(6, 'Caltex Compressor Oil VDL 36', 'Lubricants', 15, 950, 5),
(7, 'Caltex Way Lubricant X 68', 'Lubricants', 15, 1500.01, 8),
(9, 'Canopus 150', 'Lubricants', 15, 2000, 12),
(10, 'Caltex Aquatex 3180', 'Lubricants', 15, 900, 10),
(11, 'Varnish Cleaner', 'Chemicals', 15, 650, 10),
(12, 'Window Cleaner', 'Chemicals', 15, 150, 5),
(13, 'Rim/Wheel Cleaner', 'Chemicals', 15, 250, 5),
(15, 'Motor Cleaner', 'Chemicals', 15, 300, 12),
(16, 'caltex', 'Lubricants', 15, 500, 1),
(18, 'kemixcal', 'Chemicals', 15, 230, 5);

-- --------------------------------------------------------

--
-- Table structure for table `jobcard`
--

CREATE TABLE IF NOT EXISTS `jobcard` (
  `jobCardID` int(11) NOT NULL AUTO_INCREMENT,
  `date` text NOT NULL,
  `ODOReading` float NOT NULL,
  `appointmentID` int(11) NOT NULL,
  PRIMARY KEY (`jobCardID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `jobcard`
--

INSERT INTO `jobcard` (`jobCardID`, `date`, `ODOReading`, `appointmentID`) VALUES
(27, '2015-08-26', 100, 4),
(28, '2015-08-26', 100, 6),
(29, '2015-08-26', 567, 7),
(30, '2015-08-27', 500, 9),
(31, '2015-08-27', 100, 12),
(32, '2015-09-26', 45, 13),
(33, '2015-09-26', 34, 14),
(34, '2015-09-26', 23, 15),
(35, '2015-09-26', 2, 17),
(36, '2015-09-26', 456, 18),
(37, '2015-09-28', 12, 0);

-- --------------------------------------------------------

--
-- Table structure for table `leaves`
--

CREATE TABLE IF NOT EXISTS `leaves` (
  `leaveID` int(11) NOT NULL AUTO_INCREMENT,
  `empID` int(11) NOT NULL,
  `leaveType` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL,
  `start_date` varchar(10) NOT NULL,
  `end_date` varchar(10) NOT NULL,
  `dateCount` int(11) NOT NULL,
  PRIMARY KEY (`leaveID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `leaves`
--

INSERT INTO `leaves` (`leaveID`, `empID`, `leaveType`, `description`, `start_date`, `end_date`, `dateCount`) VALUES
(1, 5, 'Half Day', 'Sick', '2015-08-28', '', 0),
(2, 4, 'Half Day', 'yythghg', '2015-09-27', '2015-09-21', -5),
(3, 2, 'Half Day', 'sdsdsds', '2015-09-27', '2015-09-21', -5),
(4, 2, 'Half Day', 'cvxvc', '2015-09-27', '2015-09-30', 3),
(5, 3, 'Half Day', 'dfgdfgd', '2015-09-27', '2015-09-28', 1);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `itemcode` int(11) NOT NULL,
  `itemname` varchar(50) NOT NULL,
  `category` varchar(25) NOT NULL,
  `stock` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`itemcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`itemcode`, `itemname`, `category`, `stock`, `status`) VALUES
(2, 'Dash Carwash Shampoo', 'Chemicals', 15, 'Pending'),
(3, 'Caltex Rando HD32', 'Lubricants', 15, 'Pending'),
(4, 'Caltex Meropa 220', 'Lubricants', 17, 'Pending'),
(5, 'Caltex Regal 32', 'Lubricants', 10, 'Order Placed');

-- --------------------------------------------------------

--
-- Table structure for table `order_by`
--

CREATE TABLE IF NOT EXISTS `order_by` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `empID` int(11) NOT NULL,
  `date` date NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `order_item`
--

CREATE TABLE IF NOT EXISTS `order_item` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `itemCode` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL,
  `qty` int(11) NOT NULL,
  `ordercost` double NOT NULL,
  `date` varchar(20) NOT NULL,
  `supplierID` int(11) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `order_item`
--

INSERT INTO `order_item` (`orderID`, `itemCode`, `type`, `description`, `qty`, `ordercost`, `date`, `supplierID`) VALUES
(1, 10, 'Lubricants', 'By cash', 12, 10800, '27/08/2015', 3),
(2, 9, 'Lubricants', 'By check', 15, 30000, '27/08/2015', 5),
(3, 10, 'Lubricants', 'By check', 3, 2700, '27/08/2015', 1),
(4, 16, 'Lubricants', 'By cash', 6, 3000, '05/09/2015', 1),
(5, 22, 'Chemicals', 'By cash', 3, 9, '20/09/2015', 2),
(6, 5, 'Lubricants', 'By check', 20, 20000, '25/09/2015', 1),
(7, 10, 'Lubricants', 'By cash', 23, 20700, '26/09/2015', 1);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
  `paymentID` int(11) NOT NULL AUTO_INCREMENT,
  `method` varchar(20) NOT NULL,
  `receiptID` int(11) NOT NULL,
  PRIMARY KEY (`paymentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE IF NOT EXISTS `receipt` (
  `receiptID` int(11) NOT NULL AUTO_INCREMENT,
  `job_cost` float NOT NULL,
  `item_cost` float NOT NULL,
  `jobCardID` int(11) NOT NULL,
  `date` varchar(45) NOT NULL,
  PRIMARY KEY (`receiptID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`receiptID`, `job_cost`, `item_cost`, `jobCardID`, `date`) VALUES
(40, 1500, 1870, 28, '2015-08-26'),
(41, 2250, 1370, 29, '2015-08-26'),
(42, 1250, 2200, 30, '2015-08-27'),
(43, 1250, 2900, 31, '2015-08-27'),
(44, 1500, 3900, 32, '2015-09-26'),
(45, 500, 4350, 33, '2015-09-26'),
(46, 1500, 4350, 34, '2015-09-26');

-- --------------------------------------------------------

--
-- Table structure for table `salary_report`
--

CREATE TABLE IF NOT EXISTS `salary_report` (
  `reportID` int(11) NOT NULL AUTO_INCREMENT,
  `empID` int(11) NOT NULL,
  `employee_name` varchar(50) NOT NULL,
  `dept` varchar(50) NOT NULL,
  `yearMonth` varchar(10) NOT NULL,
  `olTaken` int(11) NOT NULL,
  `finalSalary` float NOT NULL,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `serviceID` int(11) NOT NULL AUTO_INCREMENT,
  `serviceName` varchar(50) NOT NULL,
  `service_cost` float NOT NULL,
  PRIMARY KEY (`serviceID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`serviceID`, `serviceName`, `service_cost`) VALUES
(1, 'Body Cleaning', 500),
(2, 'Wheel Alignment', 750),
(3, 'Interior Cleaning', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `serv_appoint`
--

CREATE TABLE IF NOT EXISTS `serv_appoint` (
  `appointmentID` int(11) NOT NULL,
  `serviceID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `serv_appoint`
--

INSERT INTO `serv_appoint` (`appointmentID`, `serviceID`) VALUES
(1, 1),
(2, 1),
(5, 1),
(5, 3),
(6, 1),
(6, 3),
(6, 1),
(6, 2),
(6, 3),
(7, 1),
(7, 2),
(7, 3),
(8, 1),
(8, 3),
(9, 1),
(9, 2),
(10, 1),
(10, 3),
(11, 1),
(11, 2),
(12, 1),
(12, 2),
(13, 1),
(13, 3),
(14, 1),
(15, 1),
(15, 3),
(16, 1),
(16, 3),
(17, 1),
(17, 3),
(18, 1),
(18, 1),
(18, 3),
(19, 1),
(19, 3),
(20, 1),
(20, 3),
(21, 1),
(21, 3),
(22, 1),
(22, 3);

-- --------------------------------------------------------

--
-- Table structure for table `serv_job`
--

CREATE TABLE IF NOT EXISTS `serv_job` (
  `serv_jobID` int(11) NOT NULL AUTO_INCREMENT,
  `jobcardID` int(11) NOT NULL,
  `itemCode` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `unitPrice` float NOT NULL,
  PRIMARY KEY (`serv_jobID`),
  KEY `jobcardID` (`jobcardID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=76 ;

--
-- Dumping data for table `serv_job`
--

INSERT INTO `serv_job` (`serv_jobID`, `jobcardID`, `itemCode`, `qty`, `unitPrice`) VALUES
(64, 28, 22, 1, 870),
(65, 28, 23, 2, 500),
(66, 29, 22, 1, 870),
(67, 29, 23, 1, 500),
(68, 30, 4, 2, 600),
(69, 30, 6, 1, 1000),
(70, 31, 2, 1, 500),
(71, 31, 3, 3, 800),
(72, 32, 3, 3, 800),
(73, 32, 4, 2, 750),
(74, 33, 1, 5, 870),
(75, 34, 1, 5, 870);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `supplierID` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(30) NOT NULL,
  `mobileNo` varchar(10) NOT NULL,
  `officeNo` int(11) NOT NULL,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`supplierID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplierID`, `supplierName`, `address`, `email`, `mobileNo`, `officeNo`, `category`) VALUES
(1, 'Ruwan Peries', 'Colombo', 'ruwanperies@gmail.com', '0775896584', 322254755, 'Chemicals'),
(2, 'Nishan Fernando', 'Kandy', 'nishanf@gmail.com', '0715896385', 312258746, 'Chemicals'),
(3, 'Madushan Herath', 'Chilaw', 'maduh@yahoo.com', '0785694875', 354769584, 'Lubricants'),
(4, 'Hashini Pothuwila', 'Nattandiya', 'hashinip93@gmail.com', '0725645857', 315469854, 'Spare Parts'),
(5, 'Nimali Ranasinghe', 'Koswadiya', 'nimarana@hotmail.com', '0754689568', 315469875, 'Lubricants'),
(6, 'Shashith Tharaka', 'Mahawewa', 'shashitharaka@gmail.com', '0784658215', 315479852, 'Chemicals');

-- --------------------------------------------------------

--
-- Table structure for table `sup_item`
--

CREATE TABLE IF NOT EXISTS `sup_item` (
  `supplierID` int(11) NOT NULL,
  `itemCode` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sup_item`
--

INSERT INTO `sup_item` (`supplierID`, `itemCode`, `qty`) VALUES
(1, 3, 5),
(1, 19, 3);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE IF NOT EXISTS `vehicle` (
  `CustID` int(10) NOT NULL,
  `vehicleNo` varchar(50) NOT NULL,
  `Year` varchar(20) NOT NULL,
  `Colour` varchar(20) NOT NULL,
  `Model` varchar(50) NOT NULL,
  `ODOReading` int(11) NOT NULL,
  `ChassisNo` varchar(20) NOT NULL,
  `Borrowed_Date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`CustID`, `vehicleNo`, `Year`, `Colour`, `Model`, `ODOReading`, `ChassisNo`, `Borrowed_Date`) VALUES
(5, 'WD-5678', '2014', 'Maroon', 'Nissan Leaf', 0, 'iuy789', 'Sunday,September 27,2015'),
(5, 'WD2456', '2011', 'Maroon', 'Item 3', 0, 'iuy001', 'Sunday,September 27,2015'),
(6, 'WD-5432', '2011', 'Metal Black', 'Nissan Leaf', 0, 'er67', 'Sunday,September 27,2015'),
(1, 'WD-3478', '2013', 'Maroon', 'Nissan Leaf', 0, 'rt567', 'Sunday,September 27,2015'),
(4, 'CAD-4578', '2015', 'Metal Black', 'Nissan Leaf', 0, 'fgt568', 'Sunday,September 27,2015'),
(1, 'WE-9087', '2014', 'Maroon', 'Nissan Leaf', 0, 'SE-i8', 'Monday,September 28,2015');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `evtable`
--
ALTER TABLE `evtable`
  ADD CONSTRAINT `evtable_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  ADD CONSTRAINT `evtable_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `event_vehicle` (`vehicle_id`);

--
-- Constraints for table `serv_job`
--
ALTER TABLE `serv_job`
  ADD CONSTRAINT `chkID` FOREIGN KEY (`jobcardID`) REFERENCES `jobcard` (`jobCardID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
