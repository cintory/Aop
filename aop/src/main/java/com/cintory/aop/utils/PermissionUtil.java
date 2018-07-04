package com.cintory.aop.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Cintory on 2018/7/3 14:37
 * 邮箱：Cintory@gmail.com
 */
public class PermissionUtil {

    private static int mRequestCode = -1;

    private static OnPermissionListener sPermissionListener;


    public static void requestPermissionResult(Activity activity, int requestCode, String[] permission, OnPermissionListener callback) {
        requestPermissions(activity, requestCode, permission, callback);
    }


    public static void requestPermissionsResult(android.app.Fragment fragment, int requestCode, String[] permission, OnPermissionListener callback) {
        requestPermissions(fragment, requestCode, permission, callback);
    }


    public static void requestPermissionsResult(android.support.v4.app.Fragment fragment, int requestCode, String[] permission, OnPermissionListener callback) {
        requestPermissions(fragment, requestCode, permission, callback);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode, String[] permissions, OnPermissionListener callback) {

        checkCallingObjectSuitability(object);
        sPermissionListener = callback;

        if (checkPermissions(getContext(object), permissions)) {
            if (sPermissionListener != null) sPermissionListener.onPermissionGranted();
        }
        else {
            List<String> deniedPermissions = getDeniedPermissions(getContext(object), permissions);
            if (deniedPermissions.size() > 0) {
                mRequestCode = requestCode;
                if (object instanceof Activity) {
                    ((Activity) object).requestPermissions(
                            deniedPermissions.toArray(new String[deniedPermissions.size()]),
                            requestCode);
                }
                else if (object instanceof android.app.Fragment) {
                    ((android.app.Fragment) object).requestPermissions(
                            deniedPermissions.toArray(new String[deniedPermissions.size()]),
                            requestCode);
                }
                else if (object instanceof android.support.v4.app.Fragment) {
                    ((android.support.v4.app.Fragment) object).requestPermissions(
                            deniedPermissions.toArray(new String[deniedPermissions.size()]),
                            requestCode);
                }
                else {
                    mRequestCode = -1;
                }
            }
        }
    }


    private static Context getContext(Object object) {
        Context context;
        if (object instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        }
        else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }
        else {
            context = (Activity) object;
        }
        return context;
    }


    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (sPermissionListener != null) sPermissionListener.onPermissionGranted();
            }
            else {
                if (sPermissionListener != null) sPermissionListener.onPermissionDenied();
            }
        }
    }


    public static void showTipsDialog(final Context context) {
        new AlertDialog.Builder(context).setTitle("提示信息")
                                        .setMessage(
                                                "当前应用缺少必要权限，该功能暂时无法使用。如需要，请单击【确定】按钮前往设置中心进行权限授权。")
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startAppSettings(context);
                                                    }
                                                })
                                        .show();
    }


    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }


    private static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }


    private static void checkCallingObjectSuitability(Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof android.app.Activity;
        boolean isSupportFragment = object instanceof android.support.v4.app.Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;

        if (!(isActivity || isSupportFragment || isAppFragment)) {
            throw new IllegalArgumentException("Caller must be an Activity or a Fragment");
        }
    }


    private static boolean checkPermissions(Context context, String... permissions) {
        if (isOverMarshmallow()) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }


    private static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public interface OnPermissionListener {

        void onPermissionGranted();

        void onPermissionDenied();
    }
}
