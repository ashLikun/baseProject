package com.ashlikun.common.mode.javabean;

import android.content.Intent;

import com.ashlikun.baseproject.libcore.constant.SpKey;
import com.ashlikun.baseproject.libcore.libarouter.RouterManage;
import com.ashlikun.common.EvenBusKey;
import com.ashlikun.livedatabus.EventBus;
import com.ashlikun.okhttputils.json.GsonHelper;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.orm.db.annotation.Ignore;
import com.ashlikun.orm.db.annotation.PrimaryKey;
import com.ashlikun.orm.db.annotation.Table;
import com.ashlikun.orm.db.assit.QueryBuilder;
import com.ashlikun.orm.db.enums.AssignType;
import com.ashlikun.utils.AppUtils;
import com.ashlikun.utils.other.SharedPreUtils;
import com.google.gson.JsonSyntaxException;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27 10:00
 * 邮箱　　：496546144@qq.com
 * <p>
 * 方法功能：拉起app的或者推送
 */
@Table("JpushJsonData")
public class JpushJsonData {
    @PrimaryKey(AssignType.BY_MYSELF)
    public transient String userId;//用户id

    /**
     * 唯一标识消息的 ID, 可用于上报统计等。JPushInterface.EXTRA_MSG_ID
     */
    public transient String msgId;
    /**
     * 标题
     */
    public transient String title;
    /**
     * 通知栏的Notification ID，可以用于清除Notification
     * 如果服务端内容（alert）字段为空，则notification id 为0
     */
    @Ignore
    public transient int notificationID;
    /**
     * 推送的时间戳 数据库排序用的
     */
    public long pushTime;

    /**
     * 消息类型
     */
    public int type;
    /**
     * 内容ID
     */
    public String id;

    public int getIdInt() {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static JpushJsonData getJpushData(Intent intent) {
//        String action = intent.getAction();
//        Bundle bundle = intent.getExtras();
//        String EXTRA_EXTRA = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        String EXTRA_MSG_ID = bundle.getString(JPushInterface.EXTRA_MSG_ID);
//        if (EXTRA_EXTRA == null) {
//            return null;
//        }
//        JpushJsonData extra = JpushJsonData.jsonParse(EXTRA_EXTRA);
//        extra.msgId = EXTRA_MSG_ID;
//        // extra.contentType = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//        //自定义消息
//        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
//            //   extra.title = bundle.getString(JPushInterface.EXTRA_TITLE);
//            //  extra.message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action) ||
//                JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
//            extra.title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//            // extra.message = bundle.getString(JPushInterface.EXTRA_ALERT);
//            extra.notificationID = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID, 0);
//        }
//        return extra;
        return JpushJsonData.jsonParse("");
    }

    public static JpushJsonData jsonParse(String str) {
        JpushJsonData data = null;
        try {
            data = GsonHelper.getGson().fromJson(str, JpushJsonData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (data == null) {
            return new JpushJsonData();
        }
        return data;
    }

    /**
     * 保存最新的数据
     */
    public void save() {
        if (RouterManage.getLogin().isLogin()) {
            userId = RouterManage.getLogin().getUserId();
            LiteOrmUtil.get().save(this);
            addOrRemove(true);
            EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(this);
        }
    }


    /**
     * 新增或者删除一条
     *
     * @param isAdd
     */
    public static void addOrRemove(boolean isAdd) {
        if (isAdd) {
            SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER,
                    SharedPreUtils.getInt(AppUtils.getApp(), SpKey.JPUSH_NUMBER) + 1);
        } else {
            int number = SharedPreUtils.getInt(AppUtils.getApp(), SpKey.JPUSH_NUMBER) - 1;
            SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER,
                    Math.max(number, 0));
        }
        EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(new JpushJsonData());
    }

    /**
     * 清空数据
     */
    public static void delete() {
        if (RouterManage.getLogin().isLogin()) {
            LiteOrmUtil.get().delete(JpushJsonData.class);
            SharedPreUtils.setKeyAndValue(AppUtils.getApp(), SpKey.JPUSH_NUMBER, 0);
            EventBus.get(EvenBusKey.EVENBUS_JPUSH_RECEIVER_SAVE).post(new JpushJsonData());
        }
    }

    /**
     * 获取最新的缓存数据
     *
     * @return
     */
    public static JpushJsonData getNewestData() {
        if (!RouterManage.getLogin().isLogin()) {
            return null;
        }
        try {
            return LiteOrmUtil.get().query(QueryBuilder.create(JpushJsonData.class)
                    .whereEquals("userId", RouterManage.getLogin().getUserId())
                    .appendOrderAscBy("pushTime")).get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
