package datart.core.mappers;

import datart.core.entity.Department;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author wangya
 */
public interface DepartmentMapper {

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Select({
            "select d.dept_id, d.parent_id, d.ancestors, d.dept_name, d.order_num,",
            "d.status, d.del_flag, d.create_by, d.create_time, d.update_by, d.update_time",
            "from department d",
            "where dept_id = #{deptId,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "dept_id", property = "deptId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ancestors", property = "ancestors", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dept_name", property = "deptName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_name", property = "parentName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    Department selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Select({
            "select count(*) from department where status = 0 and del_flag = '0' and find_in_set(#{deptId}, ancestors)"
    })
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Select({
            "select count(1) from department where parent_id = #{deptId} limit 1"
    })
    int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Select({
            "select count(1) from user where dept_id = #{deptId} "
    })
    int checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    @Select({
            "select d.dept_id, d.parent_id, d.ancestors, d.dept_name, d.order_num,",
            "d.status, d.del_flag, d.create_by, d.create_time",
            "from department d",
            "where dept_name=#{deptName} and parent_id = #{parentId} limit 1"
    })
    Department checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @InsertProvider(type = DepartmentSqlProvider.class, method = "insertSelective")
    int insertDept(Department dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Update({
            "<script>",
            "update department\n" +
                    "<set>\n" +
                    "<if test=\"parentId != null and parentId != 0\">parent_id = #{parentId},</if>\n" +
                    "<if test=\"deptName != null and deptName != ''\">dept_name = #{deptName},</if>\n" +
                    "<if test=\"ancestors != null and ancestors != ''\">ancestors = #{ancestors},</if>\n" +
                    "<if test=\"orderNum != null\">order_num = #{orderNum},</if>\n" +
                    "<if test=\"status != null and status != ''\">status = #{status},</if>\n" +
                    "<if test=\"updateBy != null and updateBy != ''\">update_by = #{updateBy},</if>\n" +
                    "update_time = sysdate()\n" +
                    "</set>\n" +
                    "where dept_id = #{deptId}",
            "</script>"
    })
    int updateDept(Department dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    @Update({
            "<script>",
            "update department set status = '0' where dept_id in \n" +
                    "<foreach collection=\"array\" item=\"deptId\" open=\"(\" separator=\",\" close=\")\">\n" +
                    "#{deptId}\n" +
                    "</foreach>",
            "</script>"
    })
    void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    @Update({
            "<script>",
            " update department set ancestors =\n" +
                    "<foreach collection=\"depts\" item=\"item\" index=\"index\"\n" +
                    "separator=\" \" open=\"case dept_id\" close=\"end\">\n" +
                    "when #{item.deptId} then #{item.ancestors}\n" +
                    "</foreach>\n" +
                    "where dept_id in\n" +
                    "<foreach collection=\"depts\" item=\"item\" index=\"index\"\n" +
                    "separator=\",\" open=\"(\" close=\")\">\n" +
                    "#{item.deptId}\n" +
                    "</foreach>",
            "</script>"
    })
    int updateDeptChildren(@Param("depts") List<Department> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Delete({
            "delete from department where dept_id = #{deptId}"
    })
    int deleteDeptById(Long deptId);
}
