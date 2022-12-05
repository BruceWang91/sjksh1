package datart.server.service.impl;

import datart.core.base.consts.Const;
import datart.core.base.exception.Exceptions;
import datart.core.common.FileUtils;
import datart.core.entity.*;
import datart.core.mappers.FileSaveStreamMapper;
import datart.core.mappers.ext.*;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.common.BinUtil;
import datart.server.common.StringUtils;
import datart.server.service.BaseService;
import datart.server.service.IFileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件保存Service业务层处理
 *
 * @author wangya
 * @date 2022-06-17
 */
@Service
public class FileSaveServiceImpl extends BaseService implements IFileSaveService {
    @Autowired
    private FileSaveMapperExt fileSaveMapper;
    @Autowired
    private RelRoleResourceMapperExt rrrMapper;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Autowired
    private DepartmentMapperExt departmentMapperExt;
    @Autowired
    private FileClassMapperExt fileClassMapperExt;
    @Autowired
    private FileSaveStreamMapper fileSaveStreamMapper;
    @Autowired
    private ToolsServiceImpl toolsService;
    @Value("${spring.upload}")
    private String localurl;

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

    @Override
    public FileSave rebuildFile(Long id) {

        FileSave fileSave = fileSaveMapper.selectFileSaveById(id);
        FileSaveStream stream = fileSaveStreamMapper.selectFileSaveStreamByFileSaveId(fileSave.getId());
        String fullFilePath = fileSave.getUrl().replace("/upload/", localurl);
        FileUtils.mkdirParentIfNotExist(fullFilePath);
        try {
            byte[] fileStream = stream.getFileStream();
            String fileName = fileSave.getNewName();
            String path = fullFilePath.replace(fileSave.getNewName(), "");
            BinUtil.binToFile(new String(fileStream, "ISO-8859-1"), fileName, path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String pdfUrl = fileSave.getPdfurl();
        if (StringUtils.isNotEmpty(pdfUrl)) {

            String fullPdfPath = pdfUrl.replace("/upload/", localurl);
            try {
                byte[] pdfStream = stream.getPdfStream();
                String pdfName = fileSave.getPdfName();
                String pdfPath = fullPdfPath.replace(fileSave.getPdfName(), "");
                BinUtil.binToFile(new String(pdfStream, "ISO-8859-1"), pdfName, pdfPath);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return fileSave;
    }

    /**
     * 查询文件保存列表
     *
     * @param fileSave 文件保存
     * @return 文件保存
     */
    @Override
    public List<FileSave> selectFileSaveList(FileSave fileSave) {

        if (null != fileSave.getDeptId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileSave.getDeptId());
            ids.addAll(departmentMapperExt.getAllChiledId(fileSave.getDeptId()));
            fileSave.setDepIds(ids);
        }
        if (null != fileSave.getClassId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileSave.getClassId());
            ids.addAll(fileClassMapperExt.getAllChiledId(fileSave.getClassId()));
            fileSave.setClassIds(ids);
        }
        List<FileSave> files = fileSaveMapper.getList(fileSave);
        if (!CollectionUtils.isEmpty(files)) {
            for (FileSave save : files) {

                if (null != save.getDeptId()) {
                    Department department = toolsService.getCompany(save.getDeptId());
                    if (department != null) {
                        save.setDeptName(department.getDeptName());
                    }
                }
                if (save.getIsFolder() != 1) {
                    String file = save.getFileName().substring(0, save.getFileName().indexOf("."));
                    String suffix = save.getFileName().substring(file.length() + 1, save.getFileName().length());
                    if (suffix.equals("pdf") || StringUtils.isNotEmpty(save.getPdfurl())) {

                        String pdfurl = save.getPdfurl();
                        save.setPdfurl(StringUtils.isEmpty(pdfurl) ? save.getUrl() : pdfurl);
                        save.setCanShowFlag(true);
                    } else {

                        save.setCanShowFlag(false);
                    }
                    String url = save.getUrl().replace("/upload/", localurl);
                    if (!new File(url).exists()) {

                        rebuildFile(save.getId());
                    }
                }
            }
        }
        Map<Long, FileSave> filtered = new HashMap<>();

        List<FileSave> permitted = files.stream().filter(file -> {
            try {
                requirePermission(file, Const.READ);
                return true;
            } catch (Exception e) {
                filtered.put(file.getId(), file);
                return false;
            }
        }).collect(Collectors.toList());

//        while (!filtered.isEmpty()) {
//            boolean updated = false;
//            for (FileSave file : permitted) {
//                FileSave parent = filtered.remove(file.getParentId());
//                if (parent != null) {
//                    permitted.add(parent);
//                    updated = true;
//                    break;
//                }
//            }
//            if (!updated) {
//                break;
//            }
//        }
        return permitted;
    }

    @Override
    public void requirePermission(FileSave file, int permission) {
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
                    ResourceType.FILE + ":" + file.getFileName() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, FileSave file, int permission) {
        if (file.getId() == null || rrrMapper.countRolePermission(file.getId().toString(), role.getId()) == 0) {

            return false;
//            FileSave parent = fileSaveMapper.selectFileSaveById(file.getParentId());
//            if (parent == null) {
//                return securityManager.hasPermission(PermissionHelper.viewPermission(file.getOrgId(), role.getId(), ResourceType.VIEW.name(), permission));
//            } else {
//                return hasPermission(role, parent, permission);
//            }
        } else {
            return securityManager.hasPermission(PermissionHelper.filePermission(file.getOrgId(), role.getId(), file.getId().toString(), permission));
        }
    }

    /**
     * 新增文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Override
    public int insertFileSave(FileSave fileSave) {

        return fileSaveMapper.insertFileSave(fileSave);
    }

    /**
     * 新增文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Override
    public FileSave insertFileSaveFolder(FileSave fileSave) {

        fileSaveMapper.insertFileSave(fileSave);
        return fileSave;
    }

    /**
     * 修改文件保存
     *
     * @param fileSave 文件保存
     * @return 结果
     */
    @Override
    public int updateFileSave(FileSave fileSave) {

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

        int count = 0;
        fileSaveStreamMapper.deleteFileSaveStreamByFileSaveIds(ids);
        count += fileSaveMapper.deleteFileSaveByIds(ids);
        return count;
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
