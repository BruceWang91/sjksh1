package datart.core.entity.result;

import datart.core.entity.FileMain;

import java.util.List;

public class FileMainResult extends FileMain {

    private List<FileSheetsResult> sheets;

    public List<FileSheetsResult> getSheets() {
        return sheets;
    }

    public void setSheets(List<FileSheetsResult> sheets) {
        this.sheets = sheets;
    }
}
