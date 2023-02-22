package datart.server.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import datart.core.base.consts.Const;
import datart.core.base.exception.Exceptions;
import datart.core.entity.Organization;
import datart.core.entity.Role;
import datart.core.entity.TableImport;
import datart.core.entity.TableImportField;
import datart.core.entity.param.TableImportFieldParam;
import datart.core.entity.param.TableImportParam;
import datart.core.mappers.TableImportFieldMapper;
import datart.core.mappers.TableImportMapper;
import datart.core.mappers.ext.DepartmentMapperExt;
import datart.core.mappers.ext.OrganizationMapperExt;
import datart.core.mappers.ext.RoleMapperExt;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.common.Convert;
import datart.server.common.StringUtils;
import datart.server.config.datasource.DynamicDataSource;
import datart.server.service.BaseService;
import datart.server.service.TableImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableImportServiceImpl extends BaseService implements TableImportService {
    @Autowired
    private TableImportMapper tableImportMapper;
    @Autowired
    private TableImportFieldMapper tableImportFieldMapper;
    @Autowired
    private ToolsServiceImpl toolsService;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Autowired
    private OrganizationMapperExt organizationMapper;
    @Autowired
    private DepartmentMapperExt departmentMapperExt;

    @Override
    public List<TableImport> tableImportList(TableImport tableImport) {

        List<TableImport> tableList = tableImportMapper.selectTableImportList(tableImport);
        if (!CollectionUtils.isEmpty(tableList)) {

            for (TableImport table : tableList) {

                List<TableImportField> list = tableImportFieldMapper.selectTableImportFieldList(new TableImportField() {{
                    setTableImportId(table.getId());
                }});
                table.setList(list);
            }
        }
//        return tableList;
        Map<Long, TableImport> filtered = new HashMap<>();
        List<TableImport> permitted = tableList.stream().filter(file -> {
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

    public void requirePermission(TableImport file, int permission) {
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
                    ResourceType.IMM_DATA + ":" + file.getId() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, TableImport file, int permission) {
        if (file.getId() == null || rrrMapper.countRolePermission(file.getId().toString(), role.getId()) == 0) {

            return false;
//            FileMain parent = fileMainMapper.selectFileMainByFileId(file.getParentId());
//            if (parent == null) {
//                return securityManager.hasPermission(PermissionHelper.viewPermission(file.getOrgId(), role.getId(), ResourceType.VIEW.name(), permission));
//            } else {
//                return hasPermission(role, parent, permission);
//            }
        } else {
            return securityManager.hasPermission(PermissionHelper.tableImportPermission(file.getOrgId(), role.getId(), file.getId().toString(), permission));
        }
    }


    @Override
    public TableImport selectTableImportById(Long id) {

        TableImport tableImport = tableImportMapper.selectTableImportById(id);
        if (tableImport != null) {

            List<TableImportField> list = tableImportFieldMapper.selectTableImportFieldList(new TableImportField() {{
                setTableImportId(id);
            }});
            tableImport.setList(list);
        }
        return tableImport;
    }

    @Override
    @Transactional
    public TableImport add(TableImport tableImport) {

        Date now = new Date();
        String userId = getCurrentUser().getId();
        tableImport.setCreateBy(userId);
        tableImport.setCreateTime(now);
        tableImportMapper.insertTableImport(tableImport);
        List<TableImportField> list = tableImport.getList();
        if (!CollectionUtils.isEmpty(list)) {
            for (TableImportField tableImportField : list) {

                tableImportField.setTableImportId(tableImport.getId());
                tableImportField.setCreateBy(userId);
                tableImportField.setCreateTime(now);
                tableImportFieldMapper.insertTableImportField(tableImportField);
            }
        }
        return tableImport;
    }

    @Override
    public int update(TableImport tableImport) {

        Date now = new Date();
        String userId = getCurrentUser().getId();
        tableImport.setUpdateBy(userId);
        tableImport.setUpdateTime(now);
        Long tableImportId = tableImport.getId();
        // 已存储的字段
        List<TableImportField> list = tableImportFieldMapper.selectTableImportFieldList(new TableImportField() {
            {
                setTableImportId(tableImportId);
            }
        });
        // 更新的字段
        List<TableImportField> updateList = tableImport.getList();
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(updateList)) {

            for (TableImportField oldfield : list) {

                String oldFieldName = oldfield.getTableField();
                boolean exist = false;
                for (TableImportField newfield : updateList) {

                    String newFieldName = newfield.getTableField();
                    if (oldFieldName.equals(newFieldName)) {

                        exist = true;
                    }
                }
                if (!exist) {

                    tableImportFieldMapper.deleteTableImportFieldById(oldfield.getId());
                }
            }
            for (TableImportField newfield : updateList) {

                String newFieldName = newfield.getTableField();
                boolean exist = false;
                for (TableImportField oldfield : list) {

                    String oldFieldName = oldfield.getTableField();
                    if (oldFieldName.equals(newFieldName)) {

                        exist = true;
                    }
                }
                if (!exist) {

                    newfield.setTableImportId(tableImportId);
                    newfield.setCreateTime(now);
                    newfield.setCreateBy(userId);
                    tableImportFieldMapper.insertTableImportField(newfield);
                }
            }
        }
        return tableImportMapper.updateTableImport(tableImport);
    }

    @Override
    public int delete(String ids) {

        int count = 0;
        String[] idlst = ids.split(",");
        for (String Id : idlst) {

            Long id = Long.parseLong(Id);
            List<TableImport> list = tableImportMapper.selectTableImportList(new TableImport() {{
                setParentId(id);
            }});
            if (list.size() > 0) {
                throw new RuntimeException("ID" + Id + "存在下级数据无法删除！");
            }
            count += tableImportMapper.deleteTableImportById(id);
            tableImportFieldMapper.deleteTableImportFieldByTableImportId(id);
        }
        return count;
    }

    @Override
    public int importData(TableImportParam param) {

        Date now = new Date();
        Long id = param.getId();
        TableImport tableImport = tableImportMapper.selectTableImportById(id);
        String sourceId = tableImport.getSourceId();
        String tableName = tableImport.getTableName();
        List<TableImportFieldParam> fieldList = param.getFields();
        String fields = "";
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new RuntimeException("字段列不能为空！");
        }
        for (int i = 0; i < fieldList.size(); i++) {

            Long fieldId = fieldList.get(i).getId();
            String cellName = fieldList.get(i).getCellName();
            TableImportField tableImportField = tableImportFieldMapper.selectTableImportFieldById(fieldId);
            if (StringUtils.isNotBlank(cellName)) {
                tableImportField.setCellName(cellName);
            }
            tableImportField.setUpdateBy(getCurrentUser().getId());
            tableImportField.setUpdateTime(now);
            tableImportFieldMapper.updateTableImportField(tableImportField);
            if (i == fieldList.size() - 1) {

                fields += tableImportField.getTableField();
            } else {

                fields += tableImportField.getTableField() + ",";
            }
        }
        String valus = "";
        List<List<TableImportFieldParam>> list = param.getList();
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("数据列不能为空！");
        }
        for (int i = 0; i < list.size(); i++) {

            if (CollectionUtils.isEmpty(list.get(i))) {
                throw new RuntimeException("数据列不能为空！");
            }
            valus += "(";
            for (int j = 0; j < list.get(i).size(); j++) {

                boolean isnull = false;
                if (StringUtils.isEmpty(list.get(i).get(j).getData())) {
                    isnull = true;
                }
                String data = StringUtils.isEmpty(list.get(i).get(j).getData()) ? "NULL" : list.get(i).get(j).getData();
                if (j == list.get(i).size() - 1) {

                    valus += isnull ? data : "'" + data + "'";
                } else {

                    valus += isnull ? data + "," : "'" + data + "',";
                }
            }
            if (i < list.size() - 1) {

                valus += "),";
            } else {

                valus += ")";
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("fields", fields);
        map.put("valus", valus);
        //数据源切换
        toolsService.changeConnection(sourceId);
        Integer count = tableImportMapper.insertTable(map);
        DynamicDataSource.clear();
        return count;
    }

    @Override
    public List<HashMap<String, Object>> getImportData(Long id) {

        TableImport tableImport = tableImportMapper.selectTableImportById(id);
        if (null == tableImport) {
            throw new RuntimeException("数据不存在");
        }
        String sourceId = tableImport.getSourceId();
        String tableName = tableImport.getTableName();
        Organization organization = organizationMapper.getOrganization();
        HashMap<String,Object> map = new HashMap<>();
        map.put("tableName", tableName);
        if (!securityManager.isOrgOwner(organization.getId())) {
            String orgCode = departmentMapperExt.selectDeptById(getCurrentUser().getDeptId()).getOrgCode();
            List<String> orgCodes = new ArrayList<>();
            orgCodes.add(orgCode);
            String adminCompetence = getCurrentUser().getAdminCompetence();
            if (adminCompetence != null){

                List<String> Orgs = departmentMapperExt.selectOrgCodesByDeptIds(Convert.toLongArray(adminCompetence));
                orgCodes.addAll(Orgs);
            }
            orgCodes = orgCodes.stream().distinct().collect(Collectors.toList());
            map.put("orgCodes",orgCodes);
        }
        toolsService.changeConnection(sourceId);
        List<HashMap<String, Object>> list = tableImportMapper.getImportData(map);
        DynamicDataSource.clear();
        return list;
    }

    @Override
    public Integer updateImportData(TableImportParam param) {

        Long id = param.getId();
        TableImport tableImport = tableImportMapper.selectTableImportById(id);
        String sourceId = tableImport.getSourceId();
        String tableName = tableImport.getTableName();
        List<TableImportFieldParam> fieldList = param.getFields();
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new RuntimeException("字段列不能为空！");
        }
        List<List<TableImportFieldParam>> list = param.getList();
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("数据列不能为空！");
        }
        String sets = "";
        String dataid = "";
        for (int i = 0; i < fieldList.size(); i++) {

            if (CollectionUtils.isEmpty(list.get(0))) {
                throw new RuntimeException("数据列不能为空！");
            }
            List<TableImportFieldParam> datalist = list.get(0);
            String tableField = fieldList.get(i).getTableField();
            if (tableField.equals("id")) {
                dataid = datalist.get(i).getData();
            } else {
                if (StringUtils.isBlank(datalist.get(i).getData())) {
                    sets += tableField + "= null,";
                } else {
                    sets += tableField + "='" + datalist.get(i).getData() + "',";
                }
            }
        }
        sets = sets.substring(0, sets.length() - 1);
        HashMap<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("id", dataid);
        map.put("sets", sets);
        //数据源切换
        toolsService.changeConnection(sourceId);
        Integer count = tableImportMapper.updateImportData(map);
        DynamicDataSource.clear();
        return count;
    }

    @Override
    public Integer deleteImportData(TableImportParam param) {

        int count = 0;
        Long id = param.getId();
        TableImport tableImport = tableImportMapper.selectTableImportById(id);
        String sourceId = tableImport.getSourceId();
        List<TableImportFieldParam> list = param.getFields();
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("所选数据不能为空");
        }
        toolsService.changeConnection(sourceId);
        for (TableImportFieldParam tableImportFieldParam : list) {
            Long dataId = tableImportFieldParam.getDataId();
            count += tableImportMapper.deleteImportData(new HashMap<String, Object>() {
                {
                    put("tableName", tableImport.getTableName());
                    put("id", dataId);
                }
            });
        }
        DynamicDataSource.clear();
        return count;
    }
}
