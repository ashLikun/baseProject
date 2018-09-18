package com.ashlikun.baseproject.libcore.utils.http;

import android.view.View;

import com.ashlikun.baseproject.libcore.R;
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseListView;
import com.ashlikun.baseproject.libcore.mvp.iview.IBaseSwipeView;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.core.iview.IBaseView;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.okhttputils.http.HttpException;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.okhttputils.http.callback.AbsCallback;
import com.ashlikun.okhttputils.http.response.HttpResponse;
import com.ashlikun.utils.AppUtils;
import com.ashlikun.utils.other.LogUtils;
import com.ashlikun.utils.other.MainHandle;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.StatusChangListener;

import java.lang.ref.WeakReference;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:12
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：http请求的回调
 */

public abstract class HttpCallBack<ResultType> extends AbsCallback<ResultType> {
    private final String ERROR_MSG_FORMAT = "%s(错误码:%d)";
    protected String hint = null;//对话框提示的文本  空就不显示对话框

    protected View[] enableView; // 失效的View
    protected boolean isCancelable = false;//对话框是否可以取消
    protected boolean isShowLoadding = true;//是否显示对话框
    //下拉刷新
    protected RefreshLayout swipeRefreshLayout;
    //自动加载刷新
    protected StatusChangListener statusChangListener;
    //界面显示管理器（加载中，加载失败，加载成功）
    private LoadSwitchService loadSwitchService;

    protected BasePresenter basePresenter;
    protected BaseActivity baseActivity;

    public HttpCallBack() {
        this(Buider.get());
    }

