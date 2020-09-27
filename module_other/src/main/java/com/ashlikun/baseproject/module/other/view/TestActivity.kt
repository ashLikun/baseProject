package com.ashlikun.baseproject.module.other.view

import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.other.ThreadPoolManage
import kotlinx.android.synthetic.main.other_activity_test.*
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.utils.other.LogUtils


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
        val money = "2QbLwkI6OUWQ3kpNeDXn27+6A09ar/dzrWS7HAo18p+Mb40rX3u8JiqFBY3iPYcDNAerPqvcyN2RIkFWR8ycc+LjrX5E+56TLwzRNQE97f6Mm18MG4SZ9Jxx8J0eEf3GHuvUJNHkOR3jO5vkWISIt1XAKdXQWWPsGTGksxtGDloLLnch6fK9htdHRxuZ4S2d"
        val getPrivateLimit = "2QbLwkI6OUWQ3kpNeDXn20Vfdvroh6e2R4BgIHu500daP3R7vvlWUT7Q5YxDiedAVbCmU1CgDyU/uORzbIffEQfM+ajF0BYFJKJcS4e1YDJYzY73l8NzoN7VO/qsxMyzQj53OzpQymqwUY1Xh8d97eqgw7LXxuQJBbNrGMD+NNi0KxDWEgx1AMG5eV56vzAL+GdHtF9K7oLzvjq94Cy4hz5bE51AmORukC/h/u0j97tTjGO3gHCgcpMN0BGnafbHTH7We1hI4CNKnxpFl4DWQGUIDRO/2uLRy/2+sQWa8ql6L8EAZp1Uu18rAYBrq8spirGzE7izGt0G4alm2woGToer2g/loZNARvH6kP64CqIt3bsS/16DwoI4EKdpDZqhSVHUUuCyHzSPNuXqoOdW+QkoHoCopr/nFlqD66p9gHzXZxkWYabJ2BHIIMo+wirZ5dZYt97XwK/tyHOuWj1fFDrRMOeCpvhsepQZWFDeMYhdtF9wHRawd3hS3SRcMvF5vMHLKbn4EBoDafOZ854OE9ZkBJ6mDn5KuDHtsYCZsBJ73xhR+xmvzIY+ou1OT2SMVFRoQ2iVUO2FiF3/LGYbfI2tdB0MMR5Tt4wJO3/57MNaqeKMoeGsl0xznab7u587U7t8UsBwEgWtAgkKl+71pgJ84N8aRYDCN3N4ajmf1iKZ6D9rOh1GrJNTeY0U0OXNP25u7egv2g2KJIMhde3vk8oH7coUdzRfAO7u9CGltUq++tyihfVqzjtMGRkANkgcDpiPUW/si3wQq76XjXzWraPD9QOrq37K/y0npy0SHZ0Im6QXYT+IiLE609bMLCcI5dJmWeHG3gkQzSRzbsA8nGkXJWbXnnUOX664uqfQ1hc3mGeXuxqmelhi+5RirJT48cp1+TWHLtwn477jiADVDC2f+gOX6bQc8EfZfybZCXh+zbMl1rlMETDp10VUD0z+hI5npia62BYk65NGrC4H+fODfmMX9MbQSSOLe8owzgxEl3naSJb8UyxCfZFBU12Ia1jDvXci8kx5lnubGoHX/TiBvGHWb/j6ITV8qcWYGSp+HIqJwKxbnTuUDpGymTEgPAfYQVdx1oejqr9APdmT1IGQ580dWXzZhJB2JVRmxorDYK7Wjl7WfVDM0nD68wfAvgKtI4NQlB+BAQ6zlNnwUKOfk0zidekHYbDmr9K6v0WkZILOkEccWx0K45mfl0VYvAKRS4AUc6YBG/mxzKzAjfPsqoVefNLesbMFnrPLg4C3s6hOxA99CYYJN0EE4Z9GnTdCSe8WALXWCSXcXzX1QldJMD6V+RX7oWSbHbtXS1nc/FaXsfSoQSBGHjwbGl62qxgdqnOvlDumtzj92vBqNOHiBfYBSex/BsK9FiECxSwvlvfGyfZxdvx90gX1f1zmesCFbzYXaPX4NGu+4UYZsM2XIgeou/KDmpjWrGOxfDU="
        val key = "10a7adae92222b6c6fabb33c3388d78f"
        val aa = "https://api.946ys.com/OpenAPI/v1/private/getPrivateLimit?uid=82477708&token=10a7adae92222b6c6fabb33c3388d78f"
        
        ceshiButton2.setOnClickListener {
            val result = AES.aesdecrypt(getPrivateLimit, key)
            LogUtils.e(result)
//            AlertDialog.Builder(this)
//                    .setCancelable(false)
//                    .setMessage("确定退出当前账户吗？")
//                    .setPositiveButton("残忍退出") { dialoog, which ->
//                    }
//                    .show()


        }

    }

    var count = 0
    override fun initData() {
        super.initData()
//        ThreadPoolManage.get().execute {
//            while (!isFinishing) {
//                Thread.sleep(50)
//                Log.e("post", "我发送了${count++}")
//                EventBus.get("111").post("")
//            }
//        }
    }

}

