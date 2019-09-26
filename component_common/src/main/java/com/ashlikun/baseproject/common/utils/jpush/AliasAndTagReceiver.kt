package com.ashlikun.baseproject.common.utils.jpush

import android.content.Context
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.ashlikun.utils.other.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * 作者　　: 李坤
 * 创建时间: 2017/11/27　14:25
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：新的tag/alias接口结果返回需要开发者配置一个自定的广播 -->
 * 该广播需要继承JPush提供的JPushMessageReceiver类, 并如下新增一个 Intent-Filter
 */
class AliasAndTagReceiver : JPushMessageReceiver() {
    companion object {
        var setAliasDisposable: Disposable? = null
        var deleteAliasDisposable: Disposable? = null
        var setTagsDisposable: Disposable? = null
        var deleteTagsDisposable: Disposable? = null
    }

    override fun onTagOperatorResult(context: Context, message: JPushMessage) {
        super.onTagOperatorResult(context, message)
        //对应操作的返回码,0为成功，其他返回码请参考错误码定义.
        if (message.sequence === JpushUtils.JPUSH_TAGS_SET_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送Tags设置失败 ,Tags = " + message.tags + ",错误码 = " + message.errorCode)
                setTagsDisposable = Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { aLong ->
                            setTagsDisposable = null
                            JpushUtils.setTags(message.tags)
                        }
            } else {
                LogUtils.e("极光推送Tags设置成功,Tags = " + message.tags)
                setTagsDisposable?.dispose()
                deleteTagsDisposable?.dispose()
            }
        } else if (message.sequence === JpushUtils.JPUSH_TAGS_DELETE_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送Tags删除失败 ,别名 = " + message.tags + ",错误码 = " + message.errorCode)
                deleteTagsDisposable = Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { aLong ->
                            deleteTagsDisposable = null
                            JpushUtils.deleteAlias()
                        }
            } else {
                LogUtils.e("极光推送Tags删除成功,别名 = " + message.tags)
                setTagsDisposable?.dispose()
                deleteTagsDisposable?.dispose()
            }
        }
    }

    override fun onAliasOperatorResult(context: Context, message: JPushMessage) {
        super.onAliasOperatorResult(context, message)
        LogUtils.e("极光推送别名 ," + message.toString())
        //对应操作的返回码,0为成功，其他返回码请参考错误码定义.
        if (message.sequence === JpushUtils.JPUSH_ALIAS_SET_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送别名设置失败 ,别名 = " + message.alias + ",错误码 = " + message.errorCode)
                setAliasDisposable = Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { aLong ->
                            setAliasDisposable = null
                            JpushUtils.setAlias()
                        }
            } else {
                LogUtils.e("极光推送别名设置成功,别名 = " + message.alias)
                setAliasDisposable?.dispose()
                deleteAliasDisposable?.dispose()
            }
        } else if (message.sequence === JpushUtils.JPUSH_ALIAS_DELETE_ID) {
            if (message.errorCode !== 0) {
                LogUtils.e("极光推送别名删除失败 ,别名 = " + message.alias + ",错误码 = " + message.errorCode)
                deleteAliasDisposable = Observable.timer(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { aLong ->
                            deleteAliasDisposable = null
                            JpushUtils.deleteAlias()
                        }
            } else {
                LogUtils.e("极光推送别名删除成功,别名 = " + message.alias)
                setAliasDisposable?.dispose()
                deleteAliasDisposable?.dispose()
            }
        }
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage)
    }
}
