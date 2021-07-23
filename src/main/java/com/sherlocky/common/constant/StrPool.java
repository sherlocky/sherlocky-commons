package com.sherlocky.common.constant;

/**
 * 常用字符串
 */
public interface StrPool {

    /**
     * 默认编码
     */
    String UTF8 = "UTF-8";
    /**
     * 其他编码
     */
    String GBK = "GBK";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";

    /**
     * RESTful JSON 资源 contentType
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 常用类全限定名
     */
    String STRING_TYPE_NAME = "java.lang.String";
    String LONG_TYPE_NAME = "java.lang.Long";
    String INTEGER_TYPE_NAME = "java.lang.Integer";
    String SHORT_TYPE_NAME = "java.lang.Short";
    String DOUBLE_TYPE_NAME = "java.lang.Double";
    String FLOAT_TYPE_NAME = "java.lang.Float";
    String BOOLEAN_TYPE_NAME = "java.lang.Boolean";
    String SET_TYPE_NAME = "java.lang.Set";
    String LIST_TYPE_NAME = "java.lang.List";
    String COLLECTION_TYPE_NAME = "java.lang.Collection";
    String DATE_TYPE_NAME = "java.util.Date";
    String LOCAL_DATE_TIME_TYPE_NAME = "java.time.LocalDateTime";
    String LOCAL_DATE_TYPE_NAME = "java.time.LocalDate";
    String LOCAL_TIME_TYPE_NAME = "java.time.LocalTime";

    /**
     * 占位符
     */
    String DOLLAR_LEFT_BRACE = "${";
    String HASH_LEFT_BRACE = "#{";
    String RIGHT_BRACE = "}";

    /**
     * 常见后缀
     */
    String DOT = ".";
    String DOTDOT = "..";
    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";

}

