package com.ashlikun.baseproject.module.login.mode.javaben;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.libarouter.constant.RouterPath;
import com.ashlikun.baseproject.libcore.libarouter.jump.RouterJump;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.orm.db.annotation.Column;
import com.ashlikun.orm.db.annotation.Ignore;
import com.ashlikun.orm.db.annotation.PrimaryKey;
import com.ashlikun.orm.db.annotation.Table;
import com.ashlikun.orm.db.assit.QueryBuilder;
import com.ashlikun.orm.db.assit.WhereBuilder;
import com.ashlikun.orm.db.enums.AssignType;
import com.ashlikun.orm.db.model.ColumnsValue;
import com.ashlikun.orm.db.model.ConflictAlgorithm;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.google.gson.annotations.SerializedName;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:14
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：用户个人信息
 */

@Table("UserData")
public class UserData {
    //用户对象
    @Ignore
    public static UserData userData;


    @PrimaryKey(AssignType.BY_MYSELF)
    //用户id
    @SerializedName("id")
    private int userId;
    @Column("token")
    private String token;

    //是否是当前登录的用户（这样就不用sb保存用户ID）
    @Column("isLogin")
    private boolean isLogin;


    public static UserData getDbUserData() {
        try {
            userData = LiteOrmUtil.get().query(QueryBuilder.create(UserData.class).where("isLogin=?", true).limit(0, 1)).get(0);
            return userData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 直接判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return getUserData() == null ? false : getDbUserData().isLogin;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/23 16:13
     * <p>
     * 方法功能：是否登录，没登录就会跳转到登录界面
     *
     * @param showToast 是否弹出toast
     */

    public static boolean isLogin(Context activity, boolean showToast) {
        if (isLogin()) {
            return true;
        } else {
            if (showToast) {
                SuperToast.get("您未登录，请先登录").info();
            }
            ARouter.getInstance().build(RouterPath.LOGIN)
                    .navigation(activity);
            return false;
        }
    }

    public static boolean isLogin(Context activity) {
        return isLogin(activity, true);
    }


    /**
     * 退出登录
     * 吧数据库标识改成false 标记全部没有登录状态
     */
    public static boolean exitLogin(Context context) {
        //清除其他登录的用户
        boolean res = exit();
        ActivityManager.getInstance().exitAllActivity();
        /**
         * 发送退出广播
         */
        //EventBus.getDefault().post(Global.EXIT_LOGIN);
        RouterJump.startLogin();
        return res;
    }

    public static boolean exit() {
        //清除其他登录的用户
        int res = LiteOrmUtil.get().update(WhereBuilder.create(UserData.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"}, new Boolean[]{false}), ConflictAlgorithm.None);
        userData = null;
        if (res <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 退出登录,对话框
     */
    public static void exit(final Context context) {

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("提示")
                .content("确定退出当前账户吗？")
                .positiveText("残忍退出")
                .negativeText("继续使用")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        exitLogin(context);
                    }
                })
                .build();

        dialog.show();
    }

    /**
     * 保存数据到数据库（）
     * 被保存的数据，这个数据会被默认成数据库的唯一登录数据
     */
    public boolean save() {
        try {
            //清除其他登录的用户
            if (getDbUserData() != null) {
                LiteOrmUtil.get().update(WhereBuilder.create(UserData.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"},
                        new Boolean[]{false}), ConflictAlgorithm.None);
            }
            //设置数据库当前登录的用户
            setLogin(true);
            LiteOrmUtil.get().save(this);
            UserData.userData = this;
            //发送通知
            //EventBus.getDefault().post(userData, Global.EVENBUS_USERDATA_CHANG);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    public static UserData getUserData() {
        return userData == null ? getDbUserData() : userData;
    }


    public String getToken() {
        return StringUtils.dataFilter(token, "");
    }

    public void setToken(String token) {
        this.token = token;
    }

}
