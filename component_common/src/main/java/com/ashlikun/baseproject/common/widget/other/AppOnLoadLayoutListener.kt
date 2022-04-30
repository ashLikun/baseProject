package com.ashlikun.baseproject.common.widget.other

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ashlikun.baseproject.common.R
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitch
import com.ashlikun.loadswitch.OnLoadLayoutListener
import com.ashlikun.loadswitch.OnLoadSwitchClick

/**
 * 作者　　: 李坤
 * 创建时间: 17:08 Administrator
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：简单的实现不同布局的切换
 */
class AppOnLoadLayoutListener(var context: Context, var clickListion: OnLoadSwitchClick?) :
    OnLoadLayoutListener {
    override fun setRetryEvent(retryView: View, data: ContextData) {
        (retryView.findViewById<View>(R.id.loadSwitchTitle) as? TextView)?.apply {
            visibility = if (data.title.isEmpty()) View.GONE else View.VISIBLE
            text = data.getTitleErrUI()
        }
        (retryView.findViewById<View>(R.id.loadSwitchImage) as? ImageView)?.apply {
            if (data.imgHeight > 0 && data.imgWidth > 0) {
                layoutParams.width = data.imgWidth
                layoutParams.height = data.imgHeight
            }
            setImageResource(data.resId)
        }
        val onClickListener = MyOnClickListener(data, 2)
        (retryView.findViewById<View>(R.id.loadSwitchReSet) as? TextView)?.apply {
            visibility = if (data.buttonText.isNotEmpty()) View.VISIBLE else View.GONE
            if (this is TextView)
                text = data.buttonText
            setOnClickListener(onClickListener)
        }
        retryView.setOnClickListener(onClickListener)
    }

    override fun setLoadingEvent(loadingView: View, data: ContextData) {
        (loadingView.findViewById<View>(R.id.loadSwitchLoadTitle) as? TextView)?.apply {
            visibility = if (data.title.isEmpty()) View.GONE else View.VISIBLE
            text = data.title
        }
    }

    override fun setEmptyEvent(emptyView: View, data: ContextData) {
        (emptyView.findViewById<View>(R.id.loadSwitchTitle) as? TextView)?.apply {
            visibility = if (data.title.isEmpty()) View.GONE else View.VISIBLE
            text = data.title
        }
        (emptyView.findViewById<View>(R.id.loadSwitchImage) as? ImageView)?.apply {
            if (data.imgHeight > 0 && data.imgWidth > 0) {
                layoutParams.width = data.imgWidth
                layoutParams.height = data.imgHeight
            }
            setImageResource(data.resId)
        }
        val onClickListener = MyOnClickListener(data, 1)
        emptyView.findViewById<View>(R.id.loadSwitchReSet)?.apply {
            visibility = if (data.buttonText.isNotEmpty()) View.VISIBLE else View.GONE
            if (this is TextView)
                text = data.buttonText
            setOnClickListener(onClickListener)
        }
        emptyView.setOnClickListener(onClickListener)
    }

    /**
     * @param data
     * @param flag 1:代表空的时候点击事件，2：加载失败，从新加载
     */
    inner class MyOnClickListener(val data: ContextData, val flag: Int) : View.OnClickListener {
        override fun onClick(v: View) {
            if (flag == 1) {
                clickListion?.onEmptyClick(data)
            } else {
                clickListion?.onRetryClick(data)
            }
        }
    }

}