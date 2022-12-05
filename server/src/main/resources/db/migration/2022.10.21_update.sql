alter table file_main add source_id varchar(32) default '' COMMENT '数据源ID';

DROP TABLE IF EXISTS `static_management`;
CREATE TABLE `static_management` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module_name` VARCHAR(300) COMMENT '模块名称',
    `type` int(1) DEFAULT 1 COMMENT '类型 1文本 2图片 3文件',
    `text_content` MediumBlob COMMENT '文本内容',
    `url` varchar(300) DEFAULT NULL COMMENT 'url',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID',
    `is_folder` tinyint(1) DEFAULT 0 COMMENT '是否文件夹 1是 0否',
    `create_by` varchar(64) DEFAULT ''  comment '创建者',
    `create_time` datetime DEFAULT NULL comment '创建时间',
    `update_by` varchar(64) DEFAULT ''  comment '更新者',
    `update_time` datetime DEFAULT NULL comment '更新时间',
    `remark` varchar(500) DEFAULT ''   comment '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='静态资源管理';

alter table department add type int(4) DEFAULT '1' COMMENT '类型 1 分组 2 公司 3 部门';

ALTER TABLE static_management ADD COLUMN link VARCHAR(500) DEFAULT '' COMMENT '链接地址';

ALTER TABLE static_management ADD COLUMN json_str VARCHAR(500) DEFAULT '' COMMENT 'JSON';

drop table if EXISTS table_import;
CREATE TABLE `table_import` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name` varchar(300) DEFAULT '' COMMENT '名称',
    `org_id` varchar(32) DEFAULT '' COMMENT '组织id',
    `source_id` varchar(32) DEFAULT '' COMMENT '数据源ID',
    `table_name` varchar(300) DEFAULT '' COMMENT '表名',
    `order_num` int(1) DEFAULT '0' COMMENT '显示顺序',
    `is_folder` tinyint(1) DEFAULT '0' COMMENT '是否文件夹 1是 0否',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(300) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表数据导入';

drop table if exists table_import_field;
create table table_import_field (
    id                bigint(20)      not null auto_increment    comment 'id',
    table_import_id   bigint(20)                                 comment '主表id',
    cell_name         VARCHAR(300)     default ''                comment '对应表头名称',
    table_field       VARCHAR(300)     default ''                comment '对应表字段',
    create_by         varchar(64)      default ''                comment '创建者',
    create_time 	    datetime                                   comment '创建时间',
    update_by         varchar(64)      default ''                comment '更新者',
    update_time       datetime                                   comment '更新时间',
    remark            VARCHAR(300)     DEFAULT ''                comment '备注',
    primary key (id)
) engine=innodb DEFAULT CHARSET=utf8 comment = '表数据导入对应字段';


