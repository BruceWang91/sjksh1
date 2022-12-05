package datart.server.service;

import datart.core.entity.param.GenericParam;

import java.util.HashMap;
import java.util.List;

public interface ICustomizeService {

    List<HashMap<String, Object>> test(HashMap<String, Object> objectObjectHashMap);

    HashMap<String, Object> generic(GenericParam genericParam);
}
