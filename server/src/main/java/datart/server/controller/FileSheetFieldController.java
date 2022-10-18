package datart.server.controller;

import datart.core.entity.FileSheetField;
import datart.server.base.dto.ResponseData;
import datart.server.service.IFileSheetFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字段对应Controller
 *
 * @author wangya
 * @date 2022-05-27
 */
@Api("字段对应")
@RestController
@RequestMapping("/file/field")
public class FileSheetFieldController extends BaseController {

    @Autowired
    private IFileSheetFieldService fileSheetFieldService;

    /**
     * 查询字段对应列表
     */
    @ApiOperation("查询字段对应列表 字段id Long fieldId，文件id Long fileId，工作簿id Long sheetId，对应列数 从0开始不允许重复 Integer cellNum，" +
            "对应表头名称 String cellName，所在行数  用于表头0开始 Integer rowNum，对应实体表字段 String entityField，" +
            "状态（0不参与数据导入 1参与数据导入使用）String status，删除标志（0代表存在 1代表删除） String delFlag")
    @PostMapping("/list")
    public ResponseData<List<FileSheetField>> list(FileSheetField fileSheetField) {

        List<FileSheetField> list = fileSheetFieldService.selectFileSheetFieldList(fileSheetField);
        return ResponseData.success(list);
    }

    /**
     * 新增保存字段对应
     */
    @ApiOperation("新增保存字段对应")
    @PostMapping("/add")
    public ResponseData<Integer> addSave(FileSheetField fileSheetField) {
        return ResponseData.success(fileSheetFieldService.insertFileSheetField(fileSheetField));
    }

    /**
     * 修改保存字段对应
     */
    @ApiOperation("修改保存字段对应")
    @PostMapping("/edit")
    public ResponseData<Integer> editSave(FileSheetField fileSheetField) {
        return ResponseData.success(fileSheetFieldService.updateFileSheetField(fileSheetField));
    }

    /**
     * 删除字段对应
     */
    @ApiOperation("删除字段对应")
    @PostMapping("/remove")
    public ResponseData<Integer> remove(String ids) {
        return ResponseData.success(fileSheetFieldService.deleteFileSheetFieldByFieldIds(ids));
    }
}
