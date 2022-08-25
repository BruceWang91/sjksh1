package datart.core.mappers.ext;

import datart.core.entity.Department;
import datart.core.mappers.DepartmentMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapperExt extends DepartmentMapper {

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Select({
            "<script>",
            "select d.dept_id, d.parent_id, d.ancestors, d.dept_name, d.order_num, " +
                    "d.status, d.del_flag, d.create_by, d.create_time, d.update_by, d.update_time \n" +
                    "from department d",
                    "where d.del_flag = '0'\n" +
                    "<if test=\"deptId != null and deptId != 0\">\n" +
                    "AND dept_id = #{deptId}\n" +
                    "</if>\n" +
                    "<if test=\"parentId != null and parentId != 0\">\n" +
                    "AND parent_id = #{parentId} OR dept_id = #{parentId}" +
                    "</if>\n" +
                    "<if test=\"deptName != null and deptName != ''\">\n" +
                    "AND dept_name like concat('%', #{deptName}, '%')\n" +
                    "</if>\n" +
                    "<if test=\"status != null and status != ''\">\n" +
                    "AND status = #{status}\n" +
                    "</if>\n" +
                    "order by d.parent_id, d.order_num",
            "</script>"
    })
    List<Department> selectDeptList(Department dept);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    @Select({
            "select * from department where find_in_set(#{deptId}, ancestors)"
    })
    List<Department> selectChildrenDeptById(Long deptId);
}
