package datart.core.mappers.ext;

import datart.core.entity.FileMain;
import datart.core.mappers.FileMainMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface FileMainMapperExt extends FileMainMapper {

    /**
     * 查询文件管理列表
     *
     * @param fileMain 文件管理
     * @return 文件管理集合
     */
    @Select({
            "<script>",
            "select file_id, file_name, order_num, status, del_flag, " +
                    "class_id, create_by, create_time, update_by, update_time, remark " +
                    "from file_main where 1=1",
                    "<if test=\"fileName != null  and fileName != ''\"> and file_name like concat('%', #{fileName}, '%')</if>\n" +
                    "<if test=\"orderNum != null \"> and order_num = #{orderNum}</if>\n" +
                    "<if test=\"status != null  and status != ''\"> and status = #{status}</if>\n" +
                    "<if test=\"classId != null \"> and class_id = #{classId}</if>",
            "</script>"
    })
    List<FileMain> selectFileMainList(FileMain fileMain);

    @Select({
            "<script>",
            "SELECT\n" +
                    "        filemain.*\n" +
                    "        FROM file_main filemain\n" +
                    "        LEFT JOIN sys_user suser\n" +
                    "            ON filemain.create_by = suser.user_id\n" +
                    "        WHERE 1=1\n" +
                    "        <if test=\"fileName != null  and fileName != ''\">\n" +
                    "            and file_name like concat('%', #{fileName}, '%')\n" +
                    "        </if>\n" +
                    "        <if test=\"classIds != null and classIds.size() > 0\">\n" +
                    "            AND filemain.class_id IN\n" +
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
    List<FileMain> getList(FileMain fileMain);

    @Update({
            "create table ${biname} (\n" +
                    "            id bigint(20)      not null auto_increment,\n" +
                    "            ${sqlstr}\n" +
                    "            primary key (id)\n" +
                    "        )engine=innodb auto_increment=200"
    })
    void createTable(HashMap<String, Object> map);

    @Insert({
            "insert into ${biname} (${keystr}) value ${valuestr}"
    })
    void insertTable(HashMap<String, Object> map);

    @Select({
            "select * from ${biname} order by id"
    })
    List<HashMap<String, Object>> selectByBiname(HashMap<String, Object> biname);

    @Update({
            "drop table ${tableName}"
    })
    void dropTable(String tableName);
}
