package com.ashlikun.baseproject.module.login.utils

import com.ashlikun.baseproject.libcore.utils.extend.isTokenError
import com.ashlikun.baseproject.libcore.utils.http.HttpCodeApp
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.baseproject.libcore.utils.http.requestPost
import com.ashlikun.baseproject.libcore.utils.http.syncExecute2
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.ui.extend.resString

/**
 * 作者　　: 李坤
 * 创建时间: 2019/5/8　10:36
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Session和签名失败重新获取的工具
 */
object TokenUtils {
    /**
     * 更新session，同步请求
     * @return 是否获取成功
     */
    fun refresh(): HttpResponse {
        val data401 = HttpResponse().apply {
            code = HttpCodeApp.TOKEN_ERROR
            message = R.string.login_no.resString
        }
        if (UserData.userData?.refreshToken.isNullOrEmpty()) return data401
        //请求刷新接口
        val data = "${HttpManager.URL_PATH}/user/login/refresh-token".requestPost()
            .addParam("refreshToken", UserData.userData?.refreshToken).syncExecute2<HttpResponse>(null) ?: return data401
        if (data.isSucceed && UserData.userData != null) {
            //更新缓存
            UserData.userData?.token = data.getStringValue("data", "accessToken")
            UserData.userData?.refreshToken = data.getStringValue("data", "refreshToken")
            UserData.userData?.save()
            return data
        }
        if (!data.isTokenError()) {
            return data401
        }
        return data
    }


}