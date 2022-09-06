package com.ashlikun.baseproject.module.login.mode.javabean

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ashlikun.baseproject.common.utils.extend.setNegativeButtonX
import com.ashlikun.baseproject.common.utils.extend.setPositiveButtonX
import com.ashlikun.baseproject.common.utils.jpush.JpushUtils
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.common.utils.store.AppStoreUtils
import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.orm.db.annotation.Column
import com.ashlikun.orm.db.annotation.PrimaryKey
import com.ashlikun.orm.db.annotation.Table
import com.ashlikun.orm.db.assit.QueryBuilder
import com.ashlikun.orm.db.assit.WhereBuilder
import com.ashlikun.orm.db.enums.AssignType
import com.ashlikun.orm.db.model.ColumnsValue
import com.ashlikun.orm.db.model.ConflictAlgorithm
import com.ashlikun.utils.other.coroutines.taskAsync
import com.ashlikun.utils.ui.modal.SuperToast
import com.google.gson.annotations.SerializedName

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:14
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：用户个人信息
 */

@Table("UserData")
class UserData {
    /**
     * 用户id
     */
    @PrimaryKey(AssignType.BY_MYSELF)
    @SerializedName("id")
    var id: String = ""

    @SerializedName("user_name")
    var userName: String = ""

    @Column("token")
    var token: String = ""

    /**
     * 是否是当前登录的用户（这样就不用sb保存用户ID）
     */
    @Column("isLogin")
    var isLogin: Boolean = false
        private set

    /**
     * 保存数据到数据库（）
     * 被保存的数据，这个数据会被默认成数据库的唯一登录数据
     */
    fun save(): Boolean {
        try {
            //清除其他登录的用户
            if (userData != null) {
                LiteOrmUtil.get().update(
                    WhereBuilder.create(UserData::class.java).where("isLogin = ?", true),
                    ColumnsValue(
                        arrayOf("isLogin"),
                        arrayOf(false)
                    ),
                    ConflictAlgorithm.None
                )
            }
            //设置数据库当前登录的用户
            isLogin = true
            LiteOrmUtil.get().save(this)
            userData = this
            //发送通知
            EventBus.get(EventBusKey.LOGIN).post()
            //设置推送别名
            JpushUtils.setAlias()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }


    companion object {
        /**
         * 用户对象
         */
        var userData: UserData? = null
            get() = if (field == null) try {

                val list = LiteOrmUtil.get().query(
                    QueryBuilder(UserData::class.java).where("isLogin=?", true)
                        .limit(0, 1)
                )
                if (list == null || list.isEmpty()) {
                    null
                } else {
                    field = list[0]
                    field
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } else {
                field
            }

        /**
         * @param isToLogin 是否跳转到登录页面
         * @param showToast 是否弹出toast
         */

        @JvmOverloads
        fun isLogin(isToLogin: Boolean = false, showToast: Boolean = true): Boolean {
            fun isInLogin(): Boolean {
                userData?.run {
                    return this.isLogin
                }
                return false
            }
            return if (isInLogin()) {
                true
            } else {
                if (isToLogin) {
                    if (showToast) {
                        SuperToast.get("您未登录，请先登录").info()
                    }
                    RouterJump.startLogin()
                }
                false
            }
        }


        /**
         * 退出登录
         * 吧数据库标识改成false 标记全部没有登录状态
         */
        fun exitLogin(): Boolean {
            taskAsync {
                //清除缓存,要在exit之前
                AppStoreUtils.exitLogin()
                //清除其他登录的用户
                exit()
            }
            RouterJump.startHome(1)
            return true
        }

        fun exit(): Boolean {
            //清除其他登录的用户
            val res = LiteOrmUtil.get().update(
                WhereBuilder.create(UserData::class.java).where("isLogin=?", true),
                ColumnsValue(arrayOf("isLogin"), arrayOf(false)),
                ConflictAlgorithm.None
            )
            userData = null
            if (res > 0) {
                //发送退出广播
                EventBus.get(EventBusKey.EXIT_LOGIN).post()
            }
            if (res <= 0) {
                return false
            } else {
                JpushUtils.deleteAlias()
            }
            return true
        }

        /**
         * 退出登录,对话框
         */
        fun exit(context: Context) {
            AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("确认退出登录吗？")
                .setPositiveButtonX("残忍退出") { dialoog ->
                    exitLogin()
                }
                .setNegativeButtonX("继续使用")
                .show()
        }
    }

}
