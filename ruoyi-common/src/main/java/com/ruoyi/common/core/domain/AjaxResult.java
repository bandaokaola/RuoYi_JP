package com.ruoyi.common.core.domain;

import java.util.HashMap;
import java.util.Objects;

import com.ruoyi.common.utils.StringUtils;

/**
 * 操作メッセージ通知
 *
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** ステータスコード */
    public static final String CODE_TAG = "code";

    /** メッセージ内容 */
    public static final String MSG_TAG = "msg";

    /** データオブジェクト */
    public static final String DATA_TAG = "data";

    /**
     * ステータスタイプ
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(0),
        /** 警告 */
        WARN(301),
        /** エラー */
        ERROR(500);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * 空のメッセージを表す新しい AjaxResult オブジェクトを初期化します。
     */
    public AjaxResult()
    {
    }

    /**
     * 新しい AjaxResult オブジェクトを初期化します。
     *
     * @param type ステータスタイプ
     * @param msg  メッセージ内容
     */
    public AjaxResult(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 新しい AjaxResult オブジェクトを初期化します。
     *
     * @param type ステータスタイプ
     * @param msg  メッセージ内容
     * @param data データオブジェクト
     */
    public AjaxResult(Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 成功メッセージを返します。
     *
     * @return 成功メッセージ
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 成功データを返します。
     *
     * @return 成功メッセージ
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 成功メッセージを返します。
     *
     * @param msg メッセージ内容
     * @return 成功メッセージ
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 成功メッセージを返します。
     *
     * @param msg  メッセージ内容
     * @param data データオブジェクト
     * @return 成功メッセージ
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    /**
     * 警告メッセージを返します。
     *
     * @param msg メッセージ内容
     * @return 警告メッセージ
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 警告メッセージを返します。
     *
     * @param msg  メッセージ内容
     * @param data データオブジェクト
     * @return 警告メッセージ
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(Type.WARN, msg, data);
    }

    /**
     * エラーメッセージを返します。
     *
     * @return エラーメッセージ
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作に失敗しました。");
    }

    /**
     * エラーメッセージを返します。
     *
     * @param msg メッセージ内容
     * @return エラーメッセージ
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * エラーメッセージを返します。
     *
     * @param msg  メッセージ内容
     * @param data データオブジェクト
     * @return エラーメッセージ
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(Type.ERROR, msg, data);
    }

    /**
     * 成功メッセージかどうかを判定します。
     *
     * @return 結果
     */
    public boolean isSuccess()
    {
        return Objects.equals(Type.SUCCESS.value, this.get(CODE_TAG));
    }

    /**
     * 警告メッセージかどうかを判定します。
     *
     * @return 結果
     */
    public boolean isWarn()
    {
        return Objects.equals(Type.WARN.value, this.get(CODE_TAG));
    }

    /**
     * エラーメッセージかどうかを判定します。
     *
     * @return 結果
     */
    public boolean isError()
    {
        return Objects.equals(Type.ERROR.value, this.get(CODE_TAG));
    }

    /**
     * チェーン式呼び出しを容易にするためのメソッド
     *
     * @param key   キー
     * @param value 値
     * @return データオブジェクト
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
