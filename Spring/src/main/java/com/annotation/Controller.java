package com.annotation;

import java.lang.annotation.*;

/**
 * Controller注解
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
}
