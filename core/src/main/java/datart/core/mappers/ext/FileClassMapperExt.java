package datart.core.mappers.ext;

import datart.core.entity.FileClass;
import datart.core.mappers.FileClassMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileClassMapperExt extends FileClassMapper {


    /**
     * 查询文件分类列表
     *
     * @param fileClass 文件分类
     * @return 文件分类集合
     */
    @Select({
        "<script>",
        "select id, name, parent_id, order_num, status, del_flag, ",
        "create_by, create_time, update_by, update_time, remark " ,
        "from file_class where 1=1",
        "<if test=\"id != null\">and id = #{id}</if>\n" +
        "<if test=\"name != null  and name != ''\"> and name like concat('%', #{name}, '%')</if>\n" +
        "<if test=\"parentId != null \"> and parent_id = #{parentId}</if>\n" +
        "<if test=\"orderNum != null \"> and order_num = #{orderNum}</if>\n" +
        "<if test=\"status != null  and status != ''\"> and status = #{status}</if>" +
        "ORDER BY order_num ASC",
        "</script>"
    })
    List<FileClass> selectFileClassList(FileClass fileClass);
}
