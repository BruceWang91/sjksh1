package datart.core.mappers;

import datart.core.entity.FileSheetField;
import org.apache.ibatis.jdbc.SQL;

public class FileSheetFieldSqlProvider {
    public String insertSelective(FileSheetField record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("file_sheet_field");

        if (record.getFileId() != null) {
            sql.VALUES("file_id", "#{fileId,jdbcType=BIGINT}");
        }

        if (record.getSheetId() != null) {
            sql.VALUES("sheet_id", "#{sheetId,jdbcType=BIGINT}");
        }

        if (record.getCellNum() != null) {
            sql.VALUES("cell_num", "#{cellNum,jdbcType=INTEGER}");
        }

        if (record.getCellName() != null) {
            sql.VALUES("cell_name", "#{cellName,jdbcType=VARCHAR}");
        }

        if (record.getRowNum() != null) {
            sql.VALUES("row_num", "#{rowNum,jdbcType=INTEGER}");
        }

        if (record.getEntityField() != null) {
            sql.VALUES("entity_field", "#{entityField,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }

        if (record.getDelFlag() != null) {
            sql.VALUES("del_flag", "#{delFlag,jdbcType=VARCHAR}");
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
