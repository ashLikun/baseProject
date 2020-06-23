package com.ashlikun.baseproject.common.adapter.banner

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.baseproject.common.utils.extend.showMaxPlace
import com.ashlikun.xviewpager.listener.ViewPageHelperListener
import com.ashlikun.xviewpager.view.BannerViewPager


/**
 * @author　　: 李坤
 * 创建时间: 2018/8/10 14:55
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Glide加载网络图片的ViewPageHelperListener
 */

open class BannerImagePagerListener : ViewPageHelperListener<IBannerData> {

    override fun createView(context: Context?, banner: BannerViewPager?, data: IBannerData, position: Int): View {
        val aa = ImageView(context)
        aa.scaleType = ImageView.ScaleType.CENTER_CROP
        aa.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
        aa.showMaxPlace(data.getImageUrl())
        return aa
    }
}
