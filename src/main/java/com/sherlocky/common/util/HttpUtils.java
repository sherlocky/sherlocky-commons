package com.sherlocky.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * http相关简单工具集
 * @author: zhangcx
 * @date: 2019/8/1 15:03
 */
public class HttpUtils {

    /**
     * 转换中文文件名编码（下载名称包含中文的文件时, 部分浏览器会显示乱码）
     * @param request
     * @param fileName
     * @return
     */
    public static String convert(HttpServletRequest request, String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("输入参数是null");
        }
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && (-1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident") || -1 != agent.indexOf("Edge"))) {
                return URLEncoder.encode(fileName, "UTF8").replace("+", "%20");
            } else if (null != agent && -1 != agent.indexOf("Safari")) {
                return new String(fileName.getBytes("utf-8"), "ISO8859-1");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                return "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            } else {
                return fileName;
            }
        } catch (UnsupportedEncodingException ex) {
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
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
