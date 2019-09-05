package com.ashlikun.baseproject.module.other.view

import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.xwebview.XWeb
import com.ashlikun.xwebview.websetting.AbsXWebSettings
import kotlinx.android.synthetic.main.other_activity_or_fragment_webview.*

/**
 * 作者　　: 李坤
 * 创建时间: 2018/10/9　10:20
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：H5页面
 */
@Route(path = RouterPath.ACTIVITY_H5)
class H5Activity : BaseActivity() {
    private val xWeb: XWeb by lazy {
        XWeb.withXml(this)
                .useDefaultIndicator()
                .setWebWebSettings(webSettings)
                .setWebView(webView)
                .setWebChromeClient(mWebChromeClient)
                .createWeb()
                .ready()
                .go(url)
    }
    private val url: String by lazy {
        intent.getStringExtra(RouterKey.FLAG_URL) ?: ""
    }
    private val htmlData: String by lazy {
        intent.getStringExtra(RouterKey.FLAG_URL_DATA) ?: ""
    }

    protected var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {}
        override fun onReceivedTitle(view: WebView, title: String) {
            var titleM = title
            super.onReceivedTitle(view, titleM)
            if (toolbar != null && !TextUtils.isEmpty(titleM)) {
                if (title.length > 10) {
                    titleM = title.substring(0, 10) + "..."
                }
            }
            toolbar?.setTitle(titleM)
        }
    }


    val webSettings = object : AbsXWebSettings() {
        private var xWeb: XWeb? = null
        override fun bindWebSupport(xWeb: XWeb) {
            this.xWeb = xWeb
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.other_activity_or_fragment_webview
    }

    override fun initView() {
        toolbar?.setBackImage(R.drawable.ic_close_black)
        toolbar?.run {
            setBack(this@H5Activity)
            setTitle(intent.getStringExtra(RouterKey.FLAG_TITLE))
        }
        if (htmlData.isNotEmpty()) {
            xWeb.urlLoader.loadData(htmlData, "text/html; charset=UTF-8", null)
        } else {
            //直接加载url，一定要执行一下
            xWeb
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (xWeb.handleKeyEvent(keyCode, event)) true else super.onKeyDown(keyCode, event)
    }


    override fun onResume() {
        super.onResume()
        xWeb.webLifeCycle.onResume()
    }

    override fun onPause() {
        super.onPause()
        xWeb.webLifeCycle.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        xWeb.webLifeCycle.onDestroy()
    }
}
