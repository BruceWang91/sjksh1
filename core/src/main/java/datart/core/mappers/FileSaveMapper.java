package datart.core.mappers;

import datart.core.entity.FileSave;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 文件保存Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-17
 */
public interface FileSaveMapper {
    /**
     * 查询文件保存
     *
     * @param id 文件保存主键
     * @return 文件保存
     */
    @Select({
            "select id, class_id, file_name, new_name, pdf_name, url, pdfurl, order_num, " +
            "status, del_flag, create_by, create_time, update_by, update_time, remark " +
            "from file_save where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "class_id", property = "classId", jdbcType = JdbcType.BIGINT),
            @Result(column = "file_name", property = "fileName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "new_name", property = "newName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pdf_name", property = "pdfName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pdfurl", property = "pdfurl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    FileSave selectFileSaveById(Long id);

    /**
     * 新增文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @InsertProvider(type = FileSaveSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertFileSave(FileSave fileSave);

    /**
     * 修改文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Update({
            "<script>",
            "update file_save\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"classId != null\">class_id = #{classId},</if>\n" +
                    "            <if test=\"fileName != null\">file_name = #{fileName},</if>\n" +
                    "            <if test=\"newName != null\">new_name = #{newName},</if>\n" +
                    "            <if test=\"pdfName != null\">pdf_name = #{pdfName},</if>\n" +
                    "            <if test=\"url != null\">url = #{url},</if>\n" +
                    "            <if test=\"pdfurl != null\">pdfurl = #{pdfurl},</if>\n" +
                    "            <if test=\"orderNum != null\">order_num = #{orderNum},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"delFlag != null\">del_flag = #{delFlag},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>\n" +
            "where id = #{id}",
            "</script>"
    })
    int updateFileSave(FileSave fileSave);

    /**
     * 删除文件保存
     *
     * @param id 文件保存主键
     * @return 结果
     */
    @Delete({
            "delete from file_save where id = #{id}"
    })
    int deleteFileSaveById(Long id);

    /**
     * 批量删除文件保存
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from file_main where file_save in ",
            "<foreach collection='array' item='id' index='index' open='(' close=')' separator=','>#{id}</foreach>",
            "</script>"
    })
    int deleteFileSaveByIds(Long[] ids);
}
