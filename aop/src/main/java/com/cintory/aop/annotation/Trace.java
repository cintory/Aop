package com.cintory.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 作者：Cintory on 2018/6/25 17:37
 * 邮箱：Cintory@gmail.com
 */
@Target({ METHOD, CONSTRUCTOR }) @Retention(RetentionPolicy.RUNTIME) public @interface Trace {
    boolean enable() default true;
}
