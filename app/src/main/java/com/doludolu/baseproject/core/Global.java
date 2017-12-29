package com.doludolu.baseproject.core;

/**
 * 作者　　: 李坤
 * 创建时间: 17:54 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：全局配置
 */

public interface Global {
    String image1 = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1508848909&di=68ac3cb9cda13f97c378e737782197fc&src=http://pic7.photophoto.cn/20080529/0034034465128235_b.jpg";
    String image2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502566279593&di=4b3bd1b8263e645da3287133d17c35f7&imgtype=0&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201607%2F20160712011.jpg";
    String image3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504444351975&di=b34fd9f85b354351f04ec30fbdaef92d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D0f91462c39fa828bc52e95a095762b41%2Fa71ea8d3fd1f413424a5c7572f1f95cad1c85e0d.jpg";
    /**
     * 默认帐号
     */
    public static final String DEFAULT_PHONE = "1390154";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 EvenBus
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //退出登录
    public static final String EXIT_LOGIN = "EXIT_LOGIN";
    //用户信息变更
    public static final String EVENBUS_USERDATA_CHANG = "EVENBUS_USERDATA_CHANG";


    /********************************************************************************************
     *                                           Intent  Flag   字段判断
     ********************************************************************************************/

}
