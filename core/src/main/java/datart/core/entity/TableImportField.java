package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TableImportField {

    /**
     * id
     */
    private Long id;

    /**
     * 主表id
     */
    private Long tableImportId;

    /**
     * 对应表头名称
     */
    private String cellName;

    /**
     * 对应表字段
     */
    private String tableField;

    /**
     * 排序
     */
    private Integer index;

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
}
