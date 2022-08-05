package datart.server.service.impl;

import datart.core.entity.FileSave;
import datart.core.mappers.FileSaveMapper;
import datart.core.mappers.ext.FileSaveMapperExt;
import datart.server.common.DateUtils;
import datart.server.service.IFileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件保存Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-17
 */
@Service
public class FileSaveServiceImpl implements IFileSaveService {
    @Autowired
    private FileSaveMapperExt fileSaveMapper;

    /**
     * 查询文件保存
     *
     * @param id 文件保存主键
     * @return 文件保存
     */
    @Override
    public FileSave selectFileSaveById(Long id) {
        return fileSaveMapper.selectFileSaveById(id);
    }

    /**
     * 查询文件保存列表
     *
     * @param fileSave 文件保存
     * @return 文件保存
     */
    @Override
    public List<FileSave> selectFileSaveList(FileSave fileSave) {
        return fileSaveMapper.getList(fileSave);
    }

    /**
     * 新增文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Override
    public int insertFileSave(FileSave fileSave) {
        fileSave.setCreateTime(DateUtils.getNowDate());
        return fileSaveMapper.insertFileSave(fileSave);
    }

    /**
     * 修改文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Override
    public int updateFileSave(FileSave fileSave) {
        fileSave.setUpdateTime(DateUtils.getNowDate());
        return fileSaveMapper.updateFileSave(fileSave);
    }

    /**
     * 批量删除文件保存
     *
     * @param ids 需要删除的文件保存主键
     * @return 结果
     */
    @Override
    public int deleteFileSaveByIds(Long[] ids) {
        return fileSaveMapper.deleteFileSaveByIds(ids);
    }

    /**
     * 删除文件保存信息
     *
     * @param id 文件保存主键
     * @return 结果
     */
    @Override
    public int deleteFileSaveById(Long id) {
        return fileSaveMapper.deleteFileSaveById(id);
    }
}
