drop table if exists file_main;
create table file_main (
    file_id           bigint(20)       not null auto_increment    comment '文件id',
    file_name         varchar(300)     default ''                 comment '文件名称',
    order_num         int(1)           default 0                  comment '显示顺序',
    status            char(1)          default '0'                comment '文件状态（0正常 1停用）',
    del_flag          char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
    class_id          bigint(20)                                  comment '分类id',
    create_by         varchar(64)      default ''                 comment '创建者',
    create_time 	  datetime                                    comment '创建时间',
    update_by         varchar(64)      default ''                 comment '更新者',
    update_time       datetime                                    comment '更新时间',
    remark            varchar(300)     default ''                 comment '备注',
    primary key (file_id)
) engine=innodb auto_increment=200 comment = '文件管理表';

drop table if exists file_sheets;
create table file_sheets (
    sheet_id          bigint(20)      not null auto_increment    comment '工作簿id',
    file_id           bigint(20)                                 comment '文件id',
    title             varchar(300)     default ''                comment '标题',
    sheet_name        varchar(300)     default ''                comment '工作簿名称',
    entity_name       VARCHAR(300)     default ''                comment '对应实体名称',
    order_num         int(1)          default 0                  comment '顺序',
    start_row         int(1)          default 0                  comment '读取开始行',
    start_cell        int(1)          default 0                  comment '读取开始列',
    cell_count        int(1)          default 0                  comment '有效列数',
    status            char(1)         default '0'                comment '状态（0正常 1停用）',
    del_flag          char(1)         default '0'                comment '删除标志（0代表存在 1代表删除）',
    create_by         varchar(64)     default ''                 comment '创建者',
    create_time 	    datetime                                 comment '创建时间',
    update_by         varchar(64)     default ''                 comment '更新者',
    update_time       datetime                                   comment '更新时间',
    remark            VARCHAR(5000)     DEFAULT ''               comment '备注',
    primary key (sheet_id)
) engine=innodb auto_increment=200 comment = '文件工作簿表';

drop table if exists file_sheet_field;
create table file_sheet_field (
    field_id          bigint(20)      not null auto_increment    comment '字段id',
    file_id           bigint(20)                                 comment '文件id',
    sheet_id          bigint(20)                                 comment '工作簿id',
    cell_num          int(1)          default 0                  comment '对应列数 从0开始',
    cell_name         VARCHAR(300)     default ''                comment '对应表头名称',
    row_num           int(1)          default 0                  comment '所在行数  用于表头',
    entity_field      VARCHAR(300)     default ''                comment '对应实体表字段',
    status            char(1)         default '0'                comment '状态（0不参与数据导入 1参与数据导入使用）',
    del_flag          char(1)         default '0'                comment '删除标志（0代表存在 1代表删除）',
    create_by         varchar(64)     default ''                 comment '创建者',
    create_time 	    datetime                                 comment '创建时间',
    update_by         varchar(64)     default ''                 comment '更新者',
    update_time       datetime                                   comment '更新时间',
    remark            VARCHAR(300)     DEFAULT ''                comment '备注',
    primary key (field_id)
) engine=innodb auto_increment=200 comment = '字段对应表';

drop table if exists file_save;
create table file_save (
    id                bigint(20)       not null auto_increment    comment '文档id',
    class_id          bigint(20)                                  comment '分类id',
    file_name         varchar(300)     default ''                 comment '文件名称',
    new_name          varchar(300)     default ''                 comment '存入后名称',
    pdf_name          varchar(300)     default ''                 comment 'PDF文件名称',
    url               varchar(300)     default ''                 comment 'URL',
    pdfurl            varchar(300)     default ''                 comment 'PDFURL',
    order_num         int(1)           default 0                  comment '显示顺序',
    status            char(1)          default '0'                comment '文件状态（0正常 1停用）',
    del_flag          char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
    create_by         varchar(64)      default ''                 comment '创建者',
    create_time 	  datetime                                    comment '创建时间',
    update_by         varchar(64)      default ''                 comment '更新者',
    update_time       datetime                                    comment '更新时间',
    remark            varchar(300)     default ''                 comment '备注',
    primary key (id)
) engine=innodb auto_increment=200 comment = '文件保存表';

drop table if exists file_class;
create table file_class (
    id                bigint(20)       not null auto_increment    comment 'id',
    name              varchar(300)     default ''                 comment '分类名称',
    parent_id         bigint(20)       default 0                  comment '上级分类id',
    order_num         int(1)           default 0                  comment '显示顺序',
    status            char(1)          default '0'                comment '状态（0正常 1停用）',
    del_flag          char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
    create_by         varchar(64)      default ''                 comment '创建者',
    create_time 	  datetime                                    comment '创建时间',
    update_by         varchar(64)      default ''                 comment '更新者',
    update_time       datetime                                    comment '更新时间',
    remark            varchar(300)     default ''                 comment '备注',
    primary key (id)
) engine=innodb auto_increment=200 comment = '文件分类';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
    `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
    `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
    `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
    `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
    `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='部门表';