package com.cintory.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.aspectj.lang.annotation.Aspect;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 作者：Cintory on 2018/6/25 13:36
 * 邮箱：Cintory@gmail.com
 */
@Target({ METHOD }) @Retention(CLASS) public @interface LogMethod {}
