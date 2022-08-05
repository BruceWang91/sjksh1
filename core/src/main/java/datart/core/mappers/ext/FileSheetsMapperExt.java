package datart.core.mappers.ext;

import datart.core.entity.FileSheets;
import datart.core.mappers.FileSheetsMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface FileSheetsMapperExt extends FileSheetsMapper {

    /**
     * 查询文件工作簿列表
     *
     * @param fileSheets 文件工作簿
     * @return 文件工作簿集合
     */
    @Select({
            "<script>",
            "select sheet_id, file_id, sheet_name, entity_name, order_num, " +
                    "start_row, start_cell, cell_count, status, del_flag, " +
                    "create_by, create_time, update_by, update_time, remark " +
                    "from file_sheets where 1=1 " +
                    "<if test=\"fileId != null \"> and file_id = #{fileId}</if>\n" +
                    "            <if test=\"sheetName != null  and sheetName != ''\"> and sheet_name like concat('%', #{sheetName}, '%')</if>\n" +
                    "            <if test=\"entityName != null  and entityName != ''\"> and entity_name like concat('%', #{entityName}, '%')</if>\n" +
                    "            <if test=\"orderNum != null \"> and order_num = #{orderNum}</if>\n" +
                    "            <if test=\"startRow != null \"> and start_row = #{startRow}</if>\n" +
                    "            <if test=\"startCell != null \"> and start_cell = #{startCell}</if>\n" +
                    "            <if test=\"cellCount != null \"> and cell_count = #{cellCount}</if>\n" +
                    "            <if test=\"delFlag != null and status != ''\"> and del_flag = #{delFlag}</if>\n" +
                    "            <if test=\"status != null  and status != ''\"> and status = #{status}</if>",
            "</script>"
    })
    List<FileSheets> selectFileSheetsList(FileSheets fileSheets);

    @Select({
            "<script>",
            "SELECT\n" +
                    "            sheets.*\n" +
                    "        FROM file_sheets sheets\n" +
                    "        LEFT JOIN file_main main\n" +
                    "            ON main.file_id = sheets.file_id\n" +
                    "        LEFT JOIN sys_user suser\n" +
                    "            ON main.create_by = suser.user_id\n" +
                    "        WHERE 1=1\n" +
                    "        <if test=\"classIds != null and classIds.size() > 0\">\n" +
                    "            AND main.class_id IN\n" +
                    "            <foreach item=\"classIds\" collection=\"classIds\" open=\"(\" separator=\",\" close=\")\">\n" +
                    "                #{classIds}\n" +
                    "            </foreach>\n" +
                    "        </if>\n" +
                    "        <if test=\"depIds != null and depIds.size() > 0\">\n" +
                    "            AND suser.dept_id IN\n" +
                    "            <foreach item=\"depIds\" collection=\"depIds\" open=\"(\" separator=\",\" close=\")\">\n" +
                    "                #{depIds}\n" +
                    "            </foreach>\n" +
                    "        </if>",
            "</script>"
    })
    List<FileSheets> getSheetList(HashMap<String, Object> hashMap);
}
