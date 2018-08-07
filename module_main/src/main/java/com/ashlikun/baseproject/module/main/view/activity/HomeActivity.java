package com.ashlikun.baseproject.module.main.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.adapter.SectionsPagerAdapter;
import com.ashlikun.baseproject.libcore.libarouter.constant.RouterPath;
import com.ashlikun.baseproject.libcore.widget.NestViewPager;
import com.ashlikun.baseproject.module.main.R;
import com.ashlikun.baseproject.module.main.view.fragment.HomeFragment;
import com.ashlikun.bottomnavigation.AHBottomNavigation;
import com.ashlikun.bottomnavigation.AHBottomNavigationItem;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.utils.ui.ToastUtils;

import java.util.ArrayList;


/**
 * 作者　　: 李坤
 * 创建时间:2016/9/19　17:04
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = RouterPath.HOME)
public class HomeActivity extends BaseActivity
        implements AHBottomNavigation.OnTabSelectedListener
        , ViewPager.OnPageChangeListener {
    private long exitTime = 0;
    private NestViewPager viewPager;
    private AHBottomNavigation bottom_navigation_bar;
    private AHBottomNavigation bottomNavigationBar;

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Autowired
    public int index = 0;
    @Autowired()
    public String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            setCurrentItem(savedInstanceState.getInt("tab"));
        } else {
            setCurrentItem(index);
        }
    }


    public void setCurrentItem(int postion) {
        bottomNavigationBar.setCurrentItem(postion);
    }


    @Override
    public int getLayoutId() {
        return R.layout.main_activity_home;
    }


    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.white);
    }


    @Override
    public void initView() {
        viewPager = f(R.id.viewPager);
        bottomNavigationBar = f(R.id.bottomNavigationBar);
        bottomNavigationBar.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigationBar.addItem(new AHBottomNavigationItem.Builder(R.string.main_bottom_1,
                R.mipmap.app_logo, R.mipmap.app_logo).builder());
        bottomNavigationBar.addItem(new AHBottomNavigationItem.Builder(R.string.main_bottom_2,
                R.mipmap.app_logo, R.mipmap.app_logo).builder());
        bottomNavigationBar.addItem(new AHBottomNavigationItem.Builder(R.string.main_bottom_3,
                R.mipmap.app_logo, R.mipmap.app_logo).builder());

        bottomNavigationBar.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigationBar.setCurrentItem(0, false);
        bottomNavigationBar.setForceTint(true);
        bottomNavigationBar.setOnTabSelectedListener(this);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        SectionsPagerAdapter mAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);


    }

    @Override
    public void parseIntent(Intent intent) {
        super.parseIntent(intent);
    }


    @Override
    public void onBackPressed() {
        isExitApplication(this);
    }


    public void isExitApplication(Context context) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            SuperToast.get("再按一次退出程序").info();
            exitTime = System.currentTimeMillis();
        } else {
            // 退出
            ActivityManager.getInstance().exitAllActivity();
            ToastUtils.getMyToast().cancel();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        useCallback = false;
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.setCurrentItem(position, useCallback);
    }

    boolean useCallback = false;

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        useCallback = true;
        viewPager.setCurrentItem(position, false);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
