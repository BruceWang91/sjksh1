package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StaticManagement {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 模块标记
     */
    private String mark;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 类型 1文本 2图片 3文件
     */
    private Integer type;
    /**
     * 文本内容
     */
    private byte[] textContent;

    /**
     * 文本内容字符串
     */
    private String textContentStr;

    /**
     * url
     */
    private String url;

    /**
     * pdf文件url
     */
    private String pdfUrl;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 是否文件夹 1是 0否
     */
    private Integer isFolder;

    /**
     * 排序
     */
    private Integer index;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 链接地址
     */
    private String link;

    /**
     * JSON
     */
    private String jsonStr;

    private List<StaticManagement> childs;
}
