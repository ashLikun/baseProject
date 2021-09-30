package com.ashlikun.baseproject.module.other.view

import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.databinding.ViewLineItemBinding
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.extend.inflater
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.baseproject.module.other.databinding.OtherActivityOrFragmentWebviewBinding
import com.ashlikun.baseproject.module.other.databinding.OtherActivityWelcomBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.xwebview.XWeb
import com.ashlikun.xwebview.websetting.AbsXWebSettings
import com.ashlikun.xwebview.websetting.IWebSettings
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

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
    val binding by lazy {
        OtherActivityOrFragmentWebviewBinding.inflate(layoutInflater)
    }
    private val xWeb: XWeb by lazy {
        XWeb.with(binding.webviewPar)
                .useDefaultIndicator()
                .setWebWebSettings(webSettings)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createWeb()
                .ready()
                .go(url)
    }

    //其他参数
    private val otherParams: Map<String, Any?> by lazy {
        launch {  }
        intent.getSerializableExtra(RouterKey.FLAG_DATA) as HashMap<String, Any?>? ?: hashMapOf()
    }
    private val mTitle: String by lazy {
        intent.getStringExtra(RouterKey.FLAG_TITLE) ?: otherParams["title"]?.toString() ?: ""
    }
    private val url: String by lazy {
        intent.getStringExtra(RouterKey.FLAG_URL) ?: otherParams["url"]?.toString() ?: ""
    }
    private val htmlData: String by lazy {
        intent.getStringExtra(RouterKey.FLAG_URL_DATA) ?: otherParams["htmlData"]?.toString() ?: ""
    }

    private val js: String by lazy {
        otherParams["js"]?.toString() ?: ""
    }
    protected var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            var titleM = title
            super.onReceivedTitle(view, titleM)
            if (mTitle.isNullOrEmpty()) {
                toolbar?.setTitle(titleM)
            }
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            LogUtils.e("onConsoleMessage  lineNumber = ${consoleMessage?.lineNumber()},message = ${consoleMessage?.message()},sourceID = ${consoleMessage?.sourceId()}")
            return super.onConsoleMessage(consoleMessage)
        }
    }


    val webSettings = object : AbsXWebSettings() {
        private var xWeb: XWeb? = null
        override fun bindWebSupport(xWeb: XWeb) {
            this.xWeb = xWeb
        }
    }


    override fun initView() {
        toolbar?.setBackImage(R.drawable.ic_close_black)
        toolbar?.run {
            setBack(this@H5Activity)
            setTitle(mTitle)
        }
        if (htmlData.isNotEmpty()) {
            xWeb.urlLoader.loadData(htmlData, "text/html; charset=UTF-8", null)
        } else {
            //直接加载url，一定要执行一下
            xWeb
        }
    }

    protected var mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
            LogUtils.e("onLoadResource,url =  ${url}")
        }

        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)
            if (!js.isNullOrEmpty()) {
                xWeb.jsAccessEntrace.callJs(js)
                LogUtils.e("onPageFinished${js}")
            }
        }

        override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
            super.onScaleChanged(view, oldScale, newScale)
            LogUtils.e("onScaleChanged${oldScale},newScale =  ${newScale}")
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