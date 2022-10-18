package datart.core.entity;

import lombok.Data;

@Data
public class FileSaveStream {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 文档id
     */
    private Long fileSaveId;

    /**
     * 文件数据流
     */
    private byte[] fileStream;

    /**
     * pdf数据流
     */
    private byte[] pdfStream;
}
