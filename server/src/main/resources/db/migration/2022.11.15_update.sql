alter table static_management add org_id varchar(32) default '' comment '组织id' AFTER module_name;

UPDATE static_management SET org_id = (select id from organization LIMIT 1) WHERE id > 0;

UPDATE static_management SET text_content = null WHERE url is not null or url != '';

ALTER TABLE file_sheet_field ADD import_flag char(1) DEFAULT '0' COMMENT '导入标识 （0不做筛选 1该列数据为空时不导入该行数据）' AFTER del_flag;

ALTER TABLE static_management ADD `index` INT(4) DEFAULT 0 COMMENT '排序' AFTER is_folder;

ALTER TABLE static_management ADD mark VARCHAR(32) DEFAULT '' COMMENT '模块标记' AFTER org_id;

ALTER TABLE table_import_field ADD `index` INT(4) DEFAULT 0 COMMENT '排序' AFTER table_field;
