package datart.server.service.impl;

import datart.core.entity.TableImportField;
import datart.core.mappers.TableImportFieldMapper;
import datart.server.service.BaseService;
import datart.server.service.TableImportFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TableImportFieldServiceImpl extends BaseService implements TableImportFieldService {

    @Autowired
    private TableImportFieldMapper tableImportFieldMapper;

    @Override
    public List<TableImportField> tableImportFieldList(TableImportField tableImportField) {
        return tableImportFieldMapper.selectTableImportFieldList(tableImportField);
    }

    @Override
    public TableImportField selectTableImportFieldById(Long id) {
        return tableImportFieldMapper.selectTableImportFieldById(id);
    }

    @Override
    public int add(TableImportField tableImportField) {
        tableImportField.setCreateBy(getCurrentUser().getId());
        tableImportField.setCreateTime(new Date());
        return tableImportFieldMapper.insertTableImportField(tableImportField);
    }

    @Override
    public int update(TableImportField tableImportField) {
        tableImportField.setUpdateBy(getCurrentUser().getId());
        tableImportField.setUpdateTime(new Date());
        return tableImportFieldMapper.updateTableImportField(tableImportField);
    }

    @Override
    public int delete(String ids) {
        int count = 0;
        String[] idlst = ids.split(",");
        for (String Id : idlst) {

            Long id = Long.parseLong(Id);
            count += tableImportFieldMapper.deleteTableImportFieldById(id);
        }
        return count;
    }
}
