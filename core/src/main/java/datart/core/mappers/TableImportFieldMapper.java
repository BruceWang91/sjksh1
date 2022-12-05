package datart.core.mappers;

import datart.core.entity.TableImportField;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface TableImportFieldMapper extends CRUDMapper {

    @Select({
            "SELECT id,table_import_id,cell_name,table_field,`index`," +
                    "create_by,create_time,update_by,update_time,remark\n" +
                    "FROM table_import_field where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "table_import_id", property = "tableImportId", jdbcType = JdbcType.BIGINT),
            @Result(column = "cell_name", property = "cellName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "table_field", property = "tableField", jdbcType = JdbcType.VARCHAR),
            @Result(column = "index", property = "index", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    TableImportField selectTableImportFieldById(Long id);

    @Select({
            "<script>",
            "select id,table_import_id,cell_name,table_field,`index`,create_by," +
                    "create_time,update_by,update_time,remark\n" +
                    "from table_import_field\n" +
                    "where 1=1\n" +
                    "<if test=\"tableImportId != null\"> and table_import_id = #{tableImportId} \n</if>" +
                    "<if test=\"cellName != null\"> and cell_name = #{cellName} \n</if>" +
                    "<if test=\"tableField != null\"> and table_field = #{tableField} \n</if>" +
                    "</script>"
    })
    List<TableImportField> selectTableImportFieldList(TableImportField tableImportField);

    /**
     * 新增表数据导入对应字段
     *
     * @param tableImportField 表数据导入对应字段
     * @return 结果
     */
    @InsertProvider(type = TableImportFieldSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertTableImportField(TableImportField tableImportField);

    @Update({
            "<script>",
            "update table_import_field\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"tableImportId != null\">table_import_id = #{tableImportId},</if>\n" +
                    "            <if test=\"cellName != null\">cell_name = #{cellName},</if>\n" +
                    "            <if test=\"tableField != null\">table_field = #{tableField},</if>\n" +
                    "            <if test=\"index != null\">`index` = #{index},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateTableImportField(TableImportField tableImportField);

    @Delete({
            "<script>",
            "delete from table_import_field where id = #{id}",
            "</script>"
    })
    int deleteTableImportFieldById(Long id);

    @Delete({
            "<script>",
            "delete from table_import_field where table_import_id = #{tableImportId}",
            "</script>"
    })
    int deleteTableImportFieldByTableImportId(Long tableImportId);
}
