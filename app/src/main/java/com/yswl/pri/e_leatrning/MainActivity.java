package com.yswl.pri.e_leatrning;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Locale;

public class MainActivity extends BaseBrowerActivity {

    String URL = "http://114.67.230.101:8080/online/app/app/index/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(URL);
            }
        }, 100);
//        getLangu();
    }
    private String getLangu() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String language = locale.getLanguage() + "-" + locale.getCountry();
        return language;
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
            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                return true;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView!=null){
            mWebView.clearWebViewCache(this);
            mWebView.clearCache(true);
        }
    }
}
