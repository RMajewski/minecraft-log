CREATE TABLE IF NOT EXISTS `&permission_allocate` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `parent_id` BIGINT(20) NOT NULL,
  `permission_id` BIGINT(20) NOT NULL,
  `clazz` INT(3) NOT NULL,
  `negate` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`parent_id`, `permission_id`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;