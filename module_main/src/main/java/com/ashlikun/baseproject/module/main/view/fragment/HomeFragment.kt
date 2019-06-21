package com.ashlikun.baseproject.module.main.view.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.main.R
import com.ashlikun.baseproject.module.main.iview.IBHomeView
import com.ashlikun.baseproject.module.main.presenter.HomePresenter
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.core.factory.Presenter
import com.ashlikun.core.fragment.BaseMvpFragment
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.OnLoadSwitchClick
import kotlinx.android.synthetic.main.main_fragment_home.*

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　21:17
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
@Presenter(HomePresenter::class)
@Route(path = RouterPath.FRAGMENT_HOME)
class HomeFragment : BaseMvpFragment<HomePresenter>(), IBHomeView.IHomeView, OnLoadSwitchClick {


    override fun getLayoutId(): Int {
        return R.layout.main_fragment_home
    }


    override fun initView() {
        toolbar?.run {
            setTitle("首页")
        }
        ceshiButton.setOnClickListener {
            RouterJump.startTest()
        }
    }

    override fun clearData() {}


    override fun onRetryClick(data: ContextData?) {}


    override fun onEmptyClick(data: ContextData?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
