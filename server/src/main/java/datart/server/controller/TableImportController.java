package datart.server.controller;

import datart.core.entity.TableImport;
import datart.core.entity.TableImportField;
import datart.core.entity.param.TableImportParam;
import datart.server.base.dto.ResponseData;
import datart.server.service.TableImportFieldService;
import datart.server.service.TableImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api("表数据导入")
@RestController
@RequestMapping("/table/import")
public class TableImportController extends BaseController {

    @Autowired
    private TableImportService tableImportService;
    @Autowired
    private TableImportFieldService tableImportFieldService;

    @ApiOperation("列表")
    @GetMapping("/tableImportList")
    public ResponseData<List<TableImport>> tableImportList(TableImport tableImport) {

        return ResponseData.success(tableImportService.tableImportList(tableImport));
    }

    @ApiOperation("字段列表")
    @GetMapping("/tableImportFieldList")
    public ResponseData<List<TableImportField>> tableImportFieldList(TableImportField tableImportField) {

        return ResponseData.success(tableImportFieldService.tableImportFieldList(tableImportField));
    }

    @ApiOperation("通过ID获取")
    @GetMapping("/selectTableImportById/{id}")
    public ResponseData<TableImport> selectTableImportById(@PathVariable("id") Long id) {

        return ResponseData.success(tableImportService.selectTableImportById(id));
    }

    @ApiOperation("通过字段ID获取字段")
    @GetMapping("/selectTableImportFieldById/{id}")
    public ResponseData<TableImportField> selectTableImportFieldById(@PathVariable("id") Long id) {

        return ResponseData.success(tableImportFieldService.selectTableImportFieldById(id));
    }

    @ApiOperation("添加导入表信息")
    @PostMapping("/addTableImport")
    public ResponseData<TableImport> addTableImport(@RequestBody TableImport tableImport) {

        return ResponseData.success(tableImportService.add(tableImport));
    }

    @ApiOperation("添加导入表字段")
    @PostMapping("/addTableImportField")
    public ResponseData<Integer> addTableImportField(@RequestBody TableImportField tableImportField) {

        return ResponseData.success(tableImportFieldService.add(tableImportField));
    }

    @ApiOperation("修改导入表信息")
    @PostMapping("/updateTableImport")
    public ResponseData<Integer> updateTableImport(@RequestBody TableImport tableImport) {

        return ResponseData.success(tableImportService.update(tableImport));
    }

    @ApiOperation("修改导入表字段")
    @PostMapping("/updateTableImportField")
    public ResponseData<Integer> updateTableImportField(@RequestBody TableImportField tableImportField) {

        return ResponseData.success(tableImportFieldService.update(tableImportField));
    }

    @ApiOperation("删除导入表信息")
    @PostMapping("/removeTableImport")
    public ResponseData<Integer> removeTableImport(String ids) {

        return ResponseData.success(tableImportService.delete(ids));
    }

    @ApiOperation("删除导入表字段")
    @PostMapping("/removeTableImportField")
    public ResponseData<Integer> removeTableImportField(String ids) {

        return ResponseData.success(tableImportFieldService.delete(ids));
    }

    @ApiOperation("导入数据")
    @PostMapping("/importData")
    public ResponseData<Integer> importData(@RequestBody TableImportParam param) {

        return ResponseData.success(tableImportService.importData(param));
    }

    @ApiOperation("查询已导入的内容")
    @GetMapping("/getImportData/{id}")
    public ResponseData<List<HashMap<String, Object>>> getImportData(@PathVariable("id") Long id) {

        return ResponseData.success(tableImportService.getImportData(id));
    }

    @ApiOperation("修改导入数据")
    @PostMapping("/updateImportData")
    public ResponseData<Integer> updateImportData(@RequestBody TableImportParam param) {

        return ResponseData.success(tableImportService.updateImportData(param));
    }

    @ApiOperation("删除导入数据")
    @PostMapping("/deleteImportData")
    public ResponseData<Integer> deleteImportData(@RequestBody TableImportParam importParam) {

        return ResponseData.success(tableImportService.deleteImportData(importParam));
    }
}
