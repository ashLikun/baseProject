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

class HttpRequestParam(path: String) : HttpRequest(HttpManager.BASE_URL + HttpManager.BASE_PATH + path) {

    init {
        method = "POST"
    }

    companion object {
        private const val SIGN = "baseproject"

        fun post(path: String): HttpRequestParam {
            val param = HttpRequestParam(path)
            param.method = "POST"
            return param
        }

        fun get(url: String): HttpRequestParam {
            val param = HttpRequestParam(url)
            param.method = "GET"
            return param
        }
    }

    /**
     * 添加分页数据  pageIndex,pageSize
     */
    fun addPaging(pagingHelp: PageHelp) {
        //第几页
        addParam("pageIndex", pagingHelp.currentPage)
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
    fun addUserInfo() {
        if (RouterManage.getLogin().isLogin()) {

        }
    }

    /**
     * 添加签名，在全部参数添加完毕后,请不要调用toJson方法
     */
    override fun onBuildRequestBody() {
        if (params == null) {
            return
        }
        val sign = StringBuilder()
        if (!params.isEmpty()) {
            for ((key, value) in params) {
                sign.append("$key=$value&")
            }

        }
        if (sign.length > 0) {
            //sign.append(SIGN);
            sign.delete(sign.length - 1, sign.length)
            LogUtils.i(sign.toString())
            addParam("sign", Md5Utils.getMD5(sign.toString()));
        }
        //转换成json
        //toJson()
    }


}
