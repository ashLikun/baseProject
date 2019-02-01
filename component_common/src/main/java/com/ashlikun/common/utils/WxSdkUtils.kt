package com.ashlikun.common.utils

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/20　13:27
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：微信sdk相关工具
 */
object WxSdkUtils {
//    var APP_ID = ""
//    var onWxListener: WeakReference<((result: SendAuth.Resp) -> Unit)>? = null
//
//    /**
//     * 注册到微信,也是获取IWXAPI实例的方法
//     */
//    val wxApi: IWXAPI
//        get() {
//            val api = WXAPIFactory.createWXAPI(AppUtils.getApp(), APP_ID)
//            api.registerApp(APP_ID)
//            return api
//        }
//
//    fun login(params: WxLoginParams, onWxLogin: (result: SendAuth.Resp) -> Unit) {
//        APP_ID = params.appid
//        val api = wxApi
//        if (!api.isWXAppInstalled) {
//            SuperToast.showErrorMessage("您未安装微信")
//            return
//        }
//        val req = SendAuth.Req()
//        req.scope = params.scope
//        //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），
//        // 建议第三方带上该参数，可设置为简单的随机数加session进行校验
//        req.state = params.state
//        //回掉时候区分
//        req.transaction = "login"
//        if (api.sendReq(req)) {
//            WxSdkUtils.onWxListener = WeakReference(onWxLogin)
//        }
//    }
//
//    /**
//     * @param params
//     * @param wx     0:聊天界面    1:朋友圈   2:微信收藏
//     */
//    fun share(params: ShareParams, wx: Int, onWxLogin: (result: SendAuth.Resp) -> Unit) {
//        APP_ID = params.appid
//        val api = wxApi
//        if (!api.isWXAppInstalled) {
//            SuperToast.showErrorMessage("您未安装微信")
//            return
//        }
//        if (!params.check()) {
//            SuperToast.showErrorMessage("无法吊起微信哦")
//            return
//        }
//        val req = SendMessageToWX.Req()
//        req.transaction = "share"
//        req.message = params.wxMessage
//        //发送到聊天界面——WXSceneSession
//        //发送到朋友圈——WXSceneTimeline
//        //添加到微信收藏——WXSceneFavorite
//        req.scene = if (wx == 0) SendMessageToWX.Req.WXSceneSession else if (wx == 1) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneFavorite
//        if (api.sendReq(req)) {
//            WxSdkUtils.onWxListener = WeakReference(onWxLogin)
//        }
//    }
//
//    fun isRespFromLogin(baseResp: BaseResp): Boolean {
//        return StringUtils.isEquals(baseResp.transaction, "login")
//    }
}
