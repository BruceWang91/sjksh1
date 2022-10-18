package datart.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import datart.core.base.consts.Const;
import datart.core.base.exception.Exceptions;
import datart.core.entity.JimuReportRewrite;
import datart.core.entity.Role;
import datart.core.mappers.JimuReportMapper;
import datart.core.mappers.ext.OrganizationMapperExt;
import datart.core.mappers.ext.RoleMapperExt;
import datart.security.base.ResourceType;
import datart.security.exception.PermissionDeniedException;
import datart.security.manager.shiro.ShiroSecurityManager;
import datart.security.util.PermissionHelper;
import datart.server.service.BaseService;
import datart.server.service.JimuReportRewriteService;
import org.jeecgframework.minidao.util.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 积木报表二开Service业务层处理
 *
 * @author wangya
 * @date 2022-05-27
 */
@Service
public class JimuReportRewriteServiceImpl extends BaseService implements JimuReportRewriteService {
    private static final Logger a = LoggerFactory.getLogger(JimuReportRewriteServiceImpl.class);
    @Autowired
    private JimuReportMapper jimuReportMapper;
    @Autowired
    private RoleMapperExt roleMapperExt;
    @Autowired
    private OrganizationMapperExt organizationMapperExt;

    @Override
    public JimuReportRewrite selectById(String id) {

        return jimuReportMapper.selectJimuReportRewriteById(id);
    }

    @Override
    public List<JimuReportRewrite> getlist(JimuReportRewrite jimuReportRewrite) {

        List<JimuReportRewrite> list = jimuReportMapper.selectJimuReportRewriteList(jimuReportRewrite);
        Map<String, JimuReportRewrite> filtered = new HashMap<>();

        List<JimuReportRewrite> permitted = list.stream().filter(file -> {
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
//            for (JimuReportRewrite file : permitted) {
//                JimuReportRewrite parent = filtered.remove(file.getParentId());
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
    public void requirePermission(JimuReportRewrite file, int permission) {
        if (securityManager.isOrgOwner(file.getOrgId())) {
            return;
        }
        boolean hasPermission;
        if (file.getCreateBy().equals(getCurrentUser().getUsername())) {
            hasPermission = true;
        } else {
            List<Role> roles = roleMapperExt.selectUserRoles1(getCurrentUser().getId());
            hasPermission = roles.stream().anyMatch(role -> hasPermission(role, file, permission));
        }
        if (!hasPermission) {
            Exceptions.tr(PermissionDeniedException.class, "message.security.permission-denied",
                    ResourceType.REPORT + ":" + file.getId() + ":" + ShiroSecurityManager.expand2StringPermissions(permission));
        }
    }

    private boolean hasPermission(Role role, JimuReportRewrite file, int permission) {
        if (file.getId() == null || rrrMapper.countRolePermission(file.getId(), role.getId()) == 0) {

            return false;
//            JimuReportRewrite parent = jimuReportMapper.selectJimuReportRewriteById(file.getParentId());
//            if (parent == null) {
//                return securityManager.hasPermission(PermissionHelper.viewPermission(file.getOrgId(), role.getId(), ResourceType.VIEW.name(), permission));
//            } else {
//                return hasPermission(role, parent, permission);
//            }
        } else {
            return securityManager.hasPermission(PermissionHelper.reportPermission(file.getOrgId(), role.getId(), file.getId(), permission));
        }
    }

    @Override
    public JimuReportRewrite saveReport(JSONObject json, HttpServletRequest request) {

        String orgId = organizationMapperExt.list().get(0).getId();
        String parentId = "";
        Integer isFolder = 0;
        String username = getCurrentUser().getUsername();
        a.debug("============EXCEL JSON数据正在保存==========");
        JSONObject var6 = json.getJSONObject("designerObj");
        String var7 = "";
        String var8 = "";
        if (var6 != null) {
            var7 = var6.getString("name");
            var8 = var6.getString("type");
            orgId = var6.getString("orgId");
            parentId = var6.getString("parentId");
            isFolder = Integer.valueOf(var6.getString("isFolder"));
            json.remove("designerObj");
        }
        SimpleDateFormat var9 = new SimpleDateFormat("yyyyMMddHHmmss");
        String var10 = var9.format(new Date());
        JimuReportRewrite var11 = new JimuReportRewrite();
        var11.setId(String.valueOf(SnowflakeIdWorker.generateId()));
        var11.setOrgId(orgId);
        var11.setParentId(parentId);
        var11.setIsFolder(isFolder);
        var11.setCode(var10);
        var11.setViewCount(0L);
        var11.setDelFlag(0);
        var11.setTemplate(0);
        var11.setName(var7);
        var11.setType(var8);
        var11.setCreateBy(username);
        Date var12 = new Date();
        var11.setCreateTime(var12);
        jimuReportMapper.insert(var11);
        return var11;
    }

    @Override
    public int updateParentId(JimuReportRewrite jimuReportRewrite) {

        return jimuReportMapper.updateParentId(jimuReportRewrite);
    }

    @Override
    public int deleteFolder(String id) {

        return jimuReportMapper.deleteFolder(id);
    }
}
