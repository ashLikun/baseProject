package com.ashlikun.baseproject.module.login.mode.javabean

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.common.EvenBusKey
import com.ashlikun.common.utils.jpush.JpushUtils
import com.ashlikun.common.utils.jump.RouterJump
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
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.SuperToast
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
    var id: String? = null
        private set
        get() = StringUtils.dataFilter(field, "")
    @SerializedName("user_name")
    var userName: String? = null
        private set

    @Column("token")
    var token: String? = null
        private set
        get() = StringUtils.dataFilter(field, "")
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
            if (getDbUserData() != null) {
                LiteOrmUtil.get().update(WhereBuilder.create(UserData::class.java).where("isLogin = ?", true), ColumnsValue(arrayOf("isLogin"),
                        arrayOf(false)), ConflictAlgorithm.None)
            }
            //设置数据库当前登录的用户
            isLogin = true
            LiteOrmUtil.get().save(this)
            UserData.userData = this
            //发送通知
            EventBus.get(EvenBusKey.LOGIN).post()
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
        private var userData: UserData? = null

        fun getUserData(): UserData? {
            return if (userData == null) getDbUserData() else userData
        }

        fun getDbUserData(): UserData? {
            try {
                val list = LiteOrmUtil.get().query(
                        QueryBuilder(UserData::class.java).where("isLogin=?", true)
                                .limit(0, 1))
                if (list == null || list.isEmpty()) {
                    return null
                }
                userData = list[0]
                return userData
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }


        /**
         * 作者　　: 李坤
         * 创建时间: 2016/12/23 16:13
         *
         * 方法功能：是否登录
         *
         * @param activity 如果不为null  没登录就会跳转到登录界面
         * @param showToast 是否弹出toast
         */

        @JvmOverloads
        fun isLogin(activity: Context? = null, showToast: Boolean = true): Boolean {
            fun isInLogin(): Boolean {
                getUserData()?.run {
                    return this.isLogin
                }
                return false
            }
            if (isInLogin()) {
                return true
            } else {
                activity?.let {
                    if (showToast) {
                        SuperToast.get("您未登录，请先登录").info()
                    }
                    ARouter.getInstance().build(RouterPath.LOGIN)
                            .navigation(activity)
                }
                return false
            }
        }


        /**
         * 退出登录
         * 吧数据库标识改成false 标记全部没有登录状态
         */
        fun exitLogin(context: Context): Boolean {
            //清除其他登录的用户
            val res = exit()
            /**
             * 发送退出广播
             */
            EventBus.get(EvenBusKey.EXIT_LOGIN).post()
            RouterJump.startHome(0)
            return res
        }

        fun exit(): Boolean {
            //清除其他登录的用户
            val res = LiteOrmUtil.get().update(WhereBuilder.create(UserData::class.java).where("isLogin=?", true), ColumnsValue(arrayOf("isLogin"), arrayOf(false)), ConflictAlgorithm.None)
            userData = null
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
            MaterialDialog(context)
                    .cancelable(false)
                    .show {
                        title(text = "提示")
                        message(text = "确定退出当前账户吗？")
                        positiveButton(text = "残忍退出") {
                            exitLogin(context)
                        }
                        negativeButton(text = "继续使用")
                    }
        }
    }

}
