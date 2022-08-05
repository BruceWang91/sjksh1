package datart.core.entity.param;

import datart.core.entity.FileMain;

import java.util.List;

public class FileMainParam extends FileMain {

    private List<FileSheetsParam> sheets;

    public List<FileSheetsParam> getSheets() {
        return sheets;
    }

    public void setSheets(List<FileSheetsParam> sheets) {
        this.sheets = sheets;
    }
}
