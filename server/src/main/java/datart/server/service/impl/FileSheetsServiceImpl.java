package datart.server.service.impl;

import datart.core.base.consts.Const;
import datart.core.base.exception.Exceptions;
import datart.core.entity.FileSheetField;
import datart.core.entity.FileSheets;
import datart.core.entity.Role;
import datart.core.entity.param.FileSheetsParam;
import datart.core.entity.result.FileSheetsResult;
import datart.core.mappers.ext.*;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.common.Convert;
import datart.server.common.DateUtils;
import datart.server.enums.WhetherEnum;
import datart.server.service.BaseService;
import datart.server.service.IFileSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件工作簿Service业务层处理
 *
 * @author wangya
 * @date 2022-05-27
 */
@Service
public class FileSheetsServiceImpl extends BaseService implements IFileSheetsService {
    @Autowired
    private FileSheetsMapperExt fileSheetsMapper;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Autowired
    private DepartmentMapperExt departmentMapperExt;
    @Autowired
    private FileClassMapperExt fileClassMapperExt;
    @Autowired
    private FileSheetFieldMapperExt fileSheetFieldMapper;

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

    @Override
    public void requirePermission(FileSheets file, int permission) {
        if (securityManager.isOrgOwner(file.getOrgId())) {
            return;
        }
        boolean hasPermission;
        if (file.getCreateBy().equals(getCurrentUser().getId())) {
            hasPermission = true;
        } else {
            List<Role> roles = roleMapperExt.selectUserRoles1(getCurrentUser().getId());
            hasPermission = roles.stream().anyMatch(role -> hasPermission(role, file, permission));
        }
        if (!hasPermission) {
            Exceptions.tr(PermissionDeniedException.class, "message.security.permission-denied",
                    ResourceType.EXCEL_VIEW + ":" + file.getSheetId() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, FileSheets file, int permission) {
        int a = rrrMapper.countRolePermission(file.getSheetId().toString(), role.getId());
        if (file.getSheetId() == null || a == 0) {
            return false;
        } else {
            return securityManager.hasPermission(PermissionHelper.fileSheetPermission(file.getOrgId(), role.getId(), file.getSheetId().toString(), permission));
        }
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
        fileSheets.setCreateBy(getCurrentUser().getId());
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
        fileSheets.setUpdateBy(getCurrentUser().getId());
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

        if (null != fileSheets.getDeptId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileSheets.getDeptId());
            ids.addAll(departmentMapperExt.getAllChiledId(fileSheets.getDeptId()));
            fileSheets.setDepIds(ids);
        }
        if (null != fileSheets.getClassId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileSheets.getClassId());
            ids.addAll(fileClassMapperExt.getAllChiledId(fileSheets.getClassId()));
            fileSheets.setClassIds(ids);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(fileSheets.getClassIds())) {
            hashMap.put("classIds", fileSheets.getClassIds());
        }
        if (!CollectionUtils.isEmpty(fileSheets.getDepIds())) {
            hashMap.put("depIds", fileSheets.getDepIds());
        }
        hashMap.put("delFlag", WhetherEnum.NO.getValue());
        if (null != fileSheets.getFileId()) {
            hashMap.put("fileId", fileSheets.getFileId());
        }
        List<FileSheets> list = fileSheetsMapper.getSheetList(hashMap);
        return list;
//        Map<Long, FileSheets> filtered = new HashMap<>();
//        List<FileSheets> permitted = list.stream().filter(file -> {
//            try {
//                requirePermission(file, Const.READ);
//                return true;
//            } catch (Exception e) {
//                filtered.put(file.getSheetId(), file);
//                return false;
//            }
//        }).collect(Collectors.toList());
//
//        return permitted;
    }

    @Override
    public List<FileSheetsResult> getSheetsByTables(List<String> tables) {

        List<FileSheetsResult> list = fileSheetsMapper.getSheetsByTables(tables);
        if (!CollectionUtils.isEmpty(list)) {

            for (FileSheetsResult fileSheetsResult : list) {

                List<FileSheetField> fields = fileSheetFieldMapper.selectFileSheetFieldList(new FileSheetField() {{
                    setSheetId(fileSheetsResult.getSheetId());
                }});
                if (!CollectionUtils.isEmpty(fields)) {

                    fileSheetsResult.setFieldList(fields);
                }
            }
        }
        return list;
    }
}
