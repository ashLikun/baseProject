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
    //需要登录的页面的flag
    const val FLAG_LOGIN = 0x10000000

    /********************************************************************************************
     * service path
     *******************************************************************************************/
    const val SERVICE_COMMON = "/common/service"
    const val SERVICE_OTHER = "/other/service"
    const val SERVICE_HOME = "/home/service"
    const val SERVICE_LOGIN = "/login/service"

    /********************************************************************************************
     *                                           Other模块
     ********************************************************************************************/
    const val WELCOME = "/other/activity/welcome"
    const val LAUNCH = "/other/activity/launch"
    const val TEST = "/other/activity/main"

    const val IMAGE_LOCK = "/other/activity/image_lock"
    const val ACTIVITY_H5 = "/other/activity/h5"
    const val ACTIVITY_SHOW_FRAGMENT = "/common/activity/show/fragment"

    /********************************************************************************************
     *                                           登录模块
     ********************************************************************************************/
    const val LOGIN = "/login/activity/main"
    const val AMEND_PASSWORD = "/login/activity/amend_password"
    const val UPDATA_PASSWORD = "/login/activity/updata_password"
    const val REGISTER = "/login/activity/register"

    /********************************************************************************************
     *                                           Main 模块
     ********************************************************************************************/
    //主页
    const val HOME = "/home/activity/main"

    //首页
    const val FRAGMENT_HOME = "/home/fragment/main"

    //啊实打实的
    const val ACTIVITY_ASDASDA = "/login/activity/asdasda"
}