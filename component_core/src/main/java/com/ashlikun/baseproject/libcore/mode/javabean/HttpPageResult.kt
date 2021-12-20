package com.ashlikun.baseproject.libcore.mode.javabean

import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.xrecycleview.PageHelp

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/2　17:10
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：分页数据的mode
 */
class HttpPageResult<T> : HttpResult<T>() {
    /**
     * 当前是服务器数据的第几页
     */
    var currentPage = 1

    /**
     * 一共多少页
     */
    var recordPage = -1

    /**
     * 设置分页数据
     *
     * @param pageHelp
     */
    fun setPageHelp(pageHelp: PageHelp?) {
        pageHelp?.setPageInfo(currentPage, recordPage)
    }
}
