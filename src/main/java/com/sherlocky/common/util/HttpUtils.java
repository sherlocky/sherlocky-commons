package com.sherlocky.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author: zhangcx
 * @date: 2019/8/1 15:03
 */
public class HttpUtils {

    /**
     * 中文编码转换, 典型的情况是下载中文名的文件时, 浏览器不能正确地显示汉字
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

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
