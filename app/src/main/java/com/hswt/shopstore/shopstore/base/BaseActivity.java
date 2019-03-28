package com.hswt.shopstore.shopstore.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.AppManager;

/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener{
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initView();
        initData();
        AppManager.getAppManager().addActivity(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }


    public boolean dispatchKeyEvent(KeyEvent event) {

        if (KeyEvent.ACTION_DOWN == event.getAction()
                && KeyEvent.KEYCODE_BACK == event.getKeyCode()) {

            AppManager.getAppManager().finishActivity();

            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }
}
