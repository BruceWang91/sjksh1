package datart.server.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSONArray;
import datart.core.base.consts.Const;
import datart.core.base.exception.Exceptions;
import datart.core.entity.*;
import datart.core.entity.param.FileMainParam;
import datart.core.entity.param.FileSheetsParam;
import datart.core.entity.result.FileMainResult;
import datart.core.entity.result.FileSheetsResult;
import datart.core.mappers.ext.DepartmentMapperExt;
import datart.core.mappers.ext.FileClassMapperExt;
import datart.core.mappers.ext.FileMainMapperExt;
import datart.core.mappers.ext.RoleMapperExt;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.common.Convert;
import datart.server.common.DateUtils;
import datart.server.common.PinyinHelperUtil;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.enums.WhetherEnum;
import datart.server.service.BaseService;
import datart.server.service.IFileMainService;
import datart.server.service.IFileSheetFieldService;
import datart.server.service.IFileSheetsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件管理Service业务层处理
 *
 * @author wangya
 * @date 2022-05-27
 */
@Service
public class FileMainServiceImpl extends BaseService implements IFileMainService {
    @Autowired
    private FileMainMapperExt fileMainMapper;
    @Autowired
    private IFileSheetsService fileSheetsService;
    @Autowired
    private IFileSheetFieldService fileSheetFieldService;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Autowired
    private DepartmentMapperExt departmentMapperExt;
    @Autowired
    private FileClassMapperExt fileClassMapperExt;
    @Autowired
    private ToolsServiceImpl toolsService;

    /**
     * 查询文件管理
     *
     * @param fileId 文件管理主键
     * @return 文件管理
     */
    @Override
    public FileMain selectFileMainByFileId(Long fileId) {
        return fileMainMapper.selectFileMainByFileId(fileId);
    }

    /**
     * 查询文件管理列表
     *
     * @param fileMain 文件管理
     * @return 文件管理
     */
    @Override
    public List<FileMain> selectFileMainList(FileMain fileMain) {
        return fileMainMapper.selectFileMainList(fileMain);
    }

    /**
     * 新增文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    @Override
    public int insertFileMain(FileMain fileMain) {

        fileMain.setCreateTime(DateUtils.getNowDate());
        fileMain.setCreateBy(getCurrentUser().getId());
        return fileMainMapper.insertFileMain(fileMain);
    }

    /**
     * 新增文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    @Override
    public FileMain insertFileMainFolder(FileMain fileMain) {

        fileMain.setCreateTime(DateUtils.getNowDate());
        fileMain.setCreateBy(getCurrentUser().getId());
        fileMainMapper.insertFileMain(fileMain);
        return fileMain;
    }

    /**
     * 修改文件管理
     *
     * @param fileMain 文件管理
     * @return 结果
     */
    @Override
    public int updateFileMain(FileMain fileMain) {

        fileMain.setUpdateTime(DateUtils.getNowDate());
        fileMain.setUpdateBy(getCurrentUser().getId());
        return fileMainMapper.updateFileMain(fileMain);
    }

    /**
     * 修改表样上级
     *
     * @param fileMain
     * @return
     */
    @Override
    public int updateFileMainParentId(FileMain fileMain) {

        FileMain file = new FileMain();
        file.setFileId(fileMain.getFileId());
        file.setParentId(fileMain.getParentId());
        file.setFileName(fileMain.getFileName());
        file.setUpdateTime(DateUtils.getNowDate());
        file.setUpdateBy(getCurrentUser().getId());
        return fileMainMapper.updateFileMain(file);
    }

    /**
     * 批量删除文件管理
     *
     * @param fileIds 需要删除的文件管理主键
     * @return 结果
     */
    @Override
    public int deleteFileMainByFileIds(String fileIds) {

        int count = 0;
        String[] ids = fileIds.split(",");
        for (String id : ids) {

            Long fileId = Long.parseLong(id);
            List<FileSheets> fileSheets = fileSheetsService.selectFileSheetsList(new FileSheets() {{
                setFileId(fileId);
            }});
            FileMain fileMain = fileMainMapper.selectFileMainByFileId(fileId);
            if (!CollectionUtils.isEmpty(fileSheets)) {

                for (FileSheets fileSheet : fileSheets) {

                    String tableName = fileSheet.getEntityName();
//                    SpringUtils.getAopProxy(this).dropTable(fileMain.getSourceId(), tableName);
                    dropTable(fileMain.getSourceId(), tableName);
                }
            }
            count += fileSheetFieldService.deleteFieldsByFileId(fileId);
            count += fileSheetsService.deleteSheetsByFileId(fileId);
        }
        count += fileMainMapper.deleteFileMainByFileIds(Convert.toStrArray(fileIds));
        return count;
    }

