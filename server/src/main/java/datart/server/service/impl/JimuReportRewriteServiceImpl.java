package datart.server.service.impl;

import datart.core.entity.JimuReportRewrite;
import datart.core.mappers.JimuReportMapper;
import datart.server.service.JimuReportRewriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JimuReportRewriteServiceImpl implements JimuReportRewriteService {

    @Autowired
    private JimuReportMapper jimuReportMapper;

    @Override
    public JimuReportRewrite selectById(String id){

        return jimuReportMapper.selectJimuReportRewriteById(id);
    }

    @Override
    public List<JimuReportRewrite> getlist(JimuReportRewrite jimuReportRewrite){

        return jimuReportMapper.selectJimuReportRewriteList(jimuReportRewrite);
    }
}
