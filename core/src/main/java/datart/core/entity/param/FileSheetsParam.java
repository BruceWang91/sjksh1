package datart.core.entity.param;

import datart.core.entity.FileSheetField;
import datart.core.entity.FileSheets;

import java.util.List;

public class FileSheetsParam extends FileSheets {

    private Long classId;

    private List<Long> classIds;

    private List<Long> depIds;

    private List<FileSheetField> fieldList;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<Long> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<Long> classIds) {
        this.classIds = classIds;
    }

    public List<Long> getDepIds() {
        return depIds;
    }

    public void setDepIds(List<Long> depIds) {
        this.depIds = depIds;
    }

    public List<FileSheetField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FileSheetField> fieldList) {
        this.fieldList = fieldList;
    }
}
