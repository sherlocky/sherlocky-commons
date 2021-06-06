package com.sherlocky.common.constant;

/**
 * @author: zhangcx
 * @date: 2019/12/25 17:31
 * @since:
 */
public class MessagePushConstants {
    /**
     * Server酱 发送消息接口地址，微信通道即将关闭，切换成 Turbo 版本
     */
    public static final String SERVER_CHAN_URL = "https://sctapi.ftqq.com/%s.send";
    /**
     * Server酱 发送消息 标题
     */
    public static final String SERVER_CHAN_PARAM_TITLE = "title";
    /**
     * Server酱 发送消息 内容
     */
    public static final String SERVER_CHAN_PARAM_CONTENT = "desp";
    /**
     * 推送成功 返回代码no
     */
    public static final int PUSH_SUCCESS_NO = 0;
    /**
     * 推送超时时间: 3s
     */
    public static final int PUSH_TIME_OUT = 3000;
}
