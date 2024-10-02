package com.ruoyi.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.text.CharsetKit;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.mapper.GenTableMapper;
import com.ruoyi.generator.service.IGenTableService;
import com.ruoyi.generator.util.GenUtils;
import com.ruoyi.generator.util.VelocityInitializer;
import com.ruoyi.generator.util.VelocityUtils;

/**
 * ビジネス サービス層実装
 * 
 * @author ruoyi
 */
@Service
public class GenTableServiceImpl implements IGenTableService
{
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * ビジネス情報を取得
     * 
     * @param id ビジネスID
     * @return ビジネス情報
     */
    @Override
    public GenTable selectGenTableById(Long id)
    {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * ビジネスリストを取得
     * 
     * @param genTable ビジネス情報
     * @return ビジネス集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable)
    {
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * 查询据库列表
     * 
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable)
    {
        return genTableMapper.selectDbTableList(genTable);
    }

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames)
    {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 查询所有表信息
     * 
     * @return 表信息集合
     */
    @Override
    public List<GenTable> selectGenTableAll()
    {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * ビジネス情報を更新
     * 
     * @param genTable ビジネス情報
     * @return 結果
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable)
    {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0)
        {
            for (GenTableColumn genTableColumn : genTable.getColumns())
            {
                genTableColumnMapper.updateGenTableColumn(genTableColumn);
            }
        }
    }

    /**
     * ビジネス情報を削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    @Override
    @Transactional
    public void deleteGenTableByIds(String ids)
    {
        genTableMapper.deleteGenTableByIds(Convert.toLongArray(ids));
        genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }

    /**
     * テーブルを作成
     *
     * @param sql テーブル作成のSQL文
     * @return 結果
     */
    @Override
    public boolean createTable(String sql)
    {
        return genTableMapper.createTable(sql) == 0;
    }

    /**
     * テーブル構造をインポート
     *
     * @param tableList インポートするテーブルリスト
     * @param operName 操作員
     */
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList, String operName)
    {
        try
        {
            for (GenTable table : tableList)
            {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0)
                {
                    // 列情報を保存
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns)
                    {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException("インポートに失敗しました：" + e.getMessage());
        }
    }

    /**
     * コードプレビュー
     * 
     * @param tableId コードをプレビュー
     * @return プレビューデータのリスト
     */
    @Override
    public Map<String, String> previewCode(Long tableId)
    {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // テーブル情報を照会
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // 设置主子表信息
        setSubTable(table);
        // 主子テーブル情報を設定
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        //  テンプレートリストを取得
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // テンプレートをレンダリング
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * コードを生成（ダウンロード方式）
     * 
     * @param tableName テーブル名
     * @return データ
     */
    @Override
    public byte[] downloadCode(String tableName)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * コードを生成（カスタムパス）
     * 
     * @param tableName テーブル名
     */
    @Override
    public void generatorCode(String tableName)
    {
        // テーブル情報を照会
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 主子テーブル情報を設定
        setSubTable(table);
        // 主キー列情報を設定
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // テンプレートリストを取得
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            if (!StringUtils.contains(template, "sql.vm"))
            {
                // テンプレートをレンダリング
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, Constants.UTF8);
                tpl.merge(context, sw);
                try
                {
                    String path = getGenPath(table, template);
                    FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
                }
                catch (IOException e)
                {
                    throw new ServiceException("テンプレートのレンダリングに失敗しました。テーブル名：" + table.getTableName());
                }
            }
        }
    }

    /**
     *  データベースを同期
     * 
     * @param tableName テーブル名
     */
    @Override
    @Transactional
    public void synchDb(String tableName)
    {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        Map<String, GenTableColumn> tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumnName, Function.identity()));

        List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (StringUtils.isEmpty(dbTableColumns))
        {
            throw new ServiceException("データの同期に失敗しました。元のテーブル構造が存在しません");
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            GenUtils.initColumnField(column, table);
            if (tableColumnMap.containsKey(column.getColumnName()))
            {
                GenTableColumn prevColumn = tableColumnMap.get(column.getColumnName());
                column.setColumnId(prevColumn.getColumnId());
                if (column.isList())
                {
                    // 列がリストの場合、クエリタイプ/辞書タイプのオプションを保持
                    column.setDictType(prevColumn.getDictType());
                    column.setQueryType(prevColumn.getQueryType());
                }
                if (StringUtils.isNotEmpty(prevColumn.getIsRequired()) && !column.isPk()
                        && (column.isInsert() || column.isEdit())
                        && ((column.isUsableColumn()) || (!column.isSuperColumn())))
                {
                    // (追加/編集&非主キー/非無視および親属性)の場合、必須/表示タイプのオプションを保持
                    column.setIsRequired(prevColumn.getIsRequired());
                    column.setHtmlType(prevColumn.getHtmlType());
                }
                genTableColumnMapper.updateGenTableColumn(column);
            }
            else
            {
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(delColumns))
        {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

    /**
     * コードを一括生成（ダウンロード方式）
     * 
     * @param tableNames テーブル名の配列
     * @return データ
     */
    @Override
    public byte[] downloadCode(String[] tableNames)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip)
    {
        // テーブル情報を照会
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 主子テーブル情報を設定
        setSubTable(table);
        // 主キー列情報を設定
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // テンプレートリストを取得
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // テンプレートをレンダリング
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                // zipに追加
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("テンプレートのレンダリングに失敗しました。テーブル名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * パラメータ検証を保存時に編集
     * 
     * @param genTable ビジネス情報
     */
    @Override
    public void validateEdit(GenTable genTable)
    {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory()))
        {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE)))
            {
                throw new ServiceException("ツリーのコードフィールドは空にできません");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE)))
            {
                throw new ServiceException("ツリーの親コードフィールドは空にできません");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME)))
            {
                throw new ServiceException("ツリーの名前フィールドは空にできません");
            }
        }
        else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory()))
        {
            if (StringUtils.isEmpty(genTable.getSubTableName()))
            {
                throw new ServiceException("関連するサブテーブルのテーブル名は空にできません");
            }
            else if (StringUtils.isEmpty(genTable.getSubTableFkName()))
            {
                throw new ServiceException("サブテーブルの関連する外部キー名は空にできません");
            }
        }
    }

    /**
     * 主キー列情報を設定
     * 
     * @param table ビジネステーブル情報
     */
    public void setPkColumn(GenTable table)
    {
        for (GenTableColumn column : table.getColumns())
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn()))
        {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory()))
        {
            for (GenTableColumn column : table.getSubTable().getColumns())
            {
                if (column.isPk())
                {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSubTable().getPkColumn()))
            {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     * 
     * @param table 业务表信息
     */
    public void setSubTable(GenTable table)
    {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName))
        {
            table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
        }
    }

    /**
     * 主子テーブル情報を設定
     * 
     * @param genTable ビジネステーブル情報
     */
    public void setTableFromOptions(GenTable genTable)
    {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj))
        {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setParentMenuId(parentMenuId);
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /**
     * コード生成パスを取得
     * 
     * @param table ビジネステーブル情報
     * @param template テンプレートファイルのパス
     * @return 生成パス
     */
    public static String getGenPath(GenTable table, String template)
    {
        String genPath = table.getGenPath();
        if (StringUtils.equals(genPath, "/"))
        {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}