package datart.core.entity.param;

import lombok.Data;

@Data
public class TableImportFieldParam {

    private Long id;

    /**
     * 对应表头名称
     */
    private String cellName;

    /**
     * 对应表字段
     */
    private String tableField;

    /**
     * 数据行ID
     */
    private Long dataId;
    /**
     * 数值
     */
    private String data;

    /**
     * 主表ID
     */
    private Long tableImportId;
}
