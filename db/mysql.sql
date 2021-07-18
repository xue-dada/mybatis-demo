CREATE TABLE `t_ds_api_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_id` varchar(256) DEFAULT NULL,
  `sql_id` varchar(256) DEFAULT NULL,
  `sql_str` varchar(4096) DEFAULT NULL,
  `column_name` varchar(256) DEFAULT NULL,
  `column_type` varchar(256) DEFAULT NULL,
  `last_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4