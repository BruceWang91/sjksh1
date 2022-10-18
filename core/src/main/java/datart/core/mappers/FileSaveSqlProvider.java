package datart.core.mappers;

import datart.core.entity.FileSave;
import org.apache.ibatis.jdbc.SQL;

public class FileSaveSqlProvider {
    public String insertSelective(FileSave record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("file_save");

        if (record.getClassId() != null) {
            sql.VALUES("class_id", "#{classId,jdbcType=BIGINT}");
        }

        if (record.getOrgId() != null) {
            sql.VALUES("org_id", "#{orgId,jdbcType=VARCHAR}");
        }

        if (record.getFileName() != null) {
            sql.VALUES("file_name", "#{fileName,jdbcType=VARCHAR}");
        }

        if (record.getNewName() != null) {
            sql.VALUES("new_name", "#{newName,jdbcType=VARCHAR}");
        }

        if (record.getPdfName() != null) {
            sql.VALUES("pdf_name", "#{pdfName,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }

        if (record.getPdfurl() != null) {
            sql.VALUES("pdfurl", "#{pdfurl,jdbcType=VARCHAR}");
        }

        if (record.getOrderNum() != null) {
            sql.VALUES("order_num", "#{orderNum,jdbcType=INTEGER}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }

        if (record.getDelFlag() != null) {
            sql.VALUES("del_flag", "#{delFlag,jdbcType=VARCHAR}");
        }

        if (record.getIsFolder() != null) {
            sql.VALUES("is_folder", "#{isFolder,jdbcType=INTEGER}");
        }

        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId,jdbcType=BIGINT}");
        }

        if (record.getCreateBy() != null) {
            sql.VALUES("create_by", "#{createBy,jdbcType=VARCHAR}");
        }

        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateBy() != null) {
            sql.VALUES("update_by", "#{updateBy,jdbcType=VARCHAR}");
        }

        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        return sql.toString();
    }
}
