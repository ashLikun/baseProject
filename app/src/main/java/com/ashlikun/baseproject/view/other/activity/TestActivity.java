package com.ashlikun.baseproject.view.other.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.adapter.recyclerview.multiltem.MultipleAdapter;
import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.databinding.AaaBinding;
import com.ashlikun.core.activity.DataBindingActivity;
import com.google.gson.GsonBuilder;

import static com.ashlikun.libarouter.constant.ARouterPath.TEST;


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = TEST)
public class TestActivity extends DataBindingActivity<AaaBinding> {
    MultipleAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.aaa;
    }

    @Override
    public void initView() {
//        VirtualLayoutManager manager = new VirtualLayoutManager(this);
//        adapter = new MultipleAdapter(manager);
//        dataBind.switchRoot.setAdapter(adapter);
//        dataBind.switchRoot.setLayoutManager(manager);

    }

    public void onButtonClick(View view) {

    }


}
