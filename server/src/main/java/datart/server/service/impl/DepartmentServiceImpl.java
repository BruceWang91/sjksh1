package datart.server.service.impl;

import datart.core.entity.Department;
import datart.core.entity.User;
import datart.core.mappers.ext.DepartmentMapperExt;
import datart.server.common.Convert;
import datart.server.common.StringUtils;
import datart.server.enums.UserConstants;
import datart.server.service.BaseService;
import datart.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 部门管理 服务实现
 *
 * @author wangya
 */
@Service
public class DepartmentServiceImpl extends BaseService implements IDepartmentService {
    @Autowired
    private DepartmentMapperExt deptMapper;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<Department> selectDeptList(Department dept) {

        List<Department> list = deptMapper.selectDeptList(dept);
        for (Department department : list) {

            Long deptId = department.getDeptId();
            List<User> users = userService.getUsersByDeptId(deptId);
            department.setUsers(users);
        }
        return list;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<Department> buildDeptTree(List<Department> depts) {
        List<Department> returnList = new ArrayList<Department>();
        List<Long> tempList = new ArrayList<Long>();
        for (Department dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Department dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Department> buildDeptTreeSelect(List<Department> depts) {
        List<Department> deptTrees = buildDeptTree(depts);
        return deptTrees;
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Department selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Department dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Department info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Department dept) {
        dept.setCreateBy(getCurrentUser().getId());
        dept.setCreateTime(new Date());
        Department info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (info != null && !UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
//            throw new Exception("部门停用，不允许新增");
            return 0;
        }
        dept.setAncestors(null == info ? "0" : info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(Department dept) {
        dept.setUpdateBy(getCurrentUser().getId());
        dept.setUpdateTime(new Date());
        Department newParentDept = deptMapper.selectDeptById(dept.getParentId());
        Department oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(Department dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<Department> children = deptMapper.selectChildrenDeptById(deptId);
        for (Department child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Department> list, Department t) {
        // 得到子节点列表
        List<Department> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Department tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Department> getChildList(List<Department> list, Department t) {
        List<Department> tlist = new ArrayList<Department>();
        Iterator<Department> it = list.iterator();
        while (it.hasNext()) {
            Department n = (Department) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Department> list, Department t) {
        return getChildList(list, t).size() > 0;
    }
}
