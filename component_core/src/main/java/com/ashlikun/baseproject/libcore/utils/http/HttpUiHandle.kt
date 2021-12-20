package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.content.Context
import android.view.View
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.core.mvvm.launch
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.response.HttpErrorCode
import com.ashlikun.okhttputils.http.response.IHttpResponse
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.other.coroutines.taskLaunchMain
import com.ashlikun.utils.ui.modal.SuperToast
import com.ashlikun.xrecycleview.PageHelpListener
import com.ashlikun.xrecycleview.RefreshLayout
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/25　13:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：请求各种状态处理
 * 1弹窗
 * 2布局切换
 * 3生命周期
 * 4失效view
 *
 * 使用
 * 1：内部自动处理网络请求状态
 * ------协程调用[launch]
 * ------列表 调用[HttpUiHandle.setLoadingStatus]
 * ------如果多个请求顺序执行，如有必要在下一个任务之前调用[HttpUiHandle.setLoadSwitchLoadingCanShow]
 * 2：自己处理请求状态
 * ------协程调用[launch] 传入 [HttpUiHandle.coroutineExceptionHandler]
 * ------启动异步任务 协程 [async]
 * ------必须依次调用 [HttpUiHandle.start] -> 接口请求成功 -> [HttpUiHandle.success] -> 数据处理完成 -> [HttpUiHandle.completed]
 */
