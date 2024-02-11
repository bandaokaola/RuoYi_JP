package com.ruoyi.common.core.domain.entity;

import java.util.Set;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ロールテーブル sys_role
 * 
 * @author ruoyi
 */
public class SysRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ロールID */
    @Excel(name = "ロールID", cellType = ColumnType.NUMERIC)
    private Long roleId;

    /** ロール名 */
    @Excel(name = "ロール名")
    private String roleName;

    /** ロール権限 */
    @Excel(name = "ロール権限")
    private String roleKey;

    /** ロール並び替え */
    @Excel(name = "ロール並び替え", cellType = ColumnType.NUMERIC)
    private String roleSort;

    /** データ範囲（1：全データ権限；2：カスタムデータ権限；3：本部門データ権限；4：本部門および以下のデータ権限；5：個人のみデータ権限）  */
    @Excel(name = "データ範囲", readConverterExp = "1=全データ権限,2=カスタムデータ権限,3=本部門データ権限,4=本部門および以下のデータ権限,5=個人のみデータ権限")
    private String dataScope;

    /** ロール状態（0正常 1停用） */
    @Excel(name = "ロール状態", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 削除フラグ（0は存在 2は削除） */
    private String delFlag;

    /** ユーザーがこのロールを持っているかどうかを示すデフォルトは存在しない */
    private boolean flag = false;

    /** メニューグループ */
    private Long[] menuIds;

    /** 部門グループ（データ権限） */
    private Long[] deptIds;

    /** ロールメニュー権限 */
    private Set<String> permissions;

    public SysRole()
    {

    }

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

    public String getDataScope()
    {
        return dataScope;
    }

    public void setDataScope(String dataScope)
    {
        this.dataScope = dataScope;
    }

    @NotBlank(message = "ロール名は必須です")
    @Size(min = 0, max = 30, message = "ロール名は30文字を超えてはいけません")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @NotBlank(message = "権限文字は必須です")
    @Size(min = 0, max = 100, message = "権限文字は100文字を超えてはいけません")
    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    @NotBlank(message = "表示順は必須です")
    public String getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(String roleSort)
    {
        this.roleSort = roleSort;
    }

    public String getStatus()
    {
        return status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }

    public Long[] getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(Long[] deptIds)
    {
        this.deptIds = deptIds;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
