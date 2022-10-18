package datart.core.mappers;

import datart.core.entity.FileSaveStream;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface FileSaveStreamMapper {

    @Select({
            "select * from file_save_stream where file_save_id = #{fileSaveId}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "file_save_id", property = "fileSaveId", jdbcType = JdbcType.BIGINT),
            @Result(column = "file_stream", property = "fileStream", jdbcType = JdbcType.BLOB),
            @Result(column = "pdf_stream", property = "pdfStream", jdbcType = JdbcType.BLOB)
    })
    FileSaveStream selectFileSaveStreamByFileSaveId(Long fileSaveId);

    /**
     * 新增文件流保存
     *
     * @param fileSaveStream 文件流保存
     * @return 结果
     */
    @InsertProvider(type = FileSaveStreamSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertFileSaveStream(FileSaveStream fileSaveStream);

    @Delete({
            "<script>",
            "delete from file_save_stream where file_save_id in",
            "<foreach collection='array' item='fileSaveId' index='index' open='(' close=')' separator=','>#{fileSaveId}</foreach>",
            "</script>"
    })
    int deleteFileSaveStreamByFileSaveIds(Long[] fileSaveIds);
}
