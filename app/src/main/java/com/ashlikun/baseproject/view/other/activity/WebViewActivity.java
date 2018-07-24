package com.ashlikun.baseproject.view.other.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.R;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.libarouter.constant.ARouterKey;
import com.ashlikun.libarouter.constant.ARouterPath;
import com.ashlikun.libcore.utils.http.HttpManager;
import com.ashlikun.superwebview.SuperWebView;
import com.ashlikun.utils.http.HttpLocalUtils;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.xrecycleview.SuperSwipeRefreshLayout;


/**
 * 作者　　: 李坤
 * 创建时间:2016/10/28　9:03
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = ARouterPath.WEBVIEW)
public class WebViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    String url;
    String title;
    SuperWebView webView;
    SuperSwipeRefreshLayout switchRoot;

    public static void startUI(Context context, String title, String url) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
            SuperToast.get("此链接不存在").warn();
            return;
        }
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ARouterKey.FLAG_TITLE, title);
        intent.putExtra(ARouterKey.FLAG_URL, url);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_or_fragment_webview;
    }


    @Override
    public int getStatusBarColor() {
        return R.color.translucent;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initView() {
        webView = (SuperWebView) findViewById(R.id.webView);
        switchRoot = (SuperSwipeRefreshLayout) findViewById(R.id.switchRoot);
        toolbar.setSuperTitle(title);
        toolbar.setBack(this);
        toolbar.addAction(0, "", R.drawable.md_transparent);
        switchRoot.setOnRefreshListener(this);
        webView.loadUrl(HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, url));
    }

    @Override
    public void parseIntent(Intent intent) {
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
            SuperToast.get("此链接不存在").warn();
            finish();
        }
    }


    @Override
    public void onRefresh() {
        webView.loadUrl(HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, url));
    }
}
