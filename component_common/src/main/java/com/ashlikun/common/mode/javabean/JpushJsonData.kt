package com.ashlikun.common.mode.javabean

import android.content.Intent
import com.ashlikun.baseproject.libcore.constant.EvenBusKey
import com.ashlikun.baseproject.libcore.constant.SpKey
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.gson.GsonHelper
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.orm.db.annotation.Ignore
import com.ashlikun.orm.db.annotation.PrimaryKey
import com.ashlikun.orm.db.annotation.Table
import com.ashlikun.orm.db.assit.QueryBuilder
import com.ashlikun.orm.db.enums.AssignType
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.SharedPreUtils
import com.google.gson.JsonSyntaxException

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27 10:00
 * 邮箱　　：496546144@qq.com
 *
 *
 * 方法功能：拉起app的或者推送
 */
@Table("JpushJsonData")
class JpushJsonData {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Transient
    var userId: String = ""//用户id

    /**
     * 唯一标识消息的 ID, 可用于上报统计等。JPushInterface.EXTRA_MSG_ID
     */
    @Transient
    var msgId: String? = null
    /**
     * 标题
     */
    @Transient
    var title: String? = null
    /**
     * 通知栏的Notification ID，可以用于清除Notification
     * 如果服务端内容（alert）字段为空，则notification id 为0
     */
    @Ignore
    @Transient
    var notificationID: Int = 0
    /**
     * 推送的时间戳 数据库排序用的
     */
    var pushTime: Long = 0

    /**
     * 消息类型
     */
    var type: Int = 0
    /**
     * 内容ID
     */
    var id: String? = null

    fun getIdInt(): Int {
        try {
            return id?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            return 0
        }
    }

    /**
     * 保存最新的数据
     */
    fun save() {
        RouterManage.getLogin().run {
            if (isLogin()) {
                userId = getUserId()
                LiteOrmUtil.get().save(this)
                addOrRemove(true)
                EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(this)
            }
        }
    }

    companion object {

        fun getJpushData(intent: Intent): JpushJsonData? {
            val action = intent.action
//            val bundle = intent.extras
//            val EXTRA_EXTRA = bundle!!.getString(JPushInterface.EXTRA_EXTRA)
//            val EXTRA_MSG_ID = bundle.getString(JPushInterface.EXTRA_MSG_ID)
//            if (EXTRA_EXTRA == null) {
//                return null
//            }
//            val extra = JpushJsonData.jsonParse(EXTRA_EXTRA)
//            extra.msgId = EXTRA_MSG_ID
//            // extra.contentType = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//            //自定义消息
//            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
//                //   extra.title = bundle.getString(JPushInterface.EXTRA_TITLE);
//                //  extra.message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action) || JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
//                extra.title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
//                // extra.message = bundle.getString(JPushInterface.EXTRA_ALERT);
//                extra.notificationID = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID, 0)
//            }
            return JpushJsonData.jsonParse(action)
        }

        fun jsonParse(str: String): JpushJsonData? {
            var data: JpushJsonData? = null
            try {
                data = GsonHelper.getGson().fromJson(str, JpushJsonData::class.java)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
            return data
        }


        /**
         * 新增或者删除一条
         *
         * @param isAdd
         */
        fun addOrRemove(isAdd: Boolean) {
            if (isAdd) {
                SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER,
                        SharedPreUtils.getInt(AppUtils.getApp(), SpKey.JPUSH_NUMBER) + 1)
            } else {
                val number = SharedPreUtils.getInt(AppUtils.getApp(), SpKey.JPUSH_NUMBER) - 1
                SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER,
                        Math.max(number, 0))
            }
            EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(JpushJsonData())
        }

        /**
         * 清空数据
         */
        fun delete() {
            RouterManage.getLogin().run {
                if (isLogin()) {
                    LiteOrmUtil.get().delete(JpushJsonData::class.java)
                    SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER, 0)
                    EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(JpushJsonData())
                }
            }
        }

        /**
         * 获取最新的缓存数据
         *
         * @return
         */
        fun getNewestData(): JpushJsonData? {
            RouterManage.getLogin().run {
                if (isLogin()) {
                    try {
                        return LiteOrmUtil.get().query(QueryBuilder.create(JpushJsonData::class.java)
                                .whereEquals("userId", getUserId())
                                .appendOrderAscBy("pushTime"))[0]
                    } catch (e: Exception) {
                    }
                }
            }
            return null
        }
    }
}