class HttpUiHandle private constructor() {
    val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, error ->
            error.printStackTrace()
            when (error) {
                is HttpHandelResultException -> {
                    //不显示toast
                    isErrorToastShow = false
                    //如果code全局处理的时候错误了，那么是不会走success的，这里就得自己处理UI设置为错误状态
                    error(
                        ContextData().setErrCode(error.exception.code)
                            .setTitle(error.exception.message)
                            .setResId(R.drawable.material_service_error)
                    )
                }
                is HttpException -> {
                    error(
                        ContextData().setErrCode(error.code)
                            .setTitle(error.message)
                            .setResId(R.drawable.material_service_error)
                    )
                }
                else -> {
                    error(
                        ContextData().setErrCode(HttpErrorCode.HTTP_UNKNOWN)
                            .setTitle(error.message)
                            .setResId(R.drawable.material_service_error)
                    )
                }
            }
            completed()
        }
    }

    //是否接口请求完成了,请求的状态
    var isStatusCompleted = true

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
     * 分页助手
     */
    internal var pageHelpListener: PageHelpListener? = null

    /**
     * 界面显示管理器（加载中，加载失败，加载成功）
     */
    internal var loadSwitchService: LoadSwitchService? = null

    /**
     * 请求成功，但是接口Code错误的时候是否内部处理错误状态
     * 默认为走错误方法
     */
    internal var isAutoHanderError: Boolean = true

    /**
     * tag类,标识这个请求，会传递到Request里面
     * 可以是 [BaseViewModel]
     * [tag]优先级高
     */
    internal var tag: Any? = null
    internal var context: Context? = null

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

    fun getTag() = tag ?: context
    fun setTag(tag: Any): HttpUiHandle {
        this.tag = tag
        return this
    }

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Int = tag?.run { OkHttpUtils.get().countRequest(this) } ?: 0

    /**
     * 设置View的使能
     */
    fun goSetEnableView(enable: Boolean) {
        enableView?.forEach { it?.isEnabled = enable }
    }


    inline fun set(funs: HttpUiHandle.() -> Unit): HttpUiHandle {
        funs()
        return this
    }


    fun setHint(hint: String = "加载中"): HttpUiHandle {
        this.hint = hint
        return this
    }


    fun setEnableView(vararg view: View?): HttpUiHandle {
        this.enableView = view
        return this
    }

    fun setCancelable(cancelable: Boolean): HttpUiHandle {
        isCancelable = cancelable
        return this
    }

    /**
     * 错误的时候是否显示toast
     */
    fun isToastShow(isToastShow: Boolean): HttpUiHandle {
        this.isErrorToastShow = isToastShow
        return this
    }


    /**
     * 下拉刷新的监听
     */
    fun setSwipeRefreshLayout(swipeRefreshLayout: RefreshLayout?): HttpUiHandle {
        this.swipeRefreshLayout = swipeRefreshLayout
        return this
    }

    /**
     * 分页助手
     */
    fun setPageHelpListener(pageHelpListener: PageHelpListener?): HttpUiHandle {
        this.pageHelpListener = pageHelpListener
        return this
    }


    fun setContext(context: Context): HttpUiHandle {
        this.context = context
        return this
    }

    /**
     * 重新加载的监听
     */
    fun setLoadSwitchService(loadSwitchService: LoadSwitchService?): HttpUiHandle {
        this.loadSwitchService = loadSwitchService
        return this
    }

    /**
     * 再次请求
     */
    fun setLoadSwitchLoadingCanShow(): HttpUiHandle {
        loadSwitchService?.isLoadingCanShow = true
        return this
    }


    /**
     * 显示加载的监听
     */
    fun setShowLoadding(showLoadding: Boolean): HttpUiHandle {
        isShowLoadding = showLoadding
        return this
    }

    /**
     * 自动处理  Code(接口是成功的)错误，布局切换
     */
    fun setAutoHanderError(autoHanderError: Boolean): HttpUiHandle {
        isAutoHanderError = autoHanderError
        return this
    }

    fun setShowProgress(showProgress: Boolean): HttpUiHandle {
        isShowProgress = showProgress
        return this
    }

    /**
     * 设置默默调用，不提示
     */
    fun setNoTips(): HttpUiHandle {
        setShowLoadding(false)
        isToastShow(false)
        return this
    }

    /**
     * 设置加载框样式
     */
    fun setLoadDialogStyle(): HttpUiHandle {
        if (getActivity()?.isFinishing == false) {
            if (loadDialog == null) {
                loadDialog = LoadDialog(getActivity()!!)
            }
        }
        return this
    }

    /**
     * 设置下位刷新，底加载，布局切换
     */
    fun setLoadingStatus(tag: Any? = this.tag): HttpUiHandle {
        if (tag is BaseListViewModel) {
            tag?.run {
                if (tag.swipeRefreshLayout != null) {
                    isShowLoadding = false
                    setSwipeRefreshLayout(tag.swipeRefreshLayout)
                }
                if (tag.pageHelpListener != null) {
                    isShowLoadding = false
                    setPageHelpListener(tag.pageHelpListener)
                }
            }
        }
        //布局切换
        if (tag is BaseViewModel) {
            setLoadSwitchService(tag?.loadSwitchService)
        }
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

    /**
     * 显示对话框
     */
    fun showDialog() {
        if (isShowLoadding) {
            if (getActivity()?.isFinishing == false) {
                if (loadDialog == null) {
                    loadDialog = LoadDialog(getActivity()!!)
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

    /**
     * 隐藏对话框
     */
    fun hintProgress() {
        //如果不显示对话框,就直接返回
        if (!isShowLoadding || loadDialog == null) {
            return
        }
        //如果count > 1，说明当前页面还有请求，就不销毁对话框
        if (count() > 1) {
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
        return context ?: if (tag is BaseViewModel) (tag as BaseViewModel).context else null
    }

    /**
     * 开始执行
     */
    fun start() {
        isStatusCompleted = false
        goSetEnableView(false)
        if (swipeRefreshLayout?.isRefreshing() == true) {
            if (loadSwitchService?.isStatusContent == false) {
                //布局切换没有归为
                showLoading()
            }
            return
        }
        if (loadSwitchService?.isLoadingCanShow == true) {
            showLoading()
        } else {
            showDialog()
        }
    }

    /**
     * 接口成功回调
     */
    fun success(result: Any) {
        pageHelpListener?.complete()
        if (result is IHttpResponse && isAutoHanderError) {
            if (result.isSucceed) {
                showContent()
            } else {
                successCodeError(ContextData(result.code, result.message))
            }
        } else {
            showContent()
        }
    }

    /**
     * 请求成功，但是接口Code错误
     * 默认直接走错误方法
     */
    fun successCodeError(data: ContextData) {
        error(data)
    }

    /**
     * 接口错误，Http的错误
     */
    fun error(data: ContextData) {
        val message = "${data.title}(错误码:${data.errCode})"
        var isShowToastNeibu = isErrorToastShow
        if (pageHelpListener != null) {
            showContent()
            pageHelpListener?.failure(message)
        }
        //这里判断是否需要显示错误页面
        if (pageHelpListener?.itemCount ?: 0 == 0) {
            isShowToastNeibu = !showRetry(data) && isShowToastNeibu
        }
        if (isShowToastNeibu) {
            SuperToast.showErrorMessage(message)
        }
    }

    fun completed() {
        goSetEnableView(true)
        dismissUi()
        isStatusCompleted = true
    }

    fun dismissUi() {
        swipeRefreshLayout?.setRefreshing(false)
        hintProgress()
    }


    companion object {

        operator fun get(baseViewModel: BaseViewModel): HttpUiHandle {
            val buider = get()
            buider.tag = baseViewModel
            return buider
        }

        operator fun get(context: Context?): HttpUiHandle {
            val buider = get()
            buider.context = context
            return buider
        }

        fun getNoTips(baseViewModel: BaseViewModel): HttpUiHandle {
            val buider = get()
            buider.tag = baseViewModel
            buider.setNoTips()
            return buider
        }

        fun getNoTips(context: Context?): HttpUiHandle {
            val buider = get()
            buider.context = context
            buider.setNoTips()
            return buider
        }

        fun getNoTips(tag: Any? = null): HttpUiHandle {
            val buider = get(tag)
            buider.setNoTips()
            return buider
        }

        fun get(tag: Any? = null): HttpUiHandle {
            val buider = HttpUiHandle()
            buider.tag = tag
            return buider
        }
    }
}