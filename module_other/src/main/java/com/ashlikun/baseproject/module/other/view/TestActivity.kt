package com.ashlikun.baseproject.module.other.view

import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.other.ThreadPoolManage
import kotlinx.android.synthetic.main.other_activity_test.*


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = RouterPath.TEST)
class TestActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return com.ashlikun.baseproject.module.other.R.layout.other_activity_test
    }

    override fun initView() {
        ceshiButton.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_title)
                    .setMessage("11111111111111")
                    .setPositiveButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_ok, DialogInterface.OnClickListener { dialog, which ->

                    })
                    .setNegativeButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_cancel, null)
                    .create().show()

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

