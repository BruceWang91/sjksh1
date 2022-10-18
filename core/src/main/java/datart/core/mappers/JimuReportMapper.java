package datart.core.mappers;

import datart.core.entity.JimuReportRewrite;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface JimuReportMapper extends CRUDMapper {
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
            "select * from jimu_report where del_flag = 0 " +
                    "<if test=\"type != null and type != ''\"> and type like concat('%', #{type}, '%') </if>" +
                    "<if test=\"template != null\"> and template = #{type} </if>" +
                    "<if test=\"createBy != null and createBy != ''\"> and create_by = #{createBy} </if>" +
                    "<if test=\"name != null and name != ''\"> and name like concat('%', #{name}, '%') </if>" +
                    "<if test=\"isFolder != null\"> and is_folder = #{isFolder} </if>" +
                    "<if test=\"parentId != null\"> and parent_id = #{parentId} </if>" +
                    "order by create_time desc" +
                    "</script>"
    })
    List<JimuReportRewrite> selectJimuReportRewriteList(JimuReportRewrite jimuReportRewrite);

    @Insert({
            "<script>",
            "insert into jimu_report (id, org_id, code, ",
            "name, type, create_by, create_time, ",
            "del_flag, template, view_count, is_folder, parent_id)",
            "values (#{id,jdbcType=VARCHAR}, #{orgId,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, ",
            "#{name,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
            "#{createBy,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{delFlag,jdbcType=INTEGER}, #{template,jdbcType=INTEGER}, #{viewCount,jdbcType=BIGINT}, ",
            "#{isFolder,jdbcType=INTEGER}, #{parentId,jdbcType=VARCHAR})",
            "</script>"
    })
    int insert(JimuReportRewrite jimuReport);

    @Update({
            "<script>",
            "update jimu_report\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "   <if test=\"parentId != null and parentId != ''\"> parent_id = #{parentId}, \n</if>\n" +
                    "   <if test=\"name != null and name != ''\"> name = #{name}, \n</if>\n" +
                    "</trim>\n" +
                    "where id = #{id}",
            "</script>"
    })
    int updateParentId(JimuReportRewrite jimuReportRewrite);

    @Delete({
            "delete from jimu_report where is_folder = 1 and id = #{id}"
    })
    int deleteFolder(String id);
}
