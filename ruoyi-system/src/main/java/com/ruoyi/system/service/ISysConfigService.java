package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/**
 * パラメーター設定 サービス層
 * 
 * @author ruoyi
 */
public interface ISysConfigService
{
    /**
     * パラメーター設定情報を取得
     * 
     * @param configId パラメーター設定ID
     * @return パラメーター設定情報
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * キーに基づいてパラメーター設定情報を取得
     * 
     * @param configKey パラメーターのキー名
     * @return パラメーターのキー値
     */
    public String selectConfigByKey(String configKey);

    /**
     * パラメーター設定リストを取得
     * 
     * @param config パラメーター設定情報
     * @return パラメーター設定のコレクション
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * パラメーター設定を新規追加
     * 
     * @param config パラメーター設定情報
     * @return 結果
     */
    public int insertConfig(SysConfig config);

    /**
     * パラメーター設定を編集
     * 
     * @param config パラメーター設定情報
     * @return 結果
     */
    public int updateConfig(SysConfig config);

    /**
     * パラメーター設定情報を複数削除
     * 
     * @param ids 削除するデータID
     */
    public void deleteConfigByIds(String ids);

    /**
     * パラメーターキャッシュデータを読み込む
     */
    public void loadingConfigCache();

    /**
     * パラメーターキャッシュデータをクリア
     */
    public void clearConfigCache();

    /**
     * パラメーターキャッシュデータをリセット
     */
    public void resetConfigCache();

    /**
     * パラメーターキーの一意性を検証
     * 
     * @param config パラメーター情報
     * @return 結果
     */
    public boolean checkConfigKeyUnique(SysConfig config);
}
