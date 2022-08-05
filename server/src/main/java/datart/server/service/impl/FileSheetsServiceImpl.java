package datart.server.service.impl;

import datart.core.entity.FileSheets;
import datart.core.entity.param.FileSheetsParam;
import datart.core.mappers.FileSheetsMapper;
import datart.core.mappers.ext.FileSheetsMapperExt;
import datart.server.common.Convert;
import datart.server.common.DateUtils;
import datart.server.service.IFileSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 文件工作簿Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Service
public class FileSheetsServiceImpl implements IFileSheetsService {
    @Autowired
    private FileSheetsMapperExt fileSheetsMapper;

    /**
     * 查询文件工作簿
     *
     * @param sheetId 文件工作簿主键
     * @return 文件工作簿
     */
    @Override
    public FileSheets selectFileSheetsBySheetId(Long sheetId) {
        return fileSheetsMapper.selectFileSheetsBySheetId(sheetId);
    }

    /**
     * 查询文件工作簿列表
     *
     * @param fileSheets 文件工作簿
     * @return 文件工作簿
     */
    @Override
    public List<FileSheets> selectFileSheetsList(FileSheets fileSheets) {
        return fileSheetsMapper.selectFileSheetsList(fileSheets);
    }

    /**
     * 新增文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    @Override
    public int insertFileSheets(FileSheets fileSheets) {
        fileSheets.setCreateTime(DateUtils.getNowDate());
        return fileSheetsMapper.insertFileSheets(fileSheets);
    }

    /**
     * 修改文件工作簿
     *
     * @param fileSheets 文件工作簿
     * @return 结果
     */
    @Override
    public int updateFileSheets(FileSheets fileSheets) {
        fileSheets.setUpdateTime(DateUtils.getNowDate());
        return fileSheetsMapper.updateFileSheets(fileSheets);
    }

    /**
     * 批量删除文件工作簿
     *
     * @param sheetIds 需要删除的文件工作簿主键
     * @return 结果
     */
    @Override
    public int deleteFileSheetsBySheetIds(String sheetIds) {
        return fileSheetsMapper.deleteFileSheetsBySheetIds(Convert.toStrArray(sheetIds));
    }

    /**
     * 删除文件工作簿信息
     *
     * @param sheetId 文件工作簿主键
     * @return 结果
     */
    @Override
    public int deleteFileSheetsBySheetId(Long sheetId) {
        return fileSheetsMapper.deleteFileSheetsBySheetId(sheetId);
    }

    @Override
    public int deleteSheetsByFileId(Long fileId) {
        return fileSheetsMapper.deleteSheetsByFileId(fileId);
    }

    /**
     * 通过分类id查询分类列表
     *
     * @param fileSheets
     * @return
     */
    @Override
    public List<FileSheets> getSheetList(FileSheetsParam fileSheets) {

        HashMap<String, Object> hashMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(fileSheets.getClassIds())) {
            hashMap.put("classIds", fileSheets.getClassIds());
        }
        if (!CollectionUtils.isEmpty(fileSheets.getDepIds())) {
            hashMap.put("depIds", fileSheets.getDepIds());
        }
        return fileSheetsMapper.getSheetList(hashMap);
    }
}
