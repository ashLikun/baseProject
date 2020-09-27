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
class AppOnLoadLayoutListener(var context: Context, var clickListion: OnLoadSwitchClick?) : OnLoadLayoutListener {
    override fun setRetryEvent(retryView: View, data: ContextData) {
        val title = retryView.findViewById<View>(R.id.loadSwitchTitle) as TextView?
        if (title != null) {
            title.visibility = View.VISIBLE
            if (data == null || TextUtils.isEmpty(data.title)) {
                title.visibility = View.GONE
            } else if (data != null && !TextUtils.isEmpty(data.title)) {
                title.visibility = View.VISIBLE
                title.text = data.title + if (data.errCode != 0) "" else "\n(错误码:${data.errCode})"
            }
        }
        val img = retryView.findViewById<View>(R.id.loadSwitchImage) as ImageView?
        if (img != null && data != null) {
            if (data.imgHeight > 0 && data.imgWidth > 0) {
                img.layoutParams.width = data.imgWidth
                img.layoutParams.height = data.imgHeight
            }
            if (data.resId <= 0) {
                img.setImageResource(R.drawable.material_service_error)
            } else if (data.resId > 0) {
                img.setImageResource(data.resId)
            }
        }
        val onClickListener = MyOnClickListener(data, 2)
        val butt = retryView.findViewById<View>(R.id.loadSwitchReSet) as TextView?
        if (butt != null) {
            if (data != null) {
                if (data.buttonShow) {
                    butt.visibility = View.VISIBLE
                } else {
                    butt.visibility = View.GONE
                }
                if (!TextUtils.isEmpty(data.buttonText)) {
                    butt.text = data.buttonText
                }
            }
            butt.setOnClickListener(onClickListener)
        }
        retryView.setOnClickListener(onClickListener)
    }

    override fun setLoadingEvent(loadingView: View, data: ContextData) {
        val title = loadingView.findViewById<View>(R.id.loadSwitchLoadTitle) as TextView?
        if (title != null) {
            if (data == null || TextUtils.isEmpty(data.title)) {
                title.visibility = View.GONE
            } else if (data != null && !TextUtils.isEmpty(data.title)) {
                title.visibility = View.VISIBLE
                title.text = data.title
            }
        }
    }

    override fun setEmptyEvent(emptyView: View, data: ContextData) {
        val title = emptyView.findViewById<View>(R.id.loadSwitchTitle) as TextView?
        if (title != null) {
            if (data == null || TextUtils.isEmpty(data.title)) {
                title.visibility = View.GONE
            } else if (data != null && !TextUtils.isEmpty(data.title)) {
                title.visibility = View.VISIBLE
                title.text = data.title
            }
        }
        val img = emptyView.findViewById<View>(R.id.loadSwitchImage) as ImageView?
        if (img != null && data != null) {
            if (data.imgHeight > 0 && data.imgWidth > 0) {
                img.layoutParams.width = data.imgWidth
                img.layoutParams.height = data.imgHeight
            }
            if (data.resId <= 0 || data.resId == LoadSwitch.BASE_LOAD_SERVICE_ERROR) {
                img.setImageResource(R.drawable.material_service_error)
            } else if (data.resId > 0) {
                img.setImageResource(data.resId)
            }
        }
        val butt = emptyView.findViewById<View>(R.id.loadSwitchReSet) as TextView?
        val onClickListener = MyOnClickListener(data, 1)
        if (butt != null) {
            if (data != null) {
                if (data.buttonShow) {
                    butt.visibility = View.VISIBLE
                } else {
                    butt.visibility = View.GONE
                }
                if (!TextUtils.isEmpty(data.buttonText)) {
                    butt.text = data.buttonText
                }
            }
            butt.setOnClickListener(onClickListener)
        }
        emptyView.setOnClickListener(onClickListener)
    }

    inner class MyOnClickListener
    /**
     * @param data
     * @param flag 1:代表空的时候点击事件，2：加载失败，从新加载
     */(val data: ContextData, val flag: Int) : View.OnClickListener {
        override fun onClick(v: View) {
            if (clickListion != null) {
                if (flag == 1) {
                    clickListion!!.onEmptyClick(data)
                } else {
                    clickListion!!.onRetryClick(data)
                }
            }
        }

    }

}