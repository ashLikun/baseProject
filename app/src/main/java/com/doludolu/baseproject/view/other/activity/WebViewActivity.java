package com.doludolu.baseproject.view.other.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.utils.http.HttpLocalUtils;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.SuperToast;
import com.ashlikun.utils.ui.WebViewUtils;
import com.ashlikun.xrecycleview.SuperSwipeRefreshLayout;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.ARouterFlag;
import com.doludolu.baseproject.code.activity.BaseActivity;
import com.doludolu.baseproject.utils.http.HttpManager;


/**
 * 作者　　: 李坤
 * 创建时间:2016/10/28　9:03
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = ARouterFlag.WEBVIEW)
public class WebViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    String url;
    String title;
    WebView webView;
    SuperSwipeRefreshLayout switchRoot;

    public static void startUI(Context context, String title, String url) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
            SuperToast.get("此链接不存在").warn();
            return;
        }
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ARouterFlag.FLAG_TITLE, title);
        intent.putExtra(ARouterFlag.FLAG_URL, url);
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

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webView);
        switchRoot = (SuperSwipeRefreshLayout) findViewById(R.id.switchRoot);
        toolbar.setSuperTitle(title);
        toolbar.setBack(this);
        toolbar.addAction(0, "", R.drawable.md_transparent);


        switchRoot.setOnRefreshListener(this);
        WebViewUtils.configWebview(webView);
        //监听webView加载完成,此方法暂时不使用
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadSwitchService.showContent();
                switchRoot.setRefreshing(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadSwitchService.showLoading(new ContextData("正在加载页面信息"));
                view.getSettings().setBlockNetworkImage(true);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 90 && view.getSettings().getBlockNetworkImage()) {
                    view.getSettings().setBlockNetworkImage(false);
                }
            }
        });

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
