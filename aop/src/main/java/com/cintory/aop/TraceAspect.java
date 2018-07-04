package com.cintory.aop;

import com.cintory.aop.annotation.Trace;
import com.cintory.aop.utils.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 作者：Cintory on 2018/6/25 17:39
 * 邮箱：Cintory@gmail.com
 */
@Aspect public class TraceAspect {
    private static final String POINTCUT_METHOD
            = "execution(@com.cintory.aop.annotation.Trace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR
            = "execution(@com.cintory.aop.annotation.Trace *.new(..))";

    private static final int ns = 1000 * 1000;


    @Pointcut(POINTCUT_METHOD) public void methodAnnotatedWithTrace() {}


    @Pointcut(POINTCUT_CONSTRUCTOR) public void constructorAnnotatedTrace() {}


    @Around("methodAnnotatedWithTrace() || constructorAnnotatedTrace()")
    public Object traceMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Trace trace = methodSignature.getMethod().getAnnotation(Trace.class);
        if (trace != null && !trace.enable()) {
            return joinPoint.proceed();
        }

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        if (null == className || className.isEmpty()) {
            className = "Anonymous class";
        }
        LogUtil.d(className, buildLogMessage(methodName, stopWatch.getElapsedTime()));

        return result;
    }


    public static String buildLogMessage(String methodName, long methodDuration) {

        if (methodDuration > 10 * ns) {
            return String.format("%s() take %d ms", methodName, methodDuration / ns);
        }
        else if (methodDuration > ns) {
            return String.format("%s() take %dms %dns", methodName, methodDuration / ns,
                    methodDuration % ns);
        }
        else {
            return String.format("%s() take %dns", methodName, methodDuration % ns);
        }
    }
}
