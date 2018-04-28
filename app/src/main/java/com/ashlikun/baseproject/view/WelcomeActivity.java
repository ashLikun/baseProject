package com.ashlikun.baseproject.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.BuildConfig;
import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.mode.javabean.base.UserData;
import com.ashlikun.baseproject.presenter.home.HomePresenter;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.libarouter.constant.ARouterPath;
import com.ashlikun.libarouter.jump.ARouterJump;
import com.ashlikun.utils.other.SharedPreUtils;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.utils.ui.UiUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(path = ARouterPath.WELCOME)
public class WelcomeActivity extends BaseActivity {
    private int time = 3000;

    ImageView imageView;
    ShimmerTextView text;
    Shimmer shimmer;

    @Override
    public int getStatusBarColor() {
        return R.color.translucent;
    }

    @Override
    public boolean isStatusTranslucent() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new HomePresenter());
        WelcomeActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    protected void getPermission() {
        initViewOnPermiss();
        Observable.timer(time, TimeUnit.MILLISECONDS)
                .map(aLong -> checkIsFirst())
                .map(aLong -> getServiceUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stepCode -> {
                    //1跳转登陆或者首页，2：不跳转
                    if (stepCode == 1) {
//                        ARouter.getInstance().build(Uri.parse("/activity/home?index=1"))
                        ARouterJump.startTest();
                        finish();
                    }
                });
        checkIsFirst();
    }

    @OnPermissionDenied({
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    protected void onDenied() {
        SuperToast.get("获取权限失败").warn();
        finish();
    }

    @OnShowRationale({
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new MaterialDialog.Builder(this)
                .content(R.string.permission_rationale)
                .cancelable(false)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.proceed();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.cancel();
                    }
                })
                .show();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcom;
    }

    @Override
    public void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        text = (ShimmerTextView) findViewById(R.id.shimmer_tv);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void initViewOnPermiss() {
//        presenter.getHttpData();

        final AnimatorSet animSet = new AnimatorSet();
//        ObjectAnimator animX = ObjectAnimator.ofFloat(imageView, "translationX", 0.5f, 1.2f,1f);
//        ObjectAnimator animY = ObjectAnimator.ofFloat(imageView, "translationY", startY, endY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "scaleX", 0.7f, 1.4f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "scaleY", 0.7f, 1.4f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(UiUtils.getDecorView(this), "alpha", 0.8f, 1f);
        scaleX.setDuration(time);

        scaleY.setDuration(time);
        alpha.setDuration(time);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setDuration(time);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        animSet.playTogether(scaleX, scaleY, alpha);
        animSet.start();
        shimmer = new Shimmer();
        shimmer.start(text);
    }

    @Override
    public void parseIntent(Intent intent) {

    }


    private boolean checkIsFirst() {
        if (SharedPreUtils.getInt(this, "Run", "VersionCode") != BuildConfig.VERSION_CODE) {
            // 第一次
            SharedPreUtils.setKeyAndValue(this, "Run",
                    "VersionCode", BuildConfig.VERSION_CODE);
//            Intent intent = new Intent(this, LeadActivity.class);
//            startActivity(intent);
//            return false;
//            finish();
        } else {
        }
        return true;
    }

    @Override
    public void finish() {
        if (shimmer != null) {
            shimmer.cancel();
        }
        super.finish();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/26 17:05
     * <p>
     * 方法功能：获取用户所有信息
     * //1跳转登陆或者首页，2：不跳转
     */
    public int getServiceUser() {
        if (!UserData.isSLogin()) {
            return 1;
        }
//        HttpRequestParam p = new HttpRequestParam(UserData.getDbUserData().isStudent() ?
//                "sget_information.php" : "hget_information.php");
//        try {
//            HttpResult<UserData> result = HttpManager.getInstance().syncExecute(p, HttpResult.class, UserData.class);
//            if (result.isSucceed() && result.getData() != null) {
//                result.getData().setToken(UserData.getUserData().getToken());
//                result.getData().save();
//            }
//            return HttpManager.handelResult(result) ? 1 : 2;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return 1;
//        }
        return 1;
    }
}
