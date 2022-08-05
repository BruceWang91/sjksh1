package datart.server.service.impl;

import datart.core.entity.FileSheetField;
import datart.core.mappers.FileSheetFieldMapper;
import datart.core.mappers.ext.FileSheetFieldMapperExt;
import datart.server.common.Convert;
import datart.server.common.DateUtils;
import datart.server.service.IFileSheetFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字段对应Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Service
public class FileSheetFieldServiceImpl implements IFileSheetFieldService {
    @Autowired
    private FileSheetFieldMapperExt fileSheetFieldMapper;

    /**
     * 查询字段对应
     *
     * @param fieldId 字段对应主键
     * @return 字段对应
     */
    @Override
    public FileSheetField selectFileSheetFieldByFieldId(Long fieldId) {
        return fileSheetFieldMapper.selectFileSheetFieldByFieldId(fieldId);
    }

    /**
     * 查询字段对应列表
     *
     * @param fileSheetField 字段对应
     * @return 字段对应
     */
    @Override
    public List<FileSheetField> selectFileSheetFieldList(FileSheetField fileSheetField) {
        return fileSheetFieldMapper.selectFileSheetFieldList(fileSheetField);
    }

    /**
     * 新增字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    @Override
    public int insertFileSheetField(FileSheetField fileSheetField) {
        fileSheetField.setCreateTime(DateUtils.getNowDate());
        return fileSheetFieldMapper.insertFileSheetField(fileSheetField);
    }

    /**
     * 修改字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    @Override
    public int updateFileSheetField(FileSheetField fileSheetField) {
        fileSheetField.setUpdateTime(DateUtils.getNowDate());
        return fileSheetFieldMapper.updateFileSheetField(fileSheetField);
    }

    /**
     * 批量删除字段对应
     *
     * @param fieldIds 需要删除的字段对应主键
     * @return 结果
     */
    @Override
    public int deleteFileSheetFieldByFieldIds(String fieldIds) {
        return fileSheetFieldMapper.deleteFileSheetFieldByFieldIds(Convert.toStrArray(fieldIds));
    }

    /**
     * 删除字段对应信息
     *
     * @param fieldId 字段对应主键
     * @return 结果
     */
    @Override
    public int deleteFileSheetFieldByFieldId(Long fieldId) {
        return fileSheetFieldMapper.deleteFileSheetFieldByFieldId(fieldId);
    }

    @Override
    public int deleteFieldsByFileId(Long fileId) {
        return fileSheetFieldMapper.deleteFieldsByFileId(fileId);
    }
}
