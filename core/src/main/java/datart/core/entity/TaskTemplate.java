package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TaskTemplate {

    /**
     * id
     */
    private Long id;

    /**
     * 任务管理ID
     */
    private Long taskId;

    /**
     * 文件ID
     */
    private Long fileId;

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

    private String fileName;
}
