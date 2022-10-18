package datart.core.mappers;

import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CustomizeMapper extends CRUDMapper {

    @Select({
            "select * from ods_hxcy_b1ywtzqkfxb20220922100459"
    })
    List<HashMap<String, Object>> test1(HashMap<String, Object> map);

    @Select({
            "select * from user"
    })
    List<HashMap<String, Object>> test2(HashMap<String, Object> map);
}
