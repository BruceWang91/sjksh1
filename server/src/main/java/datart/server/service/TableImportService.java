package datart.server.service;

import datart.core.entity.TableImport;
import datart.core.entity.param.TableImportParam;

import java.util.HashMap;
import java.util.List;

public interface TableImportService {
    List<TableImport> tableImportList(TableImport tableImport);

    TableImport selectTableImportById(Long id);

    TableImport add(TableImport tableImport);

    int update(TableImport tableImport);

    int delete(String ids);

    int importData(TableImportParam param);

    List<HashMap<String, Object>> getImportData(Long id);

    Integer updateImportData(TableImportParam param);

    Integer deleteImportData(TableImportParam param);
}
