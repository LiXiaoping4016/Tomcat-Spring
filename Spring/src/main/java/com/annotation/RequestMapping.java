package com.annotation;

import java.lang.annotation.*;

/**
 * Controller注解
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
