package datart.core.mappers;

import datart.core.entity.FileClass;
import datart.core.mappers.ext.CRUDMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 文件分类Mapper接口
 *
 * @author wangya
 * @date 2022-06-17
 */
public interface FileClassMapper extends CRUDMapper {
    /**
     * 查询文件分类
     *
     * @param id 文件分类主键
     * @return 文件分类
     */
    @Select({
            "select id, name, parent_id, order_num, status, del_flag, ",
            "create_by, create_time, update_by, update_time, remark ",
            "from file_class",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_num", property = "orderNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "del_flag", property = "delFlag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_by", property = "createBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_by", property = "updateBy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    FileClass selectFileClassById(Long id);


    /**
     * 新增文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    @InsertProvider(type = FileClassSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertFileClass(FileClass fileClass);

    /**
     * 修改文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    @Update({
            "<script>",
            "update file_class\n" +
                    "        <trim prefix=\"SET\" suffixOverrides=\",\">\n" +
                    "            <if test=\"name != null\">name = #{name},</if>\n" +
                    "            <if test=\"parentId != null\">parent_id = #{parentId},</if>\n" +
                    "            <if test=\"orderNum != null\">order_num = #{orderNum},</if>\n" +
                    "            <if test=\"status != null\">status = #{status},</if>\n" +
                    "            <if test=\"delFlag != null\">del_flag = #{delFlag},</if>\n" +
                    "            <if test=\"createBy != null\">create_by = #{createBy},</if>\n" +
                    "            <if test=\"createTime != null\">create_time = #{createTime},</if>\n" +
                    "            <if test=\"updateBy != null\">update_by = #{updateBy},</if>\n" +
                    "            <if test=\"updateTime != null\">update_time = #{updateTime},</if>\n" +
                    "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                    "        </trim>\n" +
                    "where id = #{id}",
            "</script>"
    })
    int updateFileClass(FileClass fileClass);

    /**
     * 删除文件分类
     *
     * @param id 文件分类主键
     * @return 结果
     */
    @Delete({
            "delete from file_class where id = #{id,jdbcType=BIGINT}"
    })
    int deleteFileClassById(Long id);

    /**
     * 批量删除文件分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Delete({
            "<script>",
            "delete from file_class where id in ",
            "<foreach collection='array' item='id' index='index' open='(' close=')' separator=','>#{id}</foreach>",
            "</script>"
    })
    int deleteFileClassByIds(Long[] ids);
}
