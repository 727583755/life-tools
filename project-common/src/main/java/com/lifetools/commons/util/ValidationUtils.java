package com.lifetools.commons.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * hibernate validate 验证工具类
 * Created by Zheng.Ke
 * Date 2016/8/26.
 */
public final class ValidationUtils {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 验证对象
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ValidationResult validateEntity(T obj){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if(set.size() > 0){
            result.setHasErrors(true);
            StringBuffer sb = new StringBuffer();
            for(ConstraintViolation<T> cv : set){
                sb.append(cv.getPropertyPath().toString()).append(":").append(cv.getMessage()).append("; ");
            }
            result.setErrorDesc(sb.toString());
        }
        return result;
    }

    private ValidationUtils() {}

}
