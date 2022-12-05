package datart.core.mappers;

import datart.core.entity.TableImport;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TableImportMapper extends CRUDMapper {

    @Select({
            "SELECT * FROM table_import where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "org_id", property = "orgId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "source_id", property = "sourceId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "table_name", property = "tableName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "is_folder", property = "isFolder", jdbcType = JdbcType.INTEGER),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    TableImport selectTableImportById(Long id);

    @Select({
            "<script>",
            "select task.* from table_import task\n" +
                    "LEFT JOIN user suser\n" +
                    "     ON task.create_by = suser.id\n" +
                    "where 1=1\n",
            "<if test=\"createBy != null and createBy != ''\"> and task.create_by = #{createBy} \n</if>" +
                    "<if test=\"name != null and name != ''\"> and task.name like concat('%', #{name}, '%')\n</if>" +
                    "<if test=\"isFolder != null \"> and task.is_folder = #{isFolder}\n</if>",
            "<if test=\"parentId != null \"> and task.parent_id = #{parentId}\n</if>",
            "<if test=\"isFolder != null \"> and task.is_folder = #{isFolder}\n</if>",
            "</script>"
    })
    List<TableImport> selectTableImportList(TableImport tableImport);

    /**
     * 新增表数据导入
     *
     * @param tableImport 表数据导入
     * @return 结果
     */
    @InsertProvider(type = TableImportSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertTableImport(TableImport tableImport);

    /**
     * 修改新增表数据导入
     *
     * @param tableImport 新增表数据导入
     * @return 结果
     */
    @Update({
            "<script>",
            "update table_import\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"name != null and name != ''\">name = #{name},</if>\n" +
                    "            <if test=\"orgId != null and orgId != ''\">org_id = #{orgId},</if>\n" +
                    "            <if test=\"sourceId != null and sourceId != ''\">source_id = #{sourceId},</if>\n" +
                    "            <if test=\"tableName != null and tableName != ''\">table_name = #{tableName},</if>\n" +
                    "            <if test=\"orderNum != null and orderNum != ''\">order_num = #{orderNum},</if>\n" +
                    "            <if test=\"isFolder != null\">is_folder = #{isFolder},</if>\n" +
                    "            <if test=\"parentId != null\">parent_id = #{parentId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where id = #{id}",
            "</script>"
    })
    int updateTableImport(TableImport tableImport);

    @Delete({
            "<script>",
            "delete from table_import where id = #{id}",
            "</script>"
    })
    int deleteTableImportById(Long id);

    @Insert({
            "insert into ${tableName} (${fields}) values ${valus}"
    })
    int insertTable(HashMap<String,String> map);

    @Select({
            "<script>",
            "select * from ${tableName} order by id\n",
            "</script>"
    })
    List<HashMap<String, Object>> getImportData(HashMap<String, Object> map);

    @Update({
            "update ${tableName} set ${sets} where id = ${id}"
    })
    Integer updateImportData(HashMap<String, String> map);

    @Delete({
            "delete from ${tableName} where id = ${id}"
    })
    Integer deleteImportData(HashMap<String, Object> map);
}
