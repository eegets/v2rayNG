package com.v2ray.ang.custom.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.blankj.utilcode.util.ActivityUtils
import com.forest.bss.sdk.base.act.BaseActivity
import com.forest.bss.sdk.log.logger
import com.v2ray.ang.R
import com.v2ray.ang.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity() {

    var url: String? = null

    var title: String? = null

    private var binding: ActivityWebViewBinding? = null

    override fun layoutId() = R.layout.activity_web_view

    override fun initView() {
        binding?.webViewTitle?.text = "加载中..."

        url = bundle.getString("url").toString()

        title = bundle.getString("title").toString()
        if (url?.isNotEmpty() == true) {
            binding?.webView?.loadUrl(url!!)
        }

        binding?.webView?.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (title != null && (!title.contains("https") || !title.contains("http"))) {
                    binding?.webViewTitle?.text = view?.title
                    logger { "webview onReceivedTitle ${view?.title}" }
                }
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                logger { "WebViewActivity onConsoleMessage ${consoleMessage?.message()}" }
                return super.onConsoleMessage(consoleMessage)
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                val requestedResources = request?.resources
                if (requestedResources != null) {
                    for (r in requestedResources) {
                        if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) { //webView支持相机权限
                            request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                        }
                    }
                }
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
                callback?.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
        }

        binding?.webView?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_DOWN) {
                // 监听手机的返回键
                if (keyCode == KeyEvent.KEYCODE_BACK && binding?.webView?.canGoBack() == true) {
                    binding?.webView?.goBack()
                    return@OnKeyListener true
                } else {
                    binding?.webView?.removeAllViews()
                    binding?.webView?.destroy()
                    finish()
                }
            }
            false
        })

        binding?.closeIcon?.setOnClickListener {
            binding?.webView?.removeAllViews()
            binding?.webView?.destroy()
            finish()
        }

        binding?.backIcon?.setOnClickListener {
            if (binding?.webView?.canGoBack() == true) {
                binding?.webView?.goBack()
            } else {
                binding?.webView?.removeAllViews()
                binding?.webView?.destroy()
                finish()
            }
        }

        binding?.webViewRefresh?.setOnClickListener {
            binding?.webView?.reload()
        }
    }

    override fun isEnableViewBinding() = true

    override fun viewBinding(): View? {
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        return binding?.root
    }

    companion object {

        private var bundle: Bundle = Bundle()

        fun start(activity: Activity, url: String, title: String? = null) {
            Bundle().apply {
                this.putString("url", url)
                this.putString("title", title)
            }.also {
                bundle = it
                ActivityUtils.startActivity(activity, WebViewActivity::class.java, it)
            }
        }
    }
}