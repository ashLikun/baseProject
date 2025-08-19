package com.ashlikun.baseproject.common.view.activity

import android.content.Intent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.common.R
import com.ashlikun.baseproject.common.databinding.CommonActivityShowFragmentBinding
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.utils.ui.extend.resColor
import com.ashlikun.utils.ui.status.enableEdgeToEdgeX

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/22　16:44
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：显示Fragment的Activity,  所以参数会传递给Fragment
 * 必要
 * * {@link RouterKey#TARGET_FRAGMENT_PATH} fragment的path
 * 非必要
 * {@link RouterKey#FLAG_TITLE} 标题
 * {@link RouterKey#FLAG_STATUS_TRANS} 状态栏是否透明
 * {@link RouterKey#FLAG_STATUS_COLOR} 状态栏颜色
 */
@Route(path = RouterPath.ACTIVITY_SHOW_FRAGMENT)
class ShowFragmentActivity : BaseActivity() {
    override val binding by lazy {
        CommonActivityShowFragmentBinding.inflate(layoutInflater)
    }

    @JvmField
    @Autowired(name = RouterKey.FLAG_TARGET_PATH)
    var fragmentPath: String? = null

    @JvmField
    @Autowired(name = RouterKey.FLAG_TITLE)
    var title: String = ""

    @JvmField
    @Autowired(name = RouterKey.FLAG_STATUS_TRANS)
    var statusTranslucent = true

    override fun setSafeArea() {
        enableEdgeToEdgeX(isSetView = !statusTranslucent)
    }

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        if (title.isNotEmpty()) {
            statusTranslucent = false
        }
    }

    override fun initView() {
        binding.apply {
            if (title.isNotEmpty()) {
                toolbar.isVisible = true
                toolbar.setBack(this@ShowFragmentActivity)
                toolbar.setTitle(title)
            }


        }
        val fargment = ARouter.getInstance().build(fragmentPath)
            .with(intent.extras)
            .withBoolean(RouterKey.FLAG_BACK, true)
            .navigation() as Fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, fargment)
        ft.setMaxLifecycle(fargment, Lifecycle.State.RESUMED)
        ft.commitNow()
    }
}