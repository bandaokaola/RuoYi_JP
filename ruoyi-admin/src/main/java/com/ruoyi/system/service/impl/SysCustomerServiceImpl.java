package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysCustomerMapper;
import com.ruoyi.system.domain.SysCustomer;
import com.ruoyi.system.service.ISysCustomerService;
import com.ruoyi.common.core.text.Convert;

/**
 * お客様テーブルService业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@Service
public class SysCustomerServiceImpl implements ISysCustomerService 
{
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    /**
     * 查询お客様テーブル
     * 
     * @param customerId お客様テーブル主键
     * @return お客様テーブル
     */
    @Override
    public SysCustomer selectSysCustomerByCustomerId(Long customerId)
    {
        return sysCustomerMapper.selectSysCustomerByCustomerId(customerId);
    }

    /**
     * 查询お客様テーブル列表
     * 
     * @param sysCustomer お客様テーブル
     * @return お客様テーブル
     */
    @Override
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer)
    {
        return sysCustomerMapper.selectSysCustomerList(sysCustomer);
    }

    /**
     * 新增お客様テーブル
     * 
     * @param sysCustomer お客様テーブル
     * @return 结果
     */
    @Override
    public int insertSysCustomer(SysCustomer sysCustomer)
    {
        return sysCustomerMapper.insertSysCustomer(sysCustomer);
    }

    /**
     * 修改お客様テーブル
     * 
     * @param sysCustomer お客様テーブル
     * @return 结果
     */
    @Override
    public int updateSysCustomer(SysCustomer sysCustomer)
    {
        return sysCustomerMapper.updateSysCustomer(sysCustomer);
    }

    /**
     * 批量删除お客様テーブル
     * 
     * @param customerIds 需要删除的お客様テーブル主键
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByCustomerIds(String customerIds)
    {
        return sysCustomerMapper.deleteSysCustomerByCustomerIds(Convert.toStrArray(customerIds));
    }

    /**
     * 删除お客様テーブル信息
     * 
     * @param customerId お客様テーブル主键
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByCustomerId(Long customerId)
    {
        return sysCustomerMapper.deleteSysCustomerByCustomerId(customerId);
    }
}
