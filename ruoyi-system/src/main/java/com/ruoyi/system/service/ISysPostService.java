package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPost;

/**
 * 職位情報サービス層
 * 
 * @author ruoyi
 */
public interface ISysPostService
{
    /**
     * 職位情報のリストを検索します。
     * 
     * @param post 職位情報
     * @return 職位情報のリスト
     */
    public List<SysPost> selectPostList(SysPost post);

    /**
     * すべての職位を検索します。
     * 
     * @return 職位のリスト
     */
    public List<SysPost> selectPostAll();

    /**
     * ユーザーIDに基づいて職位を検索します。
     * 
     * @param userId ユーザーID
     * @return 職位のリスト
     */
    public List<SysPost> selectPostsByUserId(Long userId);

    /**
     * 職位IDに基づいて職位情報を検索します。
     * 
     * @param postId 職位ID
     * @return 職位の情報
     */
    public SysPost selectPostById(Long postId);

    /**
     * 職位情報を一括削除します。
     * 
     * @param ids 削除するデータのID
     * @return 結果
     */
    public int deletePostByIds(String ids);

    /**
     * 職位情報を新規保存します。
     * 
     * @param post 職位情報
     * @return 結果
     */
    public int insertPost(SysPost post);

    /**
     * 職位情報を変更保存します。
     * 
     * @param post 職位情報
     * @return 結果
     */
    public int updatePost(SysPost post);

    /**
     * 職位IDに基づいて職位の使用数を検索します。
     * 
     * @param postId 職位ID
     * @return 結果
     */
    public int countUserPostById(Long postId);

    /**
     * 職位名称を検証します。
     * 
     * @param post 職位情報
     * @return 結果
     */
    public boolean checkPostNameUnique(SysPost post);

    /**
     * 職位コードを検証します。
     * 
     * @param post 職位情報
     * @return 結果
     */
    public boolean checkPostCodeUnique(SysPost post);
}
