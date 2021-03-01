package com.ashlikun.baseproject.module.other.view

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.animation.DecelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.PullJumpManage
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.extend.noAnim
import com.ashlikun.baseproject.libcore.utils.extend.requestPermission
import com.ashlikun.baseproject.module.other.BuildConfig
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.SharedPreUtils
import com.ashlikun.utils.other.ThreadUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.utils.ui.UiUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.other_activity_welcom.*
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:22
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：项目入口类
 */

@Route(path = RouterPath.WELCOME)
class WelcomeActivity : BaseActivity() {


    private val time = 2000L


    override fun initView() {
        requestPermission(arrayOf(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                , denied = {
            SuperToast.get("获取权限失败").warn()
            finish()
        }) {
            initViewOnPermiss()
            launch(delayTime = time) {
                LogUtils.e(ThreadUtils.isMainThread())
                when {
                    checkIsFirst() -> RouterJump.startLaunch()
                    else -> RouterJump.startHome(0)
                }
                LogUtils.e(ThreadUtils.isMainThread())
                finish()
            }


        }
    }

    override fun getLayoutId(): Int {
        return R.layout.other_activity_welcom
    }

    override fun getStatusBarColor(): Int {
        return R.color.translucent
    }

    override fun isStatusTranslucent(): Boolean {
        return true
    }


    fun initViewOnPermiss() {
//        presenter.getHttpData()

        val animSet = AnimatorSet()
//        val animX = ObjectAnimator.ofFloat(imageView, "translationX", 0.5f, 1.2f, 1f)
//        val animY = ObjectAnimator.ofFloat(imageView, "translationY", startY, endY)
        val scaleX = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "scaleX", 0.7f, 1.4f, 1f)
        val scaleY = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "scaleY", 0.7f, 1.4f, 1f)
        val alpha = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "alpha", 0.8f, 1f)
        scaleX.duration = time.toLong()

        scaleY.duration = time.toLong()
        alpha.duration = time.toLong()
        animSet.interpolator = DecelerateInterpolator()
        animSet.duration = time.toLong()
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {}
        })
        animSet.playTogether(scaleX, scaleY, alpha)
        animSet.start()
    }

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        PullJumpManage.save(intent)
        //如果首页已经启动了，那么久不用启动首页
        if (RouterManage.home()?.isHomeStart() == true) {
            //这里是为了后续跳转使用的topactivity用的是以前的Activity栈，防止返回后回到微信或者浏览器
            ActivityManager.getInstance().exitActivity(this)
            //处理拉起App数据
            PullJumpManage.handleCachePush(this)
        }
    }

    private fun checkIsFirst(): Boolean {
        if (SharedPreUtils.getInt(this, "Run", "VersionCode") != BuildConfig.VERSION_CODE) {
//            // 第一次
//            SharedPreUtils.setKeyAndValue(this, "Run",
//                    "VersionCode", BuildConfig.VERSION_CODE)
//            val intent = Intent(this, LeadActivity::class.java)
//            startActivity(intent)
//            return false
//            finish()
        } else {
        }
        return false
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/26 17:05
     *
     *
     * 方法功能：获取用户所有信息
     * 1跳转登陆或者首页，2：不跳转
     */
    fun getServiceUser(): Int {

        RouterManage.login()?.run {
            if (isLogin()) {
//                val p = HttpRequestParam(if (UserData.getDbUserData().isStudent())
//                    "sget_information.php"
//                else
//                    "hget_information.php")
//                try {
//                    val result = HttpManager.getInstance().syncExecute(p, HttpResult<*>::class.java, UserData::class.java)
//                    if (result.isSucceed() && result.getData() != null) {
//                        result.getData().setToken(UserData.getUserData().getToken())
//                        result.getData().save()
//                    }
//                    return if (HttpManager.handelResult<HttpResult<UserData>>(result)) 1 else 2
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    return 1
//                }
            }
        }
        return 1
    }

}
