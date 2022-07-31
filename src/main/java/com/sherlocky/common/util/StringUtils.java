package com.sherlocky.common.util;

import com.sherlocky.common.constant.CharPool;
import com.sherlocky.common.constant.StrPool;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符串工具类
 * @date 2022/7/31
 * @since 0.8.0
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * 格式化文本, {} 表示占位符，类时slf4j效果<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params 参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (null == template) {
            return null;
        }
        if (ArrayUtils.isEmpty(params) || org.apache.commons.lang3.StringUtils.isBlank(template)) {
            return template.toString();
        }

        final int templateLength = template.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(templateLength + 50);

        // 记录已经处理到的位置
        int handledPosition = 0;
        // 占位符所在位置
        int delimIndex;
        for (int argIndex = 0; argIndex < params.length; argIndex++) {
            delimIndex = template.indexOf(StrPool.BRACE, handledPosition);
            // 剩余部分无占位符
            if (delimIndex == -1) {
                // 不带占位符的模板直接返回
                if (handledPosition == 0) {
                    return template;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(template, handledPosition, templateLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && template.charAt(delimIndex - 1) == CharPool.BACK_SLASH) {
                // 双转义符
                if (delimIndex > 1 && template.charAt(delimIndex - 2) == CharPool.BACK_SLASH) {
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(template, handledPosition, delimIndex - 1);
                    sbuf.append(str(params[argIndex]));
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(template, handledPosition, delimIndex - 1);
                    sbuf.append(CharPool.LEFT_BRACE);
                    handledPosition = delimIndex + 1;
                }
            } else {
                // 正常占位符
                sbuf.append(template, handledPosition, delimIndex);
                sbuf.append(str(params[argIndex]));
                handledPosition = delimIndex + 2;
            }
        }

        // 加入最后一个占位符后所有的字符
        sbuf.append(template, handledPosition, template.length());

        return sbuf.toString();
    }

    public static String str(Object obj) {
        return str(obj, StandardCharsets.UTF_8);
    }

    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrayUtils.isArray(obj)) {
            return ArrayUtils.toString(obj);
        }

        return obj.toString();
    }

}
