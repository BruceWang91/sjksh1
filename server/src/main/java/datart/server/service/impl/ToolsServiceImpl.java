package datart.server.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import datart.core.base.consts.Const;
import datart.core.base.exception.BaseException;
import datart.core.base.exception.Exceptions;
import datart.core.data.provider.DataProviderSource;
import datart.core.entity.Department;
import datart.core.entity.Source;
import datart.core.mappers.ext.DepartmentMapperExt;
import datart.core.mappers.ext.SourceMapperExt;
import datart.security.util.AESUtil;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.service.IToolsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToolsServiceImpl implements IToolsService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DepartmentMapperExt deptMapper;
    @Autowired
    private SourceMapperExt sourceMapper;

    @Override
    public JSONArray mapListToJsonArray(List<HashMap<String, Object>> hashMapList) {

        JSONArray jsonArray = new JSONArray();
        if (!CollectionUtils.isEmpty(hashMapList)) {
            for (Map<String, Object> hashMap : hashMapList) {
                JSONObject jsonObject = new JSONObject(hashMap);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    @Override
    public DataProviderSource parseDataProviderConfig(Source source) {
        DataProviderSource providerSource = new DataProviderSource();
        try {
            providerSource.setSourceId(source.getId());
            providerSource.setType(source.getType());
            providerSource.setName(source.getName());
            Map<String, Object> properties = new HashMap<>();
            if (StringUtils.isNotBlank(source.getConfig())) {
                properties = objectMapper.readValue(source.getConfig(), HashMap.class);
            }
            // decrypt values
            for (String key : properties.keySet()) {
                Object val = properties.get(key);
                if (val instanceof String) {
                    String dq = decryptValue(val.toString());
                    properties.put(key, dq);
                }
            }
            providerSource.setProperties(properties);
        } catch (Exception e) {
            Exceptions.tr(BaseException.class, "message.provider.config.error");
        }
        return providerSource;
    }

    @Override
    public String decryptValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        if (!value.startsWith(Const.ENCRYPT_FLAG)) {
            return value;
        }
        try {
            return AESUtil.decrypt(value.replaceFirst(Const.ENCRYPT_FLAG, ""));
        } catch (Exception e) {
            return value;
        }
    }

    @Override
    public Department getCompany(Long deptId) {

        Department department = deptMapper.selectDeptById(deptId);
        if (null != department && department.getType() != 2) {

            Department dept = getCompany(department.getParentId());
            return dept;
        }
        if (null == department) {
            return null;
        }
        return department;
    }

    @Override
    public void changeConnection(String sourceId) {

        Source source = sourceMapper.selectByPrimaryKey(sourceId);
        if (null == source) {
            throw new RuntimeException("数据源配置不存在");
        }
        DataProviderSource providerSource = this.parseDataProviderConfig(source);
        Map<String, Object> properties = providerSource.getProperties();
        if (!CollectionUtils.isEmpty(properties)) {
            for (String key : properties.keySet()) {
                Object val = properties.get(key);
                if (val instanceof String) {
                    properties.put(key, this.decryptValue(val.toString()));
                }
            }
        }
        boolean connect = this.testDatasource(properties.get("driverClass").toString(), properties.get("url").toString(), properties.get("user").toString(), properties.get("password").toString());
        if (!connect) {

            throw new RuntimeException("数据源" + properties.get("url").toString() + "不可用");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(properties.get("url").toString() + "?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&useAffectedRows=true");
        druidDataSource.setUsername(properties.get("user").toString());
        druidDataSource.setPassword(properties.get("password").toString());
        DynamicDataSource.setDataSource(druidDataSource);
    }

    /**
     * 测试连接情况
     *
     * @param driveClass
     * @param url
     * @param username
     * @param password
     * @return
     */
    public boolean testDatasource(String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
