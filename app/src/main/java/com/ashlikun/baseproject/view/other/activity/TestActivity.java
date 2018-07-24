package com.ashlikun.baseproject.view.other.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.adapter.recyclerview.multiltem.MultipleAdapter;
import com.ashlikun.baseproject.R;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.libarouter.constant.RouterPath;


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = RouterPath.TEST)
public class TestActivity extends BaseActivity {
    MultipleAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.aaa;
    }

    @Override
    public void initView() {
    }

    public void onButtonClick(View view) {

    }


}
