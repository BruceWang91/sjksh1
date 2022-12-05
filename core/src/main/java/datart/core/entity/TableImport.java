package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TableImport {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 数据源ID
     */
    private String sourceId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 是否文件夹 1是 0否
     */
    private Integer isFolder;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    private List<TableImportField> list;
}
