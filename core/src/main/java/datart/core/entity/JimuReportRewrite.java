package datart.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class JimuReportRewrite {

    private String id;
    private String code;
    private String name;
    private String note;
    private String status;
    private String type;
    private String jsonStr;
    private String apiUrl;
    private String apiMethod;
    private String apiCode;
    private String thumb;
    private Integer template;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Integer delFlag;
    private Long viewCount;
    private String cssStr;
    private String jsStr;
    private String tenantId;
}
