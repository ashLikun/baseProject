package com.ashlikun.baseproject.libcore.utils.http

import android.view.View

import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseListView
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseSwipeView
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.iview.IBaseView
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.callback.AbsCallback
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.StatusChangListener

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:12
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：http请求的回调
 */

abstract class HttpCallBack<ResultType> constructor(buider: Buider = Buider.get()) : AbsCallback<ResultType>() {
    private val ERROR_MSG_FORMAT = "%s(错误码:%d)"
    /**
     * 对话框提示的文本  空就不显示对话框
     */
    protected val hint: String?

    /**
     * 失效的View
     */
    protected val enableView: Array<out View>?
    /**
     * 对话框是否可以取消
     */
    protected val isCancelable: Boolean
    /**
     * 是否显示对话框
     */
    protected val isShowLoadding: Boolean
    /**
     * 下拉刷新
     */
    protected val swipeRefreshLayout: RefreshLayout?
    /**
     * 自动加载刷新
     */
    protected val statusChangListener: StatusChangListener?
    /**
     * 界面显示管理器（加载中，加载失败，加载成功）
     */
    private val loadSwitchService: LoadSwitchService?

    /**
     * tag类
     */
    protected val basePresenter: BasePresenter<*>?
    protected val baseActivity: BaseActivity?

    /**
     * 在当前页面是否第一次请求
     * 如果这个请求集合大于1，说明当前页面还有请求，
     * @return
     */
    fun isFirstRequest() = count() <= 1

    fun getTag() = if (baseActivity == null) basePresenter else baseActivity

