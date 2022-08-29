package datart.server.controller;

import datart.core.entity.JimuReportRewrite;
import datart.server.base.dto.ResponseData;
import datart.server.service.impl.JimuReportRewriteServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("积木报表")
@RestController
@RequestMapping("/jmreportrewrite")
public class JimuReportRewriteController extends BaseController {

    @Autowired
    private JimuReportRewriteServiceImpl jimuReportRewriteService;

    /**
     * 报表列表 type: datainfo 数据报表、 chartinfo 图形报表、 printinfo 打印设计 ；
     * template: 1 是、 0 否
     */
    @ApiOperation("报表列表 type: datainfo 数据报表、 chartinfo 图形报表、 printinfo 打印设计 ；" +
            "template: 1 是、 0 否")
    @GetMapping(value = "/getlist")
    public ResponseData<List<JimuReportRewrite>> getlist(JimuReportRewrite jimuReportRewrite) {

        return ResponseData.success(jimuReportRewriteService.getlist(jimuReportRewrite));
    }

    @ApiOperation("根据ID查询报表信息")
    @GetMapping("/selectById/{id}")
    public ResponseData<JimuReportRewrite> selectById(@PathVariable("id") String id) {

        return ResponseData.success(jimuReportRewriteService.selectById(id));
    }
}
