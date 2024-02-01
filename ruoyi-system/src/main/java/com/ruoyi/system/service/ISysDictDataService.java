package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * 辞書サービス
 * 
 * @author ruoyi
 */
public interface ISysDictDataService
{
    /**
     * 条件に基づいて辞書データをページネーションで検索します。
     * 
     * @param dictData 辞書データ情報
     * @return 辞書データのコレクション情報
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 辞書タイプおよび辞書キー値に基づいて辞書データ情報を検索します。
     * 
     * @param dictType 辞書タイプ
     * @param dictValue 辞書キー値
     * @return 辞書ラベル
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 辞書データIDに基づいて情報を検索します。
     * 
     * @param dictCode 辞書データID
     * @return 辞書データ
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * バッチで辞書データを削除します。
     * 
     * @param ids 削除するデータ
     */
    public void deleteDictDataByIds(String ids);

    /**
     * 新しい辞書データ情報を保存します。
     * 
     * @param dictData 辞書データ情報
     * @return 結果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 辞書データ情報を変更して保存します。
     * 
     * @param dictData 辞書データ情報
     * @return 結果
     */
    public int updateDictData(SysDictData dictData);
}
