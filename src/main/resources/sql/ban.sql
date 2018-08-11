CREATE TABLE IF NOT EXISTS `&ban` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `player_id` INT(20) DEFAULT -1,
  `from_id` INT(20) DEFAULT -1,
  `description` TEXT,
  PRIMARY KEY(`id`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;