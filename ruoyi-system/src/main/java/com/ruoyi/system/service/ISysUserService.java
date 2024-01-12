package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysUserRole;

/**
 * ユーザーサービス
 * 
 * @author ruoyi
 */
public interface ISysUserService
{
    /**
     * 条件に基づいてユーザーリストをページ分割で取得する
     * 
     * @param user ユーザー情報
     * @return ユーザー情報のリスト
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 条件に基づいて割り当て済みユーザーロールのリストをページ分割で取得する
     * 
     * @param user ユーザー情報
     * @return ユーザー情報のリスト
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 条件に基づいて未割り当てユーザーロールのリストをページ分割で取得する
     * 
     * @param user ーザー情報
     * @return ユーザー情報のリスト
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * ユーザー名を使用してユーザーを検索する
     * 
     * @param userName ユーザー名
     * @return ユーザーオブジェクト情報
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 電話番号を使用してユーザーを検索する
     * 
     * @param phoneNumber 電話番号
     * @return ユーザーオブジェクト情報
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * メールアドレスを使用してユーザーを検索する
     * 
     * @param email メールアドレス
     * @return ユーザーオブジェクト情報
     */
    public SysUser selectUserByEmail(String email);

    /**
     * ユーザーIDを使用してユーザーを検索する
     * 
     * @param userId ユーザーID
     * @return ユーザーオブジェクト情報
     */
    public SysUser selectUserById(Long userId);

    /**
     * ユーザーIDを使用してユーザーと関連するロールのリストを検索する
     * 
     * @param userId ユーザーID
     * @return ユーザーとロールの関連リスト
     */
    public List<SysUserRole> selectUserRoleByUserId(Long userId);

    /**
     * ユーザーIDを使用してユーザーを削除する
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    public int deleteUserById(Long userId);

    /**
     * ユーザー情報のバッチ削除
     * 
     * @param ids 削除するデータID
     * @return 结果
     * @throws Exception 例外
     */
    public int deleteUserByIds(String ids);

    /**
     * ユーザー情報を保存する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public int insertUser(SysUser user);

    /**
     * ユーザー情報を登録する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public boolean registerUser(SysUser user);

    /**
     * ユーザー情報を更新する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public int updateUser(SysUser user);

    /**
     * ユーザー詳細情報を更新する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public int updateUserInfo(SysUser user);

    /**
     * ユーザーにロールを承認する
     * 
     * @param userId ユーザーID
     * @param roleIds ロールグループ
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * ユーザーのパスワード情報を変更する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public int resetUserPwd(SysUser user);

    /**
     * ユーザー名が一意かどうかを確認する
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public boolean checkLoginNameUnique(SysUser user);

    /**
     * 電話番号が一意かどうかを確認する
     *
     * @param user ユーザー情報
     * @return 結果
     */
    public boolean checkPhoneUnique(SysUser user);

    /**
     * メールアドレスが一意かどうかを確認する
     *
     * @param user ユーザー情報
     * @return 結果
     */
    public boolean checkEmailUnique(SysUser user);

    /**
     * ユーザーが操作を許可されているかどうかを確認する
     * 
     * @param user ユーザー情報
     */
    public void checkUserAllowed(SysUser user);

    /**
     * ユーザーがデータ権限を持っているかどうかを確認する
     * 
     * @param userId ユーザーID
     */
    public void checkUserDataScope(Long userId);

    /**
     * ユーザーIDに基づいてユーザーが所属するロールグループを検索する
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    public String selectUserRoleGroup(Long userId);

    /**
     * ユーザーIDに基づいてユーザーが所属するポストグループを検索する
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    public String selectUserPostGroup(Long userId);

    /**
     * ユーザーデータのインポート
     * 
     * @param userList ユーザーデータリスト
     * @param isUpdateSupport 既に存在する場合、データを更新するかどうか
     * @param operName 操作ユーザー
     * @return 結果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * ユーザー状態の変更
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    public int changeStatus(SysUser user);
}
