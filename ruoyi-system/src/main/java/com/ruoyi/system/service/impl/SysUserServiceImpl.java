package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.security.Md5Utils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * ユーザーサービスの実装
 * 
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    /**
     * 条件に基づいてユーザーリストをページングで取得
     * 
     * @param user ユーザー情報
     * @return ユーザー情報リスト
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 条件に基づいて分配済みのユーザーの役割リストをページングで取得
     * 
     * @param user ユーザー情報
     * @return ユーザー情報リスト
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 条件に基づいて未分配のユーザーの役割リストをページングで取得
     * 
     * @param user ユーザー情報
     * @return ユーザー情報リスト
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * ユーザー名でユーザーを検索
     * 
     * @param userName ユーザー名
     * @return ユーザーオブジェクト情報
     */
    @Override
    public SysUser selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 電話番号でユーザーを検索
     * 
     * @param phoneNumber 電話番号
     * @return ユーザーオブジェクト情報
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * メールアドレスでユーザーを検索
     * 
     * @param email メールアドレス
     * @return ユーザーオブジェクト情報
     */
    @Override
    public SysUser selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * ユーザーIDでユーザーを検索
     * 
     * @param userId ユーザーID
     * @return ユーザーオブジェクト情報
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * ユーザーIDでユーザーと役割の関連を検索
     * 
     * @param userId ユーザーID
     * @return ユーザーと役割の関連リスト
     */
    @Override
    public List<SysUserRole> selectUserRoleByUserId(Long userId)
    {
        return userRoleMapper.selectUserRoleByUserId(userId);
    }

    /**
     * ユーザーIDでユーザーを削除
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // ユーザーと役割の関連を削除
        userRoleMapper.deleteUserRoleByUserId(userId);
        // ユーザーとポジションの関連を削除
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * ユーザー情報をバッチで削除
     * 
     * @param ids 削除するデータID
     * @return 結果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String ids)
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // ユーザーと役割の関連を削除
        userRoleMapper.deleteUserRole(userIds);
        // ユーザーとポジションの関連を削除
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * ユーザー情報を新規保存
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        // ユーザー情報を新規で追加
        int rows = userMapper.insertUser(user);
        // ユーザーとポジションの関連を新規で追加
        insertUserPost(user);
        // ユーザーと役割の関連を新規で追加
        insertUserRole(user.getUserId(), user.getRoleIds());
        return rows;
    }

    /**
     * ユーザー情報を登録
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        user.setUserType(UserConstants.REGISTER_USER_TYPE);
        return userMapper.insertUser(user) > 0;
    }

    /**
     * ユーザー情報を更新保存
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // ユーザーと役割の関連を削除
        userRoleMapper.deleteUserRoleByUserId(userId);
        // ユーザーと役割の関連を新規で追加
        insertUserRole(user.getUserId(), user.getRoleIds());
        // ユーザーとポジションの関連を削除
        userPostMapper.deleteUserPostByUserId(userId);
        // ユーザーとポジションの関連を新規で追加
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * ユーザーの個人情報を更新
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    public int updateUserInfo(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * ユーザーに役割を付与
     * 
     * @param userId ユーザーID
     * @param roleIds 役割グループ
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * ユーザーのパスワードをリセット
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    public int resetUserPwd(SysUser user)
    {
        return updateUserInfo(user);
    }

    /**
     * ユーザー役割情報を新規保存
     * 
     * @param userId ユーザーID
     * @param roleIds 役割グループ
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotNull(roleIds))
        {
            // ユーザーと役割の関連を新規で追加
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * ユーザーポジション情報を新規保存
     * 
     * @param user ユーザーオブジェクト
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // ユーザーとポジションの関連を新規で追加
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * ユーザー名の一意性を検証
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    public boolean checkLoginNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkLoginNameUnique(user.getLoginName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 電話番号の一意性を検証
     *
     * @param user ユーザー情報
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * メールアドレスの一意性を検証
     *
     * @param user ユーザー情報
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ユーザーの許可された操作かを検証
     * 
     * @param user ユーザー情報
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("管理者ユーザーの操作は許可されていません");
        }
    }

    /**
     * ユーザーがデータ権限を持っているかを検証
     * 
     * @param userId ユーザーID
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("ユーザーデータにアクセスする権限がありません！");
            }
        }
    }

    /**
     * ユーザーの所属する役割グループを検索
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * ユーザーの所属するポジショングループを検索
     * 
     * @param userId ユーザーID
     * @return 結果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * ユーザーデータのインポート
     * 
     * @param userList ユーザーデータリスト
     * @param isUpdateSupport 既に存在する場合、データを更新するかどうか
     * @param operName 操作ユーザー
     * @return 結果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("インポートするユーザーデータがありません！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
            	// ユーザーが存在するかどうかを検証
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、アカウント " + user.getLoginName() + " が正常にインポートされました");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(operName);
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、アカウント " + user.getLoginName() + " が正常に更新されました");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、アカウント " + user.getLoginName() + " は既に存在しています");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、アカウント " + user.getLoginName() + " のインポートに失敗しました：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
        	failureMsg.insert(0, "インポートに失敗しました！合計 " + failureNum + " 件のデータが正しくありません。エラーは以下の通りです：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
        	successMsg.insert(0, "データはすべて正常にインポートされました！合計 " + successNum + " 件のデータが以下の通りです：");
        }
        return successMsg.toString();
    }

    /**
     * ユーザーのステータスを変更
     * 
     * @param user ユーザー情報
     * @return 結果
     */
    @Override
    public int changeStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }
}
