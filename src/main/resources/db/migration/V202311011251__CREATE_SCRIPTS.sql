CREATE TABLE `config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT,
  `data` text DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `UK_8c6dl7eofcfmo2y3p4gd93o3t` (`key_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `lookup_columns` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `datatype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `lookup_tables` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `lookup_columns` (`id`, `name`, `datatype`) VALUES (1, "id", "NUMERIC");
INSERT INTO `lookup_columns` (`id`, `name`, `datatype`) VALUES (2, "key", "TEXT");
INSERT INTO `lookup_columns` (`id`, `name`, `datatype`) VALUES (3, "label", "TEXT");
INSERT INTO `lookup_columns` (`id`, `name`, `datatype`) VALUES (4, "value", "TEXT");

