package datart.core.mappers;

import datart.core.entity.TaskManagement;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface TaskManagementMapper extends CRUDMapper {

    /**
     * 查询任务管理列表
     *
     * @param taskManagement 任务管理
     * @return 任务管理集合
     */
    @Select({
            "<script>",
            "select task.* from task_management task\n" +
                    "LEFT JOIN user suser\n" +
                    "     ON task.create_by = suser.id\n" +
                    "where 1=1\n",
            "<if test=\"createBy != null and createBy != ''\"> and task.create_by = #{createBy} \n</if>" +
                    "<if test=\"taskName != null and taskName != ''\"> and task.task_name like concat('%', #{taskName}, '%')\n</if>" +
                    "<if test=\"status != null  and status != ''\"> and task.status = #{status}\n</if>" +
                    "<if test=\"isFolder != null \"> and task.is_folder = #{isFolder}\n</if>",
            "<if test=\"parentId != null \"> and task.parent_id = #{parentId}\n</if>",
            "<if test=\"depIds != null and depIds.size() > 0\">\n" +
                    "            AND suser.dept_id IN\n" +
                    "            <foreach item=\"depIds\" collection=\"depIds\" open=\"(\" separator=\",\" close=\")\">\n" +
                    "                #{depIds}\n" +
                    "            </foreach>\n" +
                    "</if>",
            "</script>"
    })
    List<TaskManagement> selectTaskManagementList(TaskManagement taskManagement);

    @Select({
            "SELECT * FROM task_management where task_id = #{taskId}"
    })
    @Results({
            @Result(column = "task_id", property = "taskId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "task_name", property = "taskName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "is_folder", property = "isFolder", jdbcType = JdbcType.INTEGER),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    TaskManagement selectTaskManagementByTaskId(Long taskId);

    /**
     * 新增任务管理
     *
     * @param taskManagement 任务管理
     * @return 结果
     */
    @InsertProvider(type = TaskManagementSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "taskId", keyColumn = "task_id")
    int insertTaskManagement(TaskManagement taskManagement);

    /**
     * 修改任务管理
     *
     * @param taskManagement 任务管理
     * @return 结果
     */
    @Update({
            "<script>",
            "update task_management\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"taskName != null\">task_name = #{taskName},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"startTime != null\">start_time = #{startTime},</if>\n" +
                    "            <if test=\"endTime != null\">end_time = #{endTime},</if>\n" +
                    "            <if test=\"isFolder != null\">is_folder = #{isFolder},</if>\n" +
                    "            <if test=\"parentId != null\">parent_id = #{parentId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where task_id = #{taskId}",
            "</script>"
    })
    int updateTaskManagement(TaskManagement taskManagement);

    /**
     * 删除任务管理
     *
     * @param taskId 任务管理主键
     * @return 结果
     */
    @Delete({
            "delete from task_management where task_id = #{taskId,jdbcType=BIGINT}"
    })
    int deleteTaskManagementByTaskId(Long taskId);

    /**
     * 批量删除任务管理
     *
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from task_management where task_id in ",
            "<foreach collection='array' item='taskId' index='index' open='(' close=')' separator=','>#{taskId}</foreach>",
            "</script>"
    })
    int deleteTaskManagementByTaskIds(String[] taskIds);

}
