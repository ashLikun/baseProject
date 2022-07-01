package com.ashlikun.baseproject.common.utils.jpush

import android.content.Context
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.ashlikun.baseproject.common.mode.javabean.JpushJsonData
import com.ashlikun.baseproject.libcore.router.RouterManage
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.coroutines.taskLaunch
import com.ashlikun.utils.other.coroutines.taskLaunchMain
import kotlinx.coroutines.Job

/**
 * 作者　　: 李坤
 * 创建时间: 2017/11/27　14:25
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：新的tag/alias接口结果返回需要开发者配置一个自定的广播 -->
 * 该广播需要继承JPush提供的JPushMessageReceiver类, 并如下新增一个 Intent-Filter
 */
class JpushReceiver : JPushMessageReceiver() {
    companion object {
        var setAliasJob: Job? = null
        var deleteAliasJob: Job? = null
        var setTagsJob: Job? = null
        var deleteTagsJob: Job? = null
        val delayTime = 4000L
    }

    override fun onRegister(context: Context?, registrationId: String?) {
        super.onRegister(context, registrationId)
        LogUtils.e("极光推送 onRegister id ： $registrationId")
    }

    override fun onMessage(context: Context, message: CustomMessage) {

    }

    //服务器连接成功
    override fun onConnected(p0: Context?, p1: Boolean) {
        super.onConnected(p0, p1)
        //nit 后直接 set 操作有极大可能导致失败，可能会在回调里拿到 6022,6002 等，测试的时候可以做个 7、8 秒的延时，正式业务里一般配合用户注册使用，延时基本上够用
        taskLaunch(delayTime = 4000) {
            RouterManage.login()?.run {
                if (isLogin()) JpushUtils.setAlias() else JpushUtils.deleteAlias()
            }
        }
    }

    override fun onNotifyMessageArrived(context: Context, message: NotificationMessage) {
        val data = JpushJsonData.getJpushData(message)
        data?.save()
        LogUtils.e("onNotifyMessageArrived   $message")
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        LogUtils.e("onNotifyMessageOpened   $message")
        val data = JpushJsonData.getJpushData(message)
        JpushUtils.handlePush(context, data)
    }

    override fun onTagOperatorResult(context: Context, message: JPushMessage) {
        super.onTagOperatorResult(context, message)
        //对应操作的返回码,0为成功，其他返回码请参考错误码定义.
        if (message.sequence === JpushUtils.JPUSH_TAGS_SET_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送Tags设置失败 ,Tags = " + message.tags + ",错误码 = " + message.errorCode)
                //6021 3.0.7 版本新增的错误码，多次调用 tag 相关的 API，请在获取到上一次调用回调后再做下一次操作；在未取到回调的情况下，等待 20 秒后再做下一次操作。
                if (message.errorCode != 6021) {
                    setTagsJob?.cancel()
                    setTagsJob = taskLaunchMain(delayTime = delayTime) {
                        JpushUtils.setTags(message.tags)
                    }
                }
            } else {
                LogUtils.e("极光推送Tags设置成功,Tags = " + message.tags)
                setTagsJob?.cancel()
                deleteTagsJob?.cancel()
            }
        } else if (message.sequence === JpushUtils.JPUSH_TAGS_DELETE_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送Tags删除失败 ,别名 = " + message.tags + ",错误码 = " + message.errorCode)
                //6021 3.0.7 版本新增的错误码，多次调用 tag 相关的 API，请在获取到上一次调用回调后再做下一次操作；在未取到回调的情况下，等待 20 秒后再做下一次操作。
                if (message.errorCode != 6021) {
                    deleteTagsJob?.cancel()
                    deleteTagsJob = taskLaunchMain(delayTime = delayTime) {
                        JpushUtils.deleteTags()
                    }
                }
            } else {
                LogUtils.e("极光推送Tags删除成功,别名 = " + message.tags)
                setTagsJob?.cancel()
                deleteTagsJob?.cancel()
            }
        }
    }

    override fun onAliasOperatorResult(context: Context, message: JPushMessage) {
        super.onAliasOperatorResult(context, message)
        LogUtils.e("极光推送别名 ,$message")
        //对应操作的返回码,0为成功，其他返回码请参考错误码定义.
        if (message.sequence === JpushUtils.JPUSH_ALIAS_SET_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送别名设置失败 ,别名 = " + message.alias + ",错误码 = " + message.errorCode)
                if (message.errorCode != 6022) {
                    setAliasJob?.cancel()
                    setAliasJob = taskLaunchMain(delayTime = delayTime) {
                        JpushUtils.setAlias()
                    }
                }
            } else {
                LogUtils.e("极光推送别名设置成功,别名 = " + message.alias)
                setAliasJob?.cancel()
                deleteAliasJob?.cancel()
            }
        } else if (message.sequence === JpushUtils.JPUSH_ALIAS_DELETE_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送别名删除失败 ,别名 = " + message.alias + ",错误码 = " + message.errorCode)
                //6022 3.0.7 版本新增的错误码，多次调用 alias 相关的 API，请在获取到上一次调用回调后再做下一次操作；在未取到回调的情况下，等待 20 秒后再做下一次操作。
                if (message.errorCode != 6022) {
                    deleteAliasJob?.cancel()
                    deleteAliasJob = taskLaunchMain(delayTime = delayTime) {
                        JpushUtils.deleteAlias()
                    }
                }
            } else {
                LogUtils.e("极光推送别名删除成功,别名 = " + message.alias)
                setAliasJob?.cancel()
                deleteAliasJob?.cancel()
            }
        }
    }


}