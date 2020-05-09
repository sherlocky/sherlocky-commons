package com.sherlocky.common.util;

import com.google.common.collect.Maps;
import com.sherlocky.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 简单封装 http client 工具类，base on httpclient5
 * <p>
 * 使用了 httpclient5-fluent api
 * </p>
 *
 * @author: zhangcx
 * @date: 2020/5/9 10:29
 * @since:
 */
@Slf4j
public final class HttpClientUtils {
    private HttpClientUtils() {
    }

    /**
     * 简单的 http get 请求
     *
     * @param uri
     * @param paramMap
     * @param timeoutMillis 超时时间（毫秒）
     * @return
     * @throws IOException
     */
    public static String get(String uri, Map<String, String> paramMap, int timeoutMillis) throws IOException {
        Asserts.notBlank(uri, "uri不能为空！");
        // 将参数转化为 k1=v1&k2=v2 形式
        String paramsStr = "";
        try {
            paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(paramMap2NameValuePairList(paramMap), CommonConstants.DEFAULT_CHARSET));
        } catch (ParseException e) {
            log.error("$$$ 解析get参数出错。。", e);
        }
        String parmeStrPrefix = uri.contains("?") ? "&" : "?";
        return Request.get(uri + parmeStrPrefix + paramsStr)
                .connectTimeout(Timeout.ofMilliseconds(timeoutMillis))
                .execute()
                .returnContent().asString();
    }

    /**
     * 简单的 http post 请求
     *
     * @param uri
     * @param paramMap
     * @param timeoutMillis 超时时间（毫秒）
     * @return
     * @throws IOException
     */
    public static String post(String uri, Map<String, String> paramMap, int timeoutMillis) throws IOException {
        Asserts.notBlank(uri, "uri不能为空！");
        return Request.post(uri)
                .bodyForm(paramMap2NameValuePairList(paramMap))
                .connectTimeout(Timeout.ofMilliseconds(timeoutMillis))
                .execute()
                .returnContent().asString();
    }

    /**
     * 将参数 map 转化为 httpclient 需要的 Name-Value 对形式
     *
     * @param parameterMap
     * @return
     */
    private static List<NameValuePair> paramMap2NameValuePairList(Map<String, String> parameterMap) {
        return Optional.ofNullable(parameterMap)
                .orElseGet(() -> {
                    return Maps.newHashMap();
                })
                .entrySet().stream()
                .map(entry -> {
                    return new BasicNameValuePair(entry.getKey(), entry.getValue());
                }).collect(Collectors.toList());
    }
}
