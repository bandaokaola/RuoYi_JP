package com.ruoyi.common.core.domain.entity;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 辞書タイプテーブル sys_dict_type
 * 
 * @author ruoyi
 */
public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 辞書主キー */
    @Excel(name = "辞書主キー", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** 辞書名 */
    @Excel(name = "辞書名")
    private String dictName;

    /** 辞書タイプ */
    @Excel(name = "辞書タイプ")
    private String dictType;

    /** 状態（0は正常、1は停止） */
    @Excel(name = "状態", readConverterExp = "0=正常,1=停止")
    private String status;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "辞書名を入力してください")
    @Size(min = 0, max = 100, message = "辞書名は100文字以内で入力してください")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "辞書タイプを入力してください")
    @Size(min = 0, max = 100, message = "辞書タイプは100文字以内で入力してください")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "辞書タイプはアルファベットで始まり、（小文字のアルファベット、数字、アンダースコア）のみが使用できます")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
