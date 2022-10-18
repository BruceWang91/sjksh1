ALTER TABLE file_main add `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID' AFTER class_id,
 add `is_folder` tinyint(1) DEFAULT 0 COMMENT '是否文件夹 1是 0否' AFTER class_id;


ALTER TABLE file_save ADD `parent_id` bigint(20) DEFAULT NULL COMMENT '上级ID' AFTER del_flag,
 add `is_folder` tinyint(1) DEFAULT 0 COMMENT '是否文件夹 1是 0否' AFTER del_flag;


ALTER TABLE jimu_report ADD `parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID' AFTER tenant_id,
 add `is_folder` tinyint(1) DEFAULT 0 COMMENT '是否文件夹 1是 0否' AFTER tenant_id;


alter table jimu_report_share add PREVIEW_LOCK_STATUS varchar(1);


DROP TABLE IF EXISTS `task_management`;
create table task_management (
    `task_id` BIGINT(20) not null auto_increment comment '任务管理ID',
    `task_name` varchar(300) default '' comment '任务名称',
    `status` char(1) DEFAULT '0'        comment '状态（0开启 1停用）',
    `start_time` datetime               comment '任务开始时间',
    `end_time`   datetime               comment '任务结束时间',
    `parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
    `is_folder` tinyint(1) DEFAULT 0 COMMENT '是否文件夹 1是 0否',
    `create_by` varchar(64) DEFAULT ''  comment '创建者',
    `create_time` datetime DEFAULT NULL comment '创建时间',
    `update_by` varchar(64) DEFAULT ''  comment '更新者',
    `update_time` datetime DEFAULT NULL comment '更新时间',
    `remark` varchar(5000) DEFAULT ''   comment '备注',
    primary key (`task_id`)
) engine=innodb auto_increment=200 comment = '数据导入任务管理表' CHARSET=utf8;


DROP TABLE IF EXISTS `task_users`;
create table task_users (
    `id` BIGINT(20) not null auto_increment comment 'ID',
    `task_id` BIGINT(20) not null       comment '任务管理ID',
    `user_id` varchar(32) NOT NULL      comment '人员ID',
    `create_by` varchar(64) DEFAULT ''  comment '创建者',
    `create_time` datetime DEFAULT NULL comment '创建时间',
    `update_by` varchar(64) DEFAULT ''  comment '更新者',
    `update_time` datetime DEFAULT NULL comment '更新时间',
    `remark` varchar(5000) DEFAULT ''   comment '备注',
    primary key (`id`)
) engine=innodb auto_increment=200 comment = '数据导入任务人员关联表' CHARSET=utf8;


DROP TABLE IF EXISTS `task_template`;
create table task_template (
    `id` BIGINT(20) not null auto_increment comment 'ID',
    `task_id` BIGINT(20) not null       comment '任务管理ID',
    `file_id` BIGINT(20) NOT NULL       comment '文件ID',
    `create_by` varchar(64) DEFAULT ''  comment '创建者',
    `create_time` datetime DEFAULT NULL comment '创建时间',
    `update_by` varchar(64) DEFAULT ''  comment '更新者',
    `update_time` datetime DEFAULT NULL comment '更新时间',
    `remark` varchar(5000) DEFAULT ''   comment '备注',
    primary key (`id`)
) engine=innodb auto_increment=200 comment = '数据导入任务模板关联表' CHARSET=utf8;


DROP TABLE IF EXISTS `file_save_stream`;
CREATE TABLE `file_save_stream` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `file_save_id` bigint(20) NOT NULL COMMENT '文件保存表主键',
    `file_stream` MediumBlob COMMENT '文件流',
    `pdf_stream` MediumBlob COMMENT 'PDF文件流',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件保存数据流关联表';

