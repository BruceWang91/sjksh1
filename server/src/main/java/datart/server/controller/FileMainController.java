package datart.server.controller;

import com.alibaba.fastjson2.JSONArray;
import datart.core.entity.FileMain;
import datart.core.entity.param.FileMainParam;
import datart.core.entity.result.FileMainResult;
import datart.core.entity.result.FileSheetsResult;
import datart.server.base.dto.ResponseData;
import datart.server.service.IFileMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 文件管理Controller
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Api("文件管理")
@RestController
@RequestMapping("/file/filemain")
public class FileMainController extends BaseController {

    @Autowired
    private IFileMainService fileMainService;

    /**
     * 查询文件管理列表
     */
    @ApiOperation("查询文件管理列表 文件id Long fileId，文件名称 String fileName，显示顺序 Integer orderNum，文件状态（0正常 1停用） String status，" +
            "删除标志（0代表存在 2代表删除） String delFlag，分类id Long classId")
    @GetMapping("/list")
    public ResponseData<List<FileMain>> list(FileMain fileMain) {

        List<FileMain> list = fileMainService.getList(fileMain);
        return ResponseData.success(list);
    }

    /**
     * 新增保存文件管理
     */
    @ApiOperation("新增保存文件管理")
    @PostMapping("/add")
    public ResponseData<Integer> addSave(@RequestBody FileMain fileMain) {
        return ResponseData.success(fileMainService.insertFileMain(fileMain));
    }

    /**
     * 修改保存文件管理
     */
    @ApiOperation("修改保存文件管理")
    @PostMapping("/edit")
    public ResponseData<Integer> editSave(@RequestBody FileMain fileMain) {
        return ResponseData.success(fileMainService.updateFileMain(fileMain));
    }

    /**
     * 删除文件管理
     */
    @ApiOperation("删除文件管理")
    @PostMapping("/remove")
    public ResponseData<Integer> remove(String ids) {
        return ResponseData.success(fileMainService.deleteFileMainByFileIds(ids));
    }

    /**
     * 导入表样配置
     *
     * @param fileMainParam
     * @return
     */
    @ApiOperation("导入表样配置")
    @PostMapping("/batchUpdateForFileId")
    public ResponseData<Integer> batchUpdateForFileId(@RequestBody FileMainParam fileMainParam) {

        return ResponseData.success(fileMainService.batchUpdateForFileId(fileMainParam));
    }

    /**
     * 查询文件内容
     *
     * @param fileId
     * @return
     */
    @ApiOperation("查询文件内容")
    @GetMapping("/selectForFileId/{fileId}")
    public ResponseData<FileMainResult> selectForFileId(@PathVariable("fileId") Long fileId) {

        return ResponseData.success(fileMainService.selectForFileId(fileId));
    }

    /**
     * 查询工作簿内容
     *
     * @param sheetId
     * @return
     */
    @ApiOperation("查询工作簿内容")
    @GetMapping("/selectForSheetId/{sheetId}")
    public ResponseData<FileSheetsResult> selectForSheetId(@PathVariable("sheetId") Long sheetId) {

        return ResponseData.success(fileMainService.selectForSheetId(sheetId));
    }

    /**
     * 查询已导入工作簿的内容
     *
     * @param sheetId
     * @return
     */
    @ApiOperation("查询已导入工作簿的内容")
    @GetMapping("/getSheetData/{sheetId}")
    public ResponseData<JSONArray> getSheetData(@PathVariable("sheetId") Long sheetId) {

        return ResponseData.success(fileMainService.getSheetData(sheetId));
    }

    @ApiOperation("查询已导入工作簿的内容")
    @GetMapping("/getbiaoname/{biaoname}")
    public ResponseData<List<HashMap<String, Object>>> getbiaoname(@PathVariable("biaoname")String biaoname){

        return ResponseData.success(fileMainService.selectByBiname(new HashMap<String,Object>(){{put("biname",biaoname);}}));
    }
}