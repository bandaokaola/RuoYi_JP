package com.ruoyi.generator.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.generator.domain.GenTable;

/**
 * ビジネス サービス層
 * 
 * @author ruoyi
 */
public interface IGenTableService
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
     * ビジネス情報を取得
     * 
     * @param id ビジネスID
     * @return ビジネス情報
     */
    public GenTable selectGenTableById(Long id);

    /**
     * ビジネス情報を更新
     * 
     * @param genTable ビジネス情報
     * @return 結果
     */
    public void updateGenTable(GenTable genTable);

    /**
     * ビジネス情報を削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    public void deleteGenTableByIds(String ids);

    /**
     * テーブルを作成
     *
     * @param sql テーブル作成のSQL文
     * @return 結果
     */
    public boolean createTable(String sql);

    /**
     * テーブル構造をインポート
     *
     * @param tableList インポートするテーブルリスト
     * @param operName 操作員
     */
    public void importGenTable(List<GenTable> tableList, String operName);

    /**
     * コードプレビュー
     * 
     * @param tableId コードをプレビュー
     * @return プレビューデータのリスト
     */
    public Map<String, String> previewCode(Long tableId);

    /**
     * コードを生成（ダウンロード方式）
     * 
     * @param tableName テーブル名
     * @return データ
     */
    public byte[] downloadCode(String tableName);

    /**
     * コードを生成（カスタムパス）
     * 
     * @param tableName テーブル名
     */
    public void generatorCode(String tableName);
    
    /**
     *  データベースを同期
     * 
     * @param tableName テーブル名
     */
    public void synchDb(String tableName);

    /**
     * コードを一括生成（ダウンロード方式）
     * 
     * @param tableNames テーブル名の配列
     * @return データ
     */
    public byte[] downloadCode(String[] tableNames);

    /**
     * パラメータ検証を保存時に編集
     * 
     * @param genTable ビジネス情報
     */
    public void validateEdit(GenTable genTable);
}
