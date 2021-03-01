package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.okhttputils.http.request.HttpRequest
import com.ashlikun.utils.encryption.Md5Utils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.xrecycleview.PageHelp

/**
 * @author　　: 李坤
 * 创建时间: 2018/11/19 15:32
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：请求参数
 */
fun String.requestGet(): HttpRequestParam = HttpRequestParam.get(this)
fun String.requestPost(): HttpRequestParam = HttpRequestParam.post(this)

class HttpRequestParam private constructor(action: String? = null, path: String = HttpManager.BASE_PATH, url: String? = null) :
        HttpRequest(if (url.isNullOrEmpty()) HttpManager.createUrl(action, path) else url) {
    init {
        method = "POST"
        //时间戳公共不能放到统一的地方，这里每次初始化实例重新调用,保证最新时间戳
//        addParam("timestamp", System.currentTimeMillis())
    }

    companion object {
        private const val SIGN = "BaseProject"

        /**
         * 创建
         */
        @JvmStatic
        fun create(url: String): HttpRequestParam {
            return HttpRequestParam(url = url)
        }

        /**
         * post
         */
        @JvmStatic
        fun post(path: String): HttpRequestParam {
            val param = HttpRequestParam(path)
            param.method = "POST"
            return param
        }

        /**
         * get请求
         */
        @JvmStatic
        fun get(path: String): HttpRequestParam {
            val param = HttpRequestParam(path)
            param.method = "GET"
            return param
        }
    }

    /**
     * 添加分页数据  pageIndex,pageSize
     */
    fun addPaging(pagingHelp: PageHelp?): HttpRequestParam {
        //第几页
        if (pagingHelp != null) {
            addParam("page", pagingHelp?.currentPage)
        }
        return this
    }

    /**
     * 是否正常的字符串
     *
     * @return
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.length == 0
    }

    /**
     * 通用参数：Mobile：手机号码，PassWord：密码
     */
    fun addUserInfo(): HttpRequestParam {
        if (RouterManage.login()?.isLogin() == true) {

        }
        return this
    }

    override fun onBuildRequestBodyHasCommonParams() {
        super.onBuildRequestBodyHasCommonParams()
//        if (params == null) {
//            return
//        }
//        val sign = StringBuilder()
//        params?.forEach { it ->
//            val value = it?.value
//            if (value is String && value.isNullOrEmpty()) {
//            } else {
//                sign.append("${it.key}=$value&")
//            }
//        }
//
//        if (sign.isNotEmpty()) {
//            sign.delete(sign.length - 1, sign.length)
//            sign.append("&secret=8d68a9777b8b7115364452c712837616")
//            LogUtils.i("Httpsign  $sign")
//            addParam("sign", Md5Utils.getMD5(sign.toString()).toUpperCase())
//        }
        //转换成json
        toJson()
//        setContent("{}")
    }


    fun addId(id: Int): HttpRequestParam {
        addParam("id", id)
        return this
    }

    fun addId(id: String?): HttpRequestParam {
        addParam("id", id)
        return this
    }
}
