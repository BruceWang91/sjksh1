package datart.core.mappers;

import datart.core.entity.TableImportField;
import org.apache.ibatis.jdbc.SQL;

public class TableImportFieldSqlProvider {

    public String insertSelective(TableImportField record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("table_import_field");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id}");
        }
        if (record.getTableImportId() != null){
            sql.VALUES("table_import_id","#{tableImportId}");
        }
        if (record.getCellName() != null){
            sql.VALUES("cell_name","#{cellName}");
        }
        if (record.getTableField() != null){
            sql.VALUES("table_field","#{tableField}");
        }
        if (record.getIndex() != null){
            sql.VALUES("`index`","#{index}");
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
