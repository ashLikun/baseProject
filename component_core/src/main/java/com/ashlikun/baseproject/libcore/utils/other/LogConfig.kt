package com.ashlikun.baseproject.libcore.utils.other

import android.app.Activity
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.livedatabus.XLiveData
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.store.getBoolStore
import com.ashlikun.utils.other.store.putStore
import com.ashlikun.utils.ui.fActivity

/**
 * 作者　　: 李坤
 * 创建时间: 2023/2/16　12:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：日志配置
 */
object LogConfig {


    //Http接口日志
    var httpIsLog = AppUtils.isDebug

    //刷新
    val refresh: () -> Unit = {
        httpIsLog = "httpIsLog".getBoolStore(httpIsLog)
    }

    init {
        //正式环境还原默认
        if (!AppUtils.isDebug) {
            refresh()
        }
    }


    data class Model(
        val key: String,
        val name: String,
        val getValue: () -> Boolean
    )

    val items = listOf(
        Model("httpIsLog", "Http接口日志") {
            "httpIsLog".getBoolStore(httpIsLog)
        },
    )

    fun showDialog() {
        //初始化
        TestLogService.get()
        val selectIndex = items.mapIndexed { index, model -> index to model.getValue() }.filter { it.second }.map { it.first }
        MaterialDialog(fActivity!!).show {
            title(text = "日志选择")
            listItemsMultiChoice(
                items = items.map { it.name },
                initialSelection = selectIndex.toIntArray()
            ) { dialog, indices, data ->
                items.forEach { it.key.putStore(false) }
                data.forEach {
                    items.find { itt -> itt.name == it }?.key?.putStore(true)
                    refresh()
                }
            }
        }
    }

    data class TestLogData(val type: String, val tag: String, val content: String) {
        fun getAll() =
            "类型:${type}" +
                    "\nTag:${tag}" +
                    "\n内容：${content}"
    }

    class TestLogService {
        companion object {
            private val instance by lazy { TestLogService() }
            fun get(): TestLogService = instance
        }

        val logs = mutableListOf<TestLogData>()
        val call: XLiveData<List<TestLogData>> = XLiveData()

        init {
            LogUtils.call = { type, tag, content ->
                logs.add(TestLogData(type, tag, content))
                call.post(logs)
                true
            }
        }

        fun clear() {
            logs.clear()
            call.post(logs)
        }
    }


}