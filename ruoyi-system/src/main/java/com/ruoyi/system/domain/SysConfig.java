package com.ruoyi.system.domain;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * パラメーター設定テーブル sys_config
 * 
 * @author ruoyi
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** パラメーター主キー */
    @Excel(name = "パラメーター主キー", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** パラメーター名 */
    @Excel(name = "パラメーター名")
    private String configName;

    /** パラメーターキー */
    @Excel(name = "パラメーターキー")
    private String configKey;

    /** パラメーターバリュー */
    @Excel(name = "パラメーターバリュー")
    private String configValue;

    /** システム組み込み（Y: はい, N: いいえ） */
    @Excel(name = "システム組み込み", readConverterExp = "Y: はい, N: いいえ")
    private String configType;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "パラメーター名は必須です")
    @Size(min = 0, max = 100, message = "パラメーター名は100文字以内で入力してください")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "パラメーターキーは必須です")
    @Size(min = 0, max = 100, message = "パラメーターキーは100文字以内で入力してください")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "パラメーター値は必須です")
    @Size(min = 0, max = 500, message = "パラメーター値は500文字以内で入力してください")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
