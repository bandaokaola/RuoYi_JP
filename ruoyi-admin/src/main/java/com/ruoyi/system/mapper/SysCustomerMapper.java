package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysCustomer;

/**
 * お客様テーブルMapper接口
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public interface SysCustomerMapper 
{
    /**
     * 查询お客様テーブル
     * 
     * @param customerId お客様テーブル主键
     * @return お客様テーブル
     */
    public SysCustomer selectSysCustomerByCustomerId(Long customerId);

    /**
     * 查询お客様テーブル列表
     * 
     * @param sysCustomer お客様テーブル
     * @return お客様テーブル集合
     */
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer);

    /**
     * 新增お客様テーブル
     * 
     * @param sysCustomer お客様テーブル
     * @return 结果
     */
    public int insertSysCustomer(SysCustomer sysCustomer);

    /**
     * 修改お客様テーブル
     * 
     * @param sysCustomer お客様テーブル
     * @return 结果
     */
    public int updateSysCustomer(SysCustomer sysCustomer);

    /**
     * 删除お客様テーブル
     * 
     * @param customerId お客様テーブル主键
     * @return 结果
     */
    public int deleteSysCustomerByCustomerId(Long customerId);

    /**
     * 批量删除お客様テーブル
     * 
     * @param customerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCustomerByCustomerIds(String[] customerIds);
}
