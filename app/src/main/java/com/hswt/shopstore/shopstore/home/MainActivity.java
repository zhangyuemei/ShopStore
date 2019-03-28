package com.hswt.shopstore.shopstore.home;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.hswt.shopstore.shopstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation_main_a)
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView(){
        ButterKnife.bind(this);

    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {

    //    navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
      //  bind = DataBindingUtil.bind(headerView);
      //  bind.setListener(this);
      //  bind.dayNightSwitch.setChecked(SPUtils.getNightMode());

      //  ImageLoadUtil.displayCircle(bind.ivAvatar, ConstantsImageUrl.IC_AVATAR);
     //   bind.llNavExit.setOnClickListener(this);
     //   bind.ivAvatar.setOnClickListener(this);

     /*   bind.llNavHomepage.setOnClickListener(listener);
        bind.llNavScanDownload.setOnClickListener(listener);
        bind.llNavDeedback.setOnClickListener(listener);
        bind.llNavAbout.setOnClickListener(listener);
        bind.llNavLogin.setOnClickListener(listener);
        bind.llNavCollect.setOnClickListener(listener);*/
    }


}
