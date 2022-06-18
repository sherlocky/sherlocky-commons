package com.sherlocky.common.entity;

import com.sherlocky.common.util.JsonUtils;

import java.io.Serializable;

import static com.sherlocky.common.constant.CommonConstants.*;

public class ValidateResult<T> implements Serializable {

    private static final long serialVersionUID = 4271155729693579231L;
    /**
     * 返回码
     * <p>
     * 1:成功  默认成功<br/>
     * 其它:错误码<br/>
     * </p>
     */
    private String code = SUCCESS_CODE;
    /**
     * 错误信息
     */
    private String msg = "success";
    /**
     * 成功结果
     */
    private T data;

    public ValidateResult() {
    }

    public ValidateResult(T data) {
        this.data = data;
    }


    public ValidateResult(String code, String message) {
        this.code = code;
        this.msg = message;
    }


    public ValidateResult(Integer code, String message) {
        this(String.valueOf(code), message);
    }

    public ValidateResult(String code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }

    /**
     * 请求成功方法 ，data返回值，msg提示信息
     *
     * @param data 结果
     * @param msg  消息
     * @return 调用结果
     */
    public static <E> ValidateResult<E> success(E data, String msg) {
        return new ValidateResult<>(SUCCESS_CODE, msg, data);
    }

    /**
     * 请求成功方法 ，data返回值
     *
     * @param data 结果
     * @return 调用结果
     */
    public static <E> ValidateResult<E> success(E data) {
        return new ValidateResult<>(SUCCESS_CODE, null, data);
    }

    /**
     * 请求失败消息，根据异常类型，获取不同的提供消息
     *
     * @param throwable 异常
     * @return RPC调用结果
     */
    public static <E> ValidateResult<E> fail(Throwable throwable) {
        return fail(ERROR_CODE, throwable != null ? throwable.getMessage() : DEFAULT_ERROR_MESSAGE);
    }

    /**
     * 请求失败消息
     *
     * @param msg
     * @return
     */
    public static <E> ValidateResult<E> fail(String code, String msg) {
        return new ValidateResult<>(code,
                (msg == null || msg.isEmpty()) ? DEFAULT_ERROR_MESSAGE : msg);
    }

    public static <E> ValidateResult<E> fail(String msg) {
        return fail(ERROR_CODE, msg);
    }

    public static <E> ValidateResult<E> fail(String msg, Object... args) {
        String message = (msg == null || msg.isEmpty()) ? DEFAULT_ERROR_MESSAGE : msg;
        return new ValidateResult<>(ERROR_CODE,
                String.format(message, args));
    }

    public static <E> ValidateResult<E> result(String code, E data, String msg) {
        return new ValidateResult<>(code, msg, data);
    }

    public static <E> ValidateResult<E> successDef(E data) {
        return new ValidateResult<>(SUCCESS_CODE, null, data);
    }

    public static <E> ValidateResult<E> successDef() {
        return new ValidateResult<>(SUCCESS_CODE, null, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}