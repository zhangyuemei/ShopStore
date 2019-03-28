package com.hswt.shopstore.shopstore.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.AppManager;
import com.hswt.shopstore.shopstore.fragment.AroundFragment;
import com.hswt.shopstore.shopstore.fragment.ClassificationFragment;
import com.hswt.shopstore.shopstore.fragment.HomeFragment;
import com.hswt.shopstore.shopstore.fragment.MineFragment;
import com.hswt.shopstore.shopstore.fragment.ShopcarFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private Fragment nowFragment;//当前显示的fragment


    private HomeFragment homeFragment;
    private ClassificationFragment classificationFragment;
    private AroundFragment aroundFragment;
    private ShopcarFragment shopcarFragment;
    private MineFragment mineFragment;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AppManager.getAppManager().addActivity(this);

        initView();
    }


    private void initView() {
        //添加角标的消息数量
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("1")
                .setHideOnSelect(true);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation_home_a);
//            bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.main_home,"首页").setInActiveColor(R.color.main_tab_checked).setBadgeItem(numberBadgeItem))
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.homepage1bl, "首页")
                .setInactiveIconResource(R.mipmap.homepage1))
                .setActiveColor(R.color.main_tab_selected_color)
                .setInActiveColor(R.color.main_tab_no_selected_color)
                .addItem(new BottomNavigationItem(R.mipmap.classification1bl, "分类")
                        .setInactiveIconResource(R.mipmap.classification1))
                .setActiveColor(R.color.main_tab_selected_color)
                .setInActiveColor(R.color.main_tab_no_selected_color)
                .addItem(new BottomNavigationItem(R.mipmap.location1bl, "周边")
                        .setInactiveIconResource(R.mipmap.location1))
                .setActiveColor(R.color.main_tab_selected_color)
                .setInActiveColor(R.color.main_tab_no_selected_color)
                .addItem(new BottomNavigationItem(R.mipmap.shoppingcart1bl, "购物车")
                        .setInactiveIconResource(R.mipmap.shoppingcart1))
                .setActiveColor(R.color.main_tab_selected_color)
                .setInActiveColor(R.color.main_tab_no_selected_color)
                .addItem(new BottomNavigationItem(R.mipmap.personalcenter1bl, "我的")
                        .setInactiveIconResource(R.mipmap.personalcenter1))
                .setActiveColor(R.color.main_tab_selected_color)
                .setInActiveColor(R.color.main_tab_no_selected_color)
                .setFirstSelectedPosition(0)
                .initialise();
        fragmentManager = getSupportFragmentManager();  //获取FragmentManager
        fragments = new ArrayList<>();
        setDefaultFragment();//设置默认选项
        bottomNavigationBar.setTabSelectedListener(this); //设置监听

    }

    private void setDefaultFragment() {
        homeFragment = HomeFragment.newInstance("首页");
        fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a, homeFragment).commit();
        fragments.add(homeFragment);
        nowFragment = homeFragment;
    }


    @Override
    public void onTabSelected(int position) {
        this.position = position;
        switch (position) {
            case 0:
                fragmentManager.beginTransaction().show(homeFragment).commit();
                nowFragment = homeFragment;
                break;
            case 1:
                if (classificationFragment == null) {
                    classificationFragment = ClassificationFragment.newInstance("通讯录");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            classificationFragment).commit();
                    fragments.add(classificationFragment);
                }
                fragmentManager.beginTransaction().show(classificationFragment).commit();
                nowFragment = classificationFragment;
                break;
            case 2:
                if (aroundFragment == null) {
                    aroundFragment = aroundFragment.newInstance("消息");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            aroundFragment).commit();
                    fragments.add(aroundFragment);
                }
                fragmentManager.beginTransaction().show(aroundFragment).commit();
                nowFragment = aroundFragment;
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance("我的");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            mineFragment).commit();
                    fragments.add(mineFragment);
                } else {
                    //获取用户信息(防止无网络状况)
                    //  mineFragment.getUserInfo();
                }

                fragmentManager.beginTransaction().show(mineFragment).commit();
                nowFragment = mineFragment;
                break;
        }
        //隐藏其它的fragment
        for (Fragment fragment : fragments) {
            if (fragment == nowFragment)
                continue;
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        this.position = position;
        switch (position) {
            case 0:
                fragmentManager.beginTransaction().show(homeFragment).commit();
                nowFragment = homeFragment;
                break;
            case 1:
                if (classificationFragment == null) {
                    classificationFragment = ClassificationFragment.newInstance("通讯录");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            classificationFragment).commit();
                    fragments.add(classificationFragment);
                }
                fragmentManager.beginTransaction().show(classificationFragment).commit();
                nowFragment = classificationFragment;
                break;
            case 2:
                if (aroundFragment == null) {
                    aroundFragment = aroundFragment.newInstance("消息");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            aroundFragment).commit();
                    fragments.add(aroundFragment);
                }
                fragmentManager.beginTransaction().show(aroundFragment).commit();
                nowFragment = aroundFragment;
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance("我的");
                    fragmentManager.beginTransaction().add(R.id.bottom_nav_content_main_a,
                            mineFragment).commit();
                    fragments.add(mineFragment);
                } else {
                    //获取用户信息(防止无网络状况)
                    //  mineFragment.getUserInfo();
                }

                fragmentManager.beginTransaction().show(mineFragment).commit();
                nowFragment = mineFragment;
                break;
        }
        //隐藏其它的fragment
        for (Fragment fragment : fragments) {
            if (fragment == nowFragment)
                continue;
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }


}
