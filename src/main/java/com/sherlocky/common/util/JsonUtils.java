package com.sherlocky.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sherlocky.common.exception.JsonException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON相关工具类，base on Jackson
 */
public final class JsonUtils {
    private static final String MSG_PARAM_NOT_NULL = "参数不能为null！";
    private static final String MSG_PARAM_NOT_EMPTY = "参数不能为空！";
    private static final String MSG_TYPE_REFERENCE_PARAM_NOT_NULL = "typeReference参数不能为空";
    private static final String MSG_ERROR_CAST_EXCEPTION_MESSAGE_TEMPLATE = "转换json异常--原始json串:[%s],class:[%s]";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 缓存 ObjectMapper
     */
    private static final Map<String, ObjectMapper> OBJECT_MAPPER_CACHE = new ConcurrentHashMap<>();

    static {
        configure();
    }

    private JsonUtils() {}

    /**
     * 将对象转换成json字符串
     *
     * @param object 必填待转换的json对象
     * @return 转换之后的字符串
     */
    public static String toJson(Object object) {
        return toJSONString(object);
    }

    /**
     * 将json字符串转换为java对象
     *
     * @param json      json字符串
     * @param valueType 要转换成的java对象的类
     * @param <T>
     * @return 转换之后的对象
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        return parseObject(json, valueType);
    }

    /**
     * 将json字符串转换为ObjectNode,如果json转换异常将抛出JsonException
     *
     * @param json json字符串,不能为空，为空则抛出IllegalArgumentException
     * @return 转换之后得到的JsonNode对象
     */
    public static JsonNode parseObject(String json) {
        if (json == null) {
            return null;
        }
        try {
            Asserts.notBlank(json, MSG_PARAM_NOT_EMPTY);
            return OBJECT_MAPPER.readValue(json, JsonNode.class);
        } catch (IOException e) {
            throw new JsonException(String.format(MSG_ERROR_CAST_EXCEPTION_MESSAGE_TEMPLATE, json, JsonNode.class), e);
        }
    }

    /**
     * 将json字符串转换为java对象,如果json转换异常将抛出JsonException
     *
     * @param json   json字符串,不能为空
     * @param tClass 需要转换的java对象,不能为空
     * @param <T>    泛型
     * @return 转换之后得到的java对象
     * @see JsonException
     */
    public static <T> T parseObject(String json, Class<T> tClass) {
        if (json == null) {
            return null;
        }
        try {
            Asserts.notBlank(json, MSG_PARAM_NOT_EMPTY);
            Asserts.notNull(tClass, "tClass参数不能为空");
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            throw new JsonException(String.format(MSG_ERROR_CAST_EXCEPTION_MESSAGE_TEMPLATE, json, tClass), e);
        }
    }

    /**
     * 将json字符串转换为java对象,支持对Date类型格式化,如果json转换异常将抛出JsonException
     *
     * @param json   json字符串,不能为空
     * @param tClass 需要转换的java对象,不能为空
     * @param <T>    泛型
     * @return 转换之后得到的java对象
     * @see JsonException
     */
    public static <T> T parseObjectWithPattern(String json, Class<T> tClass, String pattern) {
        if (json == null) {
            return null;
        }
        try {
            Asserts.notBlank(json, MSG_PARAM_NOT_EMPTY);
            Asserts.notNull(tClass, MSG_TYPE_REFERENCE_PARAM_NOT_NULL);
            if (pattern == null) {
                return parseObject(json, tClass);
            }
            return getObjectMapperByPattern(pattern).readValue(json, tClass);
        } catch (Exception e) {
            throw new JsonException(String.format(MSG_ERROR_CAST_EXCEPTION_MESSAGE_TEMPLATE, json, tClass), e);
        }
    }


    /**
     * 将json字符串转换为java对象,如果json转换异常将抛出JsonException
     *
     * @param json          json字符串,不能为空
     * @param typeReference 需要转换的java类型，支持泛型,不能为空
     * @param <T>           泛型
     * @return 转换之后得到的java对象
     * @see JsonException
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        if (json == null) {
            return null;
        }
        try {
            Asserts.notBlank(json, MSG_PARAM_NOT_EMPTY);
            Asserts.notNull(typeReference, MSG_TYPE_REFERENCE_PARAM_NOT_NULL);
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new JsonException(String.format(MSG_ERROR_CAST_EXCEPTION_MESSAGE_TEMPLATE, json, typeReference.getType()), e);
        }
    }

    /**
     * 将对象转换为JSON字符串,如果json转换异常将抛出JsonException
     *
     * @param object 需要转换为json的对象,不能为空
     * @return json字符串
     * @see JsonException
     */
    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException("json转换异常", e);
        }
    }

    /**
     * 将对象转换为JSON字符串，可指定支持格式化/美化/优雅的形式输出,如果json转换异常将抛出JsonException
     *
     * @param object 需要转换为json的对象,不能为空
     * @param isPrettyFormat 是否格式化/美化/优雅的输出
     * @return json字符串
     * @see JsonException
     */
    public static String toJSONString(Object object, boolean isPrettyFormat) {
        if (object == null) {
            return null;
        }
        if (!isPrettyFormat) {
            return toJSONString(object);
        }
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException("json转换异常", e);
        }
    }

    /**
     * 将对象转换为JSON字符串（仅支持java.util.Date类型的格式化输出）
     * 如果json转换异常将抛出JsonException
     *
     * @param object  需要转换为json的对象,不能为空
     * @param pattern 日期格式化字符串，例如：yyyy-MM-dd HH:mm:ss 不能为空
     * @return json字符串
     * @see JsonException
     */
    public static String toJSONWithPattern(Object object, String pattern) {
        if (object == null) {
            return null;
        }
        Asserts.notNull(object, MSG_PARAM_NOT_NULL);
        Asserts.notBlank(pattern, "日期格式不能为空");
        try {
            return getObjectMapperByPattern(pattern).writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException("json转换异常", e);
        }
    }

    /**
     * 获取支持日期格式化的ObjectMapper
     *
     * @param pattern 日期模式字符串
     * @return json字符串
     */
    private static ObjectMapper getObjectMapperByPattern(String pattern) {
        ObjectMapper objectMapper = OBJECT_MAPPER_CACHE.get(pattern);
        if (objectMapper != null) {
            return objectMapper;
        }
        objectMapper = OBJECT_MAPPER.copy();
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        OBJECT_MAPPER_CACHE.put(pattern, objectMapper);
        return objectMapper;
    }

    /**
     * 允许子类对配置自定义,子类可重写此方法进行配置
     */
    protected static void configure() {
        // 解决实体未包含字段反序列化时抛出异常
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 对于空的对象转json的时候不抛出错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //使用null表示集合类型字段是时不抛异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //识别单引号
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //识别特殊字符
        OBJECT_MAPPER.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        // 允许属性名称没有引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // 允许单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // 允许稀疏数组
        OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_MISSING_VALUES.mappedFeature(), true);

        // 使用枚举的name()值作为序列化方式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        OBJECT_MAPPER.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

        //允许将JSON空字符串强制转换为null对象值
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // null值不参与序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //识别Java8时间
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        //识别Guava包的类
        OBJECT_MAPPER.registerModule(new GuavaModule());
    }
}
