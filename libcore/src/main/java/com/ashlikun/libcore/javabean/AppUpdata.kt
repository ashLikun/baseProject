package com.ashlikun.libcore.javabean

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:17
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：app更新的实体类
 */

class AppUpdata {
    /**
     * ID 		int		自增ID
     * VersionNum 	int		版本号
     * VersionName	varchar(50)	版本名
     * UpdateTiime	datetime	更新时间
     *
     *
     * AppPath		varchar(100)	路径
     * IsUPdate                     是否强制升级
     * VersionIntro    nvarchar(200)   版本介绍
     */
    var IsUPdate = false
    var VersionNum = 0
    var AppPath: String? = null
    var VersionIntro: String? = null
    var VersionName: String? = null


}
