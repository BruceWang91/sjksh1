package datart.core.mappers;

import datart.core.entity.FileSaveStream;
import org.apache.ibatis.jdbc.SQL;

public class FileSaveStreamSqlProvider {

    public String insertSelective(FileSaveStream record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("file_save_stream");

        if (record.getFileSaveId() != null) {
            sql.VALUES("file_save_id", "#{fileSaveId,jdbcType=BIGINT}");
        }

        if (record.getFileStream() != null) {
            sql.VALUES("file_stream", "#{fileStream}");
        }

        if (record.getPdfStream() != null) {
            sql.VALUES("pdf_stream", "#{pdfStream}");
        }
        return sql.toString();
    }
}
