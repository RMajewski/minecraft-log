CREATE TABLE IF NOT EXISTS `&group_parent` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `parent_id` BIGINT(20) NOT NULL,
  `child_id` BIGINT(20) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`parent_id`, `child_id`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;