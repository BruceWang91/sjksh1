package datart.core.mappers;

import datart.core.entity.TaskManagement;
import org.apache.ibatis.jdbc.SQL;

public class TaskManagementSqlProvider {

    public String insertSelective(TaskManagement record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("task_management");

        if (record.getTaskId() != null) {
            sql.VALUES("task_id", "#{taskId,jdbcType=BIGINT}");
        }

        if (record.getTaskName() != null) {
            sql.VALUES("task_name", "#{taskName,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }

        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }

        if (record.getEndTime() != null) {
            sql.VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
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
