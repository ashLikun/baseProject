package com.ashlikun.common;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/8/25　17:13
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：全局EvenBus的key对应的消息通道
 */
public interface EvenBusKey {
    /**
     * 退出登录
     */
    String EXIT_LOGIN = "EXIT_LOGIN";
    /**
     * 登录
     */
    String LOGIN = "LOGIN";
    /**
     * 用户信息变更
     */
    String EVENBUS_USERDATA_CHANG = "EVENBUS_USERDATA_CHANG";
    /**
     * 定位成功数据 LatLng
     */
    String EVENTBUS_LOCATION_SUCCESS = "EVENTBUS_LOCATION";
    /**
     * 选择优惠券,数据类型 1:CouponData
     */
    String EVENTBUS_SELECT_COUPON = "EVENTBUS_SELECT_COUPON";
    /**
     * 选择搜索的地址 1:PoiItem
     */
    String EVENTBUS_SELECT_ADDRESS = "EVENTBUS_SELECT_ADDRESS";
}
