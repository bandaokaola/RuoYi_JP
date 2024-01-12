package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * ユーザーオブジェクト sys_user
 * 
 * @author ruoyi
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ユーザーID */
    @Excel(name = "ユーザーID", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 部門ID */
    @Excel(name = "部門番号", type = Type.IMPORT)
    private Long deptId;

    /** 部門親ID */
    private Long parentId;

    /** 役割ID */
    private Long roleId;

    /** ログイン名 */
    @Excel(name = "ログイン名")
    private String loginName;

    /** ユーザー名 */
    @Excel(name = "ユーザー名")
    private String userName;

    /** ユーザータイプ */
    private String userType;

    /** ユーザーのメールアドレス */
    @Excel(name = "ユーザーのメールアドレス")
    private String email;

    /** 携帯電話番号 */
    @Excel(name = "携帯電話番号")
    private String phonenumber;

    /** ユーザーの性別 */
    @Excel(name = "ユーザーの性別", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** ユーザーのアバター */
    private String avatar;

    /** パスワード */
    private String password;

    /** ソルト暗号化 */
    private String salt;

    /** アカウントのステータス（0正常 1停止） */
    @Excel(name = "アカウントのステータス", readConverterExp = "0=正常,1=停止")
    private String status;

    /** 削除フラグ（0存在 2削除） */
    private String delFlag;

    /** 最終ログインIP */
    @Excel(name = "最終ログインIP", type = Type.EXPORT)
    private String loginIp;

    /** 最終ログイン時間 */
    @Excel(name = "最終ログイン時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /** パスワードの最終更新時間 */
    private Date pwdUpdateDate;

    /** 部門オブジェクト */
    @Excels({
        @Excel(name = "部門名", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "部門責任者", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    private List<SysRole> roles;

    /** 役割グループ */
    private Long[] roleIds;

    /** ポストグループ */
    private Long[] postIds;

    public SysUser()
    {

    }

    public SysUser(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Xss(message = "ログインアカウントにスクリプト文字を含めることはできません")
    @NotBlank(message = "ログインアカウントは必須です")
    @Size(min = 0, max = 30, message = "ログインアカウントの長さは30文字を超えることはできません")
    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    @Xss(message = "ユーザー名にスクリプト文字を含めることはできません")
    @Size(min = 0, max = 30, message = "ユーザー名の長さは30文字を超えることはできません")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    @Email(message = "メールアドレスの形式が正しくありません")
    @Size(min = 0, max = 50, message = "メールアドレスの長さは50文字を超えることはできません")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "携帯電話番号の長さは11文字を超えることはできません")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @JsonIgnore
    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public Date getPwdUpdateDate()
    {
        return pwdUpdateDate;
    }

    public void setPwdUpdateDate(Date pwdUpdateDate)
    {
        this.pwdUpdateDate = pwdUpdateDate;
    }

    public SysDept getDept()
    {
        if (dept == null)
        {
            dept = new SysDept();
        }
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("loginName", getLoginName())
            .append("userName", getUserName())
            .append("userType", getUserType())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
			.append("roles", getRoles())
            .toString();
    }
}
