package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.content.Context
import android.view.View
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.utils.main.ActivityUtils
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
    internal var hint: String? = null

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
     * tag类,标识这个请求，会传递到Request里面
     * [tag]优先级高
     */
    internal var baseViewModel: BaseViewModel? = null
    internal var context: Context? = null
    internal var tag: Any? = null

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

    fun getTag() = tag ?: (context ?: baseViewModel)

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Long {
        return OkHttpUtils.getInstance().countRequest(tag, context, baseViewModel)
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


    fun setHint(hint: String = "加载中"): HttpCallbackHandle {
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
     * 下拉刷新的监听
     */
    fun setSwipeRefreshLayout(swipeRefreshLayout: RefreshLayout?): HttpCallbackHandle {
        this.swipeRefreshLayout = swipeRefreshLayout
        return this
    }

    /**
     * 状态改加载监听
     */
    fun setStatusChangListener(statusChangListener: StatusChangListener?): HttpCallbackHandle {
        this.statusChangListener = statusChangListener
        return this
    }

    fun setPresenter(baseViewModel: BaseViewModel): HttpCallbackHandle {
        this.baseViewModel = baseViewModel
        return this
    }

    fun setContext(context: Context): HttpCallbackHandle {
        this.context = context
        return this
    }

    /**
     * 重新加载的监听
     */
    fun setLoadSwitchService(loadSwitchService: LoadSwitchService?): HttpCallbackHandle {
        this.loadSwitchService = loadSwitchService
        return this
    }


    /**
     * 显示加载的监听
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
     * 设置默默调用，不提示
     */
    fun setNoTips(): HttpCallbackHandle {
        setShowLoadding(false)
        isToastShow(false)
        return this
    }

    /**
     * 方法功能：设置下位刷新，底加载，布局切换
     */
    fun setLoadingStatus(baseViewModel: BaseViewModel? = this.baseViewModel): HttpCallbackHandle {
        if (baseViewModel is BaseListViewModel) {
            (baseViewModel as BaseListViewModel)?.run {
                if (swipeRefreshLayout != null) {
                    isShowLoadding = false
                    setSwipeRefreshLayout(swipeRefreshLayout)
                }
                if (statusChangListener != null) {
                    isShowLoadding = false
                    setStatusChangListener(statusChangListener)
                }
            }
        }
        //布局切换
        setLoadSwitchService(baseViewModel?.loadSwitchService)
        return this
    }


    fun showLoading() {
        loadSwitchService?.showLoading(ContextData(hint))
    }

    fun showContent() {
        loadSwitchService?.showContent()
    }

    fun showRetry(data: ContextData): Boolean {
        loadSwitchService?.showRetry(data)
        return loadSwitchService != null
    }

    fun showDialog() {
        if (isShowLoadding) {
            if (getActivity()?.isFinishing == false) {
                if (loadDialog == null) {
                    loadDialog = LoadDialog(getActivity())
                }
                loadDialog?.run {
                    setContent(hint)
                    setCancelable(isCancelable)
                    try {
                        show()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    fun hintProgress() {
        loadDialog?.dismiss()
    }

    private fun getActivity(): Activity? {
        var activity: Activity? = ActivityUtils.getActivity(context)
        if (activity == null) {
            activity = ActivityUtils.getActivity(baseViewModel?.context)
        }
        return activity
    }

    companion object {

        operator fun get(baseViewModel: BaseViewModel): HttpCallbackHandle {
            val buider = get()
            buider.baseViewModel = baseViewModel
            return buider
        }

        operator fun get(context: Context?): HttpCallbackHandle {
            val buider = get()
            buider.context = context
            return buider
        }

        fun getNoTips(baseViewModel: BaseViewModel): HttpCallbackHandle {
            val buider = get()
            buider.baseViewModel = baseViewModel
            buider.setNoTips()
            return buider
        }

        fun getNoTips(context: Context?): HttpCallbackHandle {
            val buider = get()
            buider.context = context
            buider.setNoTips()
            return buider
        }

        fun getNoTips(tag: Any? = null): HttpCallbackHandle {
            val buider = get(tag)
            buider.setNoTips()
            return buider
        }

        fun get(tag: Any? = null): HttpCallbackHandle {
            val buider = HttpCallbackHandle()
            buider.tag = tag
            return buider
        }
    }
}