package datart.server.service.impl;

import datart.core.mappers.CustomizeMapper;
import datart.server.config.datasource.DataSourceNames;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.service.ICustomizeService;
import datart.server.service.IToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 自定义接口 服务实现
 *
 * @author wangya
 */
@Service
public class CustomizeServiceImpl implements ICustomizeService {

    @Autowired
    private IToolsService toolsService;
    @Autowired
    private CustomizeMapper customizeMapper;

    @Override
    public List<HashMap<String, Object>> test(HashMap<String, Object> hashMap) {

        DynamicDataSource.setDataSource(DataSourceNames.SLAVE);
        List<HashMap<String, Object>> list = customizeMapper.test1(new HashMap<>());
        DynamicDataSource.setDataSource(DataSourceNames.MASTER);
        List<HashMap<String, Object>> list1 = customizeMapper.test2(new HashMap<>());
        list.addAll(list1);
        //清除数据源配置
        DynamicDataSource.clearDataSource();
        list.add(hashMap);
        return list;
    }
}
