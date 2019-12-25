package com.sherlocky.common.util;

import com.sherlocky.common.constant.MessagePushConstants;

/**
 * 消息推送 工具
 * <p>
 *     base on <a href="http://sc.ftqq.com/">Server酱「ServerChan」</a>，使用时需要自行申请``SCKEY``。
 * </p>
 * @author: zhangcx
 * @date: 2019/12/25 17:38
 * @since:
 */
public final class MessagePushUtils {
    /*private final String SCKEY;
    private final String pushServerUrl;*/
    private MessagePushUtils() {}

    /**
     * 推送消息
     * @param title 标题【必填】
     * @return
     */
    public static boolean push(String sckey, String title) {
        return MessagePushUtils.push(sckey, title, null);
    }

    /**
     * 推送消息
     * @param title 标题【必填，最长256】
     * @param content 内容【选填，最长64K，支持markdown】
     * @return
     */
    public static boolean push(String sckey, String title, String content) {
        String pushServerUrl = String.format(MessagePushConstants.SERVER_CHAN_URL, sckey);
        //TODO 缺少 http client utils？

        return false;
    }
}
