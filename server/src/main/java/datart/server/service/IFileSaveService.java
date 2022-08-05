package datart.server.service;

import datart.core.entity.FileSave;

import java.util.List;

/**
 * 文件保存Service接口
 *
 * @author ruoyi
 * @date 2022-06-17
 */
public interface IFileSaveService {
    /**
     * 查询文件保存
     *
     * @param id 文件保存主键
     * @return 文件保存
     */
    public FileSave selectFileSaveById(Long id);

    /**
     * 查询文件保存列表
     *
     * @param fileSave 文件保存
     * @return 文件保存集合
     */
    public List<FileSave> selectFileSaveList(FileSave fileSave);

    /**
     * 新增文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    public int insertFileSave(FileSave fileSave);

    /**
     * 修改文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    public int updateFileSave(FileSave fileSave);

    /**
     * 批量删除文件保存
     *
     * @param ids 需要删除的文件保存主键集合
     * @return 结果
     */
    public int deleteFileSaveByIds(Long[] ids);

    /**
     * 删除文件保存信息
     *
     * @param id 文件保存主键
     * @return 结果
     */
    public int deleteFileSaveById(Long id);
}
