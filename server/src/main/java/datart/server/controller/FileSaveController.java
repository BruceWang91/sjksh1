package datart.server.controller;

import datart.core.entity.FileSave;
import datart.server.base.dto.ResponseData;
import datart.server.common.page.TableDataInfo;
import datart.server.service.IFileSaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 文件保存Controller
 *
 * @author wangya
 * @date 2022-06-17
 */
@Api("文件保存")
@RestController
@RequestMapping("/system/save")
public class FileSaveController extends BaseController {
    @Autowired
    private IFileSaveService fileSaveService;

    @Value("${spring.upload}")
    private String localurl;

    /**
     * 查询文件保存列表
     */
    @ApiOperation("查询文件保存列表 文档id Long id，文件名称 String fileName，存入后名称 String newName，PDF文件名称 String pdfName，URL String url，PDFURL String pdfurl，" +
            "显示顺序 Integer orderNum，文件状态（0正常 1停用）String status，删除标志（0代表存在 2代表删除） String delFlag，创建者 String createBy，创建时间 Date createTime，" +
            "更新者 String updateBy，更新时间 Date updateTime，备注 String remark")
    @GetMapping("/list")
    public TableDataInfo list(FileSave fileSave) {

        startPage();
        List<FileSave> list = fileSaveService.selectFileSaveList(fileSave);
        return getDataTable(list);
    }

    /**
     * 获取文件保存详细信息
     */
    @GetMapping(value = "/{id}")
    public ResponseData<FileSave> getInfo(@PathVariable("id") Long id) {
        return ResponseData.success(fileSaveService.selectFileSaveById(id));
    }

    /**
     * 新增文件上传文件夹
     */
    @ApiOperation("新增文件上传文件夹")
    @PostMapping("/addFolder")
    public ResponseData<FileSave> add(@RequestBody FileSave fileSave) {

        fileSave.setIsFolder(1);
        fileSave.setCreateBy(getCurrentUser().getId());
        fileSave.setCreateTime(new Date());
        return ResponseData.success(fileSaveService.insertFileSaveFolder(fileSave));
    }

    /**
     * 修改文件上传文件夹
     */
    @ApiOperation("修改文件上传文件夹")
    @PutMapping("/editFolder")
    public ResponseData<Integer> edit(@RequestBody FileSave fileSave) {

        fileSave.setUpdateBy(getCurrentUser().getId());
        fileSave.setUpdateTime(new Date());
        return ResponseData.success(fileSaveService.updateFileSave(fileSave));
    }

    /**
     * 删除文件保存
     */
    @ApiOperation("删除文件保存")
    @DeleteMapping("/{ids}")
    public ResponseData<Integer> remove(@PathVariable Long[] ids) {

        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            FileSave fileSave = fileSaveService.selectFileSaveById(id);
            String url = fileSave.getUrl();
            url = url.replace("/upload/", localurl);
            String pdfurl = fileSave.getPdfurl();
            pdfurl = pdfurl.replace("/upload/", localurl);
            File file = new File(url);
            file.delete();
            File pdffile = new File(pdfurl);
            pdffile.delete();
        }
        return ResponseData.success(fileSaveService.deleteFileSaveByIds(ids));
    }
}
