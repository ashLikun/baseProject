package com.ashlikun.common.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.common.R;
import com.ashlikun.baseproject.libcore.libarouter.constant.RouterKey;
import com.ashlikun.baseproject.libcore.libarouter.constant.RouterPath;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.xwebview.XWeb;
import com.ashlikun.xwebview.websetting.AbsXWebSettings;
import com.ashlikun.xwebview.websetting.IWebSettings;
import com.ashlikun.xwebview.websetting.WebListenerManager;
import com.ashlikun.xwebview.webview.XWebView;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/10/9　10:20
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = RouterPath.ACTIVITY_H5)
public class H5Activity extends BaseActivity {
    private XWebView webView;
    private XWeb xWeb;
    private String url;
    private String htmlData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_or_fragment_webview;
    }

    @Override
    public void parseIntent(Intent intent) {
        super.parseIntent(intent);
        url = intent.getStringExtra(RouterKey.FLAG_URL);
        htmlData = intent.getStringExtra(RouterKey.FLAG_URL_DATA);
    }

    @Override
    public void initView() {
        toolbar.setBack(this);
        toolbar.setTitle(getIntent().getStringExtra(RouterKey.FLAG_TITLE));
        webView = f(R.id.webView);
        xWeb = XWeb.withXml(this)
                .useDefaultIndicator()
                .setWebWebSettings(getWebSettings())
                .setWebView(webView)
                .setWebChromeClient(mWebChromeClient)
                .createWeb()
                .ready()
                .go(url);
        if (htmlData != null) {
            xWeb.getUrlLoader().loadData(htmlData, "text/html; charset=UTF-8", null);
        }
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (toolbar != null && !TextUtils.isEmpty(title)) {
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
            }
            toolbar.setTitle(title);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (xWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        xWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        xWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xWeb.getWebLifeCycle().onDestroy();
    }


    public IWebSettings getWebSettings() {
        return new AbsXWebSettings() {
            private XWeb xWeb;

            @Override
            protected void bindWebSupport(XWeb xWeb) {
                this.xWeb = xWeb;
            }

            @Override
            public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {
                return super.setDownloader(webView, downloadListener);
            }
        };
    }
}
