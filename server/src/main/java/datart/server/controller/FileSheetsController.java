package datart.server.controller;

import datart.core.entity.FileSheets;
import datart.core.entity.param.FileSheetsParam;
import datart.server.base.dto.ResponseData;
import datart.server.common.page.TableDataInfo;
import datart.server.enums.WhetherEnum;
import datart.server.service.IFileSheetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件工作簿Controller
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Api("文件工作簿")
@RestController
@RequestMapping("/file/sheets")
public class FileSheetsController extends BaseController {

    @Autowired
    private IFileSheetsService fileSheetsService;

    /**
     * 查询文件工作簿列表
     */
    @ApiOperation("查询文件工作簿列表 工作簿id Long sheetId，文件id Long fileId，工作簿名称 String sheetName，对应实体名称 String entityName，" +
            "顺序从0开始不允许重复 Integer orderNum，读取开始行 Integer startRow，读取开始列 Integer startCell，有效列数 Integer cellCount，" +
            "状态（0正常 1停用）String status，删除标志（0代表存在 1代表删除） String delFlag，分类ID数组 Long[] classIds，部门ID数组 Long[] depIds")
    @GetMapping("/list")
    public TableDataInfo list(FileSheetsParam fileSheets) {

        startPage();
        List<FileSheets> list = fileSheetsService.getSheetList(fileSheets);
        return getDataTable(list);
    }

    /**
     * 新增保存文件工作簿
     */
    @ApiOperation("新增保存文件工作簿")
    @PostMapping("/add")
    public ResponseData<Integer> addSave(@RequestBody FileSheets fileSheets) {
        return ResponseData.success(fileSheetsService.insertFileSheets(fileSheets));
    }

    /**
     * 修改保存文件工作簿
     */
    @ApiOperation("修改保存文件工作簿")
    @PostMapping("/edit")
    public ResponseData<Integer> editSave(@RequestBody FileSheets fileSheets) {
        return ResponseData.success(fileSheetsService.updateFileSheets(fileSheets));
    }

    /**
     * 删除文件工作簿
     */
    @ApiOperation("删除文件工作簿")
    @PostMapping("/remove")
    public ResponseData<Integer> remove(@RequestBody String ids) {
        return ResponseData.success(fileSheetsService.deleteFileSheetsBySheetIds(ids));
    }
}
