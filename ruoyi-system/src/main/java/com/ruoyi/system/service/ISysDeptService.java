package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;

/**
 * 部門管理サービス
 * 
 * @author ruoyi
 */
public interface ISysDeptService
{
    /**
     * 部門管理データを検索
     * 
     * @param dept 部門情報
     * @return 部門情報のリスト
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 部門管理ツリーを検索
     * 
     * @param dept 部門情報
     * @return すべての部門情報
     */
    public List<Ztree> selectDeptTree(SysDept dept);

    /**
     * 部門管理ツリーを検索（下位を除く）
     * 
     * @param dept 部門情報
     * @return すべての部門情報
     */
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept);

    /**
     * ロールIDに基づいてメニューを検索
     *
     * @param role ロールオブジェクト
     * @return メニューリスト
     */
    public List<Ztree> roleDeptTreeData(SysRole role);

    /**
     * 親部門IDに基づいて子部門の数を検索
     * 
     * @param parentId 親部門ID
     * @return 結果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 部門にユーザーが存在するかどうかを検索
     * 
     * @param deptId 部門ID
     * @return 結果 true 存在する false 存在しない
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 部門管理情報を削除
     * 
     * @param deptId 部門ID
     * @return 結果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 部門情報を新規保存
     * 
     * @param dept 部門情報
     * @return 結果
     */
    public int insertDept(SysDept dept);

    /**
     * 部門情報を修正して保存
     * 
     * @param dept 部門情報
     * @return 結果
     */
    public int updateDept(SysDept dept);

    /**
     * 部門IDに基づいて情報を検索
     * 
     * @param deptId 部門ID
     * @return 部門情報
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * IDに基づいてすべての子部門を検索（正常な状態）
     * 
     * @param deptId 部門ID
     * @return 子部門数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 部門名が一意かどうかを確認
     * 
     * @param dept 部門情報
     * @return 結果
     */
    public boolean checkDeptNameUnique(SysDept dept);

    /**
     * 部門にデータ権限があるかどうかを確認
     * 
     * @param deptId 部門id
     */
    public void checkDeptDataScope(Long deptId);
}
