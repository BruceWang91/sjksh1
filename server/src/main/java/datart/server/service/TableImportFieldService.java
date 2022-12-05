package datart.server.service;

import datart.core.entity.TableImportField;

import java.util.List;

public interface TableImportFieldService {
    List<TableImportField> tableImportFieldList(TableImportField tableImportField);

    TableImportField selectTableImportFieldById(Long id);

    int add(TableImportField tableImportField);

    int update(TableImportField tableImportField);

    int delete(String ids);
}
