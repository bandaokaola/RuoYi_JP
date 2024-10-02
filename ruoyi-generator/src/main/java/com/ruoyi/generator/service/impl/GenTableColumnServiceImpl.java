package com.ruoyi.generator.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.service.IGenTableColumnService;

/**
 * ビジネスフィールド サービス層実装
 * 
 * @author ruoyi
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService
{
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * ビジネスフィールドリストを取得
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return ビジネスフィールドの集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
    }

    /**
     * ビジネスフィールドを追加
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return 結果
     */
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    /**
     * ビジネスフィールドを更新
     * 
     * @param genTableColumn ビジネスフィールド情報
     * @return 結果
     */
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    /**
     * ビジネスフィールド情報を削除
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    @Override
    public int deleteGenTableColumnByIds(String ids)
    {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}