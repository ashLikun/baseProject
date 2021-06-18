package com.ashlikun.baseproject.libcore.utils.extend

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2020/11/11　10:39
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
public inline fun <K> Map<out K, *>?.containsKey(key: K): Boolean =
        (this as Map<K, *>?)?.containsKey(key) == true

public inline fun <K> Map<out K, *>?.containsKeyToInt(key: K): Boolean = if ((this as Map<K, *>?)?.containsKey(key) == true) {
    this?.get(key)?.toString()?.toIntOrNull() != null
} else {
    false
}

public inline fun <K> Map<out K, *>?.getInt(key: K): Int? = this?.get(key)?.toString()?.toIntOrNull()
public inline fun <K> Map<out K, *>?.getString(key: K): String? = this?.get(key)?.toString()
public inline fun <K> Map<out K, *>?.getFloat(key: K): Float? = this?.get(key)?.toString()?.toFloatOrNull()
public inline fun <K> Map<out K, *>?.getBoolean(key: K): Boolean? = this?.get(key)?.toString()?.equals("true")

inline fun Map<String, Any?>.bundle(): Bundle {
    val bundle = Bundle()
    forEach {
        when (it.value) {
            is String -> bundle.putString(it.key, it.value as String)
            is Int -> bundle.putInt(it.key, it.value as Int)
            is Long -> bundle.putLong(it.key, it.value as Long)
            is Boolean -> bundle.putBoolean(it.key, it.value as Boolean)
            is Byte -> bundle.putByte(it.key, it.value as Byte)
            is Char -> bundle.putChar(it.key, it.value as Char)
            is Short -> bundle.putShort(it.key, it.value as Short)
            is Float -> bundle.putFloat(it.key, it.value as Float)
            is CharSequence -> bundle.putCharSequence(it.key, it.value as CharSequence)
            is Parcelable -> bundle.putParcelable(it.key, it.value as Parcelable)
            is Serializable -> bundle.putSerializable(it.key, it.value as Serializable)
            is Size -> bundle.putSize(it.key, it.value as Size)
            is SizeF -> bundle.putSizeF(it.key, it.value as SizeF)
            is Array<*> -> {
                if ((it.value as Array<*>).isNotEmpty()) {
                    when ((it.value as Array<*>)[0]) {
                        is Parcelable -> bundle.putParcelableArray(it.key, it.value as Array<Parcelable>)
                        is CharSequence -> bundle.putCharSequenceArray(it.key, it.value as Array<CharSequence>)
                    }
                }
            }
            is ArrayList<*> -> {
                if ((it.value as ArrayList<*>).isNotEmpty()) {
                    when ((it.value as ArrayList<*>)[0]) {
                        is Parcelable -> bundle.putParcelableArrayList(it.key, it.value as ArrayList<Parcelable>)
                        is CharSequence -> bundle.putCharSequenceArrayList(it.key, it.value as ArrayList<CharSequence>)
                        is String -> bundle.putStringArrayList(it.key, it.value as ArrayList<String>)
                        is Int -> bundle.putIntegerArrayList(it.key, it.value as ArrayList<Int>)
                    }
                }
            }
            is SparseArray<*> -> bundle.putSparseParcelableArray(it.key, it.value as SparseArray<Parcelable>)
            is ByteArray -> bundle.putByteArray(it.key, it.value as ByteArray)
            is ShortArray -> bundle.putShortArray(it.key, it.value as ShortArray)
            is CharArray -> bundle.putCharArray(it.key, it.value as CharArray)
            is FloatArray -> bundle.putFloatArray(it.key, it.value as FloatArray)
            is Bundle -> bundle.putBundle(it.key, it.value as Bundle)
            is Binder -> bundle.putBinder(it.key, it.value as Binder)

        }
    }
    return bundle;
}