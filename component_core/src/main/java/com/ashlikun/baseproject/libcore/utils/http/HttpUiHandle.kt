package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.dialog.LoadBackDialog
import com.ashlikun.baseproject.libcore.dialog.LoadBackView
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.core.mvvm.launch
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.customdialog.LoadView
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.OkHttpManage
import com.ashlikun.okhttputils.http.response.HttpErrorCode
import com.ashlikun.okhttputils.http.response.IHttpResponse
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.coroutines.taskLaunchMain
import com.ashlikun.utils.ui.extend.toLifecycleOrNull
import com.ashlikun.utils.ui.modal.SuperToast
import com.ashlikun.xrecycleview.PageHelpListener
import com.ashlikun.xrecycleview.RefreshLayout
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

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
    /**
     * tag类,标识这个请求，会传递到Request里面可以是 [BaseViewModel] [tag]优先级高
     */
    internal var tag: Any? = null
        //这里不要 使用context 防止死循环
        get() = field ?: mContext
    internal var mContext: Context? = null

    /**
     * 开始超时,防止对话框不会消失
     */
    internal var jobTimeOut: Job? = null

    val activity by lazy {
        ActivityUtils.getActivity(context)
    }

    /**
     * Dialog 上面显示对话框
     */
    val dialog by lazy {
        tag as? Dialog
    }
    val context by lazy {
        mContext
            ?: if (tag is BaseViewModel) (tag as BaseViewModel).context else if (tag is Dialog) (tag as Dialog).context else if (tag is Activity) tag as Context else null
    }

    /**
     * 是否接口请求完成了,请求的状态
     */
    var isStatusCompleted = true

    /**
     * 对话框提示的文本  空就不显示对话框
     */
    var hint: String? = null

    /**
     * 失效的View
     */
    var enableView: Array<out View?>? = null

    /**
     * 对话框是否可以取消
     */
    var isCancelable = true

    /**
     * 触摸取消
     */
    var isCanceledOnTouchOutside = false

    /**
     * 是否错误的时候toast提示,http 错误,代码错误
     */
    var isErrorToastShow = true

    /**
     * 请求成功，但是接口Code错误的时候
     * json code 非success 是否显示toast
     */
    var isDataCodeErrorToastShow = true

    /**
     * 请求成功，但是接口Code错误的时候是否内部处理错误状态
     * 默认为走错误方法
     * 一般用于布局切换和显示toast
     */
    var isAutoHanderError: Boolean = true

    /**
     * 是否显示进度，进度回调才用到的
     */
    var isShowProgress = true

    /**
     * 是否显示对话框
     */
    var isShowLoadding = true

    /**
     * 加载是否以对话框形式
     * 默认 View
     */
    var isDialogMode = false

    /**
     * 下拉刷新
     */
    var swipeRefreshLayout: RefreshLayout? = null

    /**
     * 分页助手
     */
    var pageHelpListener: PageHelpListener? = null

    /**
     * 界面显示管理器（加载中，加载失败，加载成功）
     */
    var switchService: LoadSwitchService? = null

    /**
     * 加载框
     */
    var loadView: LoadView? = null
    var loadDialog: LoadDialog? = null

    /**
     * LoadView 附属在那个页面上，默认Activity
     */
    var loadTarget: View? = null

    /**
     * 加载对话框被取消
     */
    var onLoadCancel: (() -> Unit)? = null

    /**
     * 在当前页面是否第一次请求
     * 如果这个请求集合大于1，说明当前页面还有请求，
     */
    fun isFirstRequest() = count() <= 1

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Int = tag?.let { OkHttpManage.countRequest(it) } ?: 0

    /**
     * 设置View的使能
     */
    fun goSetEnableView(enable: Boolean) {
        enableView?.forEach { it?.isEnabled = enable }
    }

    /**
     * 再次请求
     */
    fun setLoadSwitchLoadingCanShow(): HttpUiHandle {
        switchService?.isLoadingCanShow = true
        return this
    }

    /**
     * 设置默默调用，不提示
     */
    fun setNoTips(): HttpUiHandle {
        isShowLoadding = false
        isErrorToastShow = false
        return this
    }

    /**
     * 设置加载框样式,只能在start之前调用
     */
    fun setLoadDialogStyle(): HttpUiHandle {
        if (isDialogMode) {
            if (loadDialog == null && canShowLoadding()) {
                loadDialog = LoadBackDialog(context!!)
            }
        } else {
            if (loadView == null && canShowLoadding()) {
                loadView = LoadBackView(context!!)
            }
        }
        return this
    }

    /**
     * 设置下位刷新，底加载，布局切换
     */
    fun setLoadingStatus(tag: BaseViewModel): HttpUiHandle {
        if (tag is BaseListViewModel) {
            tag?.also {
                if (tag.swipeRefreshLayout != null) {
                    isShowLoadding = false
                    swipeRefreshLayout = tag.swipeRefreshLayout
                }
                if (tag.pageHelpListener != null) {
                    isShowLoadding = false
                    pageHelpListener = tag.pageHelpListener
                }
            }
        }
        //布局切换
        if (tag is BaseViewModel) switchService = tag?.loadSwitchService
        return this
    }


    private fun showLoading() {
        switchService?.showLoading(ContextData(title = hint.orEmpty()))
    }

    private fun showContent() {
        switchService?.showContent()
    }

    private fun showRetry(data: ContextData): Boolean {
        switchService?.showRetry(data)
        return switchService != null
    }

    /**
     * 是否可以显示加载相关的
     */
    private fun canShowLoadding() = activity?.isFinishing == false || dialog?.isShowing == true

    /**
     * 显示对话框
     */
    private fun showDialog() {
        if (isShowLoadding) {
            if (canShowLoadding()) {
                if (isDialogMode) {
                    if (loadDialog == null && context != null) {
                        loadDialog = LoadDialog(context!!)
                    }
                    loadDialog?.run {
                        setContent(hint)
                        setCancelable(isCancelable)
                        setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                        if (onLoadCancel != null) {
                            setOnCancelListener {
                                onLoadCancel?.invoke()
                            }
                        } else setOnCancelListener(null)
                        try {
                            show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    if (loadView == null && context != null) {
                        loadView = LoadView(context!!)
                        if (loadTarget != null) {
                            loadView!!.attachedView = loadTarget!!
                        }
                    }
                    loadView?.run {
                        setContent(hint)
                        this.isCancelable = this@HttpUiHandle.isCancelable
                        this.isCanceledOnTouchOutside = this@HttpUiHandle.isCanceledOnTouchOutside
                        onCancelListener = onLoadCancel
                        try {
                            show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    /**
     * 隐藏对话框
     */
    private fun hintProgress() {
        //如果不显示对话框,就直接返回
        if (!isShowLoadding || (loadDialog == null && loadView == null)) {
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
        jobTimeOut?.cancel()
        jobTimeOut = null
        loadDialog?.dismiss()
        loadView?.dismiss()
    }


    /**
     * 开始执行
     */
    fun start() {
        MainHandle.post {
            isStatusCompleted = false
            goSetEnableView(false)
            if (swipeRefreshLayout?.isRefreshing() == true) {
                if (switchService?.isStatusContent == false) {
                    //布局切换没有归为
                    showLoading()
                }
                return@post
            }
            if (switchService?.isLoadingCanShow == true) {
                showLoading()
            } else {
                showDialog()
            }
        }
    }

    /**
     * 接口成功回调
     * 失败会自动提示
     */
    fun success(result: Any) {
        pageHelpListener?.complete()
        if (result is IHttpResponse && isAutoHanderError) {
            if (result.isSucceed) {
                showContent()
            } else {
                //请求成功，但是接口Code错误
                error(ContextData(title = result.message, errCode = result.code), isDataCodeErrorToastShow)
            }
        } else {
            showContent()
        }
    }

    /**
     * 接口错误，
     * @param showToast 是否显示toast，无论isErrorToastShow 是什么,true 就显示
     */
    fun error(data: ContextData, showToast: Boolean? = null) {
//        val message = "${data.title}"
        val message = "${data.title} (${data.errCode})"
        var isShowToastNeibu = isErrorToastShow
        if (pageHelpListener != null) {
            showContent()
            pageHelpListener?.failure(message)
        }
        if (showToast == true) {
            isShowToastNeibu = true
        }
        //这里判断是否需要显示错误页面
        if (pageHelpListener?.itemCount ?: 0 == 0) {
            isShowToastNeibu = !showRetry(data) && isShowToastNeibu
        }
        if (isShowToastNeibu && showToast != false) {
            SuperToast.showErrorMessage(message)
        }
    }

    fun completed() {
        MainHandle.post {
            jobTimeOut?.cancel()
            jobTimeOut = null
            goSetEnableView(true)
            dismissUi()
            isStatusCompleted = true
        }
    }

    /**
     * 调用success和completed
     */
    fun completedSuccess() {
        MainHandle.post {
            success(Any())
            completed()
        }
    }

    fun dismissUi() {
        MainHandle.post {
            swipeRefreshLayout?.setRefreshing(false)
            hintProgress()
        }
    }

    fun isShow() = loadDialog?.isShowing ?: loadView?.isShowing ?: false

    /**
     * 开始超时,防止对话框不会消失
     */
    fun startTimeOut(time: Int = 3000) {
        if (!isShowLoadding) return
        jobTimeOut?.cancel()
        jobTimeOut = activity?.toLifecycleOrNull()?.launch {
            delay(time.toLong())
            completedSuccess()
        }
    }

    companion object {

        operator fun get(baseViewModel: BaseViewModel): HttpUiHandle {
            val buider = get()
            buider.tag = baseViewModel
            return buider
        }

        operator fun get(context: Context?): HttpUiHandle {
            val buider = get()
            buider.mContext = context
            return buider
        }

        operator fun get(dialog: Dialog?): HttpUiHandle {
            val buider = get()
            buider.tag = dialog
            //附属到对话框上
            buider.loadTarget = dialog?.window?.decorView as ViewGroup
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
            buider.mContext = context
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

val HttpUiHandle.coroutineExceptionHandler
    get() = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
        when (error) {
            is HttpHandelResultException -> {
                //不显示toast
                //如果code全局处理的时候错误了，那么是不会走success的，这里就得自己处理UI设置为错误状态
                error(ContextData(title = error.exception.message, resId = R.drawable.material_service_error, errCode = error.exception.code), false)
            }

            is HttpException -> error(ContextData(title = error.message, resId = R.drawable.material_service_error, errCode = error.code))
            else -> error(
                ContextData(
                    title = error.message.orEmpty(), resId = R.drawable.material_service_error, errCode = HttpErrorCode.HTTP_UNKNOWN
                )
            )
        }
        completed()
    }
