package com.cintory.aop;

import com.cintory.aop.utils.LogUtil;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者：Cintory on 2018/6/25 13:57
 * 邮箱：Cintory@gmail.com
 */
@Aspect public class LogMethodAspect {

    private static final String TAG = "LogMethodAspect";


    @Around("execution(!synthetic * *(..)) && onLogMethod()")
    public Object doLogMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod(joinPoint);
    }


    @Pointcut(
            "@within(com.cintory.aop.annotation.LogMethod) || @annotation(com.cintory.aop.annotation.LogMethod)")
    public void onLogMethod() {

    }


    public Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        LogUtil.d(TAG, joinPoint.getSignature().toShortString() + " Args: " +
                (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        LogUtil.d(TAG, joinPoint.getSignature().toShortString() + " Result: " +
                ("void".equalsIgnoreCase(type) ? "void" : result));
        return result;
    }
}
