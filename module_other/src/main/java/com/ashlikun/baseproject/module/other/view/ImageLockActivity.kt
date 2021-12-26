package com.ashlikun.baseproject.module.other.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.common.utils.extend.show
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.baseproject.module.other.databinding.OtherActivityImageLockBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.photoview.PhotoView
import com.ashlikun.photoview.ScaleFinishView
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.other.file.FileIOUtils
import com.ashlikun.utils.ui.UiUtils
import com.ashlikun.utils.ui.image.BitmapUtil
import com.ashlikun.utils.ui.modal.SuperToast
import com.ashlikun.xviewpager.listener.ViewPageHelperListener
import com.ashlikun.xviewpager.view.BannerViewPager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
class ImageLockActivity : BaseActivity(), ScaleFinishView.OnSwipeListener {
    override val binding by lazy {
        OtherActivityImageLockBinding.inflate(layoutInflater)
    }
    override val isStatusTranslucent = true

    @Autowired(name = RouterKey.FLAG_DATA)
    lateinit var listDatas: ArrayList<ImageData>

    @Autowired(name = RouterKey.FLAG_POSITION)
    @JvmField
    var position: Int = 0

    //是否显示下载按钮
    @Autowired(name = RouterKey.FLAG_SHOW_DOWNLOAD)
    @JvmField
    var isShowDownload = false
    val onPageChangCallback: ViewPager.SimpleOnPageChangeListener by lazy {
        object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.textView.text = (position + 1).toString() + "/" + listDatas?.size
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        //当<item name="android:windowBackground">@color/translucent</item> 的时候Activity的动画可能不执行
        //这里需要在开始和结束的时候手动调用
        overridePendingTransition(
                R.anim.mis_anim_fragment_lookphotp_in,
                R.anim.mis_anim_fragment_lookphotp_out
        )
    }

    override fun initView() {
        binding.run {
            window.setBackgroundDrawableResource(R.color.translucent)
            viewPager.setPages(adapter, listDatas)
            textView.text = (position + 1).toString() + "/" + listDatas.size
            if (position < listDatas.size) {
                viewPager.setCurrentItem(position, false)
            }
            viewPager.addOnPageChangeListener(onPageChangCallback)
            if (isShowDownload) {
                actionDownLoad.visibility = View.VISIBLE
                val drawable = GradientDrawable()
                drawable.setColor(0x77666666)
                drawable.cornerRadius = DimensUtils.dip2px(this@ImageLockActivity, 3f).toFloat()
                actionDownLoad.background = drawable
                actionDownLoad.setOnClickListener {
                    val url = listDatas[viewPager.realPosition].getImageUrl()
                    GlideUtils.downloadBitmap(this@ImageLockActivity, url) { file ->
                        if (file != null && file.exists()) {
                            var saveFile =
                                    File("${CacheUtils.appSDFilePath}${File.separator}${System.currentTimeMillis()}.jpg")
                            if (FileIOUtils.copyFile(file, saveFile, false)) {
                                SuperToast.showInfoMessage("图片已保存至 /${CacheUtils.rootName}/file 文件夹")
                                BitmapUtil.updatePhotoMedia(this@ImageLockActivity, saveFile)
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
    }

    val adapter by lazy {
        object : ViewPageHelperListener<ImageData> {
            override fun createView(
                    context: Context,
                    banner: BannerViewPager,
                    data: ImageData,
                    position: Int
            ): View {
                val view =
                        UiUtils.getInflaterView(this@ImageLockActivity, R.layout.other_item_image_lock)
                val photoView = view.findViewById<PhotoView>(R.id.photoView)
                photoView.setOnClickListener { view ->
                    finish()
                }
                view.findViewById<ScaleFinishView>(R.id.scaleFinishView)
                        ?.setOnSwipeListener(this@ImageLockActivity)
                photoView?.show(data.image, requestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        view.findViewById<View>(R.id.progressView)?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        view.findViewById<View>(R.id.progressView)?.visibility = View.GONE
                        val imgBili = resource.intrinsicHeight / (resource.intrinsicWidth * 1f)
                        val screenBili = photoView.height / (photoView.width * 1f)
                        if ((resource.intrinsicHeight > photoView.height / 2 && imgBili > screenBili) || imgBili > 6) {
                            photoView?.scaleType = ImageView.ScaleType.CENTER_CROP
                        } else {
                            photoView?.scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                        return false
                    }
                })
                return view

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


    override fun finish() {
        super.finish()
        overridePendingTransition(
                R.anim.mis_anim_fragment_lookphotp_in,
                R.anim.mis_anim_fragment_lookphotp_out
        )
    }
}
