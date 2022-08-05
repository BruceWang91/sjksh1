package datart.server.service;

import datart.core.entity.FileSheetField;

import java.util.List;

/**
 * 字段对应Service接口
 *
 * @author ruoyi
 * @date 2022-05-27
 */
public interface IFileSheetFieldService {
    /**
     * 查询字段对应
     *
     * @param fieldId 字段对应主键
     * @return 字段对应
     */
    public FileSheetField selectFileSheetFieldByFieldId(Long fieldId);

    /**
     * 查询字段对应列表
     *
     * @param fileSheetField 字段对应
     * @return 字段对应集合
     */
    public List<FileSheetField> selectFileSheetFieldList(FileSheetField fileSheetField);

    /**
     * 新增字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    public int insertFileSheetField(FileSheetField fileSheetField);

    /**
     * 修改字段对应
     *
     * @param fileSheetField 字段对应
     * @return 结果
     */
    public int updateFileSheetField(FileSheetField fileSheetField);

    /**
     * 批量删除字段对应
     *
     * @param fieldIds 需要删除的字段对应主键集合
     * @return 结果
     */
    public int deleteFileSheetFieldByFieldIds(String fieldIds);

    /**
     * 删除字段对应信息
     *
     * @param fieldId 字段对应主键
     * @return 结果
     */
    public int deleteFileSheetFieldByFieldId(Long fieldId);

    public int deleteFieldsByFileId(Long fileId);
}
