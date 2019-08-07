/*
 * Copyright 2019 Sherlocky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sherlocky.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

import static com.sherlocky.common.constant.CommonConstants.ENCODING;

/**
 * http相关简单工具集
 *
 * @author: zhangcx
 * @date: 2019/8/1 15:03
 */
@Slf4j
public class HttpUtils {
    /**
     * 中文文件名编码转换（例如：下载文件文件名包含中文时, 部分浏览器会乱码）
     *
     * @param request
     * @param fileName
     * @return
     */
    public static String convertFileName(HttpServletRequest request, String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("文件名称参数为 null");
        }
        String agent = request.getHeader("USER-AGENT");
        if (StringUtils.isBlank(agent)) {
            return fileName;
        }
        try {
            // 微软系浏览器
            if (StringUtils.containsAny(fileName, "MSIE", "Trident", "Edge")) {
                return URLEncoder.encode(fileName, ENCODING).replace("+", "%20");
            }
            if (agent.contains("Safari")) {
                return new String(fileName.getBytes(ENCODING), "ISO8859-1");
            }
            if (agent.contains("Mozilla")) {
                // 此处使用Java8 原生的 Base64 类
                return "=?UTF-8?B?" + (new String(Base64.getEncoder().encodeToString(fileName.getBytes(ENCODING)))) + "?=";
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("$$$$$$ 转换文件名编码失败！", ex);
        } finally {
            return fileName;
        }
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个不为 unknow 的才为真实IP
            String[] ips = StringUtils.split(ip, ",");
            for (String i : ips) {
                // 多个ip之间以「英文逗号 + 空格」隔开
                i = StringUtils.trim(i);
                if (StringUtils.isNotBlank(i) && !"unknown".equalsIgnoreCase(i)) {
                    ip = i;
                    break;
                }
            }
        }
        if (unknown(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (unknown(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (unknown(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (unknown(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (unknown(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean unknown(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
