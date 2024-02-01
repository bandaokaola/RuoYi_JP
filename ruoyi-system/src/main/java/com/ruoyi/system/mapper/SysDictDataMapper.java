package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * 辞書テーブル データアクセス層
 * 
 * @author ruoyi
 */
public interface SysDictDataMapper
{
    /**
     * 条件に基づいて辞書データをページネーションで検索します。
     * 
     * @param dictData 辞書データ情報
     * @return 辞書データのコレクション情報
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 辞書タイプに基づいて辞書データを検索します。
     * 
     * @param dictType 辞書タイプ
     * @return 辞書データのコレクション情報
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 辞書タイプおよび辞書キー値に基づいて辞書データ情報を検索します。
     * 
     * @param dictType 辞書タイプ
     * @param dictValue 辞書キー値
     * @return 辞書ラベル
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 辞書データIDに基づいて情報を検索します。
     * 
     * @param dictCode 辞書データID
     * @return 辞書データ
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 辞書タイプに基づいて辞書データをカウントします。
     * 
     * @param dictType 辞書タイプ
     * @return 辞書データの数
     */
    public int countDictDataByType(String dictType);

    /**
     * 辞書IDに基づいて辞書データ情報を削除します。
     * 
     * @param dictCode 辞書データID
     * @return 結果
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * バッチで辞書データを削除します。
     * 
     * @param ids 削除するデータ
     * @return 結果
     */
    public int deleteDictDataByIds(String[] ids);

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

    /**
     * 辞書タイプを同期して変更します。
     * 
     * @param oldDictType 旧辞書タイプ
     * @param newDictType 新辞書タイプ
     * @return 結果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
