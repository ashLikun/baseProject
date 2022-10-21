package com.ashlikun.baseproject.libcore.constant

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:32
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：路由的key值
 */
object RouterKey {
    /********************************************************************************************
     *                                           SHOW_FRAGMENT
     ********************************************************************************************/
    const val FLAG_TARGET_PATH = "FLAG_TARGET_PATH"
    const val FLAG_STATUS_TRANS = "FLAG_STATUS_TRANS"
    const val FLAG_STATUS_COLOR = "STATUS_COLOR"

    /********************************************************************************************
     * Intent  Flag   int
     */
    const val FLAG_TYPE = "FLAG_TYPE"
    const val FLAG_ID = "FLAG_ID"
    const val FLAG_INDEX = "FLAG_INDEX"
    const val FLAG_POSITION = "FLAG_POSITION"

    /********************************************************************************************
     * Intent  Flag   boolean
     */
    //是否显示返回键
    const val FLAG_BACK = "FLAG_BACK"

    //是否显示下载按钮
    const val FLAG_SHOW_DOWNLOAD = "FLAG_SHOW_DOWNLOAD"

    /********************************************************************************************
     * Intent  Flag   String
     */
    const val FLAG_TITLE = "FLAG_TITLE"
    const val FLAG_URL_DATA = "FLAG_URL_DATA"
    const val FLAG_JS = "js"
    const val FLAG_URL = "FLAG_URL"

    /********************************************************************************************
     * Intent Flag   object
     */
    const val FLAG_OPEN_ID = "FLAG_OPEN_ID"
    const val FLAG_ACCESS_TOKEN = "FLAG_ACCESS_TOKEN"

    /********************************************************************************************
     * Intent Flag   object
     */
    const val FLAG_DATA = "FLAG_DATA"
}
