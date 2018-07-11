CREATE TABLE IF NOT EXISTS `&block` (
  `id` BIGINT(20) AUTO_INCREMENT,
  `minecraft_id` INT(11) DEFAULT 0,
  `name` VARCHAR(50),
  `max_stack_size` TINYINT(2) DEFAULT 0,
  `is_block` TINYINT(1) DEFAULT 0,
  `is_edible` TINYINT(1) DEFAULT 0,
  `is_record` TINYINT(1) DEFAULT 0,
  `is_solid` TINYINT(1) DEFAULT 0,
  `is_transparent` TINYINT(1) DEFAULT 0,
  `is_flammable` TINYINT(1) DEFAULT 0,
  `is_burnable` TINYINT(1) DEFAULT 0,
  `is_occluding` TINYINT(1) DEFAULT 0,
  `has_gravity` TINYINT(1) DEFAULT 0,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`name`)
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;