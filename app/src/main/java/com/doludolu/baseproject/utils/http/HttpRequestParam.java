package com.doludolu.baseproject.utils.http;

import com.ashlikun.okhttputils.http.request.RequestParam;
import com.ashlikun.utils.other.LogUtils;
import com.ashlikun.xrecycleview.PagingHelp;
import com.doludolu.baseproject.mode.javabean.base.UserData;

import java.util.Map;

/**
 * 作者　　: 李坤
 * 创建时间:2016/10/14　15:03
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍： 请求参数
 */
public class HttpRequestParam extends RequestParam {
    private static final String SIGN = "baseproject";

    public HttpRequestParam(String php) {
        super(HttpManager.BASE_URL + HttpManager.BASE_PATH + php);
        post();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/10/27 16:58
     * <p>
     * 方法功能：添加分页数据  pageIndex,pageSize
     */
    public void addPaging(PagingHelp pagingHelp) {
        //第几页
        addParam("pageIndex", pagingHelp.getPageindex());
        //每页大小
        addParam("pageSize", pagingHelp.getPageCount());
    }

    /**
     * 是否正常的字符串
     *
     * @return
     */
    public boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    //通用参数：Mobile：手机号码，PassWord：密码
    public void addUserInfo() {
        if (UserData.isSLogin()) {
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/8 9:45
     * 邮箱　　：496546144@qq.com
     * 方法功能：添加签名，在全部参数添加完毕后,请不要调用toJson方法
     */
    public void onBuildRequestBody() {
        if (params == null) return;
        StringBuilder sign = new StringBuilder();
        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sign.append(entry.getKey());
                sign.append("=");
                sign.append(entry.getValue());
                sign.append("&");
            }

        }
        if (sign.length() > 0) {
//            sign.append(SIGN);
            sign.delete(sign.length() - 1, sign.length());
            LogUtils.i(sign.toString());
//            addParam("sign", Md5Utils.getMD5(sign.toString()));
            addParam("sign", sign.toString());
        }
        toJson();
    }
}
