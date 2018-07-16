CREATE TABLE IF NOT EXISTS `&permission` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `name` VARCHAR(50),
  `description` TEXT,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`name`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;