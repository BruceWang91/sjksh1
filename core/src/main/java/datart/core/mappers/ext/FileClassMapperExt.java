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
            "create_by, create_time, update_by, update_time, remark ",
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

    @Select({
            "SELECT id FROM (\n" +
                    "SELECT t1.id,\n" +
                    "IF(FIND_IN_SET(parent_id, @pids) > 0, @pids := CONCAT(@pids, ',', id), 0) AS ischild\n" +
                    "FROM (\n" +
                    "SELECT id,parent_id FROM file_class t ORDER BY parent_id, id) t1,\n" +
                    "(SELECT @pids := #{classId}) t2) t3 WHERE ischild != 0"
    })
    List<Long> getAllChiledId(Long classId);
}
