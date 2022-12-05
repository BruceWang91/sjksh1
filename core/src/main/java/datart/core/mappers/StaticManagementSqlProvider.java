package datart.core.mappers;

import datart.core.entity.StaticManagement;
import org.apache.ibatis.jdbc.SQL;

public class StaticManagementSqlProvider {

    public String insertSelective(StaticManagement record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("static_management");

        if (record.getModuleName() != null) {
            sql.VALUES("module_name", "#{moduleName}");
        }
        if (record.getOrgId() != null) {
            sql.VALUES("org_id", "#{orgId}");
        }
        if (record.getMark() != null){
            sql.VALUES("mark","#{mark}");
        }
        if (record.getType() != null) {
            sql.VALUES("type", "#{type}");
        }
        if (record.getTextContent() != null) {
            sql.VALUES("text_content", "#{textContent}");
        }
        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url}");
        }
        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId}");
        }
        if (record.getIsFolder() != null) {
            sql.VALUES("is_folder", "#{isFolder}");
        }
        if (record.getIndex() != null) {
            sql.VALUES("`index`", "#{index}");
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
        if (record.getLink() != null) {
            sql.VALUES("link", "#{link,jdbcType=VARCHAR}");
        }
        if (record.getJsonStr() != null) {
            sql.VALUES("json_str", "#{jsonStr}");
        }
        return sql.toString();
    }
}
