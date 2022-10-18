package datart.server.service;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.List;

public interface IToolsService {

    JSONArray mapListToJsonArray(List<HashMap<String, Object>> hashMapList);
}
