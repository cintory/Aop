package com.cintory.aop;

import android.support.v4.util.Preconditions;
import com.cintory.aop.annotation.HookMethod;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者：Cintory on 2018/6/29 15:31
 * 邮箱：Cintory@gmail.com
 */
@Aspect public class HookMethodAspect {

    private static final String POINTCUT_METHOD
            = "execution(@com.cintory.aop.annotation.HookMethod * *(..))";

    private static final String POINTCUT_CONSTRUCTOR
            = "execution(@com.cintory.aop.annotation.HookMethod *.new(..))";


    @Pointcut(POINTCUT_METHOD) public void methodAnnotatedWithHookMethod() {}


    @Pointcut(POINTCUT_CONSTRUCTOR) public void constructorAnnotatedHookMethod() {}


    @Around("methodAnnotatedWithHookMethod() || constructorAnnotatedHookMethod()")
    public void hookMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HookMethod hookMethod = method.getAnnotation(HookMethod.class);

        if (hookMethod == null) return;

        String beforeMethod = hookMethod.beforeMethod();
        String afterMethod = hookMethod.afterMethod();

        if (!beforeMethod.isEmpty()) {
            try {
                Method mBefore = joinPoint.getTarget().getClass().getDeclaredMethod(beforeMethod);
                mBefore.setAccessible(true);
                mBefore.invoke(joinPoint.getTarget());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        joinPoint.proceed();

        if (!afterMethod.isEmpty()) {
            try {
                Method mAfter = joinPoint.getTarget().getClass().getDeclaredMethod(afterMethod);
                mAfter.setAccessible(true);
                mAfter.invoke(joinPoint.getTarget());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}