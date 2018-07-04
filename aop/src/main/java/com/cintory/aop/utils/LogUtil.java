package com.cintory.aop.utils;

import android.util.Log;
import com.cintory.aop.BuildConfig;

/**
 * 作者：Cintory on 04/05/2018 14:44
 * 邮箱：Cintory@gmail.com
 */
public class LogUtil {
    public static boolean isDebug = BuildConfig.DEBUG;


    public static void e(String tag, String s) {
        if (isDebug) {
            Log.e(tag, s);
        }
    }


    public static void w(String tag, String s) {
        if (isDebug) {
            Log.w(tag, s);
        }
    }


    public static void d(String tag, String s) {
        if (isDebug) {
            Log.d(tag, s);
        }
    }


    public static void i(String tag, String s) {
        if (isDebug) {
            Log.i(tag, s);
        }
    }
}
