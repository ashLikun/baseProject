package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.content.Context
import android.view.View
import com.ashlikun.baseproject.libcore.dialog.LoadTransDialog
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.coroutines.taskLaunch
import com.ashlikun.utils.other.coroutines.taskLaunchMain
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.StatusChangListener
import kotlinx.coroutines.delay

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
     * 是否错误的时候toast提示,只有Http错误的时候
     */
    var isErrorToastShow = true
        internal set(value) {
            field = value
        }

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

    //自动处理  Code(接口是成功的)错误，布局切换
    internal var isAutoHanderError: Boolean = true

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
    var loadDialog: LoadDialog? = null

    /**
     * 在当前页面是否第一次请求
     * 如果这个请求集合大于1，说明当前页面还有请求，
     * @return
     */
    fun isFirstRequest() = count() <= 1

    fun getTag() = tag ?: (context ?: baseViewModel)
    fun setTag(tag: Any): HttpCallbackHandle {
        this.tag = tag
        return this
    }

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Long {
        return OkHttpUtils.getInstance().countRequest(tag, context, baseViewModel)
    }

    /**
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
        this.isErrorToastShow = isToastShow
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

    /**
     * 自动处理  Code(接口是成功的)错误，布局切换
     */
    fun setAutoHanderError(autoHanderError: Boolean): HttpCallbackHandle {
        isAutoHanderError = autoHanderError
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
            baseViewModel?.run {
                if (baseViewModel.swipeRefreshLayout != null) {
                    isShowLoadding = false
                    setSwipeRefreshLayout(baseViewModel.swipeRefreshLayout)
                }
                if (baseViewModel.statusChangListener != null) {
                    isShowLoadding = false
                    setStatusChangListener(baseViewModel.statusChangListener)
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
                    loadDialog = LoadTransDialog(getActivity()!!)
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
        //如果不显示对话框,就直接返回
        if (!isShowLoadding || loadDialog == null) {
            return
        }
        //如果count > 0，说明当前页面还有请求，就不销毁对话框
        if (count() > 0) {
            //是否等待,Okhttp内部在子线程里面执行的，这里延时1秒检测
            //可能会一直占用内存不释放，所以这里false
            taskLaunchMain(delayTime = 1000) {
                hintProgress()
            }
            return
        }
        loadDialog?.dismiss()
    }

    fun getActivity(): Activity? {
        return ActivityUtils.getActivity(getContext())
    }

    fun getContext(): Context? {
        return context ?: baseViewModel?.context
    }

    /**
     * http错误
     */
    fun error(data: ContextData) {
        var isShowToastNeibu = isErrorToastShow
        statusChangListener?.failure()
        isShowToastNeibu = isShowToastNeibu && !showRetry(data)
        if (isShowToastNeibu) {
            SuperToast.showErrorMessage("${data.title}(错误码:${data.errCode})")
        }
    }

    /**
     * 接口错误  code
     */
    fun successError(data: ContextData) {
        if (statusChangListener != null) {
            showContent()
            statusChangListener?.failure()
        }
        if (statusChangListener?.itemCount ?: 0 == 0) {
            !showRetry(data)
        }
    }

    fun dismissUi() {
        swipeRefreshLayout?.isRefreshing = false
        hintProgress()
    }

    fun start() {
        goSetEnableView(false)
        if (swipeRefreshLayout?.isRefreshing == true) {
            return
        }
        if (loadSwitchService?.isLoadingCanShow == true) {
            showLoading()
        } else {
            showDialog()
        }
    }

    fun completed() {
        goSetEnableView(true)
        dismissUi()
    }

    fun success(result: Any) {
        statusChangListener?.complete()
        if (result is HttpResponse && isAutoHanderError) {
            if (result.isSucceed) {
                showContent()
            } else {
                successError(ContextData(result.getCode(), result.getMessage()))
            }
        } else {
            showContent()
        }
    }

    fun setLoadDialogStyle(): HttpCallbackHandle {
        if (getActivity()?.isFinishing == false) {
            if (loadDialog == null) {
                loadDialog = LoadDialog(getActivity()!!)
            }
        }
        return this
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