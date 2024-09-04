package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * ビジネスフィールド サービス層
 * 
 * @author ruoyi
 */
public interface IGenTableColumnService
{
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
     * ビジネスフィールド情報を削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    public int deleteGenTableColumnByIds(String ids);
}
