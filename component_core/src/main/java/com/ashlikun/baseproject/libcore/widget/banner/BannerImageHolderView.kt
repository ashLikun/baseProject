package com.ashlikun.baseproject.libcore.widget.banner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.utils.http.HttpLocalUtils
import com.ashlikun.xviewpager.listener.ViewPageHelperListener
import com.ashlikun.xviewpager.view.BannerViewPager
import com.bumptech.glide.request.RequestOptions
import com.ashlikun.baseproject.libcore.utils.http.HttpManager


/**
 * @author　　: 李坤
 * 创建时间: 2018/8/10 14:55
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Glide加载网络图片的BannerHolder
 */

abstract class BannerImageHolderView : ViewPageHelperListener<IBannerData> {
    private lateinit var imageView: ImageView
    internal var width = 0
    internal var height = 0

    val view: View?
        get() = imageView

    override fun createView(context: Context, banner: BannerViewPager, data: IBannerData, position: Int): View {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        with(imageView) {
            layoutParams = ViewGroup.LayoutParams(banner.width, banner.height)
            minimumWidth = width
            minimumHeight = height
            id = R.id.switchRoot
            GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, data.getImageUrl()),
                    RequestOptions().override(width, height))
            imageView.setOnClickListener { onItemClicklistener(imageView, 0, data) }
        }

        return imageView
    }

    abstract fun onItemClicklistener(item: View, position: Int, data: IBannerData)

}
