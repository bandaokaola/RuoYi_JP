package com.ruoyi.system.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysMenuMapper;
import com.ruoyi.system.mapper.SysRoleMenuMapper;
import com.ruoyi.system.service.ISysMenuService;

/**
 * メニューのビジネスロジック層
 * 
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    /**
     * ユーザーIDに基づいてメニューを取得
     * 
     * @param user ユーザー情報
     * @return メニューリスト
     */
    @Override
    public List<SysMenu> selectMenusByUser(SysUser user)
    {
        List<SysMenu> menus = new LinkedList<SysMenu>();
        // 管理者はすべてのメニュー情報を表示
        if (user.isAdmin())
        {
            menus = menuMapper.selectMenuNormalAll();
        }
        else
        {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return getChildPerms(menus, 0);
    }

    /**
     * システムメニューリストを取得
     * 
     * @param menu メニュー情報
     * @param userId ユーザーID
     * @return メニューリスト
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId)
    {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * メニューのコレクションを取得
     * 
     * @param userId ユーザーID
     * @return すべてのメニュー情報
     */
    @Override
    public List<SysMenu> selectMenuAll(Long userId)
    {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuAll();
        }
        else
        {
            menuList = menuMapper.selectMenuAllByUserId(userId);
        }
        return menuList;
    }

    /**
     * ユーザーIDに基づいて権限を取得
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * ロールIDに基づいて権限を取得
     * 
     * @param roleId ロールID
     * @return 権限リスト
     */
    @Override
    public Set<String> selectPermsByRoleId(Long roleId)
    {
        List<String> perms = menuMapper.selectPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * ロールIDに基づいてメニューを取得
     * 
     * @param role ロールオブジェクト
     * @param userId ユーザーID
     * @return メニューリスト
     */
    @Override
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysMenu> menuList = selectMenuAll(userId);
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        }
        else
        {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * すべてのメニュー情報を取得
     * 
     * @param userId ユーザーID
     * @return メニューリスト
     */
    @Override
    public List<Ztree> menuTreeData(Long userId)
    {
        List<SysMenu> menuList = selectMenuAll(userId);
        List<Ztree> ztrees = initZtree(menuList);
        return ztrees;
    }

    /**
     * システムのすべての権限を取得
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll(Long userId)
    {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<SysMenu> permissions = selectMenuAll(userId);
        if (StringUtils.isNotEmpty(permissions))
        {
            for (SysMenu menu : permissions)
            {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * オブジェクトをメニューツリーに変換する
     * 
     * @param menuList メニューリスト
     * @return ツリー構造のリスト
     */
    public List<Ztree> initZtree(List<SysMenu> menuList)
    {
        return initZtree(menuList, null, false);
    }

    /**
     * オブジェクトをメニューツリーに変換する
     * 
     * @param menuList メニューリスト
     * @param roleMenuList ロールに既存のメニューリスト
     * @param permsFlag 権限表示の必要性
     * @return ツリー構造のリスト
     */
    public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (SysMenu menu : menuList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck)
            {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(SysMenu menu, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * メニュー管理情報を削除
     * 
     * @param menuId メニューID
     * @return 結果
     */
    @Override
    public int deleteMenuById(Long menuId)
    {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * メニューIDに基づいて情報を取得
     * 
     * @param menuId メニューID
     * @return メニュー情報
     */
    @Override
    public SysMenu selectMenuById(Long menuId)
    {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * メニューの数を取得
     * 
     * @param parentId メニュー親ID
     * @return 結果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId)
    {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * メニュー使用数を取得
     * 
     * @param menuId メニューID
     * @return 結果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId)
    {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * メニュー情報を新規保存
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    @Override
    public int insertMenu(SysMenu menu)
    {
        return menuMapper.insertMenu(menu);
    }

    /**
     * メニュー情報を変更保存
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    @Override
    public int updateMenu(SysMenu menu)
    {
        return menuMapper.updateMenu(menu);
    }

    /**
     * メニュー名が一意かどうかを確認
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenu menu)
    {
        Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 親ノードのIDに基づいてすべての子ノードを取得する
     * 
     * @param list 分類表
     * @param parentId 親ノードID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、親ノードIDに基づいて、その親ノードのすべての子ノードにアクセス
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 再帰リスト
     * 
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 子ノードリストを取得
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 子ノードリストを取得
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 子ノードがあるかどうかを判断する
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0;
    }
}
