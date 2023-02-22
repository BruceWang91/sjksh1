package datart.server.service;

import com.alibaba.fastjson.JSONArray;
import datart.core.data.provider.DataProviderSource;
import datart.core.entity.Department;
import datart.core.entity.FileSheetField;
import datart.core.entity.Source;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public interface IToolsService {

    JSONArray mapListToJsonArray(List<HashMap<String, Object>> hashMapList);

    DataProviderSource parseDataProviderConfig(Source source);

    String decryptValue(String value);

    Department getCompany(Long deptId);

    void changeConnection(String sourceId);
}
