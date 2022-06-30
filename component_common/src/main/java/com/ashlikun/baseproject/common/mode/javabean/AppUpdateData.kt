package com.ashlikun.baseproject.common.mode.javabean

import com.google.gson.annotations.SerializedName


/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/7　12:07
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：应用更新的数据
 */
data class AppUpdateData(
    @SerializedName("content")
    var content: String = "",
    @SerializedName("is_force")
    var isForce: Int = 0,
    @SerializedName("newest_version")
    var newestVersion: String = "",
    @SerializedName("time")
    var time: String = "",
    @SerializedName("update_info")
    var updateInfo: String = "",
    @SerializedName("url")
    var url: String = ""
) {

}