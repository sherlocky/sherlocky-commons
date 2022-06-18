package com.sherlocky.common.util;

import com.sherlocky.common.entity.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * 数据验证工具类
 *
 * @date 2022/6/18
 */
public class ValidateUtil {
    /**
     * 初始化验证器
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 将异常结果封装返回一个字符串
     *
     * @param <T>
     * @param <T>
     * @param data
     * @return 如果验证失败，则返回错误信息，验证成功返回null
     */
    public static <T> ValidateResult<?> validate(T data) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(data, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            List<String> errorMsgs = new ArrayList<>();
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgs.add(constraintViolation.getMessage());
            }
            return ValidateResult.fail(StringUtils.join(errorMsgs, "，"));
        }
        return ValidateResult.successDef();
    }
}