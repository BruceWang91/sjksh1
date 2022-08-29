package datart.core.mappers;

import datart.core.entity.FileMain;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 文件管理Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-27
 */
public interface FileMainMapper {
    /**
     * 查询文件管理
     *
     * @param fileId 文件管理主键
     * @return 文件管理
     */
    @Select({
            "select file_id, file_name, order_num, status, del_flag, " +
                    "class_id, create_by, create_time, update_by, update_time, remark " +
                    "from file_main where file_id = #{fileId}"
    })
    @Results({
            @Result(column = "file_id", property = "fileId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "file_name", property = "fileName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "class_id", property = "classId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    FileMain selectFileMainByFileId(Long fileId);

    /**
     * 新增文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    @InsertProvider(type = FileMainSqlProvider.class, method = "insertSelective" )
    @Options(useGeneratedKeys=true, keyProperty="fileId", keyColumn="file_id")
    int insertFileMain(FileMain fileMain);

    /**
     * 修改文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    @Update({
            "<script>",
            "update file_main\n" +
                    "<trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"fileName != null\">file_name = #{fileName},</if>\n" +
                    "            <if test=\"orderNum != null\">order_num = #{orderNum},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"delFlag != null\">del_flag = #{delFlag},</if>\n" +
                    "            <if test=\"classId != null\">class_id = #{classId},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "</trim>",
            "where file_id = #{fileId}",
            "</script>"
    })
    int updateFileMain(FileMain fileMain);

    /**
     * 删除文件管理
     *
     * @param fileId 文件管理主键
     * @return 结果
     */
    @Delete({
            "delete from file_main where file_id = #{fileId,jdbcType=BIGINT}"
    })
    int deleteFileMainByFileId(Long fileId);

    /**
     * 批量删除文件管理
     *
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from file_main where file_id in ",
            "<foreach collection='array' item='fileId' index='index' open='(' close=')' separator=','>#{fileId}</foreach>",
            "</script>"
    })
    int deleteFileMainByFileIds(String[] fileIds);
}
