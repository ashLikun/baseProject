package com.ashlikun.baseproject.libcore.utils.http;

import android.os.Build;

import com.ashlikun.core.iview.IProgressView;
import com.ashlikun.okhttputils.http.callback.ProgressCallBack;
import com.ashlikun.utils.other.LogUtils;

public abstract class HttpProgressCallBack<ResultType> extends HttpCallBack<ResultType> implements ProgressCallBack {
    //下载或者上传 回调的频率  ms
    public long rate = 500;
    protected boolean isShowProgress = true;

    public HttpProgressCallBack(Buider buider) {
        super(buider);
        isShowProgress = buider.isShowProgress;
    }

    public void onStart(boolean isCompress) {
        LogUtils.e("onStart2");
        super.onStart();

        onLoading(0, 100, false, true, isCompress);
    }

    @Override
    public void onStart() {
        onStart(false);
        LogUtils.e("onStart");
    }

    @Override
    public long getRate() {
        return rate;
    }


    @Override
    public void dismissUi() {
        super.dismissUi();
        if (isShowProgress && basePresenter != null && basePresenter.getView() != null
                && basePresenter.getView() instanceof IProgressView) {
            ((IProgressView) basePresenter.getView()).dismissProgressDialog();
        }
        LogUtils.e("dismissUi");
    }

    @Override
    public void onLoading(long progress, long total, boolean done, boolean isUpdate) {
        LogUtils.e("onLoading");

        onLoading(progress, total, done, isUpdate, false);
    }


    public void onLoading(long progress, long total, boolean done, boolean isUpdate, boolean isCompress) {
        if (done) {
            dismissUi();
            return;
        }
        LogUtils.e("onLoading");
        if (basePresenter != null && basePresenter.getView() != null
                && basePresenter.getView() instanceof IProgressView) {
            int percentage = (int) (progress * 100.0 / total);
            ((IProgressView) basePresenter.getView()).upLoading(percentage, done, isUpdate, isCompress);
        }
    }
}
