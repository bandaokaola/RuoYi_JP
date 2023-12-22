package com.ruoyi.common.enums;

/**
 * 用户会话
 *
 * @author ruoyi
 */
public enum OnlineStatus
{
    /** 用户状态 */
    on_line("オンライン"), off_line("オフライン");

    private final String info;

    private OnlineStatus(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
