package com.ashlikun.baseproject.libcore.constant;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:34
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Arouter用到的path标识
 * 请按照标准定义
 * activity： /模块/activity/页面名称   或  /模块/activity/子模块../页面名称   至少三级  例子 /商城/activity/商品详情 /shop/activity/shopDetails
 * fragment： /模块/fragment/页面名称   或  /模块/fragment/子模块../页面名称   至少三级  例子 /商城/fragment/商品详情 /shop/fragment/shopDetails
 * service：   /模块/service    或   /模块/service/子模块..   至少二级  例子  /商城/service
 */
public interface RouterPath {
    /********************************************************************************************
     *                                           activity的
     ********************************************************************************************/
    String WELCOME = "/other/activity/welcome";
    String LAUNCH = "/other/activity/launch";
    String TEST = "/test/activity/main";
    String HOME = "/home/activity/main";
    String IMAGE_LOCK = "/comment/activity/image_lock";
    String ACTIVITY_H5 = "/comment/activity/h5";
    /*************************************登录模块开始*******************************************/
    String LOGIN = "/login/activity/main";
    String AMEND_PASSWORD = "/login/activity/amend_password";
    String UPDATA_PASSWORD = "/login/activity/updata_password";
    String REGISTER = "/login/activity/register";
    /*************************************登录模块结束*******************************************/


    /********************************************************************************************
     *                                           fragment的
     ********************************************************************************************/
    /**
     * 首页
     */
    String FRAGMENT_HOME = "/home/fragment/main";

    /********************************************************************************************
     *                                           service path
     ********************************************************************************************/
    String SERVICE_HOME = "/home/service";
    String SERVICE_LOGIN = "/login/service";
}
