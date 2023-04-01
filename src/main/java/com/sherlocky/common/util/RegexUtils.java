package com.sherlocky.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类
 * @date 2022/1/21
 * @since 0.7.0
 */
public class RegexUtils {

    /**
     * 获得匹配的字符串
     * @param regex 匹配的正则
     * @param content 被匹配的内容
     * @param groupIndex 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     * @since 0.9.0
     */
    public static String get(String regex, CharSequence content, int groupIndex) {
        if (null == content || null == regex) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return get(pattern, content, groupIndex);
    }

    /**
     * 获得匹配的字符串，对应分组0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     *
     * @param pattern 编译后的正则模式
     * @param content 被匹配的内容
     * @param groupIndex 匹配正则的分组序号，0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     * @return 匹配后得到的字符串，未匹配返回null
     * @since 0.9.0
     */
    public static String get(Pattern pattern, CharSequence content, int groupIndex) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        final Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param regex 正则
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     * @since 0.7.0
     */
    public static boolean isMatch(String regex, CharSequence content) {
        if (content == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        if (StringUtils.isEmpty(regex)) {
            // 正则不存在则为全匹配
            return true;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return isMatch(pattern, content);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     * @since 0.7.0
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }
}
