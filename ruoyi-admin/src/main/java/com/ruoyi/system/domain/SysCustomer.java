package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * お客様テーブル对象 sys_customer
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public class SysCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** お客様id */
    private Long customerId;

    /** お客様フルネーム */
    @Excel(name = "お客様フルネーム")
    private String customerName;

    /** お客様所属会社フルネーム */
    @Excel(name = "お客様所属会社フルネーム")
    private String customerCompanyName;

    /** お客様携帯 */
    @Excel(name = "お客様携帯")
    private String phonenumber;

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setCustomerCompanyName(String customerCompanyName) 
    {
        this.customerCompanyName = customerCompanyName;
    }

    public String getCustomerCompanyName() 
    {
        return customerCompanyName;
    }
    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() 
    {
        return phonenumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("customerCompanyName", getCustomerCompanyName())
            .append("phonenumber", getPhonenumber())
            .append("remark", getRemark())
            .toString();
    }
}
