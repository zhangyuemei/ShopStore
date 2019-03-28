package com.hswt.shopstore.shopstore.login;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.AppManager;
import com.hswt.shopstore.shopstore.app.MyApplication;
import com.hswt.shopstore.shopstore.base.BaseActivity;
import com.hswt.shopstore.shopstore.home.HomeActivity;
import com.hswt.shopstore.shopstore.home.MainActivity;
import com.hswt.shopstore.shopstore.utils.MyToast;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.HttpUrl;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static rx.Observable.*;


/**
 * @author
 * @Title: LauncherActivity.java
 * @Description: TODO(启动页)
 */

public class LauncherActivity extends BaseActivity {
    /**
     * Login Presenter
     */

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    /**
     * 初始化View
     */
    @Override
    public void initView() {
/*        mImmersionBar
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .transparentBar().init();*/


/*

        timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                     */
/*   Boolean fistOpenApp = (Boolean) SPUtils.get(MyApplication.getmContext(), Constants.FIRST_OPEN_APP, true);

                        if (fistOpenApp) {
                            AppManager.getAppManager().finishActivity();
                            startActivity(new Intent(LauncherActivity.this, WelcomeActivity.class));
                        } else {
                            String token = (String) SPUtils.get(MyApplication.getmContext(), Constants.TOKEN, "");
                            if (StringUtil.isNotEmpty(token)) {
                                LoginRequest(Constants.AUTO_LOGIN, null, null);
                            } else {
                                AppManager.getAppManager().finishActivity();
                                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                            }
                        }
                     *//*





                    }
                });
*/

/*
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
              //  overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }
        }, 200);*/

        TextView tv_to_home1 = findViewById(R.id.tv_to_home1);
        tv_to_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, HomeActivity.class));
                AppManager.getAppManager().finishActivity();
            }
        });

        TextView tv_to_home2 = findViewById(R.id.tv_to_home2);
        tv_to_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                AppManager.getAppManager().finishActivity();
            }
        });
    }


    @Override
    protected void initData() {
  /*      mLoginPresenter = new LoginPresenter(this);
          mLoginPresenter.setmBaseViewIsFlay(this);*/
    }

    @Override
    protected void otherViewClick(View view) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        // JPushInterface.onResume(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        //  JPushInterface.onPause(this);
    }


/*    @Override

    public void onSuccess(Object result, String isFlag) {
        if (result != null) {
            if (StringUtil.equals(isFlag, "login")) {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            }
        }
    }

    @Override
    public void fail(String err, String isFlag) {
        MyToast.showToast(MyApplication.getmContext(), err);
        AppManager.getAppManager().finishActivity();
        ActivityUtils.openActivity(LauncherActivity.this, new Intent(
                LauncherActivity.this, LoginActivity.class));
    }

*/


    private void toActivity() {
        AppManager.getAppManager().finishActivity();


        // 只有策划师有添加项目功能
        // 厨师长只显示EO单行，但是没有编辑能力
        // 管理员直接进入后台且无法切换
        // 策划师、厨师长无法切换至后台
        // 厨师长无通知、无添加项目
        // 主管和总经理前台后台都可查看
      /*
        管理员可以创建所有角色(除管理员)
        总经理可以创建策划师、策划师主管、厨师长(除自己)
        主管可以创建策划师
       */
   /*     Intent intent = null;
        //   roleName  策划师、策划师主管、厨师长、总经理、管理员
        switch (SPUtils.get(MyApplication.getmContext(), Constants.USER_POSTION, "").toString().trim()) {
            case Constants.ROLE_NAME1:
            case Constants.ROLE_NAME2:
            case Constants.ROLE_NAME4:
                intent = new Intent(this, MainActivity.class);
                break;
            case Constants.ROLE_NAME3:
                intent = new Intent(this, MainActivity.class);
                // intent = new Intent(this, NoMessageHomeActivity.class);
                break;
            case Constants.ROLE_NAME5:
                intent = new Intent(this, SystemHomeActivity.class);
                break;
            default:
                MyToast.showToast(MyApplication.getmContext(), "职位未知，请联系管理员查看！");
                break;
        }

        MyLog.e("---roleName--", "---roleName--" + SPUtils.get(MyApplication.getmContext(), Constants.USER_POSTION, "").toString());

        startActivity(intent);*/
    }

}
