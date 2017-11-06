package com.doludolu.baseproject.code.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.MyOnLoadLayoutListener;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.utils.ui.UiUtils;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.activity.BaseActivity;
import com.doludolu.baseproject.code.iview.IActivityAndFragment;
import com.hbung.supertoobar.SupperToolBar;


/**
 * Created by Administrator on 2016/8/13.
 */

public abstract class BaseFragment extends Fragment implements IActivityAndFragment {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */

    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：宿主activity
     */


    protected BaseActivity activity;
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：布局
     */

    protected View view;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    protected LoadSwitchService loadSwitchService = null;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：是否是回收利用的Fragment
     */
    protected boolean isRecycle = false;
    @Nullable
    protected SupperToolBar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            isRecycle = false;
            view = UiUtils.getInflaterView(activity, getLayoutId());
            UiUtils.applyFont(view);
            toolbar = (SupperToolBar) view.findViewById(R.id.toolbar);
            loadSwitchService = LoadSwitchService.generate(getSwitchRoot(), new MyOnLoadLayoutListener(getContext(), getOnLoadSwitchClick()));
        } else {
            isRecycle = true;
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            baseInitView();
            initView();
        }
    }

    protected abstract void baseInitView();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    public abstract int getLayoutId();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    @Override
    public abstract void initView();


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    @Override
    public OnLoadSwitchClick getOnLoadSwitchClick() {
        if (this instanceof OnLoadSwitchClick) {
            return (OnLoadSwitchClick) this;
        } else {
            return null;
        }
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取需要转化为{@link LoadSwitchService}的控件
     */
    @Override
    public View getSwitchRoot() {
        return view.findViewById(R.id.switchRoot);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取页面切换的布局管理器
     */
    @Override
    public LoadSwitchService getLoadSwitchService() {
        return loadSwitchService;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:09
     * <p>
     * 方法功能：是否登录
     *
     * @param isToLogin : 是否跳转到登录界面
     */

    @Override
    public boolean isLogin(boolean isToLogin) {
        return activity.isLogin(isToLogin);
    }

    /**
     * 显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showDialog(String msg, boolean isCancelable) {
        activity.showDialog(msg, isCancelable);
    }

    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    public void showDialog() {
        showDialog(null);
    }

    public void dismissDialog() {
        activity.dismissDialog();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorMessage(String result) {
        activity.showErrorMessage(result);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningMessage(String result) {
        activity.showWarningMessage(result);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showInfoMessage(String result) {
        activity.showInfoMessage(result);
    }

    public void showInfoMessage(String result, boolean isFinish) {
        activity.showInfoMessage(result, isFinish);
    }


    public void finish() {
        activity.finish();
    }


}
