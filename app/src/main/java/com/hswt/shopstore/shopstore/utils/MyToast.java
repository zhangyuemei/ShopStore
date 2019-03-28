package com.hswt.shopstore.shopstore.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义Toast
 * 作者： on 2015/7/14 10:16
 * 邮箱：306200335@qq.com
 */
public class MyToast {

    public static void showToast(Context con, String text) {
        //  Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(con, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast_long(Context con, String text) {
        Toast.makeText(con, text, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context con, int id) {
        //  Toast.makeText(con, id, Toast.LENGTH_SHORT).setGravity(Gravity.CENTER,0,0).show();
        Toast toast = Toast.makeText(con, id, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void showToast_long(Context con, int id) {
        Toast.makeText(con, id, Toast.LENGTH_LONG).show();
    }


}
