package com.ashlikun.baseproject.module.login.iview;

import com.ashlikun.core.iview.BaseView;
import com.ashlikun.core.iview.ICheckView;
import com.ashlikun.baseproject.module.login.mode.javaben.UserData;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　16:03
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public interface IBLoginView {

    /**
     * Created by yang on 2016/8/17.
     */
    interface IloginView extends BaseView, ICheckView {

        /**
         * 登录成功后接收UserData对象
         *
         * @param
         */
        void login(UserData data);
    }

    /**
     * Created by yang on 2016/8/17.
     */
    interface IAmendPasswordView extends BaseView {
        void receiverUserData(UserData UserData);
    }

    interface IUpDataPasswordView extends BaseView {

        void receiverUserData(UserData UserData);
    }

    /**
     * Created by yang on 2016/8/17.
     */
    interface IRegisterView extends BaseView, ICheckView {

        /**
         * 注册成功后接收UserData对象
         *
         * @param
         */
        void receiverUserData(UserData UserData);
    }
}
