package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * メニューのビジネスロジック層
 * 
 * @author ruoyi
 */
public interface ISysMenuService
{
    /**
     * ユーザーIDに基づいてメニューを取得
     * 
     * @param user ユーザー情報
     * @return メニューリスト
     */
    public List<SysMenu> selectMenusByUser(SysUser user);

    /**
     * システムメニューリストを取得
     * 
     * @param menu メニュー情報
     * @param userId ユーザーID
     * @return メニューリスト
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * メニューのコレクションを取得
     * 
     * @param userId ユーザーID
     * @return すべてのメニュー情報
     */
    public List<SysMenu> selectMenuAll(Long userId);

    /**
     * ユーザーIDに基づいて権限を取得
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    public Set<String> selectPermsByUserId(Long userId);

    /**
     * ロールIDに基づいて権限を取得
     * 
     * @param roleId ロールID
     * @return 権限リスト
     */
    public Set<String> selectPermsByRoleId(Long roleId);

    /**
     * ロールIDに基づいてメニューを取得
     * 
     * @param role ロールオブジェクト
     * @param userId ユーザーID
     * @return メニューリスト
     */
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId);

    /**
     * すべてのメニュー情報を取得
     * 
     * @param userId ユーザーID
     * @return メニューリスト
     */
    public List<Ztree> menuTreeData(Long userId);

    /**
     * システムのすべての権限を取得
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    public Map<String, String> selectPermsAll(Long userId);

    /**
     * メニュー管理情報を削除
     * 
     * @param menuId メニューID
     * @return 結果
     */
    public int deleteMenuById(Long menuId);

    /**
     * メニューIDに基づいて情報を取得
     * 
     * @param menuId メニューID
     * @return メニュー情報
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * メニューの数を取得
     * 
     * @param parentId メニュー親ID
     * @return 結果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * メニュー使用数を取得
     * 
     * @param menuId メニューID
     * @return 結果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * メニュー情報を新規保存
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    public int insertMenu(SysMenu menu);

    /**
     * メニュー情報を変更保存
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    public int updateMenu(SysMenu menu);

    /**
     * メニュー名が一意かどうかを確認
     * 
     * @param menu メニュー情報
     * @return 結果
     */
    public boolean checkMenuNameUnique(SysMenu menu);
}
