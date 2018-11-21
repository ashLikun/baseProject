package com.ashlikun.baseproject.libcore.constant

/**
 * 作者　　: 李坤
 * 创建时间: 2018/8/25　17:13
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：全局EvenBus的key对应的消息通道
 */
object EvenBusKey {
    /**
     * 退出登录
     */
    const val EXIT_LOGIN = "EXIT_LOGIN"
    /**
     * 登录
     */
    const val LOGIN = "LOGIN"
    /**
     * 用户信息变更
     */
    const val EVENBUS_USERDATA_CHANG = "EVENBUS_USERDATA_CHANG"
    /**
     * 定位成功数据 LatLng
     */
    const val EVENTBUS_LOCATION_SUCCESS = "EVENTBUS_LOCATION"
    /**
     * 选择优惠券,数据类型 1:CouponData
     */
    const val EVENTBUS_SELECT_COUPON = "EVENTBUS_SELECT_COUPON"
    /**
     * 选择搜索的地址 1:PoiItem
     */
    const val EVENTBUS_SELECT_ADDRESS = "EVENTBUS_SELECT_ADDRESS"
}
