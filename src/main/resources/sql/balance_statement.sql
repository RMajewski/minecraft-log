CREATE TABLE IF NOT EXISTS `&balance_statement` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `player_id` INT(11) DEFAULT -1,
  `amount` DOUBLE DEFAULT 0.0,
  `status` TINYINT(3) DEFAULT 0,
  `other_id` INT(11) DEFAULT -1,
  `message` TEXT,
  PRIMARY KEY(`id`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;