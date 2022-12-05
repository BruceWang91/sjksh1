package datart.core.entity.param;

import lombok.Data;

import java.util.List;

@Data
public class TableImportParam {


    private Long id;

    private List<TableImportFieldParam> fields;

    private List<List<TableImportFieldParam>> list;

}