    /**
     * 删除文件管理信息
     *
     * @param fileId 文件管理主键
     * @return 结果
     */
    @Override
    public int deleteFileMainByFileId(Long fileId) {
        return fileMainMapper.deleteFileMainByFileId(fileId);
    }

    /**
     * 导入表样配置
     *
     * @param fileMainParam
     * @return
     */
    @Override
    public int batchUpdateForFileId(FileMainParam fileMainParam) {

        // 创建和修改文件相关工作簿及字段内容
        Long fileId = fileMainParam.getFileId();
//        String userId = SecurityUtils.getUserId().toString();
        Date nowdate = DateUtils.getNowDate();
        String sourceId = "";
        if (null == fileId) {

            FileMain fileMain = new FileMain();
            BeanUtils.copyProperties(fileMainParam, fileMain);
            fileMain.setCreateBy(getCurrentUser().getId());
            fileMain.setCreateTime(nowdate);
            fileMainMapper.insertFileMain(fileMain);
            sourceId = fileMain.getSourceId();
            fileId = fileMain.getFileId();
        } else {

            FileMain fileMain = fileMainMapper.selectFileMainByFileId(fileId);
            fileMain.setUpdateTime(nowdate);
            fileMain.setUpdateBy(getCurrentUser().getId());
            fileMainMapper.updateFileMain(fileMain);
            sourceId = fileMain.getSourceId();
            Long fid = fileId;
            List<FileSheets> fileSheetsList = fileSheetsService.selectFileSheetsList(new FileSheets() {{
                setFileId(fid);
                setDelFlag(WhetherEnum.NO.getValue().toString());
            }});
            if (!CollectionUtils.isEmpty(fileSheetsList)) {

                for (FileSheets fileSheets : fileSheetsList) {

                    fileSheets.setDelFlag(WhetherEnum.YES.getValue().toString());
                    fileSheets.setUpdateBy(getCurrentUser().getId());
                    fileSheets.setUpdateTime(nowdate);
                    fileSheetsService.updateFileSheets(fileSheets);
                }
            }
        }
        List<FileSheetsParam> sheetsParamList = fileMainParam.getSheets();
        int count = 0;
        if (!CollectionUtils.isEmpty(sheetsParamList)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String datestr = sdf.format(date);
            for (FileSheetsParam fileSheetsParam : sheetsParamList) {

                FileSheets fileSheets = new FileSheets();
                BeanUtils.copyProperties(fileSheetsParam, fileSheets);
                fileSheets.setOrgId(fileMainParam.getOrgId());
                fileSheets.setFileId(fileId);

                String biname = "ODS_HXCY_" + PinyinHelperUtil.getPinYinHeadChar(fileSheets.getSheetName(), true) + datestr;

                fileSheets.setEntityName(biname);
                fileSheets.setCreateBy(getCurrentUser().getId());
                fileSheets.setCreateTime(nowdate);
                count += fileSheetsService.insertFileSheets(fileSheets);
                Long fileSheetId = fileSheets.getSheetId();
                List<FileSheetField> fieldList = fileSheetsParam.getFieldList();
                if (!CollectionUtils.isEmpty(fieldList)) {

                    for (FileSheetField fileSheetField : fieldList) {

                        if (WhetherEnum.YES.getValue().toString().equals(fileSheetField.getStatus())) {

                            String entityFieldName = PinyinHelperUtil.getPinYinHeadChar(fileSheetField.getCellName(), false);
                            fileSheetField.setEntityField(entityFieldName + fileSheetField.getCellNum());
                        }
                        fileSheetField.setSheetId(fileSheetId);
                        fileSheetField.setFileId(fileId);
                        count += fileSheetFieldService.insertFileSheetField(fileSheetField);
                    }
                }
//                SpringUtils.getAopProxy(this).createTale(sourceId, biname, fieldList);
                createTale(sourceId, biname, fieldList);
            }
        }
        return count;
    }

