package datart.server.service;

import com.alibaba.fastjson.JSONObject;
import datart.core.entity.JimuReportRewrite;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JimuReportRewriteService {

    JimuReportRewrite selectById(String id);

    List<JimuReportRewrite> getlist(JimuReportRewrite jimuReportRewrite);

    void requirePermission(JimuReportRewrite file, int permission);

    JimuReportRewrite saveReport(JSONObject var2, HttpServletRequest var1);

    int updateParentId(JimuReportRewrite jimuReportRewrite);

    int deleteFolder(String id);
}
