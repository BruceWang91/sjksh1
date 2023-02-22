package datart.core.mappers;

import datart.core.entity.VisitorStatistics;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface VisitorStatisticsMapper {

    @Select({
            "select * from visitor_statistics where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "module_name", property = "moduleName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "num", property = "num", jdbcType = JdbcType.BIGINT)
    })
    VisitorStatistics selectVisitorStatisticsById(Long id);

    /**
     * 访客统计
     *
     * @param visitorStatistics 访客统计
     * @return 结果
     */
    @InsertProvider(type = VisitorStatisticsSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertVisitorStatistics(VisitorStatistics visitorStatistics);

    /**
     * 修改访客统计
     *
     * @param visitorStatistics 访客统计
     * @return 结果
     */
    @Update({
            "<script>",
            "update visitor_statistics\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"moduleName != null\">module_name = #{moduleName},</if>\n" +
                    "            <if test=\"type != null\">type = #{type},</if>\n" +
                    "            <if test=\"num != null\">num = #{num},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateVisitorStatistics(VisitorStatistics visitorStatistics);

    /**
     * 删除访客统计
     *
     * @param id 访客统计主键
     * @return 结果
     */
    @Delete({
            "delete from visitor_statistics where id = #{id,jdbcType=BIGINT}"
    })
    int deleteVisitorStatisticsById(Long id);

    @Select({
            "<script>",
            "select * from visitor_statistics where 1=1\n" +
            "<if test=\"moduleName != null and moduleName != ''\"> and module_name = #{moduleName} \n</if>" +
            "<if test=\"type != null and type != ''\"> and type = #{type} \n</if>" +
            "</script>"
    })
    List<VisitorStatistics> selectVisitorStatisticsList(VisitorStatistics visitorStatistics);
}
