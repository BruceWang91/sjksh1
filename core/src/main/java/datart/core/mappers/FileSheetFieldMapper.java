package datart.core.mappers;

import datart.core.entity.FileSheetField;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 字段对应Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-27
 */
public interface FileSheetFieldMapper {
    /**
     * 查询字段对应
     *
     * @param fieldId 字段对应主键
     * @return 字段对应
     */
    @Select({
            "select field_id, file_id, sheet_id, cell_num, cell_name, row_num, " +
                    "entity_field, status, del_flag, create_by, create_time, update_by, " +
                    "update_time, remark " +
                    "from file_sheet_field where field_id = #{fieldId}"
    })
    @Results({
            @Result(column = "field_id", property = "fieldId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "file_id", property = "fieldId", jdbcType = JdbcType.BIGINT),
            @Result(column = "sheet_id", property = "sheetId", jdbcType = JdbcType.BIGINT),
            @Result(column = "cell_num", property = "cellNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "cell_name", property = "cellName", jdbcType = JdbcType.INTEGER),
            @Result(column = "row_num", property = "rowNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "entity_field", property = "entityField", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "class_id", property = "classId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    FileSheetField selectFileSheetFieldByFieldId(Long fieldId);


    /**
     * 新增字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    @InsertProvider(type = FileSheetFieldSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys=true, keyProperty="fieldId", keyColumn="field_id")
    int insertFileSheetField(FileSheetField fileSheetField);

    /**
     * 修改字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    @Update({
            "<script>",
            "update file_sheet_field\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"fileId != null\">file_id = #{fileId},</if>\n" +
                    "            <if test=\"sheetId != null\">sheet_id = #{sheetId},</if>\n" +
                    "            <if test=\"cellNum != null\">cell_num = #{cellNum},</if>\n" +
                    "            <if test=\"cellName != null\">cell_name = #{cellName},</if>\n" +
                    "            <if test=\"rowNum != null\">row_num = #{rowNum},</if>\n" +
                    "            <if test=\"entityField != null\">entity_field = #{entityField},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"delFlag != null\">del_flag = #{delFlag},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>\n" +
                    "where field_id = #{fieldId}",
            "</script>"
    })
    int updateFileSheetField(FileSheetField fileSheetField);

    /**
     * 删除字段对应
     *
     * @param fieldId 字段对应主键
     * @return 结果
     */
    @Delete({
            "delete from file_sheet_field where field_id = #{fieldId}"
    })
    int deleteFileSheetFieldByFieldId(Long fieldId);

    /**
     * 批量删除字段对应
     *
     * @param fieldIds 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from file_sheet_field where file_id in ",
            "<foreach collection='array' item='fieldId' index='index' open='(' close=')' separator=','>#{fieldId}</foreach>",
            "</script>"
    })
    int deleteFileSheetFieldByFieldIds(String[] fieldIds);

    @Delete({
            "delete from file_sheet_field where file_id = #{fileId}"
    })
    int deleteFieldsByFileId(Long fileId);
}