    init {
        hint = buider.hint
        enableView = buider.enableView
        isCancelable = buider.isCancelable
        swipeRefreshLayout = buider.swipeRefreshLayout
        statusChangListener = buider.statusChangListener
        loadSwitchService = buider.loadSwitchService
        basePresenter = buider.basePresenter
        isShowLoadding = buider.isShowLoadding
        baseActivity = buider.baseActivity
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:40
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求开始
     */
    override fun onStart() {
        setEnableView(false)
        if (swipeRefreshLayout?.isRefreshing == true) {
            return
        }
        if (loadSwitchService?.isLoadingCanShow == true) {
            loadSwitchService.showLoading(ContextData(hint))
        } else if (isShowLoadding) {
            if (baseActivity != null) {
                baseActivity.showProgress(hint, isCancelable)
            } else
                basePresenter?.view?.showProgress(hint, isCancelable)
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求完成
     */
    override fun onCompleted() {
        LogUtils.e("onCompleted")
        setEnableView(true)
        dismissUi()
    }

    /**
     * 销毁页面的状态
     */
    open fun dismissUi() {
        swipeRefreshLayout?.isRefreshing = false
        val isNoRun = basePresenter?.view == null && baseActivity == null
        if (isNoRun) {
            return
        }
        //如果count > 0，说明当前页面还有请求，就不销毁对话框
        if (count() > 0) {
            //是否等待,Okhttp内部在子线程里面执行的，这里延时1秒检测
            //可能会一直占用内存不释放，所以这里false
            MainHandle.get().postDelayed({
                if (count() <= 0) {
                    dismissDialog()
                }
            }, 1000)
            return
        }
        dismissDialog()
    }

    /**
     * 销毁对话框
     */
    fun dismissDialog() {
        if (baseActivity != null) {
            baseActivity.hintProgress()
        } else {
            basePresenter?.view?.hintProgress()
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     *
     * 方法功能：处理完后的错误数据
     */

    fun onError(data: ContextData) {
        if (data.title != null) {
            LogUtils.e(data.title)
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求出错
     */
    override fun onError(error: HttpException) {
        LogUtils.wtf(error)
        val data = ContextData()
        with(error) {
            data.title = message()
            data.errCode = code()
        }
        data.resId = R.drawable.material_service_error
        if (baseActivity != null) {
            SuperToast.showErrorMessage("$data.title(错误码:)${data.errCode}")
        } else if (basePresenter?.view != null) {
            SuperToast.showErrorMessage("$data.title(错误码:)${data.errCode}")
        }
        statusChangListener?.failure()
        if (loadSwitchService != null && isFirstRequest()) {
            loadSwitchService.showRetry(data)
        }
        onError(data)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/15 17:15
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：运行与子线程
     */

    override fun onSuccessSubThread(responseBody: ResultType) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：请求成功
     */
    override fun onSuccess(result: ResultType) {
        onSuccess(result, true)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/6 14:17
     * 邮箱　　：496546144@qq.com
     * 方法功能：接口请求成功了，但是处理code
     *
     * @return true:没问题
     * false:有问题
     */
    override fun onSuccessHandelCode(responseBody: ResultType): Boolean {
        val res = HttpManager.handelResult<ResultType>(responseBody)
        if (!res) {
            //如果code全局处理的时候错误了，那么是不会走success的，这里就得自己处理UI设置为错误状态
            onSuccess(responseBody, true)
        }
        return res
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能： 是否对错误信息处理
     */
    fun onSuccess(result: ResultType, isHanderError: Boolean) {
        LogUtils.e("onSuccess")
        statusChangListener?.complete()
        loadSwitchService?.let {
            if (result is HttpResponse && isHanderError) {
                if (result.isSucceed) {
                    loadSwitchService.showContent()
                } else {
                    loadSwitchService.showRetry(ContextData(result.getCode(), result.getMessage()))
                }
            } else {
                loadSwitchService.showContent()
            }
        }
    }

    /**
     * 统计同一个tag的请求数量
     */
    fun count(): Long {
        return OkHttpUtils.getInstance().countRequest(baseActivity, basePresenter)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     *
     * 方法功能：设置View的使能
     */
    private fun setEnableView(enable: Boolean) {
        enableView?.forEach { it.isEnabled = enable }
    }

    class Buider private constructor() {
        internal var hint: String? = null

        internal var enableView: Array<out View>? = null
        internal var isCancelable = true
        internal var isShowLoadding = true
        //下拉刷新
        internal var swipeRefreshLayout: RefreshLayout? = null
        //自动加载刷新
        internal var statusChangListener: StatusChangListener? = null
        //界面显示管理器（加载中，加载失败，加载成功）
        internal var loadSwitchService: LoadSwitchService? = null
        //进度查看才会用
        internal var isShowProgress = true

        internal var basePresenter: BasePresenter<*>? = null
        internal var baseActivity: BaseActivity? = null


        fun setHint(hint: String): Buider {
            this.hint = hint
            return this
        }

        fun setHint(): Buider {
            this.hint = AppUtils.getApp().resources.getString(R.string.loadding)
            return this
        }

        fun setEnableView(vararg view: View): Buider {
            this.enableView = view
            return this
        }

        fun setCancelable(cancelable: Boolean): Buider {
            isCancelable = cancelable
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
        fun setSwipeRefreshLayout(swipeRefreshLayout: RefreshLayout): Buider {
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
        fun setStatusChangListener(statusChangListener: StatusChangListener): Buider {
            this.statusChangListener = statusChangListener
            return this
        }

        fun setBasePresenter(basePresenter: BasePresenter<*>): Buider {
            this.basePresenter = basePresenter
            return this
        }

        fun setBaseActivity(baseActivity: BaseActivity): Buider {
            this.baseActivity = baseActivity
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
        fun setLoadSwitchService(loadSwitchService: LoadSwitchService): Buider {
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
        fun setShowLoadding(showLoadding: Boolean): Buider {
            isShowLoadding = showLoadding
            return this
        }

        fun setShowProgress(showProgress: Boolean): Buider {
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
        fun setLoadingStatus(mvpView: IBaseView?): Buider {
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

        companion object {

            operator fun get(basePresenter: BasePresenter<*>): Buider {
                val buider = get()
                buider.basePresenter = basePresenter
                return buider
            }

            operator fun get(baseActivity: BaseActivity): Buider {
                val buider = get()
                buider.baseActivity = baseActivity
                return buider
            }

            fun get(): Buider {
                return Buider()
            }
        }
    }


}
