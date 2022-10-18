package datart.core.mappers;

import datart.core.entity.TaskUsers;
import org.apache.ibatis.jdbc.SQL;

public class TaskUsersSqlProvider {

    public String insertSelective(TaskUsers record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("task_users");

        if (record.getTaskId() != null) {
            sql.VALUES("task_id", "#{taskId,jdbcType=BIGINT}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
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
