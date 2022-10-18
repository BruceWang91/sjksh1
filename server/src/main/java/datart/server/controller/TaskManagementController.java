package datart.server.controller;

import datart.core.entity.TaskManagement;
import datart.core.entity.TaskTemplate;
import datart.core.entity.TaskUsers;
import datart.core.entity.param.TaskInformationParam;
import datart.core.entity.result.TaskInformationResult;
import datart.server.base.dto.ResponseData;
import datart.server.common.page.TableDataInfo;
import datart.server.service.TaskManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件任务管理Controller
 *
 * @author wangya
 * @date 2022-09-27
 */
@Api("文件任务管理")
@RestController
@RequestMapping("/task/management")
public class TaskManagementController extends BaseController {

    @Autowired
    private TaskManagementService taskManagementService;

    @ApiOperation("获取任务列表 任务管理ID taskId;  任务名称 taskName; 状态（0开启 1停用） status; 任务开始时间" +
            " startTime;  任务结束时间 endTime;  是否文件夹 1是 0否 isFolder; 上级ID parentId; 创建者 createBy;" +
            "创建时间 createTime; 更新者 updateBy; 更新时间 updateTime; 备注 remark; 任务参与人数 totalNumOfPeople;" +
            "任务完成人数 numOfPeopleCompleted")
    @GetMapping("/getList")
    public TableDataInfo getList(TaskManagement taskManagement) {

        startPage();
        List<TaskManagement> list = taskManagementService.getList(taskManagement);
        return getDataTable(list);
    }

    @ApiOperation("添加任务管理")
    @PostMapping("/addManagement")
    public ResponseData<TaskManagement> addManagement(@RequestBody TaskManagement taskManagement) {

        return ResponseData.success(taskManagementService.addManagement(taskManagement));
    }

    @ApiOperation("修改任务管理")
    @PostMapping("/editManagement")
    public ResponseData<TaskManagement> editManagement(@RequestBody TaskManagement taskManagement) {

        return ResponseData.success(taskManagementService.editManagement(taskManagement));
    }

    @ApiOperation("删除任务管理")
    @DeleteMapping("/deleteManagement/{taskId}")
    public ResponseData<Integer> deleteManagement(@PathVariable("taskId") Long taskId) {

        return ResponseData.success(taskManagementService.deleteManagement(taskId));
    }

    @ApiOperation("添加任务人员关联")
    @PostMapping("/addTaskUsers/{taskId}")
    public ResponseData<Integer> addTaskUsers(@PathVariable("taskId") Long taskId, @RequestBody List<TaskUsers> users) {

        return ResponseData.success(taskManagementService.addTaskUsers(taskId, users));
    }

    @ApiOperation("添加任务模板文件关联")
    @PostMapping("/addTaskTemplate/{taskId}")
    public ResponseData<Integer> addTaskTemplate(@PathVariable("taskId") Long taskId, @RequestBody List<TaskTemplate> template) {

        return ResponseData.success(taskManagementService.addTaskTemplate(taskId, template));
    }

    @ApiOperation("删除任务人员关联")
    @DeleteMapping("/deleteTaskUsers/{taskId}")
    public ResponseData<Integer> deleteTaskUsers(@PathVariable("taskId") Long taskId) {

        return ResponseData.success(taskManagementService.deleteTaskUsers(taskId));
    }

    @ApiOperation("删除任务模板文件关联")
    @DeleteMapping("/deleteTaskTemplate/{taskId}")
    public ResponseData<Integer> deleteTaskTemplate(@PathVariable("taskId") Long taskId) {

        return ResponseData.success(taskManagementService.deleteTaskTemplate(taskId));
    }

    @ApiOperation("获取任务详情")
    @GetMapping("/getTeskResult")
    public TableDataInfo getTeskResult(TaskInformationParam param) {

        startPage();
        List<TaskInformationResult> list = taskManagementService.getTeskResult(param);
        return getDataTable(list);
    }
}
