package com.ashlikun.baseproject.common.view.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.utils.ui.ResUtils
import com.ashlikun.baseproject.common.R
import com.ashlikun.baseproject.common.databinding.CommonActivityShowFragmentBinding
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/22　16:44
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：显示Fragment的Activity,  所以参数会传递给Fragment
 * 必要
 * * {@link RouterKey#TARGET_FRAGMENT_PATH} fragment的path
 * 非必要
 * {@link RouterKey#STATUS_TRANS} 状态栏是否透明
 * {@link RouterKey#STATUS_COLOR} 状态栏颜色
 */
@Route(path = RouterPath.ACTIVITY_SHOW_FRAGMENT)
class ShowFragmentActivity : BaseActivity() {
    val binding by lazy {
        CommonActivityShowFragmentBinding.inflate(layoutInflater)
    }

    @Autowired(name = RouterKey.TARGET_FRAGMENT_PATH)
    @JvmField
    protected var fragmentPath: String? = null

    @Autowired(name = RouterKey.STATUS_TRANS)
    @JvmField
    protected var isStatusTrans = true

    @Autowired(name = RouterKey.STATUS_COLOR)
    @JvmField
    protected var statusColor = ResUtils.getColor(R.color.white)

    override fun initView() {
        val fargment = ARouter.getInstance().build(fragmentPath)
                .with(intent.extras)
                .withBoolean(RouterKey.FLAG_BACK, true)
                .navigation() as Fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, fargment)
        ft.setMaxLifecycle(fargment, Lifecycle.State.RESUMED)
        ft.commitNow()
    }

    override fun isStatusTranslucent() = isStatusTrans

    override fun getStatusBarColor() = statusColor
}