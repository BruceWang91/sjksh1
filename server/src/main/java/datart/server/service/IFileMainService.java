package datart.server.service;

import com.alibaba.fastjson2.JSONArray;
import datart.core.entity.FileMain;
import datart.core.entity.FileSheetField;
import datart.core.entity.param.FileMainParam;
import datart.core.entity.result.FileMainResult;
import datart.core.entity.result.FileSheetsResult;

import java.util.HashMap;
import java.util.List;

/**
 * 文件管理Service接口
 *
 * @author wangya
 * @date 2022-05-27
 */
public interface IFileMainService {
    /**
     * 查询文件管理
     *
     * @param fileId 文件管理主键
     * @return 文件管理
     */
    public FileMain selectFileMainByFileId(Long fileId);

    /**
     * 查询文件管理列表
     *
     * @param fileMain 文件管理
     * @return 文件管理集合
     */
    public List<FileMain> selectFileMainList(FileMain fileMain);

    /**
     * 新增文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    public int insertFileMain(FileMain fileMain);

    /**
     * 修改文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    public int updateFileMain(FileMain fileMain);

    /**
     * 批量删除文件管理
     *
     * @param fileIds 需要删除的文件管理主键集合
     * @return 结果
     */
    public int deleteFileMainByFileIds(String fileIds);

    /**
     * 删除文件管理信息
     *
     * @param fileId 文件管理主键
     * @return 结果
     */
    public int deleteFileMainByFileId(Long fileId);

    void requirePermission(FileMain file, int permission);

    void createTale(String sourceId, String biname, List<FileSheetField> list);

    void splitTableImport(HashMap<String, Object> hashMap);

    int batchUpdateForFileId(FileMainParam fileMainParam);

    FileMainResult selectForFileId(Long fileId);

    JSONArray getSheetData(Long sheetId);

    FileSheetsResult selectForSheetId(Long sheetId);

    List<FileMain> getList(FileMain fileMain);

    int updateFileMainParentId(FileMain fileMain);

    FileMain insertFileMainFolder(FileMain fileMain);

    List<HashMap<String, Object>> selectByBiname(String sourceId, HashMap<String, Object> map);
}
