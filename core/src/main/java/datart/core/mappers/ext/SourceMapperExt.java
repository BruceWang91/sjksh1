package datart.core.mappers.ext;

import datart.core.entity.Organization;
import datart.core.entity.Source;
import datart.core.mappers.SourceMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;


@Mapper
public interface SourceMapperExt extends SourceMapper {

    @Select({
            "<script>",
            "SELECT * FROM source  WHERE org_id=#{orgId} AND `name` = #{name}",
            "<if test=\"parentId==null\">",
            " AND parent_id IS NULL ",
            "</if>",
            "<if test=\"parentId!=null\">",
            " AND parent_id=#{parentId} ",
            "</if>",
            "</script>",
    })
    List<Source> checkName(String orgId, String parentId, String name);

    @Select({
            "SELECT s.* FROM source s WHERE s.status=#{archived} and s.org_id=#{orgId} ORDER BY create_time ASC"
    })
    List<Source> listByOrg(@Param("orgId") String orgId, boolean archived);

    @Select({
            "SELECT s.id,s.name,s.type,s.org_id,s.parent_id,s.is_folder,s.status FROM source s WHERE s.status=#{archived} and s.org_id=#{orgId} and s.type = #{type} ORDER BY create_time ASC"
    })
    List<Source> listSources(@Param("orgId") String orgId, String type, boolean archived);

    @Select({
            "SELECT org.* FROM organization org JOIN source s " +
                    "ON s.orgId=org.od AND s.id=#{sourceId}"
    })
    Organization getOrgById(@Param("sourceId") String sourceId);

    @Select({
            "select column_name as columName,column_comment columnComment\n" +
                    "from information_schema.columns \n" +
                    "where table_name=#{tableName}"
    })
    List<HashMap<String, String>> getTableColumnName(String tableName);
}
