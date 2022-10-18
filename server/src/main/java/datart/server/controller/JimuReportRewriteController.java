package datart.server.controller;

import com.alibaba.fastjson.JSONObject;
import datart.core.entity.JimuReportRewrite;
import datart.server.base.dto.ResponseData;
import datart.server.common.page.TableDataInfo;
import datart.server.service.impl.JimuReportRewriteServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.modules.jmreport.common.annotation.JimuLoginRequired;
import org.jeecg.modules.jmreport.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 积木报表
 *
 * @author wangya
 */
@Api("积木报表")
@RestController
@RequestMapping("/jmreportrewrite")
public class JimuReportRewriteController extends BaseController {
    private static final Logger a = LoggerFactory.getLogger(JimuReportRewriteController.class);
    @Autowired
    private JimuReportRewriteServiceImpl jimuReportRewriteService;

    /**
     * 报表列表 type: datainfo 数据报表、 chartinfo 图形报表、 printinfo 打印设计 ；
     * template: 1 是、 0 否
     */
    @ApiOperation("报表列表 type: datainfo 数据报表、 chartinfo 图形报表、 printinfo 打印设计 ；" +
            "template: 1 是、 0 否")
    @GetMapping(value = "/getlist")
    public TableDataInfo getlist(JimuReportRewrite jimuReportRewrite) {

        startPage();
        List<JimuReportRewrite> list = jimuReportRewriteService.getlist(jimuReportRewrite);
        return getDataTable(list);
    }

    @ApiOperation("根据ID查询报表信息")
    @GetMapping("/selectById/{id}")
    public ResponseData<JimuReportRewrite> selectById(@PathVariable("id") String id) {

        return ResponseData.success(jimuReportRewriteService.selectById(id));
    }

    @ApiOperation("积木报表保存接口")
    @PostMapping({"/save"})
    @JimuLoginRequired
    public Result<?> b(HttpServletRequest var1, @RequestBody JSONObject var2) {
        try {
            JimuReportRewrite var3 = this.jimuReportRewriteService.saveReport(var2, var1);
            return Result.OK(var3);
        } catch (Exception var4) {
            a.error(var4.getMessage(), var4);
            return Result.error("保存失败！");
        }
    }

    @ApiOperation("修改parentId")
    @PostMapping("/updateParentId")
    public ResponseData<Integer> updateParentId(@RequestBody JimuReportRewrite jimuReportRewrite) {

        return ResponseData.success(jimuReportRewriteService.updateParentId(jimuReportRewrite));
    }

    @ApiOperation("删除文件夹")
    @DeleteMapping("/deleteFolder/{id}")
    public ResponseData<Integer> deleteFolder(@PathVariable String id) {

        return ResponseData.success(jimuReportRewriteService.deleteFolder(id));
    }
}
