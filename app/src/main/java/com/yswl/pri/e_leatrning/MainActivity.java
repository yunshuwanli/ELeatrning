package com.yswl.pri.e_leatrning;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends BaseBrowerActivity {

    String URL = "http://114.67.230.101:8080/online/app/app/index/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView.loadUrl(URL);
    }

    @Override
    WebChromeClient getCustomWebChromeClient() {
        return new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (null != mPageLoadingProgressBar)
                    mPageLoadingProgressBar.setProgress(i);
            }
        };
    }

    @Override
    WebViewClient getCustomWebViewClient() {
        return new WebViewClient(){
            @Override
            public void onLoadResource(WebView webView, String url) {
                super.onLoadResource(webView,url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                return super.shouldInterceptRequest(webView, s);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return super.shouldOverrideUrlLoading(webView, s);
            }
        };
    }
}
