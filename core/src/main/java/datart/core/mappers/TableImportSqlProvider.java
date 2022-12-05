package datart.core.mappers;

import datart.core.entity.TableImport;
import org.apache.ibatis.jdbc.SQL;

public class TableImportSqlProvider {

    public String insertSelective(TableImport record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("table_import");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id}");
        }
        if (record.getName() != null) {
            sql.VALUES("name", "#{name}");
        }
        if (record.getOrgId() != null) {
            sql.VALUES("org_id", "#{orgId}");
        }
        if (record.getSourceId() != null) {
            sql.VALUES("source_id", "#{sourceId}");
        }
        if (record.getTableName() != null) {
            sql.VALUES("table_name", "#{tableName}");
        }
        if (record.getOrderNum() != null) {
            sql.VALUES("order_num", "#{orderNum}");
        }
        if (record.getIsFolder() != null) {
            sql.VALUES("is_folder", "#{isFolder}");
        }
        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId}");
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
