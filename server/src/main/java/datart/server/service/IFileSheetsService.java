package datart.server.service;

import datart.core.entity.FileSheets;
import datart.core.entity.param.FileSheetsParam;
import datart.core.entity.result.FileSheetsResult;

import java.util.List;

/**
 * 文件工作簿Service接口
 *
 * @author wangya
 * @date 2022-05-27
 */
public interface IFileSheetsService {
    /**
     * 查询文件工作簿
     *
     * @param sheetId 文件工作簿主键
     * @return 文件工作簿
     */
    public FileSheets selectFileSheetsBySheetId(Long sheetId);

    /**
     * 查询文件工作簿列表
     *
     * @param fileSheets 文件工作簿
     * @return 文件工作簿集合
     */
    public List<FileSheets> selectFileSheetsList(FileSheets fileSheets);

    void requirePermission(FileSheets file, int permission);

    /**
     * 新增文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    public int insertFileSheets(FileSheets fileSheets);

    /**
     * 修改文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    public int updateFileSheets(FileSheets fileSheets);

    /**
     * 批量删除文件工作簿
     *
     * @param sheetIds 需要删除的文件工作簿主键集合
     * @return 结果
     */
    public int deleteFileSheetsBySheetIds(String sheetIds);

    /**
     * 删除文件工作簿信息
     *
     * @param sheetId 文件工作簿主键
     * @return 结果
     */
    public int deleteFileSheetsBySheetId(Long sheetId);

    public int deleteSheetsByFileId(Long fileId);

    List<FileSheets> getSheetList(FileSheetsParam fileSheets);

    List<FileSheetsResult> getSheetsByTables(List<String> tables);
}
