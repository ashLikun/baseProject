package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.content.Context
import android.view.View
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseListView
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseSwipeView
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.iview.IBaseView
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.ResUtils
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.StatusChangListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/25　13:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：请求各种处理
 * 1弹窗
 * 2布局切换
 * 3生命周期
 * 4失效view
 */

class HttpCallbackHandle private constructor() {
    /**
     * 对话框提示的文本  空就不显示对话框
     */
    internal var hint: String = ""

    /**
     * 失效的View
     */
    internal var enableView: Array<out View?>? = null
    /**
     * 对话框是否可以取消
     */
    internal var isCancelable = true
    /**
     * 是否错误的时候toast提示
     */
    internal var isToastShow = true
    /**
     * 是否显示进度，进度回调才用到的
     */
    internal var isShowProgress = true
    /**
     * 是否显示对话框
     */
    internal var isShowLoadding = true
    /**
     * 下拉刷新
     */
    internal var swipeRefreshLayout: RefreshLayout? = null
    /**
     * 自动加载刷新
     */
    internal var statusChangListener: StatusChangListener? = null
    /**
     * 界面显示管理器（加载中，加载失败，加载成功）
     */
    internal var loadSwitchService: LoadSwitchService? = null

    /**
     * tag类
     */
    internal var basePresenter: BasePresenter<*>? = null
    internal var context: Context? = null
    /**
     * 加载框
     */
    internal var loadDialog: LoadDialog? = null

    /**
     * 在当前页面是否第一次请求
     * 如果这个请求集合大于1，说明当前页面还有请求，
     * @return
     */
    fun isFirstRequest() = count() <= 1

    fun getTag() = context ?: basePresenter

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Long {
        return OkHttpUtils.getInstance().countRequest(context, basePresenter)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     *
     * 方法功能：设置View的使能
     */
    fun goSetEnableView(enable: Boolean) {
        enableView?.forEach { it?.isEnabled = enable }
    }


    inline fun set(funs: HttpCallbackHandle.() -> Unit): HttpCallbackHandle {
        funs()
        return this
    }


    fun setHint(hint: String = AppUtils.getApp().resources.getString(R.string.loadding)): HttpCallbackHandle {
        this.hint = hint
        return this
    }


    fun setEnableView(vararg view: View?): HttpCallbackHandle {
        this.enableView = view
        return this
    }

    fun setCancelable(cancelable: Boolean): HttpCallbackHandle {
        isCancelable = cancelable
        return this
    }

    /**
     * 错误的时候是否显示toast
     */
    fun isToastShow(isToastShow: Boolean): HttpCallbackHandle {
        this.isToastShow = isToastShow
        return this
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:45
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：下拉刷新的监听
     */
    fun setSwipeRefreshLayout(swipeRefreshLayout: RefreshLayout): HttpCallbackHandle {
        this.swipeRefreshLayout = swipeRefreshLayout
        return this
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：状态改加载监听
     */
    fun setStatusChangListener(statusChangListener: StatusChangListener): HttpCallbackHandle {
        this.statusChangListener = statusChangListener
        return this
    }

    fun setPresenter(basePresenter: BasePresenter<*>): HttpCallbackHandle {
        this.basePresenter = basePresenter
        return this
    }

    fun setContext(context: Context): HttpCallbackHandle {
        this.context = context
        return this
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：重新加载的监听
     */
    fun setLoadSwitchService(loadSwitchService: LoadSwitchService): HttpCallbackHandle {
        this.loadSwitchService = loadSwitchService
        return this
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：显示加载的监听
     */
    fun setShowLoadding(showLoadding: Boolean): HttpCallbackHandle {
        isShowLoadding = showLoadding
        return this
    }

    fun setShowProgress(showProgress: Boolean): HttpCallbackHandle {
        isShowProgress = showProgress
        return this
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：设置下位刷新，底加载，状态显示
     */
    fun setLoadingStatus(mvpView: IBaseView?): HttpCallbackHandle {
        if (mvpView == null) {
            return this
        }
        if (mvpView is IBaseSwipeView) {
            setSwipeRefreshLayout(mvpView.getSwipeRefreshLayout())
        }
        if (mvpView is IBaseListView) {
            setStatusChangListener(mvpView.getStatusChangListener())
        }
        setLoadSwitchService(mvpView.switchService)
        setShowLoadding(false)
        return this
    }

    fun showDialog() {
        if (isShowLoadding) {
            if (getActivity()?.isFinishing == false) {
                if (loadDialog == null) {
                    loadDialog = LoadDialog(getActivity())
                }
                loadDialog?.run {
                    setContent(StringUtils.dataFilter(hint, (ResUtils.getString(R.string.loadding))))
                    setCancelable(isCancelable)
                    try {
                        show()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }


    fun showLoading() {
        loadSwitchService?.showLoading(ContextData(hint))
    }

    fun hintProgress() {
        loadDialog?.dismiss()
    }

    private fun getActivity(): Activity? {
        var activity: Activity? = ActivityUtils.getActivity(context)
        if (activity == null) {
            activity = basePresenter?.activity
        }
        return activity
    }

    companion object {

        operator fun get(basePresenter: BasePresenter<*>): HttpCallbackHandle {
            val buider = get()
            buider.basePresenter = basePresenter
            return buider
        }

        operator fun get(context: Context): HttpCallbackHandle {
            val buider = get()
            buider.context = context
            return buider
        }

        fun get(): HttpCallbackHandle {
            return HttpCallbackHandle()
        }
    }
}