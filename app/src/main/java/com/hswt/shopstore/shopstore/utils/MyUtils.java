package com.hswt.shopstore.shopstore.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.widget.EditText;

import com.hswt.shopstore.shopstore.app.AppManager;
import com.hswt.shopstore.shopstore.app.MyApplication;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class MyUtils {


    //移动网络是否加载图片
    public static boolean isMobileNetwork = true;

    //是否推送
    public static boolean isCloudChannel = true;

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packageInfo;
    }

    /**
     * 获取应用的版本号
     *
     * @param context 上下文
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * MD5加密
     *
     * @param key 加密的key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * @param bytes 字节数据
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * getSerialNumber
     *
     * @return result is same to getSerialNumber1()
     */

    public static String getSerialNumber() {

        String serial = null;

        try {

            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class);

            serial = (String) get.invoke(c, "ro.serialno");

        } catch (Exception e) {

            e.printStackTrace();

        }
        return serial;
    }


    public static void JudgePermissions() {

        //6.0权限申请
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MyApplication.getmContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MyApplication.getmContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            permissionList.add(Manifest.permission.CAMERA);
        }
/*
        if (ContextCompat.checkSelfPermission(MyApplication.getmContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //申请RECORD_AUDIO权限
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
*/

        String[] permissions = new String[permissionList.size()];
        permissionList.toArray(permissions);

        if (permissions.length > 0) {
            ActivityCompat.requestPermissions(AppManager.getAppManager().currentActivity(), permissions, 1);
        }

        if (!isEnableV26(MyApplication.getmContext())) {
            MyToast.showToast(MyApplication.getmContext(), "需要开启通知权限");
            toSetting();
        }

    }

    public static boolean isEnableV26(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(MyApplication.getmContext());
        boolean isOpened = manager.areNotificationsEnabled();
        return isOpened;
    }

    private static void toSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", AppManager.getAppManager().currentActivity().getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", AppManager.getAppManager().currentActivity().getPackageName());
        }
        AppManager.getAppManager().currentActivity().startActivity(localIntent);
    }

    /**
     * 设置edittext是否可编辑
     *
     * @param editText
     * @param b
     */
    public static void setEditTextEnble(EditText editText, boolean b) {
        editText.setFocusable(b);
        editText.setEnabled(b);
        editText.setLongClickable(b);
    //    editText.setInputType(b ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);  //只能固定单行
        editText.setInputType(b ? InputType.TYPE_TEXT_FLAG_MULTI_LINE : InputType.TYPE_NULL);
        //改变默认的单行模式
        editText.setSingleLine(false);
        //水平滚动设置为False
        editText.setHorizontallyScrolling(false);

        editText.setFocusableInTouchMode(b);
        if (b){
            editText.requestFocus();
        }

    }



    //通过反射获取状态栏高度，默认25dp
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = dip2px(context, 25);
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
