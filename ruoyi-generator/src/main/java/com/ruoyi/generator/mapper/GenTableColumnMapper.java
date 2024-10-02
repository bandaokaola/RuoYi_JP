package com.ruoyi.generator.mapper;

import java.util.List;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * ビジネスフィールド データ層
 * 
 * @author ruoyi
 */
public interface GenTableColumnMapper
{
    /**
     * テーブル名に基づいて列情報を取得
     * 
     * @param tableName テーブル名
     * @return 列情報
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * ビジネスフィールドリストを取得
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return ビジネスフィールドの集合
     */
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn);

    /**
     * ビジネスフィールドを追加
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return 結果
     */
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * ビジネスフィールドを更新
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return 結果
     */
    public int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * ビジネスフィールドを削除
     * 
     * @param genTableColumns 列データ
     * @return 結果
     */
    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

    /**
     * ビジネスフィールドを一括削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    public int deleteGenTableColumnByIds(Long[] ids);
}
