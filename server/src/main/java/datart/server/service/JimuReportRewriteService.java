package datart.server.service;

import datart.core.entity.JimuReportRewrite;

import java.util.List;

public interface JimuReportRewriteService  {

    JimuReportRewrite selectById(String id);

    List<JimuReportRewrite> getlist(JimuReportRewrite jimuReportRewrite);
}
