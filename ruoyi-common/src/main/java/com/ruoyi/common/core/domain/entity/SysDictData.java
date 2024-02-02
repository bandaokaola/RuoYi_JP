package com.ruoyi.common.core.domain.entity;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 辞書データテーブル sys_dict_data
 * 
 * @author ruoyi
 */
public class SysDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 辞書コード */
    @Excel(name = "辞書コード", cellType = ColumnType.NUMERIC)
    private Long dictCode;

    /** 辞書順 */
    @Excel(name = "辞書順", cellType = ColumnType.NUMERIC)
    private Long dictSort;

    /** 辞書ラベル */
    @Excel(name = "辞書ラベル")
    private String dictLabel;

    /** 辞書キー */
    @Excel(name = "辞書キー")
    private String dictValue;

    /** 辞書タイプ */
    @Excel(name = "辞書タイプ")
    private String dictType;

    /** スタイル属性（その他のスタイルの拡張 */
    @Excel(name = "スタイル属性（その他のスタイルの拡張")
    private String cssClass;

    /** テーブル辞書スタイル */
    private String listClass;

    /** デフォルトかどうか（Yはい、Nいいえ */
    @Excel(name = "デフォルトかどうか", readConverterExp = "Y=はい,N=いいえ")
    private String isDefault;

    /** 状態（0は正常、1は停止） */
    @Excel(name = "状態", readConverterExp = "0=正常,1=停止")
    private String status;

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    @NotBlank(message = "辞書ラベルは必須です")
    @Size(min = 0, max = 100, message = "辞書ラベルの長さは100文字を超えてはいけません")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "辞書キー値は必須です")
    @Size(min = 0, max = 100, message = "辞書キーの長さは100文字を超えてはいけません")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "辞書タイプは必須です")
    @Size(min = 0, max = 100, message = "辞書タイプの長さは100文字を超えてはいけません")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "スタイル属性の長さは100文字を超えてはいけません")
    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getListClass()
    {
        return listClass;
    }

    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public boolean getDefault()
    {
        return UserConstants.YES.equals(this.isDefault);
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
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
            .append("dictCode", getDictCode())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictType", getDictType())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