    public HttpCallBack(Buider buider) {
        hint = buider.hint;
        enableView = buider.enableView;
        isCancelable = buider.isCancelable;
        swipeRefreshLayout = buider.swipeRefreshLayout;
        statusChangListener = buider.statusChangListener;
        loadSwitchService = buider.loadSwitchService;
        basePresenter = buider.basePresenter;
        isShowLoadding = buider.isShowLoadding;
        baseActivity = buider.baseActivity;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:40
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：请求开始
     */
    @Override
    public void onStart() {
        setEnableView(false);
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            return;
        }
        if (loadSwitchService != null && loadSwitchService.isLoadingCanShow()) {
            loadSwitchService.showLoading(new ContextData(hint));
        } else if (isShowLoadding && basePresenter != null && basePresenter.mvpView != null) {
            basePresenter.mvpView.showProgress(hint, isCancelable);
        } else if (isShowLoadding && baseActivity != null) {
            baseActivity.showProgress(hint, isCancelable);
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：请求完成
     */
    @Override
    public void onCompleted() {
        LogUtils.e("onCompleted");
        setEnableView(true);
        dismissUi();
    }

    /**
     * 销毁页面的状态
     */
    public void dismissUi() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        boolean isNoRun = (basePresenter == null || basePresenter.mvpView == null) && baseActivity == null;
        if (isNoRun) {
            return;
        }
        //如果count > 0，说明当前页面还有请求，就不销毁对话框
        if (count() > 0) {
            //是否等待,Okhttp内部在子线程里面执行的，这里延时1秒检测
            //可能会一直占用内存不释放，所以这里false
            MainHandle.get().postDelayed(() -> {
                if (count() <= 0) {
                    dismissDialog();
                }
            }, 1000);
            return;
        }
        dismissDialog();
    }

    /**
     * 销毁对话框
     */
    public void dismissDialog() {
        if (basePresenter != null && basePresenter.mvpView != null) {
            basePresenter.mvpView.hintProgress();
        } else if (baseActivity != null) {
            baseActivity.hintProgress();
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     * <p>
     * 方法功能：处理完后的错误数据
     */

    public void onError(ContextData data) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：请求出错
     */
    @Override
    public void onError(HttpException error) {

        LogUtils.wtf(error);
        ContextData data = new ContextData();
        data.setTitle(error.message());
        data.setErrCode(error.code());
        data.setResId(R.drawable.material_service_error);
        if (basePresenter != null && basePresenter.mvpView != null) {
            SuperToast.showErrorMessage(String.format(ERROR_MSG_FORMAT, data.getTitle(), data.getErrCode()));
        } else if (baseActivity != null) {
            SuperToast.showErrorMessage(String.format(ERROR_MSG_FORMAT, data.getTitle(), data.getErrCode()));
        }

        if (statusChangListener != null) {
            statusChangListener.failure();
        }
        if (loadSwitchService != null && isFirstRequest()) {
            loadSwitchService.showRetry(data);
        }
        onError(data);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/15 17:15
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：运行与子线程
     */

    @Override
    public void onSuccessSubThread(ResultType responseBody) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：请求成功
     */
    @Override
    public void onSuccess(ResultType result) {
        onSuccess(result, true);
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
    @Override
    public boolean onSuccessHandelCode(ResultType responseBody) {
        return HttpManager.handelResult(responseBody);
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能： 是否对错误信息处理
     */
    public final void onSuccess(ResultType result, boolean isHanderError) {
        LogUtils.e("onSuccess");
        if (statusChangListener != null) {
            statusChangListener.complete();
        }
        if (loadSwitchService != null && isFirstRequest()) {
            if (result instanceof HttpResponse && isHanderError) {
                if (((HttpResponse) result).isSucceed()) {
                    loadSwitchService.showContent();
                } else {
                    loadSwitchService.showRetry(new ContextData(((HttpResponse) result).getCode(), ((HttpResponse) result).getMessage()));
                }
            } else {
                loadSwitchService.showContent();
            }
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     * <p>
     * 方法功能：设置View的使能
     */
    private void setEnableView(boolean enable) {
        if (enableView != null) {
            for (View view : enableView) {
                view.setEnabled(enable);
            }
        }
    }

    /**
     * 在当前页面是否第一次请求
     *
     * @return
     */
    public boolean isFirstRequest() {

        //如果这个请求集合大于1，说明当前页面还有请求，
        if (count() > 1) {
            return false;
        }
        return true;
    }

    public static class Buider {
        private String hint = null;

        private View[] enableView;
        private boolean isCancelable = false;
        private boolean isShowLoadding = true;
        //下拉刷新
        private RefreshLayout swipeRefreshLayout;
        //自动加载刷新
        private StatusChangListener statusChangListener;
        //界面显示管理器（加载中，加载失败，加载成功）
        private LoadSwitchService loadSwitchService;
        //进度查看才会用
        protected boolean isShowProgress = true;
        private BasePresenter basePresenter;
        private BaseActivity baseActivity;

        private Buider() {
        }

        public static Buider get(BasePresenter basePresenter) {
            Buider buider = get();
            buider.basePresenter = basePresenter;
            return buider;
        }

        public static Buider get(BaseActivity baseActivity) {
            Buider buider = get();
            buider.baseActivity = baseActivity;
            return buider;
        }

        public static Buider get() {
            return new Buider();
        }


        public Buider setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Buider setHint() {
            this.hint = AppUtils.getApp().getResources().getString(R.string.loadding);
            return this;
        }

        public Buider setEnableView(View... view) {
            this.enableView = view;
            return this;
        }

        public Buider setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        /**
         * 作者　　: 李坤
         * 创建时间: 2017/7/3 13:45
         * 邮箱　　：496546144@qq.com
         * <p>
         * 方法功能：下拉刷新的监听
         */
        public Buider setSwipeRefreshLayout(RefreshLayout swipeRefreshLayout) {
            this.swipeRefreshLayout = swipeRefreshLayout;
            return this;
        }

        /**
         * 作者　　: 李坤
         * 创建时间: 2017/7/3 13:44
         * 邮箱　　：496546144@qq.com
         * <p>
         * 方法功能：状态改加载监听
         */
        public Buider setStatusChangListener(StatusChangListener statusChangListener) {
            this.statusChangListener = statusChangListener;
            return this;
        }

        public Buider setBasePresenter(BasePresenter basePresenter) {
            this.basePresenter = basePresenter;
            return this;
        }

        public Buider setBaseActivity(BaseActivity baseActivity) {
            this.baseActivity = baseActivity;
            return this;
        }

        /**
         * 作者　　: 李坤
         * 创建时间: 2017/7/3 13:44
         * 邮箱　　：496546144@qq.com
         * <p>
         * 方法功能：重新加载的监听
         */
        public Buider setLoadSwitchService(LoadSwitchService loadSwitchService) {
            this.loadSwitchService = loadSwitchService;
            return this;
        }

        /**
         * 作者　　: 李坤
         * 创建时间: 2017/7/3 13:44
         * 邮箱　　：496546144@qq.com
         * <p>
         * 方法功能：显示加载的监听
         */
        public Buider setShowLoadding(boolean showLoadding) {
            isShowLoadding = showLoadding;
            return this;
        }

        public Buider setShowProgress(boolean showProgress) {
            isShowProgress = showProgress;
            return this;
        }

        /**
         * 作者　　: 李坤
         * 创建时间: 2017/7/3 13:44
         * 邮箱　　：496546144@qq.com
         * <p>
         * 方法功能：设置下位刷新，底加载，状态显示
         */
        public Buider setLoadingStatus(IBaseView mvpView) {
            if (mvpView == null) {
                return this;
            }
            if (mvpView instanceof IBaseSwipeView) {
                setSwipeRefreshLayout(((IBaseSwipeView) mvpView).getSwipeRefreshLayout());
            }
            if (mvpView instanceof IBaseListView) {
                setStatusChangListener(((IBaseListView) mvpView).getStatusChangListener());
            }
            setLoadSwitchService(mvpView.getSwitchService());
            return this;
        }
    }

    public long count() {
        return OkHttpUtils.getInstance().countRequest(baseActivity, basePresenter);
    }

    public Object getTag() {
        return baseActivity == null ? basePresenter : baseActivity;
    }

    /**
     * 请求数目可能不对Okhttp内部在子线程里面执行的，这里延时1秒检测,销毁对话框
     */
    private static class MyRunnable implements Runnable {
        WeakReference<HttpCallBack> reference;

        public MyRunnable(HttpCallBack httpCallBack) {
            this.reference = new WeakReference<>(httpCallBack);
        }

        @Override
        public void run() {
            if (reference.get() != null) {
                reference.get().dismissUi();
            }
        }
    }
}
