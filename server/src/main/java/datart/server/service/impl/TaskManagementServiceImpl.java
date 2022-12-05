package datart.server.service.impl;

import datart.core.entity.*;
import datart.core.entity.param.TaskInformationParam;
import datart.core.entity.result.TaskInformationResult;
import datart.core.mappers.FileMainMapper;
import datart.core.mappers.TaskManagementMapper;
import datart.core.mappers.TaskTemplateMapper;
import datart.core.mappers.TaskUsersMapper;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.service.BaseService;
import datart.server.service.IToolsService;
import datart.server.service.TaskManagementService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 任务管理Service业务层处理
 *
 * @author wangya
 * @date 2022-09-27
 */
@Service
public class TaskManagementServiceImpl extends BaseService implements TaskManagementService {
    @Autowired
    private TaskManagementMapper managementMapper;
    @Autowired
    private TaskUsersMapper taskUsersMapper;
    @Autowired
    private TaskTemplateMapper taskTemplateMapper;
    @Autowired
    private FileSheetsServiceImpl fileSheetsService;
    @Autowired
    private FileMainMapper fileMainMapper;
    @Autowired
    private IToolsService toolsService;

    @Override
    public List<TaskManagement> getList(TaskManagement taskManagement) {


        List<TaskManagement> list = managementMapper.selectTaskManagementList(taskManagement);
        if (!CollectionUtils.isEmpty(list)) {

            for (TaskManagement management : list) {

                int numOfPeopleCompleted = 0;
                Long taskId = management.getTaskId();
                List<TaskUsers> taskUsersList = taskUsersMapper.selectTaskUsersList(new TaskUsers() {
                    {
                        setTaskId(taskId);
                    }
                });
                if (!CollectionUtils.isEmpty(taskUsersList)) {

                    management.setTotalNumOfPeople(taskUsersList.size());
                    management.setTaskUsersList(taskUsersList);
                }
                List<TaskTemplate> templateList = taskTemplateMapper.selectTaskTemplateList(new TaskTemplate() {
                    {
                        setTaskId(taskId);
                    }
                });
                if (!CollectionUtils.isEmpty(templateList)) {

                    management.setTaskTemplateList(templateList);
                }
                if (!CollectionUtils.isEmpty(taskUsersList) && !CollectionUtils.isEmpty(templateList)) {

                    for (TaskUsers user : taskUsersList) {

                        int count = 0;
                        for (TaskTemplate taskTemplate : templateList) {

                            Long fileId = taskTemplate.getFileId();
                            String userId = user.getUserId();
                            if (fileId != null && StringUtils.isNotBlank(userId)) {
                                FileMain fileMain = fileMainMapper.selectFileMainByFileId(fileId);
                                List<FileSheets> sheets = fileSheetsService.selectFileSheetsList(new FileSheets() {
                                    {
                                        setFileId(fileId);
                                    }
                                });
                                if (!CollectionUtils.isEmpty(sheets)) {
                                    count += countImportDatas(fileMain.getSourceId(), sheets, userId, management.getStartTime(), management.getEndTime());
                                }
                            }
                        }
                        if (count > 0) {

                            numOfPeopleCompleted++;
                            user.setCompletionStatus(true);
                        } else {
                            user.setCompletionStatus(false);
                        }
                    }
                }
                management.setNumOfPeopleCompleted(numOfPeopleCompleted);
            }
        }
        return list;
    }

    @Override
    public TaskManagement addManagement(TaskManagement taskManagement) {

        taskManagement.setCreateBy(getCurrentUser().getId());
        taskManagement.setCreateTime(new Date());
        managementMapper.insertTaskManagement(taskManagement);
        return taskManagement;
    }

    @Override
    public TaskManagement editManagement(TaskManagement taskManagement) {

        taskManagement.setUpdateBy(getCurrentUser().getId());
        taskManagement.setUpdateTime(new Date());
        managementMapper.updateTaskManagement(taskManagement);
        return taskManagement;
    }

    @Override
    public int deleteManagement(Long taskId) {

        int count = 0;
        count += managementMapper.deleteTaskManagementByTaskId(taskId);
        count += taskUsersMapper.deleteTaskUsersByTaskId(taskId);
        count += taskTemplateMapper.deleteTaskTemplateByTaskId(taskId);
        return count;
    }

