package com.sherlocky.common.util;

import com.sherlocky.common.constant.MessagePushConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息推送 工具
 * <p>
 * base on <a href="http://sc.ftqq.com/">Server酱「ServerChan」</a>，使用时需要自行申请``SCKEY``。
 * </p>
 *
 * @author: zhangcx
 * @date: 2019/12/25 17:38
 * @since:
 */
public final class MessagePushUtils {
    private static final Logger log = LoggerFactory.getLogger(MessagePushUtils.class);

    /*private final String SCKEY;
    private final String pushServerUrl;*/
    private MessagePushUtils() {
    }

    /**
     * 推送消息
     *
     * @param title 标题【必填】
     * @return
     */
    public static boolean push(String sckey, String title) {
        return MessagePushUtils.push(sckey, title, null);
    }

    /**
     * 推送消息
     *
     * @param title   标题【必填，最长256】
     * @param content 内容【选填，最长64K，支持markdown】
     * @return
     */
    public static boolean push(String sckey, String title, String content) {
        Asserts.notNull(sckey, "消息服务sckey 不能为空！");
        Asserts.notNull(title, "消息标题 不能为空！");
        Map<String, String> msgData = new HashMap<>();
        msgData.put(MessagePushConstants.SERVER_CHAN_PARAM_TITLE, title);
        if (StringUtils.isNotEmpty(content)) {
            msgData.put(MessagePushConstants.SERVER_CHAN_PARAM_CONTENT, content);
        }
        String result = null;
        try {
            // result = HttpUtil.get(String.format(MessagePushConstants.SERVER_CHAN_URL, sckey), msgData, MessagePushConstants.PUSH_TIME_OUT);
            result = HttpClientUtils.get(String.format(MessagePushConstants.SERVER_CHAN_URL, sckey), msgData, MessagePushConstants.PUSH_TIME_OUT);
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
        } catch (Exception e) {
            log.error("$$$ 请求推送服务失败~", e);
        }
        MessagePushResponse mpr = null;
        try {
            mpr = JsonUtils.parseObject(result, MessagePushResponse.class);
        } catch (Exception e) {
            log.error("$$$ 解析推送服务返回值失败~", e);
        }
        if (mpr == null) {
            return false;
        }
        return mpr.getErrno() == MessagePushConstants.PUSH_SUCCESS_NO;
    }
}

class MessagePushResponse {
    /**
     * 0
     */
    private int errno;
    /**
     * "success"
     */
    private String errmsg;
    /**
     * "done"
     */
    private String dataset;

    public int getErrno() {
        return this.errno;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public String getDataset() {
        return this.dataset;
    }

    public void setErrno(final int errno) {
        this.errno = errno;
    }

    public void setErrmsg(final String errmsg) {
        this.errmsg = errmsg;
    }

    public void setDataset(final String dataset) {
        this.dataset = dataset;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessagePushResponse)) {
            return false;
        }
        final MessagePushResponse other = (MessagePushResponse) o;
        if (this.getErrno() != other.getErrno()) {
            return false;
        }
        if (this.getErrmsg() == null ? other.getErrmsg() != null : !this.getErrmsg().equals(other.getErrmsg())) {
            return false;
        }
        if (this.getDataset() == null ? other.getDataset() != null : !this.getDataset().equals(other.getDataset())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getErrno();
        final Object $errmsg = this.getErrmsg();
        result = result * PRIME + ($errmsg == null ? 43 : $errmsg.hashCode());
        final Object $dataset = this.getDataset();
        result = result * PRIME + ($dataset == null ? 43 : $dataset.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MessagePushResponse(errno=" + this.getErrno() + ", errmsg=" + this.getErrmsg() + ", dataset=" + this.getDataset() + ")";
    }

    public MessagePushResponse() {
    }
}
