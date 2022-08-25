package datart.core.mappers;

import datart.core.entity.JimuReportRewrite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface JimuReportMapper {
    @Select({
            "SELECT * FROM jimu_report where id = #{id,jdbcType = VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "note", property = "note", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "json_str", property = "jsonStr", jdbcType = JdbcType.VARCHAR),
            @Result(column = "api_url", property = "apiUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "api_method", property = "apiMethod", jdbcType = JdbcType.VARCHAR),
            @Result(column = "api_code", property = "apiCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "thumb", property = "thumb", jdbcType = JdbcType.VARCHAR),
            @Result(column = "template", property = "template", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.INTEGER),
            @Result(column = "view_count", property = "viewCount", jdbcType = JdbcType.BIGINT),
            @Result(column = "css_str", property = "cssStr", jdbcType = JdbcType.VARCHAR),
            @Result(column = "js_str", property = "jsStr", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tenantid", property = "tenantId", jdbcType = JdbcType.VARCHAR)
    })
    JimuReportRewrite selectJimuReportRewriteById(String id);

    @Select({
            "<script>",
                "select * from jimu_report where del_flag = 0 "+
                "<if test=\"type != null and type != ''\"> and type like concat('%', #{type}, '%') </if>" +
                "<if test=\"template != null\"> and template = #{type} </if>" +
                "<if test=\"createBy != null and createBy != ''\"> and create_by = #{createBy} </if>" +
                "order by cerate_time desc" +
            "</script>"
    })
    List<JimuReportRewrite> selectJimuReportRewriteList(JimuReportRewrite jimuReportRewrite);
}
