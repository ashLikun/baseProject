package com.ashlikun.baseproject.mode.javabean.base;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.ashlikun.utils.other.SpannableUtils;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.baseproject.R;
import com.ashlikun.libarouter.constant.ARouterPath;
import com.ashlikun.baseproject.core.Global;
import com.ashlikun.baseproject.mode.enum_type.DomeEnum;
import com.google.gson.annotations.SerializedName;
import com.ashlikun.eventbus.EventBus;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ashlikun.baseproject.core.MyApplication.myApp;


/**
 * 用户个人信息
 */
@Table("UserData")
public class UserData {
    public static final int STUDENT = 1;
    public static final int SHANGJIA = 2;


    @IntDef({STUDENT, SHANGJIA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserType {

    }

    //用户对象
    @Ignore
    public static UserData userData;
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("husername")
    public String husername;//环信的用户名

    //用户id
    @SerializedName("id")
    private int userId;
    @Column("token")
    private String token;
    @UserType
    @Column("type")
    private int type = 1;//用户类型
    //是否是当前登录的用户（这样就不用sb保存用户ID）
    @Column("isLogin")
    private boolean isLogin;

    private String companyname;
    private String adress;
    private String src;//头像地址
    private String realname;//真实姓名
    private int cishu;//合作次数
    private String school;
    @SerializedName("schoolid")
    private int schoolId;
    @SerializedName("name")
    private String teamName;//团队名字
    private String information;//团队信息
    private String jingli;//团队经历
    private int tid;//团队id
    private int renshu;//团队人数
    private String zhiwei;//团队中的职位
    private int teamCreateId;//团队负责人id
    private int userGrade;//用户的等级
    @SerializedName("paiming")
    private int teamPaiming;//团队的排名
    @DomeEnum.Code
    private int zhuangtai;//认证状态


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
    public static boolean isSLogin() {
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

    public static boolean isSLogin(Context activity, boolean showToast) {
        if (isSLogin()) {
            return true;
        } else {
            if (showToast) {
                SuperToast.get("您未登录，请先登录").info();
            }
            ARouter.getInstance().build(ARouterPath.LOGIN)
                    .navigation(activity);
            return false;
        }
    }

    public static boolean isSLogin(Context activity) {
        return isSLogin(activity, true);
    }


    /**
     * 退出登录
     * 吧数据库标识改成false 标记全部没有登录状态
     */
    public static boolean exitLogin(Context context) {
        //清除其他登录的用户
        boolean res = exit();
        ActivityManager.getInstance().exitAllActivity();
        //JpushBroadHandel.setJpushAlias(context);
        /**
         * 发送退出广播
         */
        EventBus.getDefault().post(Global.EXIT_LOGIN);
        // 返回登录页面
        ARouter.getInstance().build(ARouterPath.LOGIN)
                .navigation(context);
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
            if (getDbUserData() != null && !StringUtils.isEquals(getUserData().husername, husername)) {
                LiteOrmUtil.get().update(WhereBuilder.create(UserData.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"},
                        new Boolean[]{false}), ConflictAlgorithm.None);
            }
            //设置数据库当前登录的用户
            setLogin(true);
            LiteOrmUtil.get().save(this);
            UserData.userData = this;
            //发送通知
            EventBus.getDefault().post(userData, Global.EVENBUS_USERDATA_CHANG);
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

    public boolean isLogin() {
        return isLogin;
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

    @UserType
    public int getType() {
        return type;
    }

    //是否是学生登陆
    public boolean isStudent() {
        return type == STUDENT;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getCompanyname() {
        return StringUtils.dataFilter(companyname, "");
    }

    public String getAdress() {
        return adress;
    }

    public String getSrc() {
        return src;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSchool() {
        return school;
    }

    public int getSchoolId() {
        return schoolId;
    }


    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchool(String school) {
        this.school = school;
    }



    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getJingli() {
        return jingli;
    }

    public void setJingli(String jingli) {
        this.jingli = jingli;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }

    public String getZhiwei() {
        return zhiwei;
    }

    public void setZhiwei(String zhiwei) {
        this.zhiwei = zhiwei;
    }

    public int getTeamCreateId() {
        return teamCreateId;
    }

    public void setTeamCreateId(int teamCreateId) {
        this.teamCreateId = teamCreateId;
    }


    //获取等级
    public String getUiGrade() {
        return "LV" + userGrade;
    }//获取等级

    public String getUiTeamName() {
        return StringUtils.dataFilter(teamName, myApp.getResources().getString(R.string.no_add_team));
    }

    //商家的合作次数
    public String getHezhuoNumber() {
        return "合作次数：" + cishu;
    }

    //获取团队排名
    public CharSequence getUiTeamPaiming() {
        if (tid > 0) {
            return SpannableUtils.getBuilder("排名：")
                    .append(StringUtils.dataFilter(teamPaiming)).setForegroundColorRes(R.color.color_06c59c)
                    .create();
        }
        return myApp.getResources().getString(R.string.no_add_team);
    }

    public CharSequence getZhuangtaiStr() {
        return DomeEnum.getValue(zhuangtai);
    }

    public int getZhuangtai() {
        return zhuangtai;
    }


}
