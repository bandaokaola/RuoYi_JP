package com.ruoyi.system.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.service.ISysConfigService;

/**
 * パラメーター設定 サービス層実装
 * 
 * @author ruoyi
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService
{
    @Autowired
    private SysConfigMapper configMapper;

    /**
     * プロジェクト起動時に、パラメーターをキャッシュに初期化します。
     */
    @PostConstruct
    public void init()
    {
        loadingConfigCache();
    }

    /**
     * パラメーター設定情報を取得
     * 
     * @param configId パラメーター設定ID
     * @return パラメーター設定情報
     */
    @Override
    public SysConfig selectConfigById(Long configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * キーに基づいてパラメーター設定情報を取得
     * 
     * @param configKey パラメーターのキー名
     * @return パラメーターのキー値
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(CacheUtils.get(getCacheName(), getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            CacheUtils.put(getCacheName(), getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * パラメーター設定リストを取得
     * 
     * @param config パラメーター設定情報
     * @return パラメーター設定のコレクション
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * パラメーター設定を新規追加
     * 
     * @param config パラメーター設定情報
     * @return 結果
     */
    @Override
    public int insertConfig(SysConfig config)
    {
        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * パラメーター設定を編集
     * 
     * @param config パラメーター設定情報
     * @return 結果
     */
    @Override
    public int updateConfig(SysConfig config)
    {
        SysConfig temp = configMapper.selectConfigById(config.getConfigId());
        if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey()))
        {
            CacheUtils.remove(getCacheName(), getCacheKey(temp.getConfigKey()));
        }

        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * パラメーター設定情報を複数削除
     * 
     * @param ids 削除するデータID
     */
    @Override
    public void deleteConfigByIds(String ids)
    {
        Long[] configIds = Convert.toLongArray(ids);
        for (Long configId : configIds)
        {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
            	throw new ServiceException(String.format("内部パラメーター【%1$s】は削除できません ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            CacheUtils.remove(getCacheName(), getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * パラメーターキャッシュデータを読み込む
     */
    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * パラメーターキャッシュデータをクリア
     */
    @Override
    public void clearConfigCache()
    {
        CacheUtils.removeAll(getCacheName());
    }

    /**
     * パラメーターキャッシュデータをリセット
     */
    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * パラメーターキーの一意性を検証
     * 
     * @param config パラメーター情報
     * @return 結果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * キャッシュ名を取得します。
     * 
     * @return キャッシュ名
     */
    private String getCacheName()
    {
        return Constants.SYS_CONFIG_CACHE;
    }

    /**
     * キャッシュキーを設定します。
     * 
     * @param configKey パラメーターのキー
     * @return キャッシュキー
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
