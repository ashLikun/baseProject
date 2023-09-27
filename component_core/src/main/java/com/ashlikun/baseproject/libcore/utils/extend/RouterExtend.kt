package com.ashlikun.baseproject.libcore.utils.extend

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.ashlikun.utils.ui.ActivityManager
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/8　17:54
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
typealias OnNvCallback = (postcard: Postcard) -> Unit
typealias ARouterNavigation = (postcard: Postcard) -> Any?

/**
 * 判断extra 是否有某个flag
 */
fun Postcard.hasFlag(flag: Int) = extra and flag == flag

/**
 * 移除extra的flag
 */
fun Postcard.removeFlag(flag: Int): Postcard {
    this.extra = extra and flag.inv()
    return this
}

/**
 * extra添加flag
 */
fun Postcard.addFlag(flag: Int): Postcard {
    this.extra = this.extra or flag
    return this
}

fun Postcard.navigation(
        context: Context? = ActivityManager.get().currentActivity(),
        onArrival: OnNvCallback? = null,
        onLost: OnNvCallback? = null,
        onInterrupt: OnNvCallback? = null,
        onFound: OnNvCallback? = null,
) {
    navigation(context, if (onFound == null) null else object : NavigationCallback {
        override fun onFound(postcard: Postcard) {
            onFound?.invoke(postcard)
        }

        override fun onLost(postcard: Postcard) {
            onLost?.invoke(postcard)
        }

        override fun onArrival(postcard: Postcard) {
            onArrival?.invoke(postcard)
        }

        override fun onInterrupt(postcard: Postcard) {
            onInterrupt?.invoke(postcard)
        }
    })
}
