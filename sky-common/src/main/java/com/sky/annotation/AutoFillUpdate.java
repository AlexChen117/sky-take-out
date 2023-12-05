package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP自动更新(修改时间,修改人)
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 09:34:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoFillUpdate {
}
