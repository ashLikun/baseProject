package com.doludolu.baseproject.code.iview;

import android.content.Context;

import com.ashlikun.loadswitch.LoadSwitchService;

/**
 * 作者　　: 李坤
 * 创建时间:  2016/8/8 15:38
 * 邮箱　　：496546144@qq.com
 * <p>
 * 方法功能：
 */
public interface BaseView {
    Context getContext();

    /**
     * 显示对话框，用于网络请求
     */
    void showDialog(String msg, boolean isCancelable);

    /**
     * 显示对话框
     */
    void showDialog(String msg);

    /**
     * 显示对话框
     */
    void showDialog();

    /**
     * 关闭加载中对话框
     */
    void dismissDialog();


    /**
     * 清空数据  一般在列表使用  但是也可以作为其他的界面使用
     */
    void clearData();

    /**
     * 获取自动切换加载中布局的管理器
     */

    LoadSwitchService getLoadSwitchService();


    /**
     * 显示错误信息
     */
    void showErrorMessage(String result);

    void showWarningMessage(String result);

    void showInfoMessage(String result);
    void showInfoMessage(String result, boolean isFinish);


    void finish();
}
