package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysDeptService;

/**
 * 部門管理サービスの実装
 * 
 * @author ruoyi
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 部門管理データを検索する
     * 
     * @param dept 部門情報
     * @return 部門情報リスト
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 部門管理ツリーを検索する
     * 
     * @param dept 部門情報
     * @return すべての部門情報
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDept dept)
    {
        List<SysDept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 部門管理ツリーを検索する（下位を除く）
     * 
     * @param deptId 部門ID
     * @return すべての部門情報
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept)
    {
        Long excludeId = dept.getExcludeId();
        List<SysDept> depts = deptMapper.selectDeptList(dept);
        if (excludeId.intValue() > 0)
        {
            depts.removeIf(d -> d.getDeptId().intValue() == excludeId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), excludeId + ""));
        }
        List<Ztree> ztrees = initZtree(depts);
        return ztrees;
    }

    /**
     * 役割IDに基づいて部門を検索する（データ権限）
     *
     * @param role 役割オブジェクト
     * @return 部門リスト（データ権限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role)
    {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDept> deptList = SpringUtils.getAopProxy(this).selectDeptList(new SysDept());
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        }
        else
        {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * オブジェクトを部門ツリーに変換する
     *
     * @param deptList 部門リスト
     * @return ツリー構造リスト
     */
    public List<Ztree> initZtree(List<SysDept> deptList)
    {
        return initZtree(deptList, null);
    }

    /**
     * オブジェクトを部門ツリーに変換する
     *
     * @param deptList 部門リスト
     * @param roleDeptList 役割が既に存在するメニューリスト
     * @return ツリー構造リスト
     */
    public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList)
    {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysDept dept : deptList)
        {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 親部門IDに基づいて下位部門の数を検索する
     * 
     * @param parentId 部門ID
     * @return 結果
     */
    @Override
    public int selectDeptCount(Long parentId)
    {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 部門にユーザーが存在するかどうかを検索する
     * 
     * @param deptId 部門ID
     * @return 結果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 部門管理情報を削除する
     * 
     * @param deptId 部門ID
     * @return 結果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新規に部門情報を保存する
     * 
     * @param dept 部門情報
     * @return 結果
     */
    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 親ノードが "正常" 状態でない場合、サブノードの追加は許可されません
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("部門が停止されているため、新しいサブノードを追加できません");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 部門情報を更新して保存する
     * 
     * @param dept 部門情報
     * @return 結果
     */
    @Override
    @Transactional
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors()))
        {
            // 部門が有効な場合、その部門のすべての親部門を有効にする
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * この部門の親部門の状態を更新する
     * 
     * @param dept 現在の部門
     */
    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 子要素の関係を更新する
     * 
     * @param deptId 変更された部門ID
     * @param newAncestors 新しい親IDのコレクション
     * @param oldAncestors 古い親IDのコレクション
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 部門IDに基づいて情報を検索する
     * 
     * @param deptId 部門ID
     * @return 部門情報
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * すべての子部門（正常な状態）を含むすべての子部門を検索する
     * 
     * @param deptId 部門ID
     * @return 子部門数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 部門名が一意かどうかを確認する
     * 
     * @param dept 部門情報
     * @return 結果
     */
    @Override
    public boolean checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 部門にデータ権限があるかどうかを確認する
     * 
     * @param deptId 部門ID
     */
    @Override
    public void checkDeptDataScope(Long deptId)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts))
            {
                throw new ServiceException("部門データにアクセスする権限がありません！");
            }
        }
    }
}
