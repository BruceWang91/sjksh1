package datart.server.service;

import datart.core.entity.StaticManagement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStaticManagementService {
    List<StaticManagement> getList(StaticManagement staticManagement);

    StaticManagement getStaticManagementById(Long id);

    int delete(String ids);

    StaticManagement add(String moduleName, String orgId, String mark, String jsonStr, String link, Integer type, Long parentId, Integer isFolder, Integer index, MultipartFile file) throws IOException;

    StaticManagement add1(StaticManagement staticManagement);

    StaticManagement update(Long id, String moduleName, String mark, String jsonStr, String link, Integer type, Long parentId, Integer isFolder, Integer index, MultipartFile file) throws IOException;

    StaticManagement update1(StaticManagement staticManagement);

    StaticManagement getById(Long id);

    int editIndex(Long id, Integer index);

    StaticManagement getStaticManagementByMark(String mark);

    int getCountByMark(String mark);
}
