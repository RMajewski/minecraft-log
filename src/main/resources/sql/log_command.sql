CREATE TABLE IF NOT EXISTS `&log_command` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `player_id` BIGINT(20) NOT NULL,
  `command` VARCHAR(20) DEFAULT '',
  `label` VARCHAR(20) DEFAULT '',
  `args` VARCHAR(200) DEFAULT '',
  PRIMARY KEY(`id`)
)CHARACTER SET utf8 COLLATE utf8_unicode_ci;