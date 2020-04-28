package com.ashlikun.baseproject.common.widget.banner

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.ashlikun.baseproject.common.R
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.utils.http.HttpLocalUtils
import com.bumptech.glide.request.RequestOptions
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.xviewpager2.adapter.BasePageAdapter


/**
 * @author　　: 李坤
 * 创建时间: 2018/8/10 14:55
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Glide加载网络图片的BannerHolder
 */

open class BannerImagePagerAdapter(context: Context, data: List<IBannerData>? = null) : BasePageAdapter<IBannerData>(context, data) {
    internal var width = 0
    internal var height = 0
    override fun createView(context: Context): View {
        return ImageView(context)
    }

    override fun convert(holder: MyViewHolder, data: IBannerData) {
        val imageView = holder.itemView as ImageView
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.id = R.id.switchRoot
        GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, data.getImageUrl()),
                RequestOptions())
    }
}
