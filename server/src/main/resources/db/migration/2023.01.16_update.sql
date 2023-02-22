ALTER TABLE static_management ADD COLUMN `pdf_url` varchar(300) DEFAULT NULL COMMENT 'pdf文件地址' AFTER `url`;

ALTER TABLE `user` ADD COLUMN `admin_competence` bigint(20) DEFAULT NULL COMMENT '行政权限' AFTER `dept_id`;

