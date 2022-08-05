package datart.core.entity.result;

import datart.core.entity.FileSheetField;
import datart.core.entity.FileSheets;

import java.util.List;

public class FileSheetsResult extends FileSheets {

    private List<FileSheetField> fieldList;

    public List<FileSheetField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FileSheetField> fieldList) {
        this.fieldList = fieldList;
    }
}