package com.ashlikun.baseproject.libcore.mvp.view


import com.ashlikun.core.BasePresenter
import com.ashlikun.core.iview.IBaseView
import java.util.*


/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
 abstract class BaseListPresenter<D, V : IBaseView> : BasePresenter<V>() {
    val listDatas = ArrayList<D>()
}
