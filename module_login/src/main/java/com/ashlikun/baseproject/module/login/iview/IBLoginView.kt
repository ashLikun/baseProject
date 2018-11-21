package com.ashlikun.baseproject.module.login.iview

import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.core.iview.IBaseView
import com.ashlikun.core.iview.ICheckView

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　16:03
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

interface IBLoginView {

    interface IloginView : IBaseView, ICheckView {

        /**
         * 登录成功后接收UserData对象
         *
         * @param
         */
        fun login(data: UserData)
    }

    interface IAmendPasswordView : IBaseView {
        fun receiverUserData(UserData: UserData)
    }

    interface IUpDataPasswordView : IBaseView {

        fun receiverUserData(UserData: UserData)
    }

    interface IRegisterView : IBaseView, ICheckView {

        /**
         * 注册成功后接收UserData对象
         *
         * @param
         */
        fun receiverUserData(userData: UserData)
    }
}
