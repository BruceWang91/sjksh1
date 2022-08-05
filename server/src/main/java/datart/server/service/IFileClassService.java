package datart.server.service;

import datart.core.entity.FileClass;

import java.util.List;

/**
 * 文件分类Service接口
 *
 * @author ruoyi
 * @date 2022-06-17
 */
public interface IFileClassService {
    /**
     * 查询文件分类
     *
     * @param id 文件分类主键
     * @return 文件分类
     */
    public FileClass selectFileClassById(Long id);

    /**
     * 查询文件分类列表
     *
     * @param fileClass 文件分类
     * @return 文件分类集合
     */
    public List<FileClass> selectFileClassList(FileClass fileClass);

    /**
     * 新增文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    public int insertFileClass(FileClass fileClass);

    /**
     * 修改文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    public int updateFileClass(FileClass fileClass);

    /**
     * 批量删除文件分类
     *
     * @param ids 需要删除的文件分类主键集合
     * @return 结果
     */
    public int deleteFileClassByIds(Long[] ids);

    /**
     * 删除文件分类信息
     *
     * @param id 文件分类主键
     * @return 结果
     */
    public int deleteFileClassById(Long id);

    List<FileClass> getHierarchyClassification(FileClass fileClass);
}
