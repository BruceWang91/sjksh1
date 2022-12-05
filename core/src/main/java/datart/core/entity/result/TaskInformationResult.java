package datart.core.entity.result;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInformationResult {

    private Long taskId;

    private String taskName;

    private Date startTime;

    private Date endTime;

    private Long fileId;

    private String fileName;

    private String userId;

    private String userName;

    private Long deptId;

    private String deptName;

    private Integer importDataCount;

    /**
     * 任务参与人数
     */
    private int totalNumOfPeople;

    /**
     * 任务完成人数
     */
    private int numOfPeopleCompleted;

    private String sourceId;
}
