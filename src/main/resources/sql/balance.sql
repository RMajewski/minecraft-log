CREATE TABLE IF NOT EXISTS `&balance` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `player_id` INT(11) DEFAULT -1,
  `balance` DOUBLE DEFAULT 0.0,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`player_id`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;