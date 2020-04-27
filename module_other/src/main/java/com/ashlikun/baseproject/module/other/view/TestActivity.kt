package com.ashlikun.baseproject.module.other.view

import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.other.ThreadPoolManage
import kotlinx.android.synthetic.main.other_activity_test.*
import com.ashlikun.baseproject.module.other.R


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = RouterPath.TEST)
class TestActivity : BaseActivity() {
    val RESURL2 = listOf(
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
            "http://img1.cache.netease.com/catchpic/A/A0/A0153E1AEDA115EAE7061A0C7EBB69D2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584594344314&di=4eb56ec22f47949d7c36069f55403e5c&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2Fe573f475a02c84bf35a44be7cff56307-0.jpg",
            "http://uploadfile.bizhizu.cn/up/03/50/95/0350955b21a20b6deceea4914b1cfeeb.jpg.source.jpg",
            "http://pic1.win4000.com/wallpaper/7/5860842b353da.jpg",
            "http://pic1.win4000.com/wallpaper/b/566a37b05aac3.jpg")
    override fun getLayoutId(): Int {
        return R.layout.other_activity_test
    }

    override fun initView() {
        ceshiButton.setOnClickListener {
//            AlertDialog.Builder(this)
//                    .setTitle(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_title)
//                    .setMessage("11111111111111")
//                    .setPositiveButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_ok, DialogInterface.OnClickListener { dialog, which ->
//
//                    })
//                    .setNegativeButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_cancel, null)
//                    .create().show()
            RouterJump.startLockImage(0, RESURL2.map { ImageData(it, it, 0) } as ArrayList, true)

        }
        ceshiButton2.setOnClickListener {
            MaterialDialog(this)
                    .cancelable(false)
                    .show {
                        title(text = "提示")
                        message(text = "确定退出当前账户吗？")
                        positiveButton(text = "残忍退出") {
                        }
                        negativeButton(text = "继续使用")
                    }


        }

    }

    var count = 0
    override fun initData() {
        super.initData()
        ThreadPoolManage.get().execute {
            while (!isFinishing) {
                Thread.sleep(50)
                Log.e("post", "我发送了${count++}")
                EventBus.get("111").post("")
            }
        }
    }

}

