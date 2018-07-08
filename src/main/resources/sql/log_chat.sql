CREATE TABLE IF NOT EXISTS `&log_command` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `player_id` BIGINT(20) NOT NULL,
  `command` text
  PRIMARY KEY(`id`)
)CHARACTER SET utf8 COLLATE utf8_unicode_ci;