package datart.core.mappers;

import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CustomizeMapper extends CRUDMapper {

    @Select({
            "<script>",
            "select * from dwd_cyyyjczhfx_ywtzztqkb\n",
            "where 1=1\n",
            "<if test=\"qymc != null and qymc != '' \">and qymc4 = #{qymc}</if>\n",
            "<if test=\"qymc == null or qymc == '' \">and qymc4 != '合计'</if>\n",
            "</script>"
    })
    List<HashMap<String, Object>> test1(HashMap<String, Object> map);

    @Select({
            "select * from user"
    })
    List<HashMap<String, Object>> test2(HashMap<String, Object> map);

    @Select({
            "<script>",
            "${script}\n",
            "${condition}\n",
            "${groups}\n",
            "${orders}",
            "</script>"
    })
    List<HashMap<String, Object>> generic(HashMap<String, String> params);
}
