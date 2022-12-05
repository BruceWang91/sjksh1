package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskManagement {

    /**
     * 任务管理ID
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 状态（0开启 1停用）
     */
    private String status;

    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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

    private String createName;

    /**
     * 任务参与人数
     */
    private int totalNumOfPeople;

    /**
     * 任务完成人数
     */
    private int numOfPeopleCompleted;

    private Long deptId;

    private List<Long> depIds;

    private List<TaskUsers> taskUsersList;

    private List<TaskTemplate> taskTemplateList;

    private String sourceId;

    private String userId;
}
