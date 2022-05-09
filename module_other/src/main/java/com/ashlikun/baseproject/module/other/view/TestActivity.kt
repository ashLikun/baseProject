package com.ashlikun.baseproject.module.other.view

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.http.HttpRequestParam
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.baseproject.module.other.databinding.OtherActivityTestBinding
import com.ashlikun.baseproject.module.other.mode.ApiOther
import com.ashlikun.baseproject.module.other.utils.MaotaiUtils
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.gson.GsonHelper
import com.ashlikun.utils.encryption.Md5Utils
import com.ashlikun.utils.encryption.SHAUtil
import com.ashlikun.utils.other.DateUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.logge
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.*


@Route(path = RouterPath.TEST)
class TestActivity : BaseActivity() {
    override val binding by lazy {
        OtherActivityTestBinding.inflate(layoutInflater)
    }
    val RESURL2 = listOf(
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
        "http://img1.cache.netease.com/catchpic/A/A0/A0153E1AEDA115EAE7061A0C7EBB69D2.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584594344314&di=4eb56ec22f47949d7c36069f55403e5c&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2Fe573f475a02c84bf35a44be7cff56307-0.jpg",
        "http://uploadfile.bizhizu.cn/up/03/50/95/0350955b21a20b6deceea4914b1cfeeb.jpg.source.jpg",
        "http://pic1.win4000.com/wallpaper/7/5860842b353da.jpg",
        "http://pic1.win4000.com/wallpaper/b/566a37b05aac3.jpg"
    )

