package com.ashlikun.baseproject.common.mode.javabean

import cn.jpush.android.api.NotificationMessage
import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.gson.GsonHelper
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.orm.db.annotation.Ignore
import com.ashlikun.orm.db.annotation.PrimaryKey
import com.ashlikun.orm.db.annotation.Table
import com.ashlikun.orm.db.enums.AssignType
import com.google.gson.JsonSyntaxException

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27 10:00
 * 邮箱　　：496546144@qq.com
 *
 *
 * 拉起app的或者推送
 */
@Table("JpushJsonData")
class JpushJsonData {
    @Transient
    @PrimaryKey(value = AssignType.AUTO_INCREMENT)
    var id: Int = 0

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
    var type: String = ""

    /**
     * 具体内容
     */
    var params: Map<String, Any?>? = null

    fun getIntParams(key: String): Int {
        return try {
            params?.get(key) as Int? ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun getStringParams(key: String): String {
        return params?.get(key)?.toString() ?: ""
    }

    fun getStringIdParams(): String {
        return params?.get("id")?.toString() ?: ""
    }

    /**
     * 保存最新的数据
     */
    fun save() {
        userId = RouterManage.login()?.getUserId() ?: ""
        LiteOrmUtil.get().save(this)
        addOrRemove(true)
        EventBus.get(EventBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(this)
    }

    companion object {

        fun getJpushData(message: NotificationMessage): JpushJsonData? {
            if (message.notificationExtras == null) {
                return null
            }
            val extra = jsonParse(message.notificationExtras)
            extra?.msgId = message.msgId
            extra?.title = message.notificationTitle
            extra?.notificationID = message.notificationId
            return extra
        }


        fun jsonParse(str: String): JpushJsonData? {
            var data: JpushJsonData? = null
            try {
                data = GsonHelper.getGsonNotNull().fromJson(str, JpushJsonData::class.java)
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
//            if (isAdd) {
//                StoreUtils.setKeyAndValue(AppUtils.app(), SpKey.JPUSH_NUMBER,
//                        StoreUtils.getInt(AppUtils.app(), SpKey.JPUSH_NUMBER) + 1)
//            } else {
//                val number = StoreUtils.getInt(AppUtils.app(), SpKey.JPUSH_NUMBER) - 1
//                StoreUtils.setKeyAndValue(AppUtils.app(), SpKey.JPUSH_NUMBER,
//                        Math.max(number, 0))
//            }
//            EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(JpushJsonData())
        }

        /**
         * 清空数据
         */
        fun delete() {
//            RouterManage.getLogin().run {
//                if (isLogin()) {
//                    LiteOrmUtil.get().delete(JpushJsonData::class.java)
//                    StoreUtils.setKeyAndValue(AppUtils.app(), SpKey.JPUSH_NUMBER, 0)
//                    EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(JpushJsonData())
//                }
//            }
        }

        /**
         * 获取最新的缓存数据
         *
         * @return
         */
        fun getNewestData(): JpushJsonData? {
//            RouterManage.getLogin().run {
//                if (isLogin()) {
//                    try {
//                        return LiteOrmUtil.get().query(QueryBuilder.create(JpushJsonData::class.java)
//                                .whereEquals("userId", getUserId())
//                                .appendOrderAscBy("pushTime"))[0]
//                    } catch (e: Exception) {
//                    }
//                }
//            }
            return null
        }
    }
}
