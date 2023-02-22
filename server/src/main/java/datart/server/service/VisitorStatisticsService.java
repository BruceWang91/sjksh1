package datart.server.service;

import datart.core.entity.VisitorStatistics;

import java.util.List;

public interface VisitorStatisticsService {


    List<VisitorStatistics> selectVisitorStatistics(VisitorStatistics visitorStatistics);

    VisitorStatistics addVisitorStatistics(VisitorStatistics visitorStatistics);
}
