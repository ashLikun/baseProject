package com.ashlikun.baseproject.view.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.adapter.SectionsPagerAdapter;
import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.view.home.fragment.HomeFragment;
import com.ashlikun.bottomnavigation.AHBottomNavigation;
import com.ashlikun.bottomnavigation.AHBottomNavigationItem;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.libarouter.constant.ARouterPath;
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
@Route(path = ARouterPath.HOME)
public class HomeActivity extends BaseActivity
        implements AHBottomNavigation.OnTabSelectedListener
        , ViewPager.OnPageChangeListener {
    AHBottomNavigation ahBottomNavigation;
    private long exitTime = 0;

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    ViewPager viewPager;

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
//        TestData data = GsonHelper.getGson().fromJson(aaa, TestData.class);
//        Log.e("aaa",data.getGoods_id());
    }

    private static String aaa = "{\n" +
            "\t\t\"goods_id\": \"42155\",\n" +
            "\t\t\"goods_name\": \"\\u6b63\\u5c71\\u5c0f\\u79cd\\u7ea2\\u83361\\u76d232\\u6ce1\",\n" +
            "\t\t\"goods_des\": \"\\u6b63\\u5c71\\u5c0f\\u79cd\\u7ea2\\u8336\\u6b63\\u5b97\\u8336\\u53f6\\u793c\\u76d2\\u88c5\",\n" +
            "\t\t\"default_image\": \"https:\\/\\/ojn89n2tq.qnssl.com\\/comment0gow_58f068a015d7c?imageMogr2\\/thumbnail\\/320x\\/strip\\/quality\\/75\\/format\\/jpg\",\n" +
            "\t\t\"Price\": \"21.90\",\n" +
            "\t\t\"scPrice\": \"42.00\",\n" +
            "\t\t\"wlPrice\": \"0.00\",\n" +
            "\t\t\"seller_id\": \"6718\",\n" +
            "\t\t\"sale\": \"10238\",\n" +
            "\t\t\"base_sale\": \"0\",\n" +
            "\t\t\"has_storecoupon\": \"0\",\n" +
            "\t\t\"is_act\": 0,\n" +
            "\t\t\"seller_info\": {\n" +
            "\t\t\t\"seller_id\": \"6718\",\n" +
            "\t\t\t\"store_name\": \"\\u99a8\\u9732\\u65d7\\u8230\\u5e97\",\n" +
            "\t\t\t\"icon\": \"\"\n" +
            "\t\t},\n" +
            "\t\t\"act_image\": \"\"\n" +
            "\t}";

    public void setCurrentItem(int postion) {
        ahBottomNavigation.setCurrentItem(postion);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public int getStatusBarColor() {
        return R.color.colorPrimary;
    }


    @Override
    public void initView() {
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_bar);
        viewPager = (ViewPager) findViewById(R.id.content);

        ahBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        ahBottomNavigation.addItem(new AHBottomNavigationItem.Builder(R.string.bottom_1,
                R.mipmap.default_icon, R.mipmap.default_icon).builder());
        ahBottomNavigation.addItem(new AHBottomNavigationItem.Builder(R.string.bottom_2,
                R.mipmap.default_icon, R.mipmap.default_icon).builder());
        ahBottomNavigation.addItem(new AHBottomNavigationItem.Builder(R.string.bottom_3,
                R.mipmap.default_icon, R.mipmap.default_icon).builder());

        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        ahBottomNavigation.setCurrentItem(0, false);
        ahBottomNavigation.setForceTint(true);
        ahBottomNavigation.setOnTabSelectedListener(this);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        SectionsPagerAdapter mAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void parseIntent(Intent intent) {
        ARouter.getInstance().inject(this);
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
        ahBottomNavigation.setCurrentItem(position, useCallback);
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
