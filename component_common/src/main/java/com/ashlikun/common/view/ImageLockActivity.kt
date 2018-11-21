package com.ashlikun.common.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.common.R
import com.ashlikun.common.mode.javabean.ImageData
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.photoview.PhotoView
import com.ashlikun.photoview.ScaleFinishView
import com.ashlikun.xviewpager.listener.ViewPageHelperListener
import com.ashlikun.xviewpager.view.BannerViewPager
import kotlinx.android.synthetic.main.activity_image_lock.*
import java.util.*

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/2　17:52
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：查看图片的activity
 * 前一个页面请调用 statusBar.setFitsSystemWindows()保证页面不抖动
 */
@Route(path = RouterPath.IMAGE_LOCK)
class ImageLockActivity : BaseActivity(), ViewPageHelperListener<ImageData>, ScaleFinishView.OnSwipeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    @Autowired(name = RouterKey.FLAG_DATA)
    lateinit var listDatas: ArrayList<ImageData>
    @Autowired(name = RouterKey.FLAG_POSITION)
    @JvmField
    var position: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_image_lock
    }

    override fun initView() {
        listDatas?.run {
            viewPager.setPages(this@ImageLockActivity, this)
            textView.text = (position + 1).toString() + "/" + size
            if (position < size) {
                viewPager.setCurrentItem(position, false)
            }
        }
        viewPager.addOnPageChangeListener(this)
    }

    override fun createView(context: Context, banner: BannerViewPager, data: ImageData, position: Int): View {
        val scaleFinishView = ScaleFinishView(this)
        scaleFinishView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        scaleFinishView.setOnSwipeListener(this)
        val photoView = PhotoView(this)
        scaleFinishView.addView(photoView, ViewGroup.LayoutParams(-1, -1))
        GlideUtils.show(photoView, data.img_url)
        photoView.setOnClickListener(this)
        return scaleFinishView
    }


    override fun onOverSwipe(isFinish: Boolean) {
        if (isFinish) {
            finish()
        }
    }

    override fun onSwiping(v: Float, v1: Float): Boolean {
        return false
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        textView.text = (position + 1).toString() + "/" + listDatas?.size
    }

    override fun isStatusTranslucent(): Boolean {
        return true
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onClick(v: View) {
        finish()
    }
}
