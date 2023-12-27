package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;

/**
 * 職位情報処理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    private String prefix = "system/post";

    @Autowired
    private ISysPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "職位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "職位データ");
    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "職位管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(postService.deletePostByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 新規職位
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新規保存職位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "職位管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("新規職位'" + post.getPostName() + "'失敗，職位名称が既に存在します");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("新規職位'" + post.getPostName() + "'失敗，職位名称が既に存在します");
        }
        post.setCreateBy(getLoginName());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 職位編集
     */
    @RequiresPermissions("system:post:edit")
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap)
    {
        mmap.put("post", postService.selectPostById(postId));
        return prefix + "/edit";
    }

    /**
     * 変更保存職位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "職位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("職位'" + post.getPostName() + "'の編集失敗，職位名称が既に存在します");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("修改'" + post.getPostName() + "'の編集失敗，職位名称が既に存在します");
        }
        post.setUpdateBy(getLoginName());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 職位名称の検証
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public boolean checkPostNameUnique(SysPost post)
    {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 職位コードの検証
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public boolean checkPostCodeUnique(SysPost post)
    {
        return postService.checkPostCodeUnique(post);
    }
}
