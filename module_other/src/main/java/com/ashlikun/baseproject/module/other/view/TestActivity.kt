package com.ashlikun.baseproject.module.other.view

import android.view.View

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = RouterPath.TEST)
class TestActivity : BaseActivity() {
    internal var adapter: MultipleAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.other_activity_test
    }

    override fun initView() {}

    fun onButtonClick(view: View) {

    }


}
