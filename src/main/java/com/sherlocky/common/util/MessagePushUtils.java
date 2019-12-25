package com.sherlocky.common.util;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.sherlocky.common.constant.MessagePushConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息推送 工具
 * <p>
 *     base on <a href="http://sc.ftqq.com/">Server酱「ServerChan」</a>，使用时需要自行申请``SCKEY``。
 * </p>
 * @author: zhangcx
 * @date: 2019/12/25 17:38
 * @since:
 */
@Slf4j
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
        Asserts.notNull(sckey, "消息服务sckey 不能为空！");
        Asserts.notNull(title, "消息标题 不能为空！");
        Map<String, Object> msgData = new HashMap<>();
        msgData.put(MessagePushConstants.SERVER_CHAN_PARAM_TITLE, title);
        if (StringUtils.isNotEmpty(content)) {
            msgData.put(MessagePushConstants.SERVER_CHAN_PARAM_CONTENT, content);
        }
        String result = null;
        try {
            result = HttpUtil.get(String.format(MessagePushConstants.SERVER_CHAN_URL, sckey), msgData, MessagePushConstants.PUSH_TIME_OUT);
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
        } catch (Exception e) {
            log.error("$$$ 请求推送服务失败~", e);
        }
        MessagePushResponse mpr = null;
        try {
            mpr = JSON.parseObject(result, MessagePushResponse.class);
        } catch (Exception e) {
            log.error("$$$ 解析推送服务返回值失败~", e);
        }
        if (mpr == null) {
            return false;
        }
        return mpr.getErrno() == MessagePushConstants.PUSH_SUCCESS_NO;
    }
}

@Data
@NoArgsConstructor
class MessagePushResponse {
    // 0
    private int errno;
    // "success"
    private String errmsg;
    // "done"
    private String dataset;
}