    override fun initView() {
        toolbar?.setTitle("我是测试")
        //非常重要，没有这句话监听无法生效
//        val cb = object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
//
//            override fun onPrepare(animation: WindowInsetsAnimationCompat) {
//                LogUtils.e("onPrepare")
//                // #1: 第一，onPrepare 被调用会允许应用记录当前布局的任何状态
//            }
//
//            // #2: 在 onPrepare 之后，正常的 WindowInsets 会被下发到视图层次
//            // 结构中，它包含了结束状态。这意味着您的视图的
//            // OnApplyWindowInsetsListener 会被调用，这会导致一个布局传递
//            // 以反映结束状态
//
//            override fun onStart(
//                    animation: WindowInsetsAnimationCompat,
//                    bounds: WindowInsetsAnimationCompat.BoundsCompat
//            ): WindowInsetsAnimationCompat.BoundsCompat {
//                LogUtils.e("onStart")
//                // #3: 接下来是 onStart ，这个会在动画开始的时候被调用。
//                // 这允许应用记录下视图的目标状态或者结束状态
//                return bounds
//            }
//
//            override fun onProgress(insets: WindowInsetsCompat, runningAnimations: MutableList<WindowInsetsAnimationCompat>): WindowInsetsCompat {
//                LogUtils.e("onProgress")
//                // #4: 接下来是一个很重要的调用：onProgress 。这个会在动画中每次视窗属性
//                // 更改的时候被调用。在软键盘的这个例子中，这个调用会发生在软键盘在屏幕
//                // 上滑动的时候。
//                return insets
//            }
//
//            override fun onEnd(animation: WindowInsetsAnimationCompat) {
//                LogUtils.e("onEnd")
//                // #5: 最后 onEnd 在动画已经结束的时候被调用。使用这个来
//                // 清理任何旧的状态。
//            }
//        }
//        ViewCompat.setWindowInsetsAnimationCallback(binding.inputView, cb)
        binding.ceshiButton.setOnClickListener {
            launch {
                val aaa = ApiOther.api.test(111, HttpUiHandle.get())
                LogUtils.e(aaa.json)
            }
            //跳转
            startActivity(Intent(this, Test2Activity::class.java))
//            WindowCompat.getInsetsController(window, inputView)?.show(WindowInsetsCompat.Type.ime())
//            AlertDialog.Builder(this)
//                    .setTitle(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_title)
//                    .setMessage("11111111111111")
//                    .setPositiveButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_ok, DialogInterface.OnClickListener { dialog, which ->
//
//                    })
//                    .setNegativeButton(com.ashlikun.baseproject.module.other.R.string.ph_permission_dialog_cancel, null)
//                    .create().show()
//            RouterJump.startLockImage(0, RESURL2.map { ImageData(it, it, 0) } as ArrayList, true)

            val resss =
                "{\"regtime\":\"Wed Jan 20 11:40:42 CST 2021\",\"logintime\":\"Wed Jan 20 11:40:42 CST 2021\",\"code\":\"\",\"address\":\"\",\"loginname\":\"zwfw9108459708\",\"iid\":\"70456\",\"isalipay\":\"0\",\"mobile\":\"18662288251\",\"headportraitpath\":\"\",\"newauthlevel\":\"2\",\"uuid\":\"79f89b48db7e431b95ab96e908087e35\",\"isauthuser\":\"1\",\"name\":\"李坤\",\"authlevel\":\"1\",\"nickname\":\"\",\"registeredresidence\":\"\",\"aplipayuserid\":\"2088802461284647\",\"iswx\":\"0\",\"papersnumber\":\"320724199107080632\",\"residence\":\"\",\"paperstype\":\"1\",\"email\":\"\"}"
            val publicKey =
                "048bd8ac485d6d31d281bc1f5fd6e842ae54036c6d63a3df6ff232374bccc0fff9354b4f9f4c5c39cdeb29e9b618ced22af40b0867e4c53c31c7b3ec7120ad5182"
            val privateKey = "00cd57fc8661d62bc5ff449ef03ff02e858c07c90e93ef9c813d845405773cec66"
            val aaa =
                "04a13015b3b8cc1dbf1f2387e21c131cb22b75f54c56be603375438f6019bf62f936ebbfd186d512ae77ac2e495576886507e8704157388d5ea104e66b9ae0577d493d1228f8d3b7ab89f3d126b9883b4f5cdfbac84d89d9007ad65175f1dc0651e8145a22fd79fe5411b227f230c6eea96794363c81f1eccb672805cc119e88deee3e1ee41f223fc0a1e05e40ea6f33bee2abb3b8ba55a9dc487987506bb15d6e8effe00ae6420c3c5ede21a62c1e0e0b0a713c735a526d6a7008cf895699efd0fd0c6933c31231bcb9c8ab0097a453fdb68d549f630a47d7375e2fd78548a4f5f94fc775f7f972a8d6c40eddeda90ee3bba3f09aff8473f93fc73dc2074b6e826be0fe318da0880356f7d865ed39316d3228c117c2778f2a108f0d471579883441214c8f8ab1fd0dd7507f43cb433ae5e7ff86bc0677ac0a7252516b5975b729bcd1e4c913714738231debb14dd1983224a3799f5ffeb7838224688585f694f19e6016175e4d8633e5c097a47efded8ab137fbc0a569e2e3efeedae54c9aecec92c6e32e253bf0f4efeb00ba6c7953a4f8294ccc8a6af55cd31d6a2b9ad781e535cdc95897bcf82cb9d3f53c3221c840e448eeaaf59c227ac4b0df5ce0c13f34ad61845177883f30970b92bf1a66696d9ba75f9c57066f7ded1d29fb0bd8102f6f6f0ea029b46d3a8eb21f7f72bc4c81afd1b9dfd5d83f1c1ff1f2a237ef34533696420a00a5f44183e1e3e42c0d23bd039f32696df59c5a1d6d59295ef569beda1f0721ecf61c69917f6cecfde00eb7fc08d0bedf3a82825cb521810f36717ecbfb748094"
            try {
//                val jiami = Sm2Kit().simpleEnCode("aaaaaaaaaaaadddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddddddffffffff", publicKey)
//                LogUtils.e(jiami)
//                LogUtils.e(Sm2Kit().simpleDeCode(jiami, privateKey))
            } catch (e: Exception) {
                e.printStackTrace()
            }
//            try {
//                LogUtils.e(AESUtils.decrypt(aaa, "048bd8ac485d6d31d281bc1f5fd6e842ae54036c6d63a3df6ff232374bccc0fff9354b4f9f4c5c39cdeb29e9b618ced22af40b0867e4c53c31c7b3ec7120ad5182"))
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            try {
//                LogUtils.e(AESUtils.decrypt(aaa, "00cd57fc8661d62bc5ff449ef03ff02e858c07c90e93ef9c813d845405773cec66"))
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            try {
//                // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//                val keySpec = PKCS8EncodedKeySpec("00cd57fc8661d62bc5ff449ef03ff02e858c07c90e93ef9c813d845405773cec66".toByteArray())
//                val keyFactory = KeyFactory.getInstance("RSA")
//                val prikey = keyFactory.generatePrivate(keySpec) as RSAPrivateKey
//                LogUtils.e(String(RSAUtils.decryptData(aaa.toByteArray(), prikey)))
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }

        }
        val money =
            "2QbLwkI6OUWQ3kpNeDXn27+6A09ar/dzrWS7HAo18p+Mb40rX3u8JiqFBY3iPYcDNAerPqvcyN2RIkFWR8ycc+LjrX5E+56TLwzRNQE97f6Mm18MG4SZ9Jxx8J0eEf3GHuvUJNHkOR3jO5vkWISIt1XAKdXQWWPsGTGksxtGDloLLnch6fK9htdHRxuZ4S2d"
        val getPrivateLimit =
            "2QbLwkI6OUWQ3kpNeDXn20Vfdvroh6e2R4BgIHu500daP3R7vvlWUT7Q5YxDiedAVbCmU1CgDyU/uORzbIffEQfM+ajF0BYFJKJcS4e1YDJYzY73l8NzoN7VO/qsxMyzQj53OzpQymqwUY1Xh8d97eqgw7LXxuQJBbNrGMD+NNi0KxDWEgx1AMG5eV56vzAL+GdHtF9K7oLzvjq94Cy4hz5bE51AmORukC/h/u0j97tTjGO3gHCgcpMN0BGnafbHTH7We1hI4CNKnxpFl4DWQGUIDRO/2uLRy/2+sQWa8ql6L8EAZp1Uu18rAYBrq8spirGzE7izGt0G4alm2woGToer2g/loZNARvH6kP64CqIt3bsS/16DwoI4EKdpDZqhSVHUUuCyHzSPNuXqoOdW+QkoHoCopr/nFlqD66p9gHzXZxkWYabJ2BHIIMo+wirZ5dZYt97XwK/tyHOuWj1fFDrRMOeCpvhsepQZWFDeMYhdtF9wHRawd3hS3SRcMvF5vMHLKbn4EBoDafOZ854OE9ZkBJ6mDn5KuDHtsYCZsBJ73xhR+xmvzIY+ou1OT2SMVFRoQ2iVUO2FiF3/LGYbfI2tdB0MMR5Tt4wJO3/57MNaqeKMoeGsl0xznab7u587U7t8UsBwEgWtAgkKl+71pgJ84N8aRYDCN3N4ajmf1iKZ6D9rOh1GrJNTeY0U0OXNP25u7egv2g2KJIMhde3vk8oH7coUdzRfAO7u9CGltUq++tyihfVqzjtMGRkANkgcDpiPUW/si3wQq76XjXzWraPD9QOrq37K/y0npy0SHZ0Im6QXYT+IiLE609bMLCcI5dJmWeHG3gkQzSRzbsA8nGkXJWbXnnUOX664uqfQ1hc3mGeXuxqmelhi+5RirJT48cp1+TWHLtwn477jiADVDC2f+gOX6bQc8EfZfybZCXh+zbMl1rlMETDp10VUD0z+hI5npia62BYk65NGrC4H+fODfmMX9MbQSSOLe8owzgxEl3naSJb8UyxCfZFBU12Ia1jDvXci8kx5lnubGoHX/TiBvGHWb/j6ITV8qcWYGSp+HIqJwKxbnTuUDpGymTEgPAfYQVdx1oejqr9APdmT1IGQ580dWXzZhJB2JVRmxorDYK7Wjl7WfVDM0nD68wfAvgKtI4NQlB+BAQ6zlNnwUKOfk0zidekHYbDmr9K6v0WkZILOkEccWx0K45mfl0VYvAKRS4AUc6YBG/mxzKzAjfPsqoVefNLesbMFnrPLg4C3s6hOxA99CYYJN0EE4Z9GnTdCSe8WALXWCSXcXzX1QldJMD6V+RX7oWSbHbtXS1nc/FaXsfSoQSBGHjwbGl62qxgdqnOvlDumtzj92vBqNOHiBfYBSex/BsK9FiECxSwvlvfGyfZxdvx90gX1f1zmesCFbzYXaPX4NGu+4UYZsM2XIgeou/KDmpjWrGOxfDU="
        val key = "10a7adae92222b6c6fabb33c3388d78f"
        val aa =
            "https://api.946ys.com/OpenAPI/v1/private/getPrivateLimit?uid=82477708&token=10a7adae92222b6c6fabb33c3388d78f"

        binding.ceshiButton2.setOnClickListener {
            val appId = "youAppId" // 填应用AppId
            val api = WXAPIFactory.createWXAPI(this, appId)
            val req = WXLaunchMiniProgram.Req()
            req.userName = "gh_f8655b8d651c" // 填小程序原始id
            req.path = "pages/tabBar/home?token=youToken" //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.miniprogramType =
                WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE // 可选打开 开发版，体验版和正式版
            api.sendReq(req)
            launch {
//                val aaa = ApiOther.api.testx(HttpUiHandle.get())
//                LogUtils.e(aaa?.json)
            }
//            AlertDialog.Builder(this)
//                    .setCancelable(false)
//                    .setMessage("确定退出当前账户吗？")
//                    .setPositiveButton("残忍退出") { dialoog, which ->
//                    }
//                    .show()
            val token = "xjWseiKzeNP1JLDZD5a94ai5AwdNdzo"
            val paasid = "wechatinternationalprogram"
            val timestamp = System.currentTimeMillis() / 1000
            val time = DateUtils.getFormatTime(timestamp * 1000, "yyyy-MM-ddHH:mm:ss")
            val nonce = getRandomString(16)
            //
            val signature = SHAUtil.getSha("${timestamp}${token}${nonce}${timestamp}")
            val appmark = "JinJiHu"
            val appword = "g5JUYcMAMnbTxCp3"
            val body = mapOf(
                "appmark" to appmark,
                "time" to time,
                "servicename" to "ticketValidate",
                "sign" to Md5Utils.getMD5("$appmark$appword$time"),
                "params" to "{\"ticket\":\"7ef6dd50b5244640bf930723b078d40d3f4080e2542a4e1797d05f485342d7b3\"}",
            )
//https://one.sipac.gov.cn/szgyyqwsdt/szyqzwdt/dhlogin?ticket=745139bd4c70468aa8dfb198b52c8530283a3877e4a04c23bc19e590373fc6cf&gotoUrl=aHR0cHM6Ly9vbmUuc2lwYWMuZ292LmNuL3loengvbG9naW4vdXNlcmNlbnRlcl9zaG93LmRv            LogUtils.e("signature = ${signature}")
            launch {
//                val result = HttpRequestParam.create(url = "https://zwyyone.sipac.gov.cn/ebus/ywtbsfrz/usercenter/interfaces/jissso_packaging.do")
//                        .addHeader("x-tif-paasid", paasid)
//                        .addHeader("x-tif-signature", signature.toUpperCase())
//                        .addHeader("x-tif-timestamp", "$timestamp")
//                        .addHeader("x-tif-nonce", nonce)
//                        .setContentJson(GsonHelper.getBuilderNotNull().create().toJson(body))
//                        .syncExecute<String>(HttpUiHandle[this], String::class.java)
//                LogUtils.e(result)
//                val resultRes = HttpResponse(result)
//                var token = resultRes.getStringValue("data")
//                token = HttpResponse(token).getStringValue("token")
////                val result = HttpRequestParam.create(url = "https://one.sipac.gov.cn/szgyyqwsdt/szyqzwdt/html/pages/default/index.html")
////                        .setMethod("GET")
////                        .syncExecute<String>(HttpUiHandle[this], String::class.java)
//                val body = mapOf(
//                        "SERVETYPE " to "010102,010203",
//                        "appmark" to appmark,
//                        "time" to time,
//                        "servicename" to "findOutsideUserByToken",
//                        "sign" to Md5Utils.getMD5("$appmark$appword$time"),
//                        "params" to "{\"token\":\"${token}\"}",
//                )
                val body2 = mapOf(
                    "SERVETYPE " to "010102,010203"
                )
                val result2 =
                    HttpRequestParam.create(url = "https://zwyyone.sipac.gov.cn/ebus/ywtbsxk/getForeignItemList")
                        .addHeader("x-tif-paasid", paasid)
                        .addHeader("x-tif-signature", signature.toUpperCase())
                        .addHeader("x-tif-timestamp", "$timestamp")
                        .addHeader("x-tif-nonce", nonce)
                        .setContent(GsonHelper.getBuilderNotNull().create().toJson(body))
                        .syncExecute<String>(HttpUiHandle[this])
                LogUtils.e(result2)
//                LogUtils.e(AESUtils.decrypt(HttpResponse(result2).getStringValue("data"), appmark))
            }
        }
        binding.ceshiButton3.setOnClickListener {
            MaotaiUtils.start()
        }
    }

    //length用户要求产生字符串的长度
    fun getRandomString(length: Int): String? {
        val str = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length) {
            val number: Int = random.nextInt(str.length)
            sb.append(str[number])
        }
        return sb.toString()
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        "onResume".logge()
    }

    override fun onPause() {
        super.onPause()
        "onPause".logge()
    }

    override fun onStop() {
        super.onStop()
        "onStop".logge()
    }

}

