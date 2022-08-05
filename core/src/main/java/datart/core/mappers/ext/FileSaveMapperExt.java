package datart.core.mappers.ext;

import datart.core.entity.FileSave;
import datart.core.mappers.FileSaveMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileSaveMapperExt extends FileSaveMapper {

    /**
     * 查询文件保存列表
     *
     * @param fileSave 文件保存
     * @return 文件保存集合
     */
    @Select({
            "<script>",
            "select id, class_id, file_name, new_name, pdf_name, url, pdfurl, " +
                    "order_num, status, del_flag, create_by, create_time, update_by, " +
                    "update_time, remark from file_save where 1=1 "+
                    "<if test=\"classId != null \"> and class_id = #{classId}</if>\n" +
                    "            <if test=\"fileName != null  and fileName != ''\"> and file_name like concat('%', #{fileName}, '%')</if>\n" +
                    "            <if test=\"newName != null  and newName != ''\"> and new_name like concat('%', #{newName}, '%')</if>\n" +
                    "            <if test=\"pdfName != null  and pdfName != ''\"> and pdf_name like concat('%', #{pdfName}, '%')</if>\n" +
                    "            <if test=\"url != null  and url != ''\"> and url = #{url}</if>\n" +
                    "            <if test=\"pdfurl != null  and pdfurl != ''\"> and pdfurl = #{pdfurl}</if>\n" +
                    "            <if test=\"orderNum != null \"> and order_num = #{orderNum}</if>\n" +
                    "            <if test=\"status != null  and status != ''\"> and status = #{status}</if>",
            "</script>"
    })
    List<FileSave> selectFileSaveList(FileSave fileSave);

    @Select({
            "<script>",
                    "SELECT\n" +
                    "            filesave.*\n" +
                    "        FROM file_save filesave\n" +
                    "        LEFT JOIN sys_user suser\n" +
                    "            ON filesave.create_by = suser.user_id\n" +
                    "        WHERE 1=1\n" +
                    "        <if test=\"depIds != null and depIds.size() > 0\">\n" +
                    "            AND suser.dept_id IN\n" +
                    "            <foreach item=\"depIds\" collection=\"depIds\" open=\"(\" separator=\",\" close=\")\">\n" +
                    "                #{depIds}\n" +
                    "            </foreach>\n" +
                    "        </if>\n" +
                    "        <if test=\"classId != null \">\n" +
                    "          and class_id = #{classId}\n" +
                    "        </if>\n" +
                    "        <if test=\"fileName != null  and fileName != ''\">\n" +
                    "          and file_name like concat('%', #{fileName}, '%')\n" +
                    "        </if>",
            "</script>"
    })
    List<FileSave> getList(FileSave fileSave);
}
