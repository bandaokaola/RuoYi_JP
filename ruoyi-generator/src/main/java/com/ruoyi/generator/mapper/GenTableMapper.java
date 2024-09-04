package com.ruoyi.generator.mapper;

import java.util.List;
import com.ruoyi.generator.domain.GenTable;

/**
 * ビジネス データ層
 * 
 * @author ruoyi
 */
public interface GenTableMapper
{
    /**
     * ビジネスリストを取得
     * 
     * @param genTable ビジネス情報
     * @return ビジネス集合
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * データベースリストを取得
     * 
     * @param genTable ビジネス情報
     * @return データベーステーブルの集合
     */
    public List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * データベースリストを取得
     * 
     * @param tableNames テーブル名の配列
     * @return データベーステーブルの集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 全てのテーブル情報を取得
     * 
     * @return テーブル情報の集合
     */
    public List<GenTable> selectGenTableAll();

    /**
     * テーブルIDによるビジネス情報を取得
     * 
     * @param id ビジネスID
     * @return ビジネス情報
     */
    public GenTable selectGenTableById(Long id);

    /**
     * テーブル名によるビジネス情報を取得
     * 
     * @param tableName テーブル名
     * @return ビジネス情報
     */
    public GenTable selectGenTableByName(String tableName);

    /**
     * ビジネスを追加
     * 
     * @param genTable ビジネス情報
     * @return 結果
     */
    public int insertGenTable(GenTable genTable);

    /**
     * ビジネスを更新
     * 
     * @param genTable ビジネス情報
     * @return 結果
     */
    public int updateGenTable(GenTable genTable);

    /**
     * ビジネスを一括削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    public int deleteGenTableByIds(Long[] ids);

    /**
     * テーブルを作成
     *
     * @param sql
     * @return 結果
     */
    public int createTable(String sql);
}