    /**
     * 查询文件内容
     *
     * @param fileId
     * @return
     */
    @Override
    public FileMainResult selectForFileId(Long fileId) {

        if (null == fileId) {
            return null;
//            throw new ServiceException("文件ID为空，无法查看");
        }
        FileMain fileMain = fileMainMapper.selectFileMainByFileId(fileId);
        FileMainResult result = new FileMainResult();
        BeanUtils.copyProperties(fileMain, result);
        List<FileSheets> fileSheetsList = fileSheetsService.selectFileSheetsList(new FileSheets() {{
            setFileId(fileId);
            setDelFlag(WhetherEnum.NO.getValue().toString());
        }});
        List<FileSheetsResult> sheetsResultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fileSheetsList)) {

            for (FileSheets fileSheets : fileSheetsList) {

                FileSheetsResult sheetsResult = new FileSheetsResult();
                BeanUtils.copyProperties(fileSheets, sheetsResult);
                List<FileSheetField> fieldList = fileSheetFieldService.selectFileSheetFieldList(new FileSheetField() {{
                    setSheetId(fileSheets.getSheetId());
                    setFileId(fileId);
                }});
                sheetsResult.setFieldList(fieldList);
                sheetsResultList.add(sheetsResult);
            }
        }
        if (!CollectionUtils.isEmpty(sheetsResultList)) {

            result.setSheets(sheetsResultList);
        }
        return result;
    }

    /**
     * 查询工作簿内容
     *
     * @param sheetId
     * @return
     */
    @Override
    public FileSheetsResult selectForSheetId(Long sheetId) {
        if (null == sheetId) {
//            throw new ServiceException("工作簿ID为空，无法查询");
            return null;
        }
        FileSheets sheet = fileSheetsService.selectFileSheetsBySheetId(sheetId);
        if (null == sheet) {
//            throw new ServiceException("查询不到该工作簿");
            return null;
        }
        FileSheetsResult result = new FileSheetsResult();
        BeanUtils.copyProperties(sheet, result);
        if (null != result) {

            List<FileSheetField> fieldList = fileSheetFieldService.selectFileSheetFieldList(new FileSheetField() {
                {
                    setSheetId(sheetId);
                }
            });
            if (!CollectionUtils.isEmpty(fieldList)) {

                result.setFieldList(fieldList);
            }
        }
        return result;
    }

    @Override
    public List<FileMain> getList(FileMain fileMain) {

        if (null != fileMain.getDeptId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileMain.getDeptId());
            ids.addAll(departmentMapperExt.getAllChiledId(fileMain.getDeptId()));
            fileMain.setDepIds(ids);
        }
        if (null != fileMain.getClassId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(fileMain.getClassId());
            ids.addAll(fileClassMapperExt.getAllChiledId(fileMain.getClassId()));
            fileMain.setClassIds(ids);
        }
        List<FileMain> list = fileMainMapper.getList(fileMain);
        if (!CollectionUtils.isEmpty(list)) {
            for (FileMain main : list) {
                if (null != main.getDeptId()) {
                    Department department = toolsService.getCompany(main.getDeptId());
                    if (null != department) {
                        main.setDeptName(department.getDeptName());
                    }
                }
            }
        }
        Map<Long, FileMain> filtered = new HashMap<>();

        List<FileMain> permitted = list.stream().filter(file -> {
            try {
                requirePermission(file, Const.READ);
                return true;
            } catch (Exception e) {
                filtered.put(file.getFileId(), file);
                return false;
            }
        }).collect(Collectors.toList());

