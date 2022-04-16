package com.ashlikun.baseproject.module.main.view.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.main.databinding.MainFragmentHomeBinding
import com.ashlikun.baseproject.module.main.viewmodel.HomeViewModel
import com.ashlikun.core.mvvm.BaseMvvmFragment
import com.ashlikun.core.mvvm.IViewModel
import com.ashlikun.loadswitch.ContextData

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　21:17
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
@IViewModel(HomeViewModel::class)
@Route(path = RouterPath.FRAGMENT_HOME)
class HomeFragment : BaseMvvmFragment<HomeViewModel>() {
    override val binding by lazy {
        MainFragmentHomeBinding.inflate(layoutInflater)
    }
    val RESURL2 = listOf(
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
        "http://img1.cache.netease.com/catchpic/A/A0/A0153E1AEDA115EAE7061A0C7EBB69D2.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584594344314&di=4eb56ec22f47949d7c36069f55403e5c&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2Fe573f475a02c84bf35a44be7cff56307-0.jpg",
        "http://uploadfile.bizhizu.cn/up/03/50/95/0350955b21a20b6deceea4914b1cfeeb.jpg.source.jpg",
        "http://pic1.win4000.com/wallpaper/7/5860842b353da.jpg",
        "http://pic1.win4000.com/wallpaper/b/566a37b05aac3.jpg"
    )

    override fun initView() {
        toolbar?.run {
            setTitle("首页")
        }
        binding.ceshiButton.setOnClickListener {
            RouterJump.startLockImage(0, RESURL2.map { ImageData(it, it, 0) } as ArrayList, true)
        }
        binding.ceshi2Button.setOnClickListener {
            RouterJump.startTest()
        }
    }

    override fun clearData() {}


    override fun onRetryClick(data: ContextData) {}


    override fun onEmptyClick(data: ContextData) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
