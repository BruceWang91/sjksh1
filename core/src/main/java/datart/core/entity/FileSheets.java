package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Digits;
import java.util.Date;

/**
 * 文件工作簿对象 file_sheets
 *
 * @author ruoyi
 * @date 2022-05-30
 */
public class FileSheets {
    private static final long serialVersionUID = 1L;

    /**
     * 工作簿id
     */
    private Long sheetId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 工作簿名称
     */
    private String sheetName;

    /**
     * 对应实体名称
     */
    private String entityName;

    /**
     * 顺序
     */
    @Digits(integer = 12, fraction = 0)
    private Integer orderNum;

    /**
     * 读取开始行
     */
    @Digits(integer = 12, fraction = 0)
    private Integer startRow;

    /**
     * 读取开始列
     */
    @Digits(integer = 12, fraction = 0)
    private Integer startCell;

    /**
     * 有效列数
     */
    @Digits(integer = 12, fraction = 0)
    private Integer cellCount;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

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

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartCell(Integer startCell) {
        this.startCell = startCell;
    }

    public Integer getStartCell() {
        return startCell;
    }

    public void setCellCount(Integer cellCount) {
        this.cellCount = cellCount;
    }

    public Integer getCellCount() {
        return cellCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sheetId", getSheetId())
                .append("fileId", getFileId())
                .append("sheetName", getSheetName())
                .append("entityName", getEntityName())
                .append("orderNum", getOrderNum())
                .append("startRow", getStartRow())
                .append("startCell", getStartCell())
                .append("cellCount", getCellCount())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
