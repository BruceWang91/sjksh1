package datart.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 字段对应对象 file_sheet_field
 *
 * @author wangya
 * @date 2022-06-12
 */
public class FileSheetField {
    private static final long serialVersionUID = 1L;

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 工作簿id
     */
    private Long sheetId;

    /**
     * 对应列数 从0开始
     */
    private Integer cellNum;

    /**
     * 对应表头名称
     */
    private String cellName;

    /**
     * 所在行数  用于表头
     */
    private Integer rowNum;

    /**
     * 对应实体表字段
     */
    private String entityField;

    /**
     * 状态（0不参与数据导入 1参与数据导入使用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    /**
     * 导入标识 （0不做筛选 1该列数据为空时不导入该行数据）
     */
    private String importFlag;

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

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setCellNum(Integer cellNum) {
        this.cellNum = cellNum;
    }

    public Integer getCellNum() {
        return cellNum;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellName() {
        return cellName;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setEntityField(String entityField) {
        this.entityField = entityField;
    }

    public String getEntityField() {
        return entityField;
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

    public String getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(String importFlag) {
        this.importFlag = importFlag;
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
                .append("fieldId", getFieldId())
                .append("fileId", getFileId())
                .append("sheetId", getSheetId())
                .append("cellNum", getCellNum())
                .append("cellName", getCellName())
                .append("rowNum", getRowNum())
                .append("entityField", getEntityField())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
