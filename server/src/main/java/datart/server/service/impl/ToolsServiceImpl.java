package datart.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import datart.server.service.IToolsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToolsServiceImpl implements IToolsService {

    @Override
    public JSONArray mapListToJsonArray(List<HashMap<String, Object>> hashMapList) {

        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> hashMap : hashMapList) {
            JSONObject jsonObject = new JSONObject(hashMap);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
