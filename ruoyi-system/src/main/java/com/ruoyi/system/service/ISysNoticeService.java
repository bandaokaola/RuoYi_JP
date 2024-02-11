package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNotice;

/**
 * お知らせサービス層
 * 
 * @author ruoyi
 */
public interface ISysNoticeService
{
    /**
     * お知らせ情報を検索
     * 
     * @param noticeId お知らせID
     * @return お知らせ情報
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * お知らせリストを検索
     * 
     * @param notice お知らせ情報
     * @return お知らせのリスト
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 新規お知らせ追加
     * 
     * @param notice お知らせ情報
     * @return 処理結果
     */
    public int insertNotice(SysNotice notice);

    /**
     * お知らせ編集
     * 
     * @param notice お知らせ情報
     * @return 処理結果
     */
    public int updateNotice(SysNotice notice);

    /**
     * お知らせ情報削除
     * 
     * @param ids 削除対象のデータID
     * @return 処理結果
     */
    public int deleteNoticeByIds(String ids);
}
