package com.yswl.pri.e_leatrning;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;


public class BaseBrowerActivity extends MActivity {
    public static FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    public static FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 6);

    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    protected X5WebView mWebView;
    protected WebViewProgressBar mPageLoadingProgressBar = null;
    protected ViewGroup mViewParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_browser);
        setHardware();
        initUI();

    }


    private void initUI() {
        mViewParent = findViewById(R.id.webView1);
        mWebView = new X5WebView(this, null);
        mPageLoadingProgressBar = new WebViewProgressBar(this);// new
        mViewParent.addView(mWebView, lp2);
        mViewParent.addView(mPageLoadingProgressBar, lp);
        setWebChromeClient(mWebView);
        setWebViewClient(mWebView);
    }

    public void setWebViewClient(X5WebView webView) {
        if (getCustomWebViewClient() != null) {
            webView.setWebViewClient(getCustomWebViewClient());
        }
    }

    void setWebChromeClient(X5WebView webView) {
        if (getCustomWebChromeClient() != null)
            webView.setWebChromeClient(getCustomWebChromeClient());
    }

    WebChromeClient getCustomWebChromeClient() {
        return null;
    }

    WebViewClient getCustomWebViewClient() {
        return null;
    }


    /**
     * 硬件加速
     */
    private void setHardware() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow().setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception ignored) {
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.stopLoading();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }


}
