package com.ruoyi.system.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysCustomer;
import com.ruoyi.system.service.ISysCustomerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * お客様テーブルController
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@Controller
@RequestMapping("/system/customer")
public class SysCustomerController extends BaseController
{
    private String prefix = "system/customer";

    @Autowired
    private ISysCustomerService sysCustomerService;

    @RequiresPermissions("system:customer:view")
    @GetMapping()
    public String customer()
    {
        return prefix + "/customer";
    }

    /**
     * 查询お客様テーブル列表
     */
    @RequiresPermissions("system:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCustomer sysCustomer)
    {
        startPage();
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        return getDataTable(list);
    }

    /**
     * 导出お客様テーブル列表
     */
    @RequiresPermissions("system:customer:export")
    @Log(title = "お客様テーブル", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCustomer sysCustomer)
    {
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        ExcelUtil<SysCustomer> util = new ExcelUtil<SysCustomer>(SysCustomer.class);
        return util.exportExcel(list, "お客様テーブル数据");
    }

    /**
     * 新增お客様テーブル
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存お客様テーブル
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "お客様テーブル", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCustomer sysCustomer)
    {
        return toAjax(sysCustomerService.insertSysCustomer(sysCustomer));
    }

    /**
     * 修改お客様テーブル
     */
    @RequiresPermissions("system:customer:edit")
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap)
    {
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerByCustomerId(customerId);
        mmap.put("sysCustomer", sysCustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存お客様テーブル
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "お客様テーブル", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCustomer sysCustomer)
    {
        return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
    }

    /**
     * 删除お客様テーブル
     */
    @RequiresPermissions("system:customer:remove")
    @Log(title = "お客様テーブル", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysCustomerService.deleteSysCustomerByCustomerIds(ids));
    }
}
