package com.ashlikun.baseproject.common.view

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/20　13:40
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：微信分享和登录的回调
 */
//class WXEntryActivity : BaseActivity(), IWXAPIEventHandler {
//    private var iwxapi: IWXAPI? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun setActivityContentView(layoutId: Int) {
//
//    }
//
//    override fun getLayoutId() = 0
//
//    override fun initView() {
//        iwxapi = WxSdkUtils.wxApi
//    }
//
//    override fun parseIntent(intent: Intent?) {
//        super.parseIntent(intent)
//
//        //接收到分享以及登录的intent传递handleIntent方法，处理结果
//        iwxapi = WXAPIFactory.createWXAPI(this, WxSdkUtils.APP_ID, false)
//        iwxapi?.handleIntent(getIntent(), this)
//    }
//
//    override fun onReq(baseReq: BaseReq) {
//
//    }
//
//    /**
//     * 请求回调结果处理
//     *
//     * @param baseResp
//     */
//    override fun onResp(baseResp: BaseResp) {
//        //微信回调
//        val isLogin = WxSdkUtils.isRespFromLogin(baseResp)
//        when (baseResp.errCode) {
//            BaseResp.ErrCode.ERR_OK -> {
//                if (!isLogin) {
//                    SuperToast.showInfoMessage("分享成功")
//                }
//                //回掉
//                WxSdkUtils.onWxListener?.run {
//                    get()?.invoke(baseResp as SendAuth.Resp)
//                }
//                finish()
//            }
//            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
//                //用户拒绝授权
//                SuperToast.showErrorMessage(if (isLogin) "拒绝登陆" else "拒绝分享")
//                finish()
//            }
//            BaseResp.ErrCode.ERR_USER_CANCEL -> {
//                //用户取消
//                SuperToast.showErrorMessage(if (isLogin) "取消登陆" else "取消分享")
//                finish()
//            }
//            else -> {
//                SuperToast.showErrorMessage((if (isLogin) "登陆失败（code:" else "分享失败（code:") + baseResp.errCode + "）")
//                finish()
//            }
//        }
//        WxSdkUtils.onWxListener = null
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        WxSdkUtils.onWxListener = null
//    }
//}
