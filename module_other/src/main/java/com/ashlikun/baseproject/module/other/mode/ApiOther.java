package com.ashlikun.baseproject.module.other.mode;

import com.ashlikun.baseproject.libcore.utils.http.BaseApiService;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：请求mode类
 */

public class ApiOther extends BaseApiService {
    private static ApiOther api;

    public static ApiOther getApi() {
        if (api == null) {
            synchronized (ApiOther.class) {
                if (api == null) {
                    api = new ApiOther();
                }
            }
        }
        return api;
    }
}
