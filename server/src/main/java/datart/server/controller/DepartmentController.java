package datart.server.controller;

import datart.core.entity.Department;
import datart.core.entity.TreeSelect;
import datart.server.base.dto.ResponseData;
import datart.server.common.StringUtils;
import datart.server.enums.UserConstants;
import datart.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 部门信息
 *
 * @author ruoyi
 */
@Api("部门信息")
@RestController
@RequestMapping("/system/dept")
public class DepartmentController extends BaseController {
    @Autowired
    private IDepartmentService deptService;

    /**
     * 获取部门列表
     */
    @ApiOperation("获取部门列表")
    @GetMapping("/list")
    public ResponseData<List<Department>> list(Department dept) {
        List<Department> depts = deptService.selectDeptList(dept);
        return ResponseData.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @ApiOperation("查询部门列表（排除节点）")
    @GetMapping("/list/exclude/{deptId}")
    public ResponseData<List<Department>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<Department> depts = deptService.selectDeptList(new Department());
        Iterator<Department> it = depts.iterator();
        while (it.hasNext()) {
            Department d = (Department) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return ResponseData.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @ApiOperation("根据部门编号获取详细信息")
    @GetMapping(value = "/{deptId}")
    public ResponseData<Department> getInfo(@PathVariable Long deptId) {
        return ResponseData.success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @ApiOperation("获取部门下拉树列表")
    @GetMapping("/treeselect")
    public ResponseData<List<TreeSelect>> treeselect(Department dept) {
        List<Department> depts = deptService.selectDeptList(dept);
        return ResponseData.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 新增部门
     */
    @ApiOperation("新增部门")
    @PostMapping
    public ResponseData add(@Validated @RequestBody Department dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseData.failure("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        return ResponseData.success(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @ApiOperation("修改部门")
    @PutMapping
    public ResponseData<Integer> edit(@Validated @RequestBody Department dept) {
        Long deptId = dept.getDeptId();
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseData.failure("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return ResponseData.failure("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return ResponseData.failure("该部门包含未停用的子部门！");
        }
        return ResponseData.success(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @ApiOperation("删除部门")
    @DeleteMapping("/{deptId}")
    public ResponseData<Integer> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return ResponseData.failure("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return ResponseData.failure("部门存在用户,不允许删除");
        }
        return ResponseData.success(deptService.deleteDeptById(deptId));
    }
}
