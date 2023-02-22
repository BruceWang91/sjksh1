package datart.server.controller;

import datart.core.entity.StaticManagement;
import datart.server.base.dto.ResponseData;
import datart.server.service.IStaticManagementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/static/management")
public class StaticManagementController extends BaseController {

    @Autowired
    private IStaticManagementService staticManagementService;

    @ApiOperation("静态资源管理列表")
    @GetMapping("/list")
    public ResponseData<List<StaticManagement>> list(StaticManagement staticManagement) {

        List<StaticManagement> list = staticManagementService.getList(staticManagement);
        return ResponseData.success(list);
    }

    @ApiOperation("静态资源管理获取详情")
    @GetMapping("/getById/{id}")
    public ResponseData<StaticManagement> getById(@PathVariable("id") Long id) {

        StaticManagement staticManagement = staticManagementService.getById(id);
        return ResponseData.success(staticManagement);
    }

    @ApiOperation("查询静态资源内容")
    @GetMapping("/getStaticManagementById/{id}")
    public ResponseData<StaticManagement> getStaticManagementById(@PathVariable("id") Long id) {

        StaticManagement staticManagement = staticManagementService.getStaticManagementById(id);
        return ResponseData.success(staticManagement);
    }

    @ApiOperation("新增静态资源管理")
    @PostMapping("/add")
    public ResponseData<StaticManagement> addSave(@RequestParam("moduleName") String moduleName,
                                                  @RequestParam(value = "orgId", required = false) String orgId,
                                                  @RequestParam(value = "mark", required = false) String mark,
                                                  @RequestParam(value = "jsonStr", required = false) String jsonStr,
                                                  @RequestParam(value = "link", required = false) String link,
                                                  @RequestParam("type") Integer type,
                                                  @RequestParam("parentId") Long parentId,
                                                  @RequestParam("isFolder") Integer isFolder,
                                                  @RequestParam("index") Integer index,
                                                  @RequestParam("needChange") Integer needChange,
                                                  @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        //needchange 传入1 将文件转换为pdf
        return ResponseData.success(staticManagementService.add(moduleName, orgId, mark, jsonStr, link, type, parentId, isFolder, index, needChange, file));
    }

    @ApiOperation("新增静态资源管理")
    @PostMapping("/add1")
    public ResponseData<StaticManagement> addSave1(@RequestBody StaticManagement staticManagement) {
        return ResponseData.success(staticManagementService.add1(staticManagement));
    }

    @ApiOperation("修改静态资源管理")
    @PostMapping("/edit")
    public ResponseData<StaticManagement> editSave(@RequestParam("id") Long id,
                                                   @RequestParam("moduleName") String moduleName,
                                                   @RequestParam(value = "mark", required = false) String mark,
                                                   @RequestParam(value = "jsonStr", required = false) String jsonStr,
                                                   @RequestParam(value = "link", required = false) String link,
                                                   @RequestParam("type") Integer type,
                                                   @RequestParam("parentId") Long parentId,
                                                   @RequestParam("isFolder") Integer isFolder,
                                                   @RequestParam("index") Integer index,
                                                   @RequestParam("needChange") Integer needChange,
                                                   @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        //needchange 传入1 将文件转换为pdf
        return ResponseData.success(staticManagementService.update(id, moduleName, mark, jsonStr, link, type, parentId, isFolder, index, needChange, file));
    }

    @ApiOperation("修改排序号")
    @PostMapping("/editIndex")
    public ResponseData<Integer> editIndex(@RequestParam("id") Long id, @RequestParam("index") Integer index) {

        return ResponseData.success(staticManagementService.editIndex(id, index));
    }

    @ApiOperation("修改静态资源管理")
    @PostMapping("/edit1")
    public ResponseData<StaticManagement> editSave1(@RequestBody StaticManagement staticManagement) {
        return ResponseData.success(staticManagementService.update1(staticManagement));
    }

    @ApiOperation("删除静态资源管理")
    @PostMapping("/remove")
    public ResponseData<Integer> remove(String ids) {
        return ResponseData.success(staticManagementService.delete(ids));
    }

    @ApiOperation("用标记代码查询静态资源内容")
    @GetMapping("/getStaticManagementByMark/{mark}")
    public ResponseData<StaticManagement> getStaticManagementByMark(@PathVariable("mark") String mark) {

        checkBlank(mark, "mark");
        StaticManagement staticManagement = staticManagementService.getStaticManagementByMark(mark);
        return ResponseData.success(staticManagement);
    }

    @ApiOperation("确认标记码是否可用")
    @GetMapping("/getCountByMark/{mark}")
    public ResponseData<Integer> getCountByMark(@PathVariable("mark") String mark){

        checkBlank(mark, "mark");
        return ResponseData.success(staticManagementService.getCountByMark(mark));
    }
}
