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

import com.sherlocky.common.constant.CommonConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static com.sherlocky.common.constant.CommonConstants.DEFAULT_CHARSET_NAME;

/**
 * http相关简单工具集
 *
 * @author: zhangcx
 * @date: 2019/8/1 15:03
 */
public final class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
    }

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
            if (StringUtils.containsAny(agent, "MSIE", "Trident", "Edge")) {
                return URLEncoder.encode(fileName, DEFAULT_CHARSET_NAME).replace("+", "%20");
            }
            if (agent.contains("Safari")) {
                return new String(fileName.getBytes(DEFAULT_CHARSET_NAME), StandardCharsets.ISO_8859_1);
            }
            if (agent.contains("Mozilla")) {
                // 此处使用Java8 原生的 Base64 类
                return "=?UTF-8?B?" + (new String(Base64.getEncoder().encodeToString(fileName.getBytes(DEFAULT_CHARSET_NAME)))) + "?=";
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("$$$$$$ 转换文件名编码失败！", ex);
        }
        return fileName;
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

    /**
     * 下载文件
     * @param downloadUrl 下载地址
     * @param destFilePath 文件下载目标路径
     * @return
     * @since 0.0.6
     */
    public static boolean downloadFile(String downloadUrl, String destFilePath) {
        return HttpUtils.downloadFile(downloadUrl, null, destFilePath, false);
    }

    /**
     * 下载文件
     * @param downloadUrl 下载地址
     * @param httpHeaders 下载可能需要的 Header 信息
     * @param destFilePath 文件下载目标路径
     * @param forceDeleteOnExit
     * @return
     * @since 0.0.6
     */
    public static boolean downloadFile(String downloadUrl, Map<String, String> httpHeaders, String destFilePath, boolean forceDeleteOnExit) {
        Asserts.notNull(downloadUrl, "$$$ downloadUrl 不能为 null！");
        Asserts.notNull(destFilePath, "$$$ destFilePath 不能为 null！");
        /**
         * 校验下载链接，如果下载链接不包含协议头，默认加一个,否则无法下载
         */
        if (!downloadUrl.contains(CommonConstants.SCHEMA_SEPARATOR)) {
            downloadUrl = String.format("%s%s%s", CommonConstants.DEFAULT_SCHEMA, CommonConstants.SCHEMA_SEPARATOR, downloadUrl);
        }
        destFilePath = FilenameUtils.normalizeNoEndSeparator(destFilePath);
        File destFile = new File(destFilePath);
        // 强制创建目录结构
        try {
            FileUtils.forceMkdirParent(destFile);
        } catch (IOException e) {
            log.error("$$$ 目录结构创建失败！", e);
        }
        if (!destFile.getParentFile().exists() || !destFile.getParentFile().isDirectory()) {
            return false;
        }
        boolean isExists = destFile.exists();
        if (isExists && !forceDeleteOnExit) {
            if (log.isInfoEnabled()) {
                log.info("### 文件已存在，无需下载~ " + destFilePath);
            }
            return true;
        }
        // 如果已存在，先删除掉
        if (isExists && !FileUtils.deleteQuietly(destFile)) {
            // 如删除失败，退出
            log.error("$$$ 文件下载失败，已存在同名文件，且无法删除！" + destFilePath);
            return false;
        }
        /**
         * FileUtils.copyURLToFile 必须带协议头，且不支持http->https的协议自动重定向，弃用
         */
        copyURLToFileByHttpClient(downloadUrl, httpHeaders, destFile);
        // 下载后校验
        if (destFile.exists() && destFile.length() > 0) {
            if (log.isInfoEnabled()) {
                log.info("### 下载成功！" + destFilePath);
            }
            return true;
        }
        return false;
    }

    private static void copyURLToFileByHttpClient(String downloadUrl, Map<String, String> headers, File destFile) {
        if (log.isDebugEnabled()) {
            log.debug("@@@@@@ 下载开始：%s", downloadUrl);
        }
        CloseableHttpClient httpClient = null;
        InputStream in = null;
        FileOutputStream out = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(downloadUrl);
            if (MapUtils.isNotEmpty(headers)) {
                // 设置header
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    in = entity.getContent();
                    out = new FileOutputStream(destFile);
                    IOUtils.copy(in, out);
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    log.error("$$$ 下载文件流出错！", e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (final IOException ioe) {
                        // ignore
                    }
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (final IOException ioe) {
                        // ignore
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("### 状态码：{}", response.getCode());
            }
        } catch (IOException e) {
            log.error(String.format("$$$ 请求文件下载失败，下载地址 -> %s 路径：%s", downloadUrl, destFile.getAbsolutePath()), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (final IOException ioe) {
                // ignore
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (final IOException ioe) {
                // ignore
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("@@@@@@ 下载完成。");
        }
    }
}
