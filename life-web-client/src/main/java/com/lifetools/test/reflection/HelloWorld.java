package com.lifetools.test.reflection;

import java.lang.annotation.*;

/**
 * Created by Zheng.Ke
 * Date 2016/11/9.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HelloWorld {
    String value() default "defaultValue";
}
