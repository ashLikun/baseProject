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

fun Postcard.navigation(context: Context? = ActivityManager.getInstance().currentActivity(),
                        onArrival: OnNvCallback? = null,
                        onLost: OnNvCallback? = null,
                        onInterrupt: OnNvCallback? = null,
                        onFound: OnNvCallback? = null,) {
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

/**
 * 添加map参数
 */
fun Postcard.withMap(params: Map<String, Any?>? = null): Postcard {
    params?.forEach {
        if (it.value != null) {
            when (it.value) {
                is String -> withString(it.key, it.value as String)
                is Int -> withInt(it.key, it.value as Int)
                is Long -> withLong(it.key, it.value as Long)
                is Boolean -> withBoolean(it.key, it.value as Boolean)
                is Byte -> withByte(it.key, it.value as Byte)
                is Char -> withChar(it.key, it.value as Char)
                is Short -> withShort(it.key, it.value as Short)
                is Float -> withFloat(it.key, it.value as Float)
                is CharSequence -> withCharSequence(it.key, it.value as CharSequence)
                is Parcelable -> withParcelable(it.key, it.value as Parcelable)
                is Serializable -> withSerializable(it.key, it.value as Serializable)
                is Array<*> -> {
                    if ((it.value as Array<*>).isNotEmpty()) {
                        when ((it.value as Array<*>)[0]) {
                            is Parcelable -> withParcelableArray(it.key, it.value as Array<Parcelable>)
                            is CharSequence -> withCharSequenceArray(it.key, it.value as Array<CharSequence>)
                        }
                    }
                }
                is ArrayList<*> -> {
                    if ((it.value as ArrayList<*>).isNotEmpty()) {
                        when ((it.value as ArrayList<*>)[0]) {
                            is Parcelable -> withParcelableArrayList(it.key, it.value as ArrayList<Parcelable>)
                            is CharSequence -> withCharSequenceArrayList(it.key, it.value as ArrayList<CharSequence>)
                            is String -> withStringArrayList(it.key, it.value as ArrayList<String>)
                            is Int -> withIntegerArrayList(it.key, it.value as ArrayList<Int>)
                        }
                    }
                }
                is SparseArray<*> -> withSparseParcelableArray(it.key, it.value as SparseArray<Parcelable>)
                is ByteArray -> withByteArray(it.key, it.value as ByteArray)
                is ShortArray -> withShortArray(it.key, it.value as ShortArray)
                is CharArray -> withCharArray(it.key, it.value as CharArray)
                is FloatArray -> withFloatArray(it.key, it.value as FloatArray)
                is Bundle -> withBundle(it.key, it.value as Bundle)
                else -> withObject(it.key, it.value)
            }
        }
    }
    return this
}
