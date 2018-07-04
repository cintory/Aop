package com.cintory.aop;

import android.view.View;
import com.cintory.aop.utils.LogUtil;
import java.util.Calendar;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 作者：Cintory on 2018/7/2 15:53
 * 邮箱：Cintory@gmail.com
 */
@Aspect public class SingleTriggerAspect {

    private static final String TAG = "SingleTriggerAspect";

    static int TIME_TAG = R.id.click_time;
    public static final int MIN_CLICK_DELAY_TIME = 600;


    @Pointcut("execution(@com.cintory.aop.annotation.SingleTrigger * *(..))")
    public void methodAnnotated() {

    }


    @Around("methodAnnotated()") public void aroundJoinPoint(ProceedingJoinPoint joinPoint)
            throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) view = (View) arg;
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            LogUtil.d(TAG, "lastClickTime:" + lastClickTime);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentTime);
                LogUtil.d(TAG, "currentTime" + currentTime);
                joinPoint.proceed();
            }
        }
    }
}
