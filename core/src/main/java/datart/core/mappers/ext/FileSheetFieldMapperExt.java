package datart.core.mappers.ext;

import datart.core.entity.FileSheetField;
import datart.core.mappers.FileSheetFieldMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileSheetFieldMapperExt extends FileSheetFieldMapper {


    /**
     * 查询字段对应列表
     *
     * @param fileSheetField 字段对应
     * @return 字段对应集合
     */
    @Select({
            "<script>",
            "select field_id, file_id, sheet_id, cell_num, " +
                    "cell_name, row_num, entity_field, status, del_flag, import_flag," +
                    "create_by, create_time, update_by, update_time, remark " +
                    "from file_sheet_field where 1=1" +
                    "<if test=\"fileId != null \"> and file_id = #{fileId}</if>\n" +
                    "            <if test=\"importFlag != null and importFlag != ''\"> and import_flag = #{importFlag}</if>\n" +
                    "            <if test=\"sheetId != null \"> and sheet_id = #{sheetId}</if>\n" +
                    "            <if test=\"cellNum != null \"> and cell_num = #{cellNum}</if>\n" +
                    "            <if test=\"cellName != null  and cellName != ''\"> and cell_name like concat('%', #{cellName}, '%')</if>\n" +
                    "            <if test=\"rowNum != null \"> and row_num = #{rowNum}</if>\n" +
                    "            <if test=\"entityField != null  and entityField != ''\"> and entity_field = #{entityField}</if>\n" +
                    "            <if test=\"status != null  and status != ''\"> and status = #{status}</if>",
            "</script>"
    })
    List<FileSheetField> selectFileSheetFieldList(FileSheetField fileSheetField);
}
