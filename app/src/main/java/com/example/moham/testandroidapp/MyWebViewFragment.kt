package com.example.moham.testandroidapp

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import clock.aut.SingleTon
import com.bulutsoft.attendance.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EncodingUtils
import service.base.MyGlobal


class MyWebViewFragment(var mContext: Context,var isPost:Boolean=false) : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)

        webView = view.findViewById(R.id.webView)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        // Add other WebView settings as needed

        webView.webViewClient=MyWebViewClient();

        // Load the initial URL or restore the state if available
        if (savedInstanceState == null) {

            if (isPost){
                //  String url="http://demo.sobhansystems.ir/mobile/web/index";
                val url = MyGlobal.serverBaseUrlMobile

                val token = "token=" + SingleTon.getInstance().token

                webView.postUrl(url, EncodingUtils.getBytes(token, "UTF-8"))
            }
            else{
                webView.loadUrl("${MyGlobal.serverBase}/Account/Register")
                webView.addJavascriptInterface(MyJavaScriptInterface(mContext), "AndroidBridge")

            }


        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }



    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        }
    }

    private class MyWebViewClient : WebViewClient() {
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // Prevent links from opening in external browser
            view.loadUrl(url,getHeadersWithJwt())
            return true
        }

        private fun getHeadersWithJwt(): MutableMap<String, String> {
            val headers: MutableMap<String, String> = HashMap()
            val token = SingleTon.getInstance().token;
            headers["Authorization"] = "Bearer $token"
            return headers
        }
    }


    class MyJavaScriptInterface(var context: Context?) {


        @JavascriptInterface
        fun goLogin(data: String?) {
            // Handle the received data in Android
            // You can perform any action with the data here


            // Start a new activity
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra("key_data", attr.data) // You can pass data to the new activity

            context!!.startActivity(intent)

            // Optionally, finish the current activity if you don't want to go back to it
            (context as Activity).finish()
        }
    }

}
