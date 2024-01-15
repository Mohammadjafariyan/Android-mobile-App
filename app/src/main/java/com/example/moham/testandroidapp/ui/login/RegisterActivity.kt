package com.example.moham.testandroidapp.ui.login

import android.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.moham.testandroidapp.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        webView = binding.registerWebView!!

        // Enable JavaScript (if needed)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true


        // Set a WebViewClient to handle links within the WebView
        webView.webViewClient = MyWebViewClient()

        // Load a web page
     //   webView.loadUrl("http://localhost:53817/Account/Register")
        webView.loadUrl("http://100.66.70.166:81/Account/Register")

    }
    private class MyWebViewClient : WebViewClient() {
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // Prevent links from opening in external browser
            view.loadUrl(url)
            return true
        }
    }


}

