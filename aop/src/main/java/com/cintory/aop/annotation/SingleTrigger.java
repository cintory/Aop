package com.cintory.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 作者：Cintory on 2018/7/2 15:50
 * 邮箱：Cintory@gmail.com
 */
@Target({ METHOD }) @Retention(CLASS) public @interface SingleTrigger {}
