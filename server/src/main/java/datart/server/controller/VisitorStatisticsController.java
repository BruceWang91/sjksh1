package datart.server.controller;

import datart.core.entity.VisitorStatistics;
import datart.server.base.dto.ResponseData;
import datart.server.service.VisitorStatisticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vistioir/statistics")
public class VisitorStatisticsController extends BaseController {

    @Autowired
    private VisitorStatisticsService visitorStatisticsService;

    @ApiOperation("查询访客统计数量")
    @GetMapping("/selectVisitorStatistics")
    public ResponseData<List<VisitorStatistics>> selectVisitorStatistics(VisitorStatistics visitorStatistics) {

        List<VisitorStatistics> visitorStatisticsList = visitorStatisticsService.selectVisitorStatistics(visitorStatistics);
        return ResponseData.success(visitorStatisticsList);
    }

    @ApiOperation("新增访客记录")
    @PostMapping("/addVisitorStatistics")
    public ResponseData<VisitorStatistics> addVisitorStatistics(@RequestBody VisitorStatistics visitorStatistics) {

        return ResponseData.success(visitorStatisticsService.addVisitorStatistics(visitorStatistics));
    }
}
