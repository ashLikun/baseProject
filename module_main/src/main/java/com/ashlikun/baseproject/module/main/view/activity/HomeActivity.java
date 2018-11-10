package com.ashlikun.baseproject.module.main.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.libcore.constant.RouterKey;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.baseproject.libcore.libarouter.RouterManage;
import com.ashlikun.baseproject.module.main.R;
import com.ashlikun.bottomnavigation.AHBottomNavigation;
import com.ashlikun.bottomnavigation.AHBottomNavigationItem;
import com.ashlikun.common.EvenBusKey;
import com.ashlikun.common.utils.jump.RouterJump;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.core.listener.OnDispatcherMessage;
import com.ashlikun.livedatabus.EventBus;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.utils.ui.ToastUtils;
import com.ashlikun.xviewpager.fragment.FragmentPagerAdapter;
import com.ashlikun.xviewpager.fragment.FragmentPagerItem;
import com.ashlikun.xviewpager.view.NestViewPager;

import java.util.List;


/**
 * 作者　　: 李坤
 * 创建时间:2016/9/19　17:04
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = RouterPath.HOME)
public class HomeActivity extends BaseActivity
        implements AHBottomNavigation.OnTabSelectedListener {
    private long exitTime = 0;
    private NestViewPager viewPager;
    private AHBottomNavigation bottomNavigationBar;

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public int index = 0;
    public int cachePosition = -1;
    FragmentPagerAdapter adapter;

    @Override
    public void parseIntent(Intent intent) {
        index = intent.getIntExtra(RouterKey.FLAG_INDEX, -1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        if (index != -1) {
            setCurrentItem(index);
        } else if (cachePosition != -1) {
            setCurrentItem(cachePosition);
            cachePosition = -1;
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
        bottomNavigationBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigationBar.setupWithViewPager(viewPager);
        bottomNavigationBar.addOnTabSelectedListener(this);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() != 0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                ft.remove(fragments.get(i));
            }
            ft.commit();
        }
        adapter = FragmentPagerAdapter.Builder.get(getSupportFragmentManager())
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .build();
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        //登录之后可以左右滑动
        viewPager.setCanSlide(RouterManage.getLogin().isLogin());
        //监听登录成功的通知
        EventBus.get(EvenBusKey.LOGIN).registerLifecycle(this, o -> {
            //登录之后可以左右滑动
            viewPager.setCanSlide(RouterManage.getLogin().isLogin());
        });

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
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected) {
            return onTabChang(position);
        } else {
            return true;
        }
    }

    private boolean onTabChang(int position) {
        //限制登录
        if (position == 2 || position == 3) {
            if (!RouterManage.getLogin().isLogin()) {
                RouterJump.startLogin();
                cachePosition = position;
                return false;
            }
        }
        if (position == 0) {
            statusBar.translucentStatusBar();
            Fragment fragment = adapter.getCurrentFragment();
            if (fragment instanceof OnDispatcherMessage) {
                //首页自己更改状态栏
                ((OnDispatcherMessage) fragment).onDispatcherMessage(1, null);
            }
        } else {
            statusBar.setStatusBarColor(getStatusBarColor());
        }
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
