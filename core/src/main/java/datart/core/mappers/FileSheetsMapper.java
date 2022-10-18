package datart.core.mappers;

import datart.core.entity.FileSheets;
import datart.core.entity.result.FileSheetsResult;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 文件工作簿Mapper接口
 *
 * @author wangya
 * @date 2022-05-27
 */
public interface FileSheetsMapper {
    /**
     * 查询文件工作簿
     *
     * @param sheetId 文件工作簿主键
     * @return 文件工作簿
     */
    @Select({
            "select sheet_id, file_id, org_id, sheet_name, entity_name, " +
                    "order_num, start_row, start_cell, cell_count, status, " +
                    "del_flag, create_by, create_time, update_by, update_time, remark " +
                    "from file_sheets where sheet_id = #{sheetId}"
    })
    @Results({
            @Result(column = "sheet_id", property = "sheetId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "file_id", property = "fileId", jdbcType = JdbcType.BIGINT),
            @Result(column = "org_id", property = "orgId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sheet_name", property = "sheetName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entity_name", property = "entityName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "start_row", property = "startRow", jdbcType = JdbcType.INTEGER),
            @Result(column = "start_cell", property = "startCell", jdbcType = JdbcType.INTEGER),
            @Result(column = "cell_count", property = "cellCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "class_id", property = "classId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    FileSheets selectFileSheetsBySheetId(Long sheetId);

    /**
     * 新增文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    @InsertProvider(type = FileSheetsSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "sheetId", keyColumn = "sheet_id")
    int insertFileSheets(FileSheets fileSheets);

    /**
     * 修改文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    @Update({
            "<script>",
            "update file_sheets\n" +
                    "        <trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"fileId != null\">file_id = #{fileId},</if>\n" +
                    "            <if test=\"orgId != null\">org_id = #{orgId},</if>\n" +
                    "            <if test=\"sheetName != null\">sheet_name = #{sheetName},</if>\n" +
                    "            <if test=\"entityName != null\">entity_name = #{entityName},</if>\n" +
                    "            <if test=\"orderNum != null\">order_num = #{orderNum},</if>\n" +
                    "            <if test=\"startRow != null\">start_row = #{startRow},</if>\n" +
                    "            <if test=\"startCell != null\">start_cell = #{startCell},</if>\n" +
                    "            <if test=\"cellCount != null\">cell_count = #{cellCount},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"delFlag != null\">del_flag = #{delFlag},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "        </trim>\n" +
                    "        where sheet_id = #{sheetId}",
            "</script>"
    })
    int updateFileSheets(FileSheets fileSheets);

    /**
     * 删除文件工作簿
     *
     * @param sheetId 文件工作簿主键
     * @return 结果
     */
    @Delete({
            "delete from file_sheets where sheet_id = #{sheetId}"
    })
    int deleteFileSheetsBySheetId(Long sheetId);

    /**
     * 批量删除文件工作簿
     *
     * @param sheetIds 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from file_sheets where sheet_id in ",
            "<foreach collection='array' item='sheetId' index='index' open='(' close=')' separator=','>#{sheetId}</foreach>",
            "</script>"
    })
    int deleteFileSheetsBySheetIds(String[] sheetIds);

    @Delete({
            "delete from file_sheets where file_id = #{fileId}"
    })
    int deleteSheetsByFileId(Long fileId);

    @Select({
            "<script>",
            "select * from file_sheets where 1=1\n",
            "<if test=\"tables != null and tables.size() > 0\">\n" +
                    "AND entity_name IN\n" +
                    "<foreach item=\"tables\" collection=\"tables\" open=\"(\" separator=\",\" close=\")\">\n" +
                        "#{tables}\n" +
                    "</foreach>\n" +
                    "</if>",
            "</script>"
    })
    List<FileSheetsResult> getSheetsByTables(List<String> tables);
}