//        while (!filtered.isEmpty()) {
//            boolean updated = false;
//            for (FileMain file : permitted) {
//                FileMain parent = filtered.remove(file.getParentId());
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
    public void requirePermission(FileMain file, int permission) {
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
                    ResourceType.EXCEL_TEMPLATE + ":" + file.getFileId() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, FileMain file, int permission) {
        if (file.getFileId() == null || rrrMapper.countRolePermission(file.getFileId().toString(), role.getId()) == 0) {

            return false;
//            FileMain parent = fileMainMapper.selectFileMainByFileId(file.getParentId());
//            if (parent == null) {
//                return securityManager.hasPermission(PermissionHelper.viewPermission(file.getOrgId(), role.getId(), ResourceType.VIEW.name(), permission));
//            } else {
//                return hasPermission(role, parent, permission);
//            }
        } else {
            return securityManager.hasPermission(PermissionHelper.fileMainPermission(file.getOrgId(), role.getId(), file.getFileId().toString(), permission));
        }
    }

    /**
     * 切换数据源建立表样对应数据库表
     *
     * @param biname
     * @param list
     */
    @Override
    public void createTale(String sourceId, String biname, List<FileSheetField> list) {

        StringBuffer sqlstr = new StringBuffer();
        for (FileSheetField fileSheetField : list) {

            if (WhetherEnum.YES.getValue().toString().equals(fileSheetField.getStatus())) {

                sqlstr.append(fileSheetField.getEntityField() + " VARCHAR(300) DEFAULT NULL COMMENT '" + fileSheetField.getCellName() + "',");
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sqlstr", sqlstr.toString());
        map.put("biname", biname);
        toolsService.changeConnection(sourceId);
        fileMainMapper.createTable(map);
        DynamicDataSource.clear();
    }

    /**
     * 导入数据到不同数据表内
     *
     * @param hashMap
     */
    @Override
    public void splitTableImport(HashMap<String, Object> hashMap) {

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr = format.format(now);
        Long fileId = (Long) hashMap.get("fileId");
        FileMain fileMain = fileMainMapper.selectFileMainByFileId(fileId);
        List<FileSheets> sheets = fileSheetsService.selectFileSheetsList(new FileSheets() {{
            setFileId(fileId);
            setDelFlag(WhetherEnum.NO.getValue().toString());
        }});
        Set<String> stringSet = hashMap.keySet();
        for (String s : stringSet) {

            if (!s.equals("fileId")) {

                String biname = s;
                StringBuffer keystr = new StringBuffer();
                StringBuffer valuestr = new StringBuffer();
                for (FileSheets sheet : sheets) {

                    if (s.equals(sheet.getEntityName())) {

                        List<HashMap<String, Object>> objectList = (List<HashMap<String, Object>>) hashMap.get(s);
                        if (!CollectionUtils.isEmpty(objectList)) {
                            List<FileSheetField> fields = fileSheetFieldService.selectFileSheetFieldList(new FileSheetField() {{
                                setSheetId(sheet.getSheetId());
                                setStatus(WhetherEnum.YES.getValue().toString());
                            }});

                            for (int i = 0; i < fields.size(); i++) {

                                String entityField = fields.get(i).getEntityField();
                                if (i == 0) {

                                    keystr.append(entityField);
                                } else {

                                    keystr.append("," + entityField);
                                }
                            }
                            keystr.append(",create_by");
                            keystr.append(",create_time");

                            for (int j = 0; j < objectList.size(); j++) {

                                if (j == 0) {

                                    valuestr.append("(");
                                } else {

                                    valuestr.append(",(");
                                }
                                for (int i = 0; i < fields.size(); i++) {

                                    String field = StringUtils.isEmpty(objectList.get(j).get(fields.get(i).getEntityField()).toString()) ? " " : objectList.get(j).get(fields.get(i).getEntityField()).toString();
                                    if (i == 0) {

                                        valuestr.append("'" + field + "'");
                                    } else {

                                        valuestr.append(",'" + field + "'");
                                    }
                                }
                                valuestr.append(",'" + getCurrentUser().getId() + "'");
                                valuestr.append(",'" + datestr + "'");
                                valuestr.append(")");
                            }
                        }
                    }
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("biname", biname);
                map.put("keystr", keystr.toString());
                map.put("valuestr", valuestr.toString());
//                SpringUtils.getAopProxy(this).insertTable(fileMain.getSourceId(), map);
                if (keystr.toString() != null && keystr.toString().length() > 0 && valuestr.toString() != null && valuestr.toString().length() > 0) {
                    insertTable(fileMain.getSourceId(), map);
                }
            }
        }
    }

    /**
     * 切换数据源插入数据
     *
     * @param map
     */
    public void insertTable(String sourceId, HashMap<String, Object> map) {

        toolsService.changeConnection(sourceId);
        fileMainMapper.insertTable(map);
        DynamicDataSource.clear();
    }

    /**
     * 查询已导入工作簿的内容
     *
     * @param sheetId
     * @return
     */
    @Override
    public JSONArray getSheetData(Long sheetId) {

        if (null == sheetId) {
            throw new RuntimeException("工作簿ID不能为空！");
        }
        FileSheets sheet = fileSheetsService.selectFileSheetsBySheetId(sheetId);
        FileMain fileMain = fileMainMapper.selectFileMainByFileId(sheet.getFileId());
        String domainName = sheet.getEntityName();
        if (!StringUtils.isEmpty(domainName)) {

            List<HashMap<String, Object>> objectList = new ArrayList<>();
            objectList = selectByBiname(fileMain.getSourceId(), new HashMap<String, Object>() {{
                put("biname", domainName);
            }});
            JSONArray array = new JSONArray();
            if (!CollectionUtils.isEmpty(objectList)) {
                array = JSONArray.of(objectList);
            }
            return array;
        }
        return null;
    }

    /**
     * 切换数据源查询表内容
     *
     * @param map
     * @return
     */
    @Override
    public List<HashMap<String, Object>> selectByBiname(String sourceId, HashMap<String, Object> map) {

        toolsService.changeConnection(sourceId);
        List<HashMap<String, Object>> list = fileMainMapper.selectByBiname(map);
        DynamicDataSource.clear();
        return list;
    }

    /**
     * 切换数据源清空表
     *
     * @param tableName
     */
    public void dropTable(String sourceId, String tableName) {

        toolsService.changeConnection(sourceId);
        fileMainMapper.dropTable(tableName);
        DynamicDataSource.clear();
    }
}
