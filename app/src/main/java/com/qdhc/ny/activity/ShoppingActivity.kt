package com.qdhc.ny.activity

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.qdhc.ny.R
import com.qdhc.ny.base.BaseActivity
import kotlinx.android.synthetic.main.activity_shopping.*

class ShoppingActivity : BaseActivity() {

    override fun intiLayout(): Int {
        return R.layout.activity_shopping
    }

    override fun initView() {
        webView.settings.javaScriptEnabled = true
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT)
        webView.getSettings().setUseWideViewPort(true)
        webView.getSettings().setLoadWithOverviewMode(true)
        webView.getSettings().setGeolocationEnabled(true)
        webView.getSettings().setDomStorageEnabled(true)
        webView.getSettings().setSupportMultipleWindows(false)
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url)
                return true
            }
        })
        webView.loadUrl("https://www.jd.com")
    }

    override fun initClick() {

    }

    override fun initData() {

    }
}
