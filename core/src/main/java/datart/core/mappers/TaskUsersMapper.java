package datart.core.mappers;

import datart.core.entity.JimuReportRewrite;
import datart.core.entity.TaskUsers;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface TaskUsersMapper extends CRUDMapper {

    /**
     * 查询任务人员关联表列表
     *
     * @param taskUsers 数据导入任务人员关联表
     * @return 任务人员关联表
     */
    @Select({
            "<script>",
            "select taskUsers.*,u.name as userName\n" +
                    "from task_users taskUsers\n" +
                    "left join user u\n" +
                    "on taskUsers.user_id = u.id \n" +
                    " where 1=1\n",
            "<if test=\"taskId != null\"> and taskUsers.task_id = #{taskId}\n</if>" +
                    "</script>"
    })
    List<TaskUsers> selectTaskUsersList(TaskUsers taskUsers);

    @Select({
            "SELECT * FROM task_users where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "task_id", property = "taskId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    JimuReportRewrite selectTaskUsersById(Long id);

    /**
     * 新增任务人员关联表
     *
     * @param taskUsers 任务人员关联表
     * @return 结果
     */
    @InsertProvider(type = TaskUsersSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertTaskUsers(TaskUsers taskUsers);

    /**
     * 修改任务人员关联表
     *
     * @param taskUsers 任务人员关联表
     * @return 结果
     */
    @Update({
            "<script>",
            "update task_users\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"taskId != null\">task_id = #{taskId},</if>\n" +
                    "            <if test=\"userId != null\">user_id = #{userId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateTaskUsers(TaskUsers taskUsers);

    /**
     * 删除任务人员关联表
     *
     * @param taskId 任务人员关联表任务Id
     * @return 结果
     */
    @Delete({
            "delete from task_users where task_id = #{taskId,jdbcType=BIGINT}"
    })
    int deleteTaskUsersByTaskId(Long taskId);

    /**
     * 删除任务人员关联表
     *
     * @param id 需要删除的数据主键
     * @return 结果
     */
    @Delete({
            "delete from task_users where id = #{id,jdbcType=BIGINT}"
    })
    int deleteTaskUsersById(Long id);

}
