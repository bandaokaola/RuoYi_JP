package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;

/**
 * 辞書 サービス層
 * 
 * @author ruoyi
 */
public interface ISysDictTypeService
{
    /**
     * 条件に基づいてページ分割して辞書タイプをクエリする
     * 
     * @param dictType 辞書タイプ情報
     * @return 辞書タイプのコレクション情報
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * すべての辞書タイプをクエリする
     * 
     * @return 辞書タイプのコレクション情報
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * 辞書タイプに基づいて辞書データをクエリする
     * 
     * @param dictType 辞書タイプ
     * @return 辞書データのコレクション情報
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 辞書タイプIDに基づいて情報をクエリする
     * 
     * @param dictId 辞書タイプID
     * @return 辞書タイプ
     */
    public SysDictType selectDictTypeById(Long dictId);

    /**
     * 辞書タイプに基づいて情報をクエリする
     * 
     * @param dictType 辞書タイプ
     * @return 辞書タイプ
     */
    public SysDictType selectDictTypeByType(String dictType);

    /**
     * 辞書タイプを一括削除する
     * 
     * @param ids 削除するデータ
     */
    public void deleteDictTypeByIds(String ids);

    /**
     * 辞書キャッシュデータをロードする
     */
    public void loadingDictCache();

    /**
     * 辞書キャッシュデータをクリアする
     */
    public void clearDictCache();

    /**
     * 辞書キャッシュデータをリセットする
     */
    public void resetDictCache();

    /**
     * 新しい辞書タイプ情報を保存する
     * 
     * @param dictType 辞書タイプ情報
     * @return 結果
     */
    public int insertDictType(SysDictType dictType);

    /**
     * 辞書タイプ情報を更新する
     * 
     * @param dictType 辞書タイプ情報
     * @return 結果
     */
    public int updateDictType(SysDictType dictType);

    /**
     * 辞書タイプ名が一意かどうかを検証する
     * 
     * @param dictType 辞書タイプ
     * @return 結果
     */
    public boolean checkDictTypeUnique(SysDictType dictType);

    /**
     * 辞書タイプツリーをクエリする
     * 
     * @param dictType 辞書タイプ
     * @return すべての辞書タイプ
     */
    public List<Ztree> selectDictTree(SysDictType dictType);
}
