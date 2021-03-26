package com.example.shoppingapplication.activities.homeScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.shoppingapplication.R


/**
 * A simple [Fragment] subclass.
 * Use the [WebView.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebView : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_web_view, container, false)

        val url="https://android-developers.googleblog.com/"
        val webView: WebView =view.findViewById(R.id.webView)

        webView.settings.javaScriptEnabled=true
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    webView.loadUrl(url)
                }
                return true
            }
        }
        webView.loadUrl(url)


        return view
    }


}

