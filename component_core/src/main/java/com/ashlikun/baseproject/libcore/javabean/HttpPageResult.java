package com.ashlikun.baseproject.libcore.javabean;

import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.xrecycleview.PageHelp;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/2　17:10
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：分页数据的mode
 */
public class HttpPageResult<T> extends HttpResult<T> {
    /**
     * 服务器数据的第几页
     */
    public int currentPage = 1;
    /**
     * 一共多少页
     */
    public int recordPage = 0;

    /**
     * 设置分页数据
     *
     * @param pageHelp
     */
    public void setPageHelp(PageHelp pageHelp) {
        pageHelp.setPageInfo(currentPage, recordPage);
    }
}
