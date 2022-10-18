package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TaskUsers {

    /**
     * id
     */
    private Long id;

    /**
     * 任务管理ID
     */
    private Long taskId;

    /**
     * 人员ID
     */
    private String userId;

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

    /**
     * 人员名称
     */
    private String userName;
}
