package com.annotation;

import java.lang.annotation.*;

/**
 * Controller注解
 *
 * @Author lixp23692
 * @Date 2020.04.01
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
