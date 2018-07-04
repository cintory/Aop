package com.cintory.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：Cintory on 2018/7/2 17:28
 * 邮箱：Cintory@gmail.com
 */
@Retention(RUNTIME) @Target(METHOD) public @interface Permission {
    String[] value();
}
