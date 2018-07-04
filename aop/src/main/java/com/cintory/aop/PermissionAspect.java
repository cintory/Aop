package com.cintory.aop;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import com.cintory.aop.annotation.Permission;
import com.cintory.aop.utils.PermissionUtil;
import java.util.Calendar;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 作者：Cintory on 2018/7/2 17:44
 * 邮箱：Cintory@gmail.com
 */
@Aspect public class PermissionAspect {
    @Around("execution(@com.cintory.aop.annotation.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission)
            throws Throwable {
        AppCompatActivity activity = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof AppCompatActivity) activity = (AppCompatActivity) arg;
        }
        if (activity != null) {
            final AppCompatActivity finalActivity = activity;
            new AlertDialog.Builder(activity).setTitle("提示")
                                             .setMessage("为了应用可以正常使用，请您点击确认申请权限")
                                             .setNegativeButton("取消", null)
                                             .setPositiveButton("允许",
                                                     new DialogInterface.OnClickListener() {
                                                         @Override
                                                         public void onClick(DialogInterface dialog, int which) {
                                                             PermissionUtil.requestPermissionResult(
                                                                     finalActivity, 1,
                                                                     permission.value(),
                                                                     new PermissionUtil.OnPermissionListener() {
                                                                         @Override
                                                                         public void onPermissionGranted() {
                                                                             try {
                                                                                 joinPoint.proceed();
                                                                             } catch (Throwable e) {
                                                                                 e.printStackTrace();
                                                                             }
                                                                         }


                                                                         @Override
                                                                         public void onPermissionDenied() {
                                                                             PermissionUtil.showTipsDialog(
                                                                                     finalActivity);
                                                                         }
                                                                     });
                                                         }
                                                     })
                                             .create()
                                             .show();
        }
    }
}
