package datart.server.service.impl;

import datart.core.base.consts.Const;
import datart.core.base.consts.FileOwner;
import datart.core.base.exception.Exceptions;
import datart.core.common.FileUtils;
import datart.core.entity.FileMain;
import datart.core.entity.Role;
import datart.core.entity.StaticManagement;
import datart.core.mappers.StaticManagementMapper;
import datart.core.mappers.ext.RoleMapperExt;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.common.BinUtil;
import datart.server.common.StringUtils;
import datart.server.service.BaseService;
import datart.server.service.FileService;
import datart.server.service.IStaticManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StaticManagementServiceImpl extends BaseService implements IStaticManagementService {
    @Autowired
    private StaticManagementMapper staticManagementMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Value("${spring.upload}")
    private String localurl;

    @Override
    public List<StaticManagement> getList(StaticManagement staticManagement) {

        List<StaticManagement> list = staticManagementMapper.list(staticManagement);
        Map<Long, StaticManagement> filtered = new HashMap<>();
        List<StaticManagement> permitted = list.stream().filter(file -> {
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

    public void requirePermission(StaticManagement file, int permission) {
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
                    ResourceType.IMM_ASSET + ":" + file.getId() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, StaticManagement file, int permission) {
        if (file.getId() == null || rrrMapper.countRolePermission(file.getId().toString(), role.getId()) == 0) {

            return false;
//            FileMain parent = fileMainMapper.selectFileMainByFileId(file.getParentId());
//            if (parent == null) {
//                return securityManager.hasPermission(PermissionHelper.viewPermission(file.getOrgId(), role.getId(), ResourceType.VIEW.name(), permission));
//            } else {
//                return hasPermission(role, parent, permission);
//            }
        } else {
            return securityManager.hasPermission(PermissionHelper.staticManagementPermission(file.getOrgId(), role.getId(), file.getId().toString(), permission));
        }
    }

    @Override
    public StaticManagement getById(Long id) {

        StaticManagement staticManagement = staticManagementMapper.selectStaticManagementById(id);
        editmanagement(staticManagement);
        return staticManagement;
    }

    @Override
    public int editIndex(Long id, Integer index) {

        StaticManagement staticManagement = new StaticManagement();
        staticManagement.setId(id);
        staticManagement.setIndex(index);
        return staticManagementMapper.updateStaticManagement(staticManagement);
    }

    @Override
    public StaticManagement getStaticManagementById(Long id) {
        StaticManagement staticManagement = staticManagementMapper.selectStaticManagementById(id);
        editmanagement(staticManagement);
        List<StaticManagement> list = staticManagementMapper.lists(new StaticManagement() {{
            setParentId(id);
        }});
        if (!CollectionUtils.isEmpty(list)) {
            for (StaticManagement management : list) {
                editmanagement(management);
                management.setChilds(getChilds(management.getId()));
            }
        }
        staticManagement.setChilds(list);
        return staticManagement;
    }

    @Override
    public StaticManagement getStaticManagementByMark(String mark){
        StaticManagement staticManagement = staticManagementMapper.getStaticManagementByMark(mark);
        if (null == staticManagement){
            return new StaticManagement();
        }
        editmanagement(staticManagement);
        List<StaticManagement> list = staticManagementMapper.lists(new StaticManagement() {{
            setParentId(staticManagement.getId());
        }});
        if (!CollectionUtils.isEmpty(list)) {
            for (StaticManagement management : list) {
                editmanagement(management);
                management.setChilds(getChilds(management.getId()));
            }
        }
        staticManagement.setChilds(list);
        return staticManagement;
    }

    @Override
    public int getCountByMark(String mark) {

        return staticManagementMapper.getCountByMark(mark);
    }

    private List<StaticManagement> getChilds(Long id) {

        List<StaticManagement> list = staticManagementMapper.lists(new StaticManagement() {{
            setParentId(id);
        }});
        if (CollectionUtils.isEmpty(list)) {

            return null;
        } else {

            for (StaticManagement staticManagement : list) {

                editmanagement(staticManagement);
                staticManagement.setChilds(getChilds(staticManagement.getId()));
            }
            return list;
        }
    }

    private StaticManagement editmanagement(StaticManagement management) {
        if (null != management.getTextContent()) {
            String textContentstr = BinUtil.byte2str(management.getTextContent());
            management.setTextContentStr(textContentstr);
            management.setTextContent(null);
        } else {

//            if (StringUtils.isNotBlank(management.getUrl())) {
//
//                String url = management.getUrl().replace("/upload/", localurl);
//                if (!new File(url).exists()) {
//
//                    String fullFilePath = management.getUrl().replace("/upload/", localurl);
//                    FileUtils.mkdirParentIfNotExist(fullFilePath);
//                    try {
//                        byte[] fileStream = management.getTextContent();
//                        String[] fileName = url.split("/");
//                        String names = fileName[fileName.length - 1];
//                        String path = fullFilePath.replace(names, "");
//                        BinUtil.binToFile(new String(fileStream, "ISO-8859-1"), names, path);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            management.setTextContent(null);
        }
        return management;
    }

    @Override
    public StaticManagement add(String moduleName, String orgId, String mark, String jsonStr, String link, Integer type, Long parentId, Integer isFolder, Integer index, MultipartFile file) throws IOException {

        StaticManagement staticManagement = new StaticManagement();
        staticManagement.setModuleName(moduleName);
        staticManagement.setOrgId(orgId);
        staticManagement.setMark(mark);
        staticManagement.setJsonStr(jsonStr);
        staticManagement.setLink(link);
        staticManagement.setType(type);
        staticManagement.setParentId(parentId);
        staticManagement.setIsFolder(isFolder);
        staticManagement.setIndex(index);
        staticManagement.setCreateBy(getCurrentUser().getId());
        staticManagement.setCreateTime(new Date());
        staticManagementMapper.insertStaticManagement(staticManagement);

        if (type != 1 && type != 0 && null != file) {

            HashMap<String, Object> map = fileService.uploadstaticfile(FileOwner.STATIC_FILE_SAVE_DATA, staticManagement.getId(), file);
            staticManagement.setUrl(map.get("url").toString());
//            staticManagement.setTextContent((byte[]) map.get("fileStream"));
        }
        staticManagementMapper.updateStaticManagement(staticManagement);
        return editmanagement(staticManagement);
    }

    @Override
    public StaticManagement add1(StaticManagement staticManagement) {

        staticManagement.setCreateBy(getCurrentUser().getId());
        staticManagement.setCreateTime(new Date());
        if (staticManagement.getType() == 1 || staticManagement.getType() == 0) {
            String textContentStr = staticManagement.getTextContentStr();
            if (StringUtils.isNotBlank(textContentStr)) {
                byte[] bytes = BinUtil.str2byte(textContentStr);
                staticManagement.setTextContent(bytes);
            }
        }
        staticManagementMapper.insertStaticManagement(staticManagement);
        return editmanagement(staticManagement);
    }

    @Override
    public StaticManagement update(Long id, String moduleName, String mark, String jsonStr, String link, Integer type, Long parentId, Integer isFolder, Integer index, MultipartFile file) throws IOException {

        StaticManagement staticManagement = staticManagementMapper.selectStaticManagementById(id);
        staticManagement.setModuleName(moduleName);
        staticManagement.setMark(mark);
        staticManagement.setJsonStr(jsonStr);
        staticManagement.setLink(link);
        staticManagement.setType(type);
        staticManagement.setParentId(parentId);
        staticManagement.setIsFolder(isFolder);
        staticManagement.setIndex(index);
        if (type != 1 && null != file) {

            String url = staticManagement.getUrl();
            if (StringUtils.isNotBlank(url)) {

                url = url.replace("/upload/", localurl);
                File oldfile = new File(url);
                oldfile.delete();
            }
            HashMap<String, Object> map = fileService.uploadstaticfile(FileOwner.STATIC_FILE_SAVE_DATA, staticManagement.getId(), file);
            staticManagement.setUrl(map.get("url").toString());
//            staticManagement.setTextContent((byte[]) map.get("fileStream"));
        }
        staticManagementMapper.updateStaticManagement(staticManagement);
        return editmanagement(staticManagement);
    }

    @Override
    public StaticManagement update1(StaticManagement staticManagement) {
        staticManagement.setUpdateBy(getCurrentUser().getId());
        staticManagement.setUpdateTime(new Date());
        String textContentStr = staticManagement.getTextContentStr();
        if (StringUtils.isNotBlank(textContentStr)) {
            byte[] bytes = BinUtil.str2byte(textContentStr);
            staticManagement.setTextContent(bytes);
        }
        staticManagementMapper.updateStaticManagement(staticManagement);
        return editmanagement(staticManagement);
    }

    @Override
    @Transactional
    public int delete(String ids) {

        int count = 0;
        String[] idlst = ids.split(",");
        for (String Id : idlst) {

            Long id = Long.parseLong(Id);
            StaticManagement management = staticManagementMapper.selectStaticManagementById(id);
            List<StaticManagement> list = staticManagementMapper.lists(new StaticManagement() {{
                setParentId(id);
            }});
            if (list.size() > 0) {
                throw new RuntimeException("ID" + Id + "存在下级数据无法删除！");
            }
            String url = management.getUrl();
            if (StringUtils.isNotBlank(url)) {
                url = url.replace("/upload/", localurl);
                if (StringUtils.isNotBlank(url)) {

                    File file = new File(url);
                    file.delete();
                }
            }
            count += staticManagementMapper.deleteStaticManagementById(id);
        }
        return count;
    }
}
