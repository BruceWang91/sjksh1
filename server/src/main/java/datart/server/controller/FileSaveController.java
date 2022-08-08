package datart.server.controller;

import datart.core.common.FileUtils;
import datart.core.entity.FileSave;
import datart.server.base.dto.ResponseData;
import datart.server.service.IFileSaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * 文件保存Controller
 *
 * @author ruoyi
 * @date 2022-06-17
 */
@Api("文件保存")
@RestController
@RequestMapping("/system/save")
public class FileSaveController extends BaseController {
    @Autowired
    private IFileSaveService fileSaveService;

    /**
     * 查询文件保存列表
     */
    @ApiOperation("查询文件保存列表 文档id Long id，文件名称 String fileName，存入后名称 String newName，PDF文件名称 String pdfName，URL String url，PDFURL String pdfurl，" +
            "显示顺序 Integer orderNum，文件状态（0正常 1停用）String status，删除标志（0代表存在 2代表删除） String delFlag，创建者 String createBy，创建时间 Date createTime，" +
            "更新者 String updateBy，更新时间 Date updateTime，备注 String remark")
//    @PreAuthorize("@ss.hasPermi('system:save:list')")
    @GetMapping("/list")
    public ResponseData<List<FileSave>> list(FileSave fileSave) {

        List<FileSave> list = fileSaveService.selectFileSaveList(fileSave);
        return ResponseData.success(list);
    }

    /**
     * 获取文件保存详细信息
     */
    @GetMapping(value = "/{id}")
    public ResponseData<FileSave> getInfo(@PathVariable("id") Long id) {
        return ResponseData.success(fileSaveService.selectFileSaveById(id));
    }

    /**
     * 新增文件保存
     */
//    @Log(title = "文件保存", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseData<Integer> add(@RequestBody FileSave fileSave) {

        return ResponseData.success(fileSaveService.insertFileSave(fileSave));
    }

    /**
     * 修改文件保存
     */
//    @Log(title = "文件保存", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseData<Integer> edit(@RequestBody FileSave fileSave) {
        return ResponseData.success(fileSaveService.updateFileSave(fileSave));
    }

    /**
     * 删除文件保存
     */
    @ApiOperation("删除文件保存")
//    @Log(title = "文件保存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseData<Integer> remove(@PathVariable Long[] ids) {
//        String filepath = WebConfig.getUploadPath();
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            FileSave fileSave = fileSaveService.selectFileSaveById(id);
            String url = FileUtils.withBasePath(fileSave.getUrl());
            String pdfurl = FileUtils.withBasePath(fileSave.getPdfurl());
            File file = new File(url);
            file.delete();
            File pdffile = new File(pdfurl);
            pdffile.delete();
        }
        return ResponseData.success(fileSaveService.deleteFileSaveByIds(ids));
    }
}