    @Override
    public int addTaskUsers(Long taskId, List<TaskUsers> users) {

        taskUsersMapper.deleteTaskUsersByTaskId(taskId);
        int count = 0;
        for (TaskUsers user : users) {

            user.setCreateBy(getCurrentUser().getId());
            user.setCreateTime(new Date());
            count += taskUsersMapper.insertTaskUsers(user);
        }
        return count;
    }

    @Override
    public int addTaskTemplate(Long taskId, List<TaskTemplate> templates) {

        taskTemplateMapper.deleteTaskTemplateByTaskId(taskId);
        int count = 0;
        for (TaskTemplate template : templates) {

            template.setCreateBy(getCurrentUser().getId());
            template.setCreateTime(new Date());
            count += taskTemplateMapper.insertTaskTemplate(template);
        }
        return count;
    }

    @Override
    public int deleteTaskUsers(Long taskId) {

        return taskUsersMapper.deleteTaskUsersByTaskId(taskId);
    }

    @Override
    public int deleteTaskTemplate(Long taskId) {

        return taskTemplateMapper.deleteTaskTemplateByTaskId(taskId);
    }

    @Override
    public List<TaskInformationResult> getTeskResult(TaskInformationParam param) {

        List<TaskInformationResult> taskInformationResults = taskTemplateMapper.getTaskResult(param);
        for (TaskInformationResult taskInformationResult : taskInformationResults) {

            Long fileId = taskInformationResult.getFileId();
            String userId = taskInformationResult.getUserId();
            TaskManagement management = managementMapper.selectTaskManagementByTaskId(taskInformationResult.getTaskId());
            if (null != management) {

                int numOfPeopleCompleted = 0;
                Long taskId = management.getTaskId();
                List<TaskUsers> taskUsersList = taskUsersMapper.selectTaskUsersList(new TaskUsers() {
                    {
                        setTaskId(taskId);
                    }
                });
                if (!CollectionUtils.isEmpty(taskUsersList)) {

                    management.setTotalNumOfPeople(taskUsersList.size());
                    management.setTaskUsersList(taskUsersList);
                }
                List<TaskTemplate> templateList = taskTemplateMapper.selectTaskTemplateList(new TaskTemplate() {
                    {
                        setTaskId(taskId);
                    }
                });
                if (!CollectionUtils.isEmpty(templateList)) {

                    management.setTaskTemplateList(templateList);
                }
                if (!CollectionUtils.isEmpty(taskUsersList) && !CollectionUtils.isEmpty(templateList)) {

                    for (TaskUsers user : taskUsersList) {

                        int count = 0;
                        for (TaskTemplate taskTemplate : templateList) {

                            Long fId = taskTemplate.getFileId();
                            String uId = user.getUserId();
                            if (fId != null && StringUtils.isNotBlank(uId)) {
                                List<FileSheets> sheets = fileSheetsService.selectFileSheetsList(new FileSheets() {
                                    {
                                        setFileId(fId);
                                    }
                                });
                                if (!CollectionUtils.isEmpty(sheets)) {
                                    count += countImportDatas(taskInformationResult.getSourceId(), sheets, uId, management.getStartTime(), management.getEndTime());
                                }
                            }
                        }
                        if (count > 0) {

                            numOfPeopleCompleted++;
                        }
                    }
                }
                management.setNumOfPeopleCompleted(numOfPeopleCompleted);
            }

            int count = 0;
            if (fileId != null && StringUtils.isNotBlank(userId)) {
                List<FileSheets> sheets = fileSheetsService.selectFileSheetsList(new FileSheets() {
                    {
                        setFileId(fileId);
                    }
                });
                if (!CollectionUtils.isEmpty(sheets)) {
                    count += countImportDatas(taskInformationResult.getSourceId(), sheets, userId, taskInformationResult.getStartTime(), taskInformationResult.getEndTime());
                }
            }
            taskInformationResult.setImportDataCount(count);
            taskInformationResult.setTotalNumOfPeople(management.getTotalNumOfPeople());
            taskInformationResult.setNumOfPeopleCompleted(management.getNumOfPeopleCompleted());
        }
        return taskInformationResults;
    }

    public int countImportDatas(String sourceId, List<FileSheets> sheets, String userId, Date startTime, Date endTime) {

        toolsService.changeConnection(sourceId);
        int count = 0;
        for (FileSheets fileSheet : sheets) {

            String tableName = fileSheet.getEntityName();
            HashMap<String, Object> map = new HashMap<>();
            map.put("tableName", tableName);
            map.put("userId", userId);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            count += taskTemplateMapper.countImportDatas(map);
        }
        DynamicDataSource.clear();
        return count;
    }
}
