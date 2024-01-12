package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysUserRole;

/**
 * ロールサービス
 * 
 * @author ruoyi
 */
public interface ISysRoleService
{
    /**
     * 条件に基づいてページングされたロールデータを検索
     * 
     * @param role ロール情報
     * @return ロールデータのコレクション情報
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * ユーザーIDに基づいてロールリストを検索
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    public Set<String> selectRoleKeys(Long userId);

    /**
     * ユーザーIDに基づいてロール権限を検索
     * 
     * @param userId ユーザーID
     * @return ロールリスト
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * すべてのロールを検索
     * 
     * @return ロールリスト
     */
    public List<SysRole> selectRoleAll();

    /**
     * ロールIDに基づいてロールを検索
     * 
     * @param roleId ロールID
     * @return ロールオブジェクト情報
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * ロールIDに基づいてロールを削除
     * 
     * @param roleId ロールID
     * @return 結果
     */
    public boolean deleteRoleById(Long roleId);

    /**
     * ロールユーザー情報を一括削除
     * 
     * @param ids 削除するデータID
     * @return 結果
     * @throws Exception 例外
     */
    public int deleteRoleByIds(String ids);

    /**
     * 新しいロール情報を保存
     * 
     * @param role ロール情報
     * @return 結果
     */
    public int insertRole(SysRole role);

    /**
     * ロール情報を修正して保存
     * 
     * @param role ロール情報
     * @return 結果
     */
    public int updateRole(SysRole role);

    /**
     * データ権限情報を修正
     * 
     * @param role ロール情報
     * @return 結果
     */
    public int authDataScope(SysRole role);

    /**
     * ロール名が一意かどうかを確認
     * 
     * @param role ロール情報
     * @return 結果
     */
    public boolean checkRoleNameUnique(SysRole role);

    /**
     * ロール権限が一意かどうかを確認
     * 
     * @param role ロール情報
     * @return 結果
     */
    public boolean checkRoleKeyUnique(SysRole role);

    /**
     * ロールの操作が許可されているかどうかを確認
     * 
     * @param role ロール情報
     */
    public void checkRoleAllowed(SysRole role);

    /**
     * ロールがデータ権限を持っているかどうかを確認
     * 
     * @param roleId ロールid
     */
    public void checkRoleDataScope(Long roleId);

    /**
     * ロールIDに基づいてユーザーロールの数を検索
     * 
     * @param roleId ロールID
     * @return 結果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * ロールの状態を変更
     * 
     * @param role ロール情報
     * @return 結果
     */
    public int changeStatus(SysRole role);

    /**
     * ユーザーのロール権限を取り消す
     * 
     * @param userRole ユーザーとロールの関連情報
     * @return 結果
     */
    public int deleteAuthUser(SysUserRole userRole);

    /**
     * 一括でユーザーのロール権限を取り消す
     * 
     * @param roleId ロールID
     * @param userIds 削除するユーザーデータID
     * @return 結果
     */
    public int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 一括でユーザーにロールを選択的に付与する
     * 
     * @param roleId ロールID
     * @param userIds 削除するユーザーデータID
     * @return 結果
     */
    public int insertAuthUsers(Long roleId, String userIds);
}
