package datart.core.mappers;

import datart.core.entity.FileSheets;
import org.apache.ibatis.jdbc.SQL;

public class FileSheetsSqlProvider {
    public String insertSelective(FileSheets record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("file_sheets");

        if (record.getFileId() != null) {
            sql.VALUES("file_id", "#{fileId,jdbcType=BIGINT}");
        }

        if (record.getSheetName() != null) {
            sql.VALUES("sheet_name", "#{sheetName,jdbcType=VARCHAR}");
        }

        if (record.getEntityName() != null) {
            sql.VALUES("entity_name", "#{entityName,jdbcType=VARCHAR}");
        }

        if (record.getOrderNum() != null) {
            sql.VALUES("order_num", "#{orderNum,jdbcType=INTEGER}");
        }

        if (record.getStartRow() != null) {
            sql.VALUES("start_row", "#{startRow,jdbcType=INTEGER}");
        }

        if (record.getStartCell() != null) {
            sql.VALUES("start_cell", "#{startCell,jdbcType=INTEGER}");
        }

        if (record.getCellCount() != null) {
            sql.VALUES("cell_count", "#{cellCount,jdbcType=INTEGER}");
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
