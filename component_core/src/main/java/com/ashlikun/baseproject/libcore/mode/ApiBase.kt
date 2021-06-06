package com.ashlikun.baseproject.libcore.mode

import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.okhttputils.retrofit.Action
import com.ashlikun.okhttputils.retrofit.Path
import com.ashlikun.okhttputils.retrofit.Url
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2021/5/12　15:19
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
@Url(url = HttpManager.BASE_URL, method = "POST")
@Path(HttpManager.BASE_PATH)
@Action(HttpManager.ACTION)
interface ApiBase : Serializable {
}