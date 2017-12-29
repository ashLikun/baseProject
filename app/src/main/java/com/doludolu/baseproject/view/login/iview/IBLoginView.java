package com.doludolu.baseproject.view.login.iview;

import com.ashlikun.core.iview.BaseView;
import com.ashlikun.core.iview.ICheckView;
import com.ashlikun.core.iview.IDatBindView;
import com.doludolu.baseproject.databinding.ActivityAmendPasswordBinding;
import com.doludolu.baseproject.databinding.ActivityRegisterBinding;
import com.doludolu.baseproject.databinding.ActivityUpdataPasswordBinding;
import com.doludolu.baseproject.mode.javabean.base.UserData;

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
    interface IAmendPasswordView extends IDatBindView<ActivityAmendPasswordBinding> {
        void receiverUserData(UserData UserData);
    }

    interface IUpDataPasswordView extends IDatBindView<ActivityUpdataPasswordBinding> {

        void receiverUserData(UserData UserData);
    }

    /**
     * Created by yang on 2016/8/17.
     */
    interface IRegisterView extends IDatBindView<ActivityRegisterBinding>, ICheckView {

        /**
         * 注册成功后接收UserData对象
         *
         * @param
         */
        void receiverUserData(UserData UserData);
    }
}
