package com.doludolu.baseproject.code;

import com.ashlikun.okhttputils.http.ExecuteCall;
import com.doludolu.baseproject.code.iview.BaseView;

import java.util.HashSet;
import java.util.Set;


/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BasePresenter<T extends BaseView> {
    public T mvpView;
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * 方法功能：http请求执行后的集合   方便销毁
     */
    private Set<ExecuteCall> mHttpCalls;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能：当P创建的时候 把view也创建
     */

    public void onCreate(T mvpView) {
        this.mvpView = mvpView;
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/5/27 10:40
     * <p>
     * 方法功能：每调用一个请求添加
     */

    public void addHttpCall(ExecuteCall s) {
        if (this.mHttpCalls == null) {
            this.mHttpCalls = new HashSet<>();
        }
        this.mHttpCalls.add(s);
    }


    public void onStart() {
    }

    public void onResume() {
    }


    public void onPause() {
    }

    public void onStop() {
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能：销毁
     */
    public void onDestroy() {
        cancelAllHttp();
        mvpView = null;
    }
    /**
     * 销毁网络访问
     */
    private void cancelAllHttp() {
        if (mHttpCalls != null) {
            for (ExecuteCall call : mHttpCalls) {
                if (!call.isCompleted() && !call.isCanceled()) {
                    call.cancel();
                }
            }
            mHttpCalls.clear();
        }
    }


}
