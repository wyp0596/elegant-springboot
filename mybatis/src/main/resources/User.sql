-- auto Generated on 2017-03-21 15:00:44 
-- DROP TABLE IF EXISTS `user`; 
CREATE TABLE `user`(
    `id` BIGINT (15) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username` VARCHAR (255) UNIQUE NOT NULL DEFAULT '' COMMENT 'username',
    `password` VARCHAR (255) NOT NULL DEFAULT '' COMMENT 'password',
    `create_at` DATETIME NOT NULL COMMENT 'createAt',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`user`';
