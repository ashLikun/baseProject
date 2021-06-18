package com.ashlikun.baseproject.libcore.utils.extend

import android.view.LayoutInflater
import android.view.View
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.response.HttpResponse

/**
 * 作者　　: 李坤
 * 创建时间: 2021/6/18　20:14
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
fun View.inflater() = LayoutInflater.from(context)
fun View.layoutInflater() = LayoutInflater.from(context)