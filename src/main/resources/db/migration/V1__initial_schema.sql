-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: novara-main
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `action` varchar(255) NOT NULL,
                             `actor_email` varchar(255) DEFAULT NULL,
                             `actor_id` bigint DEFAULT NULL,
                             `actor_role` varchar(255) DEFAULT NULL,
                             `details` varchar(255) DEFAULT NULL,
                             `outcome` varchar(255) NOT NULL,
                             `target_details` varchar(255) DEFAULT NULL,
                             `target_id` bigint DEFAULT NULL,
                             `target_type` varchar(255) DEFAULT NULL,
                             `timestamp` datetime(6) NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `idx_timestamp_action_outcome` (`timestamp`,`action`,`outcome`),
                             CONSTRAINT `audit_log_chk_1` CHECK ((`action` in (_utf8mb4'LOGIN',_utf8mb4'LOGOUT',_utf8mb4'REGISTER',_utf8mb4'PASSWORD_RESET_REQUEST',_utf8mb4'PASSWORD_CHANGE',_utf8mb4'UPDATE_USER',_utf8mb4'BOOKING_CREATE',_utf8mb4'BOOKING_CANCEL',_utf8mb4'BOOKING_FLIGHT_CHANGE',_utf8mb4'CHANGE_USER_STATUS',_utf8mb4'SCHEDULED_TASK'))),
                             CONSTRAINT `audit_log_chk_2` CHECK ((`actor_role` in (_utf8mb4'USER',_utf8mb4'ADMIN',_utf8mb4'SYSTEM'))),
                             CONSTRAINT `audit_log_chk_3` CHECK ((`outcome` in (_utf8mb4'SUCCESS',_utf8mb4'FAILURE'))),
                             CONSTRAINT `audit_log_chk_4` CHECK ((`target_type` in (_utf8mb4'USER',_utf8mb4'BOOKING',_utf8mb4'MAINTENANCE')))
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `cancelled` bit(1) NOT NULL,
                           `contact_country_code` varchar(255) DEFAULT NULL,
                           `contact_email` varchar(255) DEFAULT NULL,
                           `contact_mobile` varchar(255) DEFAULT NULL,
                           `created_at` datetime(6) DEFAULT NULL,
                           `demo` bit(1) NOT NULL,
                           `departure_class` tinyint DEFAULT NULL,
                           `departure_flight_price` double DEFAULT NULL,
                           `reference` varchar(255) DEFAULT NULL,
                           `return_class` tinyint DEFAULT NULL,
                           `return_flight_price` double DEFAULT NULL,
                           `departure_flight_id` bigint DEFAULT NULL,
                           `payment_id` bigint DEFAULT NULL,
                           `return_flight_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK6qyxfsr6xbajdkhybqh1wse8d` (`reference`),
                           UNIQUE KEY `UKxcv4bjb631pysj91ybp40vpo` (`payment_id`),
                           KEY `FK614iil5nwccf8eoyngrf3b2n` (`departure_flight_id`),
                           KEY `FKa20mjlhdyu5y6jnxe7d3mq7g1` (`return_flight_id`),
                           CONSTRAINT `FK614iil5nwccf8eoyngrf3b2n` FOREIGN KEY (`departure_flight_id`) REFERENCES `flight_instance` (`id`),
                           CONSTRAINT `FK70t92vvx289ayx2hq2v4hdcjl` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
                           CONSTRAINT `FKa20mjlhdyu5y6jnxe7d3mq7g1` FOREIGN KEY (`return_flight_id`) REFERENCES `flight_instance` (`id`),
                           CONSTRAINT `booking_chk_1` CHECK ((`departure_class` between 0 and 2)),
                           CONSTRAINT `booking_chk_2` CHECK ((`return_class` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `booking_quote`
--

DROP TABLE IF EXISTS `booking_quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_quote` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `arrival_code` varchar(255) DEFAULT NULL,
                                 `departure_code` varchar(255) DEFAULT NULL,
                                 `departure_lower_date` date DEFAULT NULL,
                                 `departure_upper_date` date DEFAULT NULL,
                                 `expires_at` datetime(6) DEFAULT NULL,
                                 `one_way` bit(1) NOT NULL,
                                 `pax_count` int NOT NULL,
                                 `reference` varchar(255) DEFAULT NULL,
                                 `return_lower_date` date DEFAULT NULL,
                                 `return_upper_date` date DEFAULT NULL,
                                 `services_pricing` json DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `idx_reference` (`reference`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cabin_class`
--

DROP TABLE IF EXISTS `cabin_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cabin_class` (
                               `id` bigint NOT NULL,
                               `total_seats` int NOT NULL,
                               `type` enum('FIRST','LOWER','MIDDLE') NOT NULL,
                               `window_available` bit(1) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `destination`
--

DROP TABLE IF EXISTS `destination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destination` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `main_desc` varchar(500) NOT NULL,
                               `name` tinyint DEFAULT NULL,
                               `secondary_desc` varchar(400) NOT NULL,
                               PRIMARY KEY (`id`),
                               CONSTRAINT `destination_chk_1` CHECK ((`name` between 0 and 3))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `destinations_details`
--

DROP TABLE IF EXISTS `destinations_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destinations_details` (
                                        `destination_id` bigint NOT NULL,
                                        `detail_name` varchar(255) DEFAULT NULL,
                                        `detail_value` varchar(255) DEFAULT NULL,
                                        KEY `FKcp17ygcu3jq136lnh53pxnkge` (`destination_id`),
                                        CONSTRAINT `FKcp17ygcu3jq136lnh53pxnkge` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `destinations_points`
--

DROP TABLE IF EXISTS `destinations_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destinations_points` (
                                       `destination_id` bigint NOT NULL,
                                       `angle` int DEFAULT NULL,
                                       `point_id` varchar(255) DEFAULT NULL,
                                       `text_bottom` varchar(255) DEFAULT NULL,
                                       `text_top` varchar(255) DEFAULT NULL,
                                       KEY `FK4e6jtgdbnfgo9od53vq8gubkx` (`destination_id`),
                                       CONSTRAINT `FK4e6jtgdbnfgo9od53vq8gubkx` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `extra_services`
--

DROP TABLE IF EXISTS `extra_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extra_services` (
                                  `booking_id` bigint NOT NULL,
                                  `code` tinyint DEFAULT NULL,
                                  `price` double DEFAULT NULL,
                                  KEY `FKol8yk0i8d360k7tsg3wf06jam` (`booking_id`),
                                  CONSTRAINT `FKol8yk0i8d360k7tsg3wf06jam` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
                                  CONSTRAINT `extra_services_chk_1` CHECK ((`code` between 0 and 3))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flight_instance`
--

DROP TABLE IF EXISTS `flight_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_instance` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `arrival_date` datetime(6) NOT NULL,
                                   `departure_date` datetime(6) NOT NULL,
                                   `first_available_seats` int DEFAULT NULL,
                                   `first_base_price` double DEFAULT NULL,
                                   `first_locked_seats` int DEFAULT NULL,
                                   `lower_available_seats` int DEFAULT NULL,
                                   `lower_base_price` double DEFAULT NULL,
                                   `lower_locked_seats` int DEFAULT NULL,
                                   `middle_available_seats` int DEFAULT NULL,
                                   `middle_base_price` double DEFAULT NULL,
                                   `middle_locked_seats` int DEFAULT NULL,
                                   `public_id` varchar(255) NOT NULL,
                                   `status` enum('CANCELLED','DELAYED','ON_TIME') NOT NULL,
                                   `total_booked_seats` int NOT NULL,
                                   `total_seats_available` int NOT NULL,
                                   `flight_template_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `UKojkdrff6t77peqm56njwf4b87` (`public_id`),
                                   KEY `idx_departureDate` (`departure_date`),
                                   KEY `idx_templateId_departureDate` (`flight_template_id`,`departure_date`),
                                   CONSTRAINT `FKjmqc1uusatlk49sq6l1kdaymt` FOREIGN KEY (`flight_template_id`) REFERENCES `flight_template` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3464 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flight_template`
--

DROP TABLE IF EXISTS `flight_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_template` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `base_price` double NOT NULL,
                                   `departure_time` time(6) NOT NULL,
                                   `duration_minutes` int NOT NULL,
                                   `flight_number` varchar(255) NOT NULL,
                                   `orbits_arrival` int NOT NULL,
                                   `orbits_departure` int NOT NULL,
                                   `public_id_prefix` varchar(255) NOT NULL,
                                   `weekly_schedule` varchar(7) NOT NULL,
                                   `arrival_location` bigint NOT NULL,
                                   `departure_location` bigint NOT NULL,
                                   `vehicle_id` bigint NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKgc87jawalix3ckk39p3e7f7gp` (`arrival_location`),
                                   KEY `FK8u0rffokei32la0u23k4m74r9` (`departure_location`),
                                   KEY `FKmaynjodft1rplmtx75gj4133k` (`vehicle_id`),
                                   CONSTRAINT `FK8u0rffokei32la0u23k4m74r9` FOREIGN KEY (`departure_location`) REFERENCES `location` (`id`),
                                   CONSTRAINT `FKgc87jawalix3ckk39p3e7f7gp` FOREIGN KEY (`arrival_location`) REFERENCES `location` (`id`),
                                   CONSTRAINT `FKmaynjodft1rplmtx75gj4133k` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
                                         `installed_rank` int NOT NULL,
                                         `version` varchar(50) DEFAULT NULL,
                                         `description` varchar(200) NOT NULL,
                                         `type` varchar(20) NOT NULL,
                                         `script` varchar(1000) NOT NULL,
                                         `checksum` int DEFAULT NULL,
                                         `installed_by` varchar(100) NOT NULL,
                                         `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `execution_time` int NOT NULL,
                                         `success` tinyint(1) NOT NULL,
                                         PRIMARY KEY (`installed_rank`),
                                         KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
                            `id` bigint NOT NULL,
                            `accommodations` bigint NOT NULL,
                            `area` bigint NOT NULL,
                            `capacity` bigint NOT NULL,
                            `code` varchar(255) NOT NULL,
                            `description` varchar(400) NOT NULL,
                            `location` tinyint NOT NULL,
                            `location_number` varchar(255) NOT NULL,
                            `location_type` varchar(255) NOT NULL,
                            `long_name` varchar(255) NOT NULL,
                            `name` varchar(255) NOT NULL,
                            `name_details` varchar(255) NOT NULL,
                            `ports` bigint NOT NULL,
                            `region` tinyint NOT NULL,
                            `volume` bigint NOT NULL,
                            `destination_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UKegl5of52aldewjtfss34en0yi` (`code`),
                            UNIQUE KEY `UK59y48akrkev8c1oppq052b5ex` (`location`),
                            UNIQUE KEY `UKr7p8wrhgajqr9v7v9rg9yrjfv` (`location_number`),
                            UNIQUE KEY `UK9f4trumtqjc6mby9th9kq069w` (`long_name`),
                            UNIQUE KEY `UKsahixf1v7f7xns19cbg12d946` (`name`),
                            KEY `FK2jdl5tmjfjm5da00gbwtuisd` (`destination_id`),
                            CONSTRAINT `FK2jdl5tmjfjm5da00gbwtuisd` FOREIGN KEY (`destination_id`) REFERENCES `destination` (`id`),
                            CONSTRAINT `location_chk_1` CHECK ((`location` between 0 and 22)),
                            CONSTRAINT `location_chk_2` CHECK ((`region` between 0 and 6))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `age_group` tinyint DEFAULT NULL,
                             `extra_baggage_capacity` varchar(255) DEFAULT NULL,
                             `extra_baggage_price` double DEFAULT NULL,
                             `cabin_id` bigint NOT NULL,
                             `dob` date DEFAULT NULL,
                             `first_name` varchar(255) DEFAULT NULL,
                             `intra_booking_id` bigint NOT NULL,
                             `last_name` varchar(255) DEFAULT NULL,
                             `title` varchar(255) DEFAULT NULL,
                             `booking_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKtco0omesfld1qi5sw76eomvt4` (`booking_id`),
                             CONSTRAINT `FKtco0omesfld1qi5sw76eomvt4` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
                             CONSTRAINT `passenger_chk_1` CHECK ((`age_group` between 0 and 1)),
                             CONSTRAINT `passenger_chk_2` CHECK ((`extra_baggage_capacity` in (_utf8mb4'KG5',_utf8mb4'KG10',_utf8mb4'KG15',_utf8mb4'KG20',_utf8mb4'KG25',_utf8mb4'KG30',_utf8mb4'KG35',_utf8mb4'KG40',_utf8mb4'KG45',_utf8mb4'KG50')))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `password_reset_token`
--

DROP TABLE IF EXISTS `password_reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_token` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `expires_on` datetime(6) DEFAULT NULL,
                                        `token_value` varchar(255) DEFAULT NULL,
                                        `user_auth_id` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `amount` double DEFAULT NULL,
                           `booking_confirm` bit(1) NOT NULL,
                           `card_holder` varchar(255) DEFAULT NULL,
                           `card_type` varchar(255) DEFAULT NULL,
                           `created_at` datetime(6) DEFAULT NULL,
                           `email` varchar(255) DEFAULT NULL,
                           `first_four` varchar(255) DEFAULT NULL,
                           `last_four` varchar(255) DEFAULT NULL,
                           `phone_number` varchar(255) DEFAULT NULL,
                           `reference` varchar(255) DEFAULT NULL,
                           `service_reference` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `idx_serviceReference` (`service_reference`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `created_on` datetime(6) DEFAULT NULL,
                                 `expiry_date` datetime(6) NOT NULL,
                                 `family_id` varchar(36) NOT NULL,
                                 `ip_address` varchar(45) DEFAULT NULL,
                                 `public_key` varchar(255) NOT NULL,
                                 `revoked` bit(1) NOT NULL,
                                 `token` varchar(255) NOT NULL,
                                 `user_auth_id` varchar(255) NOT NULL,
                                 `revoked_at` datetime(6) DEFAULT NULL,
                                 `replaced_by` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `UKpwukmw6lditklkn5lui0j9j5c` (`public_key`),
                                 UNIQUE KEY `UKr4k4edos30bx9neoq81mdvwph` (`token`),
                                 KEY `idx_familyId` (`family_id`),
                                 KEY `idx_userAuthId` (`user_auth_id`),
                                 KEY `idx_creationDate` (`created_on`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_cards`
--

DROP TABLE IF EXISTS `user_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_cards` (
                              `user_id` bigint NOT NULL,
                              `added_on` date DEFAULT NULL,
                              `card_holder` varchar(255) DEFAULT NULL,
                              `card_type` varchar(255) DEFAULT NULL,
                              `expiry_date` varchar(255) DEFAULT NULL,
                              `first_four` varchar(255) DEFAULT NULL,
                              `last_four` varchar(255) DEFAULT NULL,
                              `reference` varchar(255) DEFAULT NULL,
                              KEY `FK55ime3genywh3rg5yu62hmfvy` (`user_id`),
                              CONSTRAINT `FK55ime3genywh3rg5yu62hmfvy` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_documents`
--

DROP TABLE IF EXISTS `user_documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_documents` (
                                  `user_id` bigint NOT NULL,
                                  `doc_id` varchar(255) DEFAULT NULL,
                                  `filename` varchar(255) DEFAULT NULL,
                                  `uploaded_on` date DEFAULT NULL,
                                  KEY `FK1r7rd4k83lt1yes6jdvq92hah` (`user_id`),
                                  CONSTRAINT `FK1r7rd4k83lt1yes6jdvq92hah` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_payments`
--

DROP TABLE IF EXISTS `user_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_payments` (
                                 `user_id` bigint NOT NULL,
                                 `payment_id` bigint NOT NULL,
                                 UNIQUE KEY `UK7tigi2jg3gl4g50w54no6pg7x` (`payment_id`),
                                 KEY `FK48m9vjig8w6q9fglp2w7urppv` (`user_id`),
                                 CONSTRAINT `FK48m9vjig8w6q9fglp2w7urppv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                 CONSTRAINT `FKaxpbv8pmjfml852vbo1ug3kei` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
                              `user_id` bigint NOT NULL,
                              `role` tinyint DEFAULT NULL,
                              KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
                              CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                              CONSTRAINT `user_roles_chk_1` CHECK ((`role` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `account_number` varchar(255) DEFAULT NULL,
                         `address_line1` varchar(255) DEFAULT NULL,
                         `address_line2` varchar(255) DEFAULT NULL,
                         `auth_id` varchar(255) DEFAULT NULL,
                         `country` varchar(255) DEFAULT NULL,
                         `country_code` varchar(255) DEFAULT NULL,
                         `created_at` datetime(6) DEFAULT NULL,
                         `deleted_at` datetime(6) DEFAULT NULL,
                         `dob` date DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `first_name` varchar(255) DEFAULT NULL,
                         `is_demo` bit(1) NOT NULL,
                         `last_login_at` datetime(6) DEFAULT NULL,
                         `last_name` varchar(255) DEFAULT NULL,
                         `last_reset_stamp` datetime(6) DEFAULT NULL,
                         `marketing` bit(1) NOT NULL,
                         `newsletter` bit(1) NOT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `phone_number` varchar(255) DEFAULT NULL,
                         `public_id` varchar(255) DEFAULT NULL,
                         `recent_reset_count` int NOT NULL,
                         `status` tinyint DEFAULT NULL,
                         `title` varchar(255) DEFAULT NULL,
                         `total_invoiced` double NOT NULL,
                         `verification_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UKbptc7wc5t9qe57nk56riv5hal` (`auth_id`),
                         UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
                         UNIQUE KEY `UKh49m1dm49r52jylluqtvg1fho` (`verification_id`),
                         KEY `idx_lastLogin` (`last_login_at`),
                         KEY `idx_creationDate` (`created_at`),
                         CONSTRAINT `FK1w17j63m1bjcqucudpnfhxi2t` FOREIGN KEY (`verification_id`) REFERENCES `verification_token` (`id`),
                         CONSTRAINT `users_chk_1` CHECK ((`status` between 0 and 3))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users_bookings`
--

DROP TABLE IF EXISTS `users_bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_bookings` (
                                  `user_id` bigint NOT NULL,
                                  `booking_id` bigint NOT NULL,
                                  KEY `FKlk6ajp7tl09xd74ebwrb62xnf` (`booking_id`),
                                  KEY `FKgofncebq1vivfgyxwclto6nhb` (`user_id`),
                                  CONSTRAINT `FKgofncebq1vivfgyxwclto6nhb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `FKlk6ajp7tl09xd74ebwrb62xnf` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `code` varchar(255) NOT NULL,
                           `name` varchar(255) NOT NULL,
                           `first_class` bigint DEFAULT NULL,
                           `lower_class` bigint DEFAULT NULL,
                           `middle_class` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKjijn903jsgikxl9lt58bdxg30` (`first_class`),
                           KEY `FK3efk3ei6lo1pqhw56hd9aoh3w` (`lower_class`),
                           KEY `FK5sb8lffxttbquqkxqw8x1jrak` (`middle_class`),
                           CONSTRAINT `FK3efk3ei6lo1pqhw56hd9aoh3w` FOREIGN KEY (`lower_class`) REFERENCES `cabin_class` (`id`),
                           CONSTRAINT `FK5sb8lffxttbquqkxqw8x1jrak` FOREIGN KEY (`middle_class`) REFERENCES `cabin_class` (`id`),
                           CONSTRAINT `FKjijn903jsgikxl9lt58bdxg30` FOREIGN KEY (`first_class`) REFERENCES `cabin_class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicle_amenities`
--

DROP TABLE IF EXISTS `vehicle_amenities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle_amenities` (
                                     `vehicle_id` bigint NOT NULL,
                                     `amenities` enum('EVA','GALLEY','OBSERVATION_LOUNGE','VR') NOT NULL,
                                     PRIMARY KEY (`vehicle_id`,`amenities`),
                                     CONSTRAINT `FKfvgva9b7anxlq1whllecelel6` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicle_regions`
--

DROP TABLE IF EXISTS `vehicle_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle_regions` (
                                   `vehicle_id` bigint NOT NULL,
                                   `supported_regions` enum('EARTH','MARS','MARS_ORBIT','MOON','MOON_ORBIT','NEAR_EARTH','VENUS_ORBIT') NOT NULL,
                                   PRIMARY KEY (`vehicle_id`,`supported_regions`),
                                   CONSTRAINT `FK5t55ark1bva676euhrd61f6up` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `code` varchar(255) NOT NULL,
                                      `created_at` datetime(6) NOT NULL,
                                      `expires_at` datetime(6) NOT NULL,
                                      `link_token` varchar(255) NOT NULL,
                                      `serial_number` int NOT NULL,
                                      `used` bit(1) NOT NULL,
                                      `user_email` varchar(255) NOT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
