package com.cintory.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 作者：Cintory on 2018/6/29 15:19
 * 邮箱：Cintory@gmail.com
 */

@Target({ METHOD, CONSTRUCTOR }) public @interface HookMethod {

    String beforeMethod() default "";

    String afterMethod() default "";
}
