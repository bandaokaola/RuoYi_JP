package com.ruoyi.generator.domain;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.ArrayUtils;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;

/**
 * 業務表 gen_table
 * 
 * @author ruoyi
 */
public class GenTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 番号 */
    private Long tableId;

    /** テーブル名 */
    @NotBlank(message = "テーブル名は空にできません")
    private String tableName;

    /** テーブル説明 */
    @NotBlank(message = "テーブル説明は空にできません")
    private String tableComment;

    /** 関連する親テーブルの名前 */
    private String subTableName;

    /** このテーブルが関連する親テーブルの外部キー名 */
    private String subTableFkName;

    /** エンティティクラス名（頭文字大文字） */
    @NotBlank(message = "エンティティクラス名は空にできません")
    private String className;

    /** 使用するテンプレート（crud単表操作、treeツリーテーブル操作、sub親子テーブル操作） */
    private String tplCategory;

    /**  生成パッケージパス */
    @NotBlank(message = "生成パッケージパスは空にできません")
    private String packageName;

    /** 生成モジュール名 */
    @NotBlank(message = "生成モジュール名は空にできません")
    private String moduleName;

    /** 生成業務名 */
    @NotBlank(message = "生成業務名は空にできません")
    private String businessName;

    /** 生成機能名 */
    @NotBlank(message = "生成機能名は空にできません")
    private String functionName;

    /** 生成者 */
    @NotBlank(message = "生成者は空にできません")
    private String functionAuthor;

    /** 生成コード方式（0：zip圧縮ファイル、1：カスタムパス） */
    private String genType;

    /** 生成パス（入力なしの場合、デフォルトはプロジェクトパス） */
    private String genPath;

    /** 主キーカラム */
    private GenTableColumn pkColumn;

    /** 子テーブル情報 */
    private GenTable subTable;

    /** テーブルカラム情報 */
    @Valid
    private List<GenTableColumn> columns;

    /** その他生成オプション */
    private String options;

    /** ツリーコードフィールド */
    private String treeCode;

    /** ツリー親コードフィールド */
    private String treeParentCode;

    /** ツリー名フィールド */
    private String treeName;

    /** 上位メニューIDフィールド */
    private String parentMenuId;

    /** 上位メニュー名フィールド */
    private String parentMenuName;

    public Long getTableId()
    {
        return tableId;
    }

    public void setTableId(Long tableId)
    {
        this.tableId = tableId;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public String getSubTableName()
    {
        return subTableName;
    }

    public void setSubTableName(String subTableName)
    {
        this.subTableName = subTableName;
    }

    public String getSubTableFkName()
    {
        return subTableFkName;
    }

    public void setSubTableFkName(String subTableFkName)
    {
        this.subTableFkName = subTableFkName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getTplCategory()
    {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory)
    {
        this.tplCategory = tplCategory;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public String getFunctionAuthor()
    {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor)
    {
        this.functionAuthor = functionAuthor;
    }

    public String getGenType()
    {
        return genType;
    }

    public void setGenType(String genType)
    {
        this.genType = genType;
    }

    public String getGenPath()
    {
        return genPath;
    }

    public void setGenPath(String genPath)
    {
        this.genPath = genPath;
    }

    public GenTableColumn getPkColumn()
    {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn)
    {
        this.pkColumn = pkColumn;
    }

    public GenTable getSubTable()
    {
        return subTable;
    }

    public void setSubTable(GenTable subTable)
    {
        this.subTable = subTable;
    }

    public List<GenTableColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns)
    {
        this.columns = columns;
    }

    public String getOptions()
    {
        return options;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getTreeCode()
    {
        return treeCode;
    }

    public void setTreeCode(String treeCode)
    {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode()
    {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode)
    {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName()
    {
        return treeName;
    }

    public void setTreeName(String treeName)
    {
        this.treeName = treeName;
    }

    public String getParentMenuId()
    {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId)
    {
        this.parentMenuId = parentMenuId;
    }

    public String getParentMenuName()
    {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName)
    {
        this.parentMenuName = parentMenuName;
    }

    public boolean isSub()
    {
        return isSub(this.tplCategory);
    }

    public static boolean isSub(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public boolean isTree()
    {
        return isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public boolean isCrud()
    {
        return isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public boolean isSuperColumn(String javaField)
    {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField)
    {
        if (isTree(tplCategory))
        {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }
}