package datart.server.service;

import datart.core.entity.TaskManagement;
import datart.core.entity.TaskTemplate;
import datart.core.entity.TaskUsers;
import datart.core.entity.param.TaskInformationParam;
import datart.core.entity.result.TaskInformationResult;

import java.util.List;

public interface TaskManagementService {

    List<TaskManagement> getList(TaskManagement taskManagement);

    TaskManagement addManagement(TaskManagement taskManagement);

    TaskManagement editManagement(TaskManagement taskManagement);

    int deleteManagement(Long taskId);

    int addTaskUsers(Long taskId, List<TaskUsers> users);

    int addTaskTemplate(Long taskId, List<TaskTemplate> template);

    int deleteTaskUsers(Long taskId);

    int deleteTaskTemplate(Long taskId);

    List<TaskInformationResult> getTeskResult(TaskInformationParam param);
}
