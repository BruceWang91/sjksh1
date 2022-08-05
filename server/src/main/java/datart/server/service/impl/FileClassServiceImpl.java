package datart.server.service.impl;

import datart.core.entity.FileClass;
import datart.core.mappers.FileClassMapper;
import datart.core.mappers.ext.FileClassMapperExt;
import datart.server.common.DateUtils;
import datart.server.service.BaseService;
import datart.server.service.IFileClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 文件分类Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-17
 */
@Service
public class FileClassServiceImpl extends BaseService implements IFileClassService {
    @Autowired
    private FileClassMapperExt fileClassMapper;

    /**
     * 根节点CODE
     */
    private static final Long TREE_ROOT_CODE = 0L;

    /**
     * 查询文件分类
     *
     * @param id 文件分类主键
     * @return 文件分类
     */
    @Override
    public FileClass selectFileClassById(Long id) {
        return fileClassMapper.selectFileClassById(id);
    }

    /**
     * 查询文件分类列表
     *
     * @param fileClass 文件分类
     * @return 文件分类
     */
    @Override
    public List<FileClass> selectFileClassList(FileClass fileClass) {
        return fileClassMapper.selectFileClassList(fileClass);
    }

    /**
     * 新增文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    @Override
    public int insertFileClass(FileClass fileClass) {
        fileClass.setCreateTime(DateUtils.getNowDate());
        fileClass.setCreateBy(getCurrentUser().getId());
        return fileClassMapper.insertFileClass(fileClass);
    }

    /**
     * 修改文件分类
     *
     * @param fileClass 文件分类
     * @return 结果
     */
    @Override
    public int updateFileClass(FileClass fileClass) {
        fileClass.setUpdateTime(DateUtils.getNowDate());
        fileClass.setUpdateBy(getCurrentUser().getId());
        return fileClassMapper.updateFileClass(fileClass);
    }

    /**
     * 批量删除文件分类
     *
     * @param ids 需要删除的文件分类主键
     * @return 结果
     */
    @Override
    public int deleteFileClassByIds(Long[] ids) {
        return fileClassMapper.deleteFileClassByIds(ids);
    }

    /**
     * 删除文件分类信息
     *
     * @param id 文件分类主键
     * @return 结果
     */
    @Override
    public int deleteFileClassById(Long id) {
        return fileClassMapper.deleteFileClassById(id);
    }

    /**
     * 获取分类层级
     *
     * @param fileClass
     * @return
     */
    @Override
    public List<FileClass> getHierarchyClassification(FileClass fileClass) {

        return getTree(fileClass);
    }

    /**
     * 标准版
     */
    public List<FileClass> getTree(FileClass fileClass) {

        List<FileClass> record = fileClassMapper.selectFileClassList(fileClass);
        List<FileClass> treeList = new LinkedList();
        for (FileClass class1 : record) {
            if (TREE_ROOT_CODE == class1.getParentId()) {

                class1.setLowClasses(getChild(class1.getId()));
                treeList.add(class1);
            }
        }
        return treeList;
    }

    private List<FileClass> getChild(Long code) {

        List<FileClass> record = fileClassMapper.selectFileClassList(new FileClass() {{
            setParentId(code);
        }});
        List<FileClass> childrenList = new LinkedList();
        for (FileClass sysDept : record) {
            if (code.equals(sysDept.getParentId())) {
                sysDept.setLowClasses(getChild(sysDept.getId()));
                childrenList.add(sysDept);
            }
        }
        return childrenList;
    }
}
