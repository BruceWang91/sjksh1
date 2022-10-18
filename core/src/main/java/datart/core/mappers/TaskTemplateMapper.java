package datart.core.mappers;

import datart.core.entity.JimuReportRewrite;
import datart.core.entity.TaskTemplate;
import datart.core.entity.param.TaskInformationParam;
import datart.core.entity.result.TaskInformationResult;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TaskTemplateMapper extends CRUDMapper {

    /**
     * 查询数据导入任务模板关联表
     *
     * @param taskTemplate 数据导入任务模板关联表
     * @return 任务模板关联表
     */
    @Select({
            "<script>",
            "select template.*,file.file_name from\n" +
                    " task_template template\n" +
                    "left join file_main file\n" +
                    "on template.file_id = file.file_id\n" +
                    " where 1=1\n",
            "<if test=\"taskId != null\"> and task_id = #{taskId}\n</if>" +
                    "</script>"
    })
    List<TaskTemplate> selectTaskTemplateList(TaskTemplate taskTemplate);

    @Select({
            "SELECT * FROM task_template where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "task_id", property = "taskId", jdbcType = JdbcType.BIGINT),
            @Result(column = "file_id", property = "fileId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "file_name", property = "fileName", jdbcType = JdbcType.VARCHAR)
    })
    JimuReportRewrite selectTaskTemplateById(Long id);

    /**
     * 新增任务模板关联表
     *
     * @param taskTemplate 任务模板关联表
     * @return 结果
     */
    @InsertProvider(type = TaskTemplateSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertTaskTemplate(TaskTemplate taskTemplate);

    /**
     * 修改任务模板关联表
     *
     * @param taskTemplate 任务模板关联表
     * @return 结果
     */
    @Update({
            "<script>",
            "update task_template\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"taskId != null\">task_id = #{taskId},</if>\n" +
                    "            <if test=\"userId != null\">file_id = #{fileId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateTaskTemplate(TaskTemplate taskTemplate);

    /**
     * 删除任务模板关联表
     *
     * @param taskId 任务模板关联表任务Id
     * @return 结果
     */
    @Delete({
            "delete from task_template where task_id = #{taskId,jdbcType=BIGINT}"
    })
    int deleteTaskTemplateByTaskId(Long taskId);

    /**
     * 删除任务模板关联表
     *
     * @param id 需要删除的数据主键
     * @return 结果
     */
    @Delete({
            "delete from task_template where id = #{id,jdbcType=BIGINT}"
    })
    int deleteTaskTemplateById(Long id);

    @Select({
            "<script>",
            "SELECT\n" +
                    "management.task_id as taskId,\n" +
                    "management.task_name as taskName,\n" +
                    "management.start_time as startTime,\n" +
                    "management.end_time as endTime,\n" +
                    "file.file_id as fileId,\n" +
                    "file.file_name as fileName,\n" +
                    "u.id as userId,\n" +
                    "u.`name` as userName,\n" +
                    "dept.dept_id as deptId,\n" +
                    "dept.dept_name as deptName\n" +
                    "FROM task_management management\n" +
                    "LEFT JOIN task_users taskusers\n" +
                    "ON management.task_id = taskusers.task_id\n" +
                    "LEFT JOIN `user` u\n" +
                    "ON taskusers.user_id = u.id\n" +
                    "LEFT JOIN department dept\n" +
                    "ON u.dept_id = dept.dept_id\n" +
                    "LEFT JOIN task_template template\n" +
                    "ON management.task_id = template.task_id\n" +
                    "LEFT JOIN file_main file \n" +
                    "on file.file_id = template.file_id \n" +
                    "where 1=1 \n" +
                    "<if test=\"taskId != null\">and management.task_id = #{taskId}\n</if>\n" +
                    "<if test=\"userId != null\">and u.id = #{userId} or management.create_by = #{userId} \n</if>\n" +
                    "</script>"
    })
    List<TaskInformationResult> getTaskResult(TaskInformationParam param);

    @Select({
            "<script>",
            "select count(1) from ${tableName} where 1=1 \n" +
                    "<if test=\"userId != null\">and create_by = #{userId}\n</if>\n" +
                    "<if test=\"startTime != null\">and create_time <![CDATA[ >= ]]> #{startTime}\n</if>\n" +
                    "<if test=\"endTime != null\">and create_time <![CDATA[ <= ]]> #{endTime}\n</if>\n" +
                    "</script>"
    })
    int countImportDatas(HashMap<String, Object> map);
}
