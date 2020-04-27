package com.ashlikun.baseproject.module.other.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.widget.banner.BannerImagePagerAdapter
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.circleprogress.CircleProgressView
import com.ashlikun.compatview.ScaleImageView
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.glideutils.GlideLoad
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.photoview.PhotoView
import com.ashlikun.photoview.ScaleFinishView
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.other.file.FileIOUtils
import com.ashlikun.utils.ui.BitmapUtil
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.utils.ui.UiUtils
import com.ashlikun.xviewpager2.adapter.BasePageAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.other_activity_image_lock.*
import java.io.File
import java.util.*

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/2　17:52
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：查看图片的activity
 * 前一个页面请调用
 *  1:override fun isStatusTranslucent() = true
 *  2:AndroidBug5497Workaround.get(this) 如果输入法有问题
 *
 * 当前页面配置：
 * 当<item name="android:windowBackground">@color/translucent</item> 的时候Activity的动画可能不执行
 * 这里需要在开始和结束的时候手动调用
 * overridePendingTransition(R.anim.mis_anim_fragment_lookphotp_in, R.anim.mis_anim_fragment_lookphotp_out)
 */
@Route(path = RouterPath.IMAGE_LOCK)
class ImageLockActivity : BaseActivity(), ScaleFinishView.OnSwipeListener, View.OnClickListener {
    @Autowired(name = RouterKey.FLAG_DATA)
    lateinit var listDatas: ArrayList<ImageData>

    @Autowired(name = RouterKey.FLAG_POSITION)
    @JvmField
    var position: Int = 0

    //是否显示下载按钮
    @Autowired(name = RouterKey.FLAG_SHOW_DOWNLOAD)
    @JvmField
    var isShowDownload = false
    val onPageChangCallback: ViewPager2.OnPageChangeCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                textView.text = (position + 1).toString() + "/" + listDatas?.size
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.other_activity_image_lock
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        //当<item name="android:windowBackground">@color/translucent</item> 的时候Activity的动画可能不执行
        //这里需要在开始和结束的时候手动调用
        overridePendingTransition(R.anim.mis_anim_fragment_lookphotp_in, R.anim.mis_anim_fragment_lookphotp_out)
    }

    override fun initView() {
        window.setBackgroundDrawableResource(R.color.translucent)
        viewPager.setAdapter(adapter)
        textView.text = (position + 1).toString() + "/" + listDatas.size
        if (position < listDatas.size) {
            viewPager.setCurrentItem(position, false)
        }
        viewPager.registerOnPageChangeCallback(onPageChangCallback)
        if (isShowDownload) {
            actionDownLoad.visibility = View.VISIBLE
            val drawable = GradientDrawable()
            drawable.setColor(0x77666666)
            drawable.cornerRadius = DimensUtils.dip2px(this, 3f).toFloat()
            actionDownLoad.background = drawable
            actionDownLoad.setOnClickListener {
                val url = listDatas[viewPager.getCurrentItemReal()].getImageUrl()
                GlideUtils.downloadBitmap(this, url) { file ->
                    if (file != null && file.exists()) {
                        var saveFile = File("${CacheUtils.appSDFilePath}${File.separator}${System.currentTimeMillis()}.jpg")
                        if (FileIOUtils.copyFile(file, saveFile, false)) {
                            SuperToast.showInfoMessage("图片已保存至 /${CacheUtils.rootName}/file 文件夹")
                            BitmapUtil.updatePhotoMedia(this, saveFile)
                        } else {
                            SuperToast.showInfoMessage("图片保存失败")
                        }
                    } else {
                        SuperToast.showInfoMessage("图片保存失败")
                    }
                }
            }
        }
    }

    val adapter by lazy {
        object : BasePageAdapter<ImageData>(this@ImageLockActivity, listDatas) {
            override fun convert(holder: MyViewHolder, data: ImageData) {
                holder.getView<ScaleFinishView>(R.id.scaleFinishView)?.setOnSwipeListener(this@ImageLockActivity)
                holder.getView<View>(R.id.photoView)?.setOnClickListener(this@ImageLockActivity)

                GlideLoad.with(this@ImageLockActivity)
                        .load(data.image)
                        .requestListener(object : RequestListener<Any> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Any>?, isFirstResource: Boolean): Boolean {
                                holder.getView<View>(R.id.progressView)?.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(resource: Any?, model: Any?, target: Target<Any>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                holder.getView<View>(R.id.progressView)?.visibility = View.GONE
                                return false
                            }
                        })
                        .show(holder.getImageView(R.id.photoView))
            }

            override fun createView(context: Context): View {
                return UiUtils.getInflaterView(this@ImageLockActivity, R.layout.other_item_image_lock)

            }

        }
    }

    override fun onOverSwipe(isFinish: Boolean) {
        if (isFinish) {
            finish()
        }
    }

    override fun onSwiping(v: Float, v1: Float): Boolean {
        return false
    }


    override fun isStatusTranslucent(): Boolean {
        return true
    }

    override fun onClick(v: View) {
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.mis_anim_fragment_lookphotp_in, R.anim.mis_anim_fragment_lookphotp_out)
    }
}