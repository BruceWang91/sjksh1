package datart.server.controller;

import datart.core.entity.FileClass;
import datart.server.base.dto.ResponseData;
import datart.server.common.page.TableDataInfo;
import datart.server.service.IFileClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件分类Controller
 *
 * @author wangya
 * @date 2022-06-17
 */
@Api("文件分类")
@RestController
@RequestMapping("/file/class")
public class FileClassController extends BaseController {
    @Autowired
    private IFileClassService fileClassService;

    /**
     * 查询文件分类列表
     */
    @ApiOperation("查询文件分类列表 id Long id，分类名称 String name，上级分类id Long parentId，显示顺序 Integer orderNum，" +
            "状态（0正常 1停用） String status，删除标志（0代表存在 1代表删除） String delFlag，下级分类 List<FileClass> lowClasses")
    @GetMapping("/list")
    public TableDataInfo list(FileClass fileClass) {

        startPage();
        List<FileClass> list = fileClassService.getHierarchyClassification(fileClass);
        return getDataTable(list);
    }

    /**
     * 获取文件分类详细信息
     */
    @ApiOperation("获取文件分类详细信息")
    @GetMapping(value = "/getInfo/{id}")
    public ResponseData<FileClass> getInfo(@PathVariable("id") Long id) {
        return ResponseData.success(fileClassService.selectFileClassById(id));
    }

    /**
     * 新增文件分类
     */
    @ApiOperation("新增文件分类")
    @PostMapping("/add")
    public ResponseData<Integer> add(@RequestBody FileClass fileClass) {
        return ResponseData.success((Integer) fileClassService.insertFileClass(fileClass));
    }

    /**
     * 修改文件分类
     */
    @ApiOperation("修改文件分类")
    @PutMapping("/edit")
    public ResponseData<Integer> edit(@RequestBody FileClass fileClass) {
        return ResponseData.success((Integer) fileClassService.updateFileClass(fileClass));
    }

    /**
     * 删除文件分类
     */
    @ApiOperation("删除文件分类")
    @DeleteMapping("/remove/{ids}")
    public ResponseData<Integer> remove(@PathVariable Long[] ids) {

        return ResponseData.success((Integer) fileClassService.deleteFileClassByIds(ids));
    }
}
