package com.ashlikun.common.utils.jpush;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/11/27　14:25
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：新的tag/alias接口结果返回需要开发者配置一个自定的广播 -->
 * 该广播需要继承JPush提供的JPushMessageReceiver类, 并如下新增一个 Intent-Filter
 */

public class AliasAndTagReceiver {
//public class AliasAndTagReceiver extends JPushMessageReceiver {
//    @Override
//    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
//        super.onTagOperatorResult(context, jPushMessage);
//    }
//
//    @Override
//    public void onAliasOperatorResult(Context context, JPushMessage message) {
//        super.onAliasOperatorResult(context, message);
//        LogUtils.e("极光推送别名 ," + message.toString());
//        //对应操作的返回码,0为成功，其他返回码请参考错误码定义.
//        if (message.getSequence() == JpushUtils.JPUSH_ALIAS_SET_ID) {
//            if (message.getErrorCode() != 0) {
//                LogUtils.e("极光推送别名设置失败 ,别名 = " + message.getAlias() + ",错误码 = " + message.getErrorCode());
//                Observable.timer(5, TimeUnit.SECONDS)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(aLong -> JpushUtils.setAlias());
//            } else {
//                LogUtils.e("极光推送别名设置成功,别名 = " + message.getAlias());
//            }
//        } else if (message.getSequence() == JpushUtils.JPUSH_ALIAS_DELETE_ID) {
//            if (message.getErrorCode() != 0) {
//                LogUtils.e("极光推送别名删除失败 ,别名 = " + message.getAlias() + ",错误码 = " + message.getErrorCode());
//                Observable.timer(5, TimeUnit.SECONDS)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(aLong -> JpushUtils.deleteAlias());
//            } else {
//                LogUtils.e("极光推送别名删除成功,别名 = " + message.getAlias());
//            }
//        }
//    }
//
//    @Override
//    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
//        super.onCheckTagOperatorResult(context, jPushMessage);
//    }
}
