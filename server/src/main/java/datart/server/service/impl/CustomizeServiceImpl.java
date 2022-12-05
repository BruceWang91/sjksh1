package datart.server.service.impl;

import datart.core.entity.TableImport;
import datart.core.entity.TableImportField;
import datart.core.entity.param.GenericParam;
import datart.core.mappers.CustomizeMapper;
import datart.core.mappers.TableImportFieldMapper;
import datart.core.mappers.TableImportMapper;
import datart.server.common.StringUtils;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.service.ICustomizeService;
import datart.server.service.IToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private TableImportMapper tableImportMapper;
    @Autowired
    private TableImportFieldMapper tableImportFieldMapper;

    @Override
    public List<HashMap<String, Object>> test(HashMap<String, Object> hashMap) {

        toolsService.changeConnection(hashMap.get("sourceId").toString());
        List<HashMap<String, Object>> list = customizeMapper.test1(hashMap);
        //清除数据源配置
        DynamicDataSource.clear();
//        List<HashMap<String, Object>> list1 = customizeMapper.test2(new HashMap<>());
//        list.addAll(list1);
//        list.add(hashMap);
        return list;
    }

    @Override
    public HashMap<String, Object> generic(GenericParam genericParam) {

        Long importId = genericParam.getImportId();
        String script = genericParam.getScript();
        String condition = genericParam.getCondition();
        TableImport tableImport = tableImportMapper.selectTableImportById(importId);
        List<TableImportField> tableImportFields = tableImportFieldMapper.selectTableImportFieldList(new TableImportField() {
            {
                setTableImportId(importId);
            }
        });
        String sourceId = tableImport.getSourceId();
        if (null == tableImport) {
            throw new RuntimeException("数据指标不存在");
        }
        if (StringUtils.isEmpty(sourceId)) {
            throw new RuntimeException("指标数据源未配置！");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("script",script);
        map.put("condition", condition);
        map.put("groups",genericParam.getGroups());
        map.put("orders",genericParam.getOrders());
        toolsService.changeConnection(sourceId);
        List<HashMap<String, Object>> list = customizeMapper.generic(map);
        DynamicDataSource.clear();
        HashMap<String, Object> result = new HashMap<>();
        result.put("datas", toolsService.mapListToJsonArray(list));

        //根据查询结果返回表头指标管理内中文备注
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(tableImportFields)) {

            Set<String> sets = list.get(0).keySet();
            List<HashMap<String, Object>> fieldsMaps = new ArrayList<>();
            for (String set : sets) {

                HashMap<String, Object> field = new HashMap<>();
                field.put(set, "");
                for (TableImportField tableImportField : tableImportFields) {

                    if (set.equalsIgnoreCase(tableImportField.getTableField())) {

                        field.remove(set);
                        field.put(set, tableImportField.getCellName());
                    }
                }
                fieldsMaps.add(field);
            }
            result.put("fields", toolsService.mapListToJsonArray(fieldsMaps));
        }
        return result;
    }
}
