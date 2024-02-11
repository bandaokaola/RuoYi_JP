package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysRoleDept;
import com.ruoyi.system.domain.SysRoleMenu;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysRoleMenuMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysRoleService;

/**
 * ロールサービスの実装
 * 
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 条件に基づいてページングされたロールデータを検索
     * 
     * @param role ロール情報
     * @return ロールデータのコレクション情報
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * ユーザーIDに基づいて権限を検索
     * 
     * @param userId ユーザーID
     * @return 権限リスト
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * ユーザーIDに基づいてロールリストを検索
     * 
     * @param userId ユーザーID
     * @return ロールリスト
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * すべてのロールを検索
     * 
     * @return ロールリスト
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * ロールIDに基づいてロールを検索
     * 
     * @param roleId ロールID
     * @return ロールオブジェクト情報
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * ロールIDに基づいてロールを削除
     * 
     * @param roleId ロールID
     * @return 結果
     */
    @Override
    @Transactional
    public boolean deleteRoleById(Long roleId)
    {
        // ロールとメニューの関連を削除
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // ロールとメニューの関連を削除
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 一括でロール情報を削除
     * 
     * @param ids 削除するデータID
     * @throws Exception
     */
    @Override
    @Transactional
    public int deleteRoleByIds(String ids)
    {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ServiceException(String.format("%1$sは既に割り当てられています。削除できません。", role.getRoleName()));
            }
        }
        // ロールとメニューの関連を削除
        roleMenuMapper.deleteRoleMenu(roleIds);
        // ロールと部門の関連を削除
        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 新しいロール情報を保存
     * 
     * @param role ロール情報
     * @return 結果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // 新しいロール情報を挿入
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * ロール情報を修正して保存
     * 
     * @param role ロール情報
     * @return 結果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        // ロール情報を修正
        roleMapper.updateRole(role);
        // ロールとメニューの関連を削除
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * データ権限情報を修正
     * 
     * @param role ロール
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // ロール情報を修正
        roleMapper.updateRole(role);
        // ロールと部門の関連を削除
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新しいロールと部門情報（データ権限）を挿入
        return insertRoleDept(role);
    }

    /**
     * 新しいロールメニュー情報を挿入
     * 
     * @param role ロールオブジェクト
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新しいユーザーとロールの関連
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新しいロール部門情報（データ権限）を挿入
     *
     * @param role ロールオブジェクト
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * ロール名が一意かどうかを検証
     * 
     * @param role ロール情報
     * @return 結果
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ロール権限が一意かどうかを検証
     * 
     * @param role ロール情報
     * @return 結果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * ロールが操作を許可するかどうかを検証
     * 
     * @param role ロール情報
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new ServiceException("管理者ロールの操作は許可されていません");
        }
    }

    /**
     * ロールがデータ権限を持っているかどうかを検証
     * 
     * @param roleId ロールID
     */
    @Override
    public void checkRoleDataScope(Long roleId)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles))
            {
                throw new ServiceException("ロールデータにアクセスする権限がありません！");
            }
        }
    }

    /**
     * ロールIDに基づいてユーザーが使用する回数を検索
     * 
     * @param roleId ロールID
     * @return 結果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * ロールの状態を変更する
     * 
     * @param role ロール情報
     * @return 結果
     */
    @Override
    public int changeStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * ユーザーのロールを取り消す
     * 
     * @param userRole ユーザーとロールの関連情報
     * @return 結果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 一括でユーザーのロールを取り消す
     * 
     * @param roleId ロールID
     * @param userIds 削除するユーザーのデータID
     * @return 結果
     */
    @Override
    public int deleteAuthUsers(Long roleId, String userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 一括でユーザーにロールを許可する
     * 
     * @param roleId ロールID
     * @param userIds 削除するユーザーのデータID
     * @return 結果
     */
    @Override
    public int insertAuthUsers(Long roleId, String userIds)
    {
        Long[] users = Convert.toLongArray(userIds);
        // 新しいユーザーとロールの管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : users)
        {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
