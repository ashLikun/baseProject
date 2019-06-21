package com.ashlikun.baseproject.common.mode.javabean;

import com.ashlikun.utils.other.StringUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/10/25　21:13
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：微信登录的数据模型
 */
public class WxLoginParams {
    /**
     * （有效时间5分钟，使用redis缓存数据）
     */
    public String state;
    /**
     * 微信开放平台编号
     */
    public String appid;
    /**
     * snsapi_userinfo
     */
    public String scope;

    public boolean check() {
        if (StringUtils.isEmpty(appid)) {
            return false;
        }
        return true;
    }
}
