package datart.server.service.impl;

import datart.core.entity.VisitorStatistics;
import datart.core.mappers.VisitorStatisticsMapper;
import datart.server.common.StringUtils;
import datart.server.service.BaseService;
import datart.server.service.VisitorStatisticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorStatisticsImpl extends BaseService implements VisitorStatisticsService {

    @Autowired
    private VisitorStatisticsMapper visitorStatisticsMapper;

    @Override
    public List<VisitorStatistics> selectVisitorStatistics(VisitorStatistics visitorStatistics) {
        return visitorStatisticsMapper.selectVisitorStatisticsList(visitorStatistics);
    }

    @Override
    public VisitorStatistics addVisitorStatistics(VisitorStatistics visitorStatistics) {

        String moduleName = visitorStatistics.getModuleName();
        String type = visitorStatistics.getType();
        if (StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(type)){
            throw new RuntimeException("模块名称及类型不能为空！");
        }
        List<VisitorStatistics> visitorStatisticsList = visitorStatisticsMapper.selectVisitorStatisticsList(visitorStatistics);
        if (CollectionUtils.isEmpty(visitorStatisticsList)){

            visitorStatisticsMapper.insertVisitorStatistics(visitorStatistics);
            visitorStatistics.setNum(1L);
        }else {

            for (VisitorStatistics statistics : visitorStatisticsList) {

                Long num = statistics.getNum();
                statistics.setNum(++num);
                visitorStatisticsMapper.updateVisitorStatistics(statistics);
                visitorStatistics.setNum(num);
            }
        }
        return visitorStatistics;
    }
}
