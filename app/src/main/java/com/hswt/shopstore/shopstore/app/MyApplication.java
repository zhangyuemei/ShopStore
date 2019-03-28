package com.hswt.shopstore.shopstore.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;


/**
 * 应用,主要用来做一下初始化的操作
 */

public class MyApplication extends Application {
    private static Context mContext;

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        myApplication = this;
/*

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //检测内存
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        */
/** bugly
 * 2a1c65f0ea 官网申请的appID
 * true 是否在logcat 打印  建议在debug 设为true ,正式版为false
 *//*

        CrashReport.initCrashReport(getApplicationContext(), "2a1c65f0ea", true);
*/

/*

        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0x5B, 0x63, 0x78))//标题栏背景颜色
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(8)//配置多选数量
//                .setEnableEdit(true)//开启编辑功能
//                .setEnableCrop(true)//开启裁剪功能
//                .setEnableRotate(true)//开启旋转功能
//                .setEnableCamera(true)//开启相机功能
//                .setCropWidth(720)//裁剪宽度
//                .setCropHeight(1280)//裁剪高度
//                .setCropSquare(true)//裁剪正方形
//                .setForceCropEdit(true)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
//                .setEnablePreview(true)//是否开启预览功能
                .build();

        //配置imageLoader
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, glideImageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
*/

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    /**
     * @return 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

}
