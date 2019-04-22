package com.ashlikun.baseproject.libcore.constant

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:34
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：Arouter用到的path标识
 * 请按照标准定义
 * activity： /模块/activity/页面名称   或  /模块/activity/子模块../页面名称   至少三级  例子 /商城/activity/商品详情 /shop/activity/shopDetails
 * fragment： /模块/fragment/页面名称   或  /模块/fragment/子模块../页面名称   至少三级  例子 /商城/fragment/商品详情 /shop/fragment/shopDetails
 * service：   /模块/service    或   /模块/service/子模块..   至少二级  例子  /商城/service
 */
object RouterPath {
    /********************************************************************************************
     * activity的
     *******************************************************************************************/
    const val WELCOME = "/other/activity/welcome"
    const val LAUNCH = "/other/activity/launch"
    const val TEST = "/test/activity/main"
    const val HOME = "/home/activity/main"
    const val IMAGE_LOCK = "/comment/activity/image_lock"
    const val ACTIVITY_H5 = "/comment/activity/h5"
    /*************************************登录模块开始 *************************************/
    const val LOGIN = "/login/activity/main"
    const val AMEND_PASSWORD = "/login/activity/amend_password"
    const val UPDATA_PASSWORD = "/login/activity/updata_password"
    const val REGISTER = "/login/activity/register"
    /*************************************登录模块结束 *************************************/


    /********************************************************************************************
     * fragment的
     *******************************************************************************************/
    /**
     * 首页
     */
    const val FRAGMENT_HOME = "/home/fragment/main"

    /********************************************************************************************
     * service path
     *******************************************************************************************/
    const val SERVICE_COMMON = "/common/service"
    const val SERVICE_OTHER = "/other/service"
    const val SERVICE_HOME = "/home/service"
    const val SERVICE_LOGIN = "/login/service"
}
