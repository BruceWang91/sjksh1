package datart.core.mappers;

import datart.core.entity.StaticManagement;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface StaticManagementMapper {

    @Select({
            "select * from static_management where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "module_name", property = "moduleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "org_id", property = "orgId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mark", property = "mark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "text_content", property = "textContent", jdbcType = JdbcType.BLOB),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "is_folder", property = "isFolder", jdbcType = JdbcType.INTEGER),
            @Result(column = "index", property = "index", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "link", property = "link", jdbcType = JdbcType.VARCHAR),
            @Result(column = "json_str", property = "jsonStr", jdbcType = JdbcType.VARCHAR)
    })
    StaticManagement selectStaticManagementById(Long id);

    /**
     * 新增静态资源管理
     *
     * @param staticManagement 静态资源管理
     * @return 结果
     */
    @InsertProvider(type = StaticManagementSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertStaticManagement(StaticManagement staticManagement);

    /**
     * 修改静态资源管理
     *
     * @param staticManagement 静态资源管理
     * @return 结果
     */
    @Update({
            "<script>",
            "update static_management\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"moduleName != null\">module_name = #{moduleName},</if>\n" +
                    "            <if test=\"orgId != null\">org_id = #{orgId},</if>\n" +
                    "            <if test=\"mark != null\">mark = #{mark},</if>\n" +
                    "            <if test=\"type != null\">type = #{type},</if>\n" +
                    "            <if test=\"textContent != null\">text_content = #{textContent},</if>\n" +
                    "            <if test=\"url != null\">url = #{url},</if>\n" +
                    "            <if test=\"isFolder != null\">is_folder = #{isFolder},</if>\n" +
                    "            <if test=\"index != null\">`index` = #{index},</if>\n" +
                    "            <if test=\"parentId != null\">parent_id = #{parentId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "            <if test=\"link != null\">link = #{link},</if>\n" +
                    "            <if test=\"jsonStr != null\">json_str = #{jsonStr},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateStaticManagement(StaticManagement staticManagement);

    @Delete({
            "<script>",
                "delete from static_management where id = #{id}",
            "</script>"
    })
    int deleteStaticManagementById(Long id);

    @Select({
            "<script>",
            "select id,module_name,org_id,mark,type,url,is_folder,`index`,parent_id,create_by,create_time,update_by,update_time,remark,link " +
                    "from static_management where 1=1\n" +
                    "            <if test=\"moduleName != null\">AND module_name = #{moduleName}\n</if>" +
                    "            <if test=\"orgId != null\">AND org_id = #{orgId}\n</if>" +
                    "            <if test=\"mark != null\">AND mark = #{mark}\n</if>" +
                    "            <if test=\"type != null\">AND type = #{type}\n</if>" +
                    "            <if test=\"textContent != null\">AND text_content = #{textContent}\n</if>" +
                    "            <if test=\"url != null\">AND url = #{url}\n</if>" +
                    "            <if test=\"isFolder != null\">AND is_folder = #{isFolder}\n</if>" +
                    "            <if test=\"index != null\">AND `index` = #{index}\n</if>" +
                    "            <if test=\"parentId != null\">AND parent_id = #{parentId}\n</if>" +
                    "            <if test=\"createBy != null\">AND create_by = #{createBy}\n</if>" +
                    "            <if test=\"createTime != null\">AND create_time = #{createTime}\n</if>" +
                    "            <if test=\"updateBy != null\">AND update_by = #{updateBy}\n</if>" +
                    "            <if test=\"updateTime != null\">AND update_time = #{updateTime}\n</if>" +
                    "            <if test=\"remark != null\">AND remark = #{remark}\n</if>" +
                    "            <if test=\"link != null\">AND link = #{link}\n</if>" +
                    "            <if test=\"jsonStr != null\">AND json_str = #{jsonStr}\n</if>" +
            "</script>"
    })
    List<StaticManagement> list (StaticManagement staticManagement);

    @Select({
            "<script>",
            "select id,module_name,org_id,mark,type,text_content,url,is_folder,`index`,parent_id,create_by,create_time,update_by,update_time,remark,link,json_str " +
                    "from static_management where 1=1\n" +
                    "            <if test=\"moduleName != null\">AND module_name = #{moduleName}\n</if>" +
                    "            <if test=\"orgId != null\">AND org_id = #{orgId}\n</if>" +
                    "            <if test=\"mark != null\">AND mark = #{mark}\n</if>" +
                    "            <if test=\"type != null\">AND type = #{type}\n</if>" +
                    "            <if test=\"textContent != null\">AND text_content = #{textContent}\n</if>" +
                    "            <if test=\"url != null\">AND url = #{url}\n</if>" +
                    "            <if test=\"isFolder != null\">AND is_folder = #{isFolder}\n</if>" +
                    "            <if test=\"index != null\">AND `index` = #{index}\n</if>" +
                    "            <if test=\"parentId != null\">AND parent_id = #{parentId}\n</if>" +
                    "            <if test=\"createBy != null\">AND create_by = #{createBy}\n</if>" +
                    "            <if test=\"createTime != null\">AND create_time = #{createTime}\n</if>" +
                    "            <if test=\"updateBy != null\">AND update_by = #{updateBy}\n</if>" +
                    "            <if test=\"updateTime != null\">AND update_time = #{updateTime}\n</if>" +
                    "            <if test=\"remark != null\">AND remark = #{remark}\n</if>" +
                    "            <if test=\"link != null\">AND link = #{link}\n</if>" +
                    "            <if test=\"jsonStr != null\">AND json_str = #{jsonStr}\n</if>" +
                    "</script>"
    })
    List<StaticManagement> lists (StaticManagement staticManagement);

    @Select({
            "select * from static_management where mark = #{mark} LIMIT 1"
    })
    StaticManagement getStaticManagementByMark(String mark);

    @Select({
            "select COUNT(1) AS counts from static_management where mark = #{mark}"
    })
    int getCountByMark(String mark);
}
