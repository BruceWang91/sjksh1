DROP TABLE IF EXISTS `visitor_statistics`;
CREATE TABLE `visitor_statistics` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module_name` VARCHAR(300) DEFAULT NULL COMMENT '模块名称',
    `type` VARCHAR(300) DEFAULT NULL COMMENT '统计地址',
    `num` bigint(20) DEFAULT 1 COMMENT '访问次数',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访客统计';



