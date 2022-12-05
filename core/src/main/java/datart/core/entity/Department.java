package datart.core.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门表 depatment
 *
 * @author wangya
 */

@Data
public class Department {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    /**
     * 类型 1 分组 2 公司 3 部门
     */
    private Integer type;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<Department> children = new ArrayList<Department>();

    /**
     * 部门内人员
     */
    private List<User> users = new ArrayList<>();

}
