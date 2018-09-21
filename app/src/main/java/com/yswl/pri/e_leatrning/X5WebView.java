package com.yswl.pri.e_leatrning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.LocaleList;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.util.Locale;

public class X5WebView extends WebView {

    public X5WebView(Context arg0) {
        super(arg0);
        initWebViewSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
//		 WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
    }


    private void initWebViewSettings() {
        this.getView().setClickable(true);
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        this.addJavascriptInterface(new LanguEngine(), "android_language");
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //setSupportMultipleWindows 默人false 不支持新窗口
        // 设置true 必须重写WebChromeClient的onCreateWindow方法
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MIN_VALUE);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setAppCachePath(getContext().getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(getContext().getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(getContext().getDir("geolocation", 0).getPath());
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计

        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        webSetting.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSetting.setDatabaseEnabled(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        webSetting.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSetting.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSetting.setAppCacheEnabled(true);


        CookieSyncManager.createInstance(getContext());
        CookieSyncManager.getInstance().sync();

    }

    // 继承自Object类
    public class LanguEngine extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public String getSysCurrLangu(String msg) {
//            System.out.println("JS调用了Android的hello方法");
            return getLangu();
        }
    }

    private String getLangu() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String language = locale.getLanguage() + "-" + locale.getCountry();
        return language;
    }

    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static final String TAG = "Webview";

    public void clearWebViewCache(Context context) {

        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(context.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath() + "/webviewCache");
        Log.e(TAG, "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

	/*@Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		boolean ret = super.drawChild(canvas, child, drawingTime);
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0x7fff0000);
		paint.setTextSize(24.f);
		paint.setAntiAlias(true);
		if (getX5WebViewExtension() != null) {
			canvas.drawText(this.getContext().getPackageName() + "-pid:"
					+ android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText(
					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
					100, paint);
		} else {
			canvas.drawText(this.getContext().getPackageName() + "-pid:"
					+ android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText("Sys Core", 10, 100, paint);
		}
		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
		canvas.drawText(Build.MODEL, 10, 200, paint);
		canvas.restore();
		return ret;
	}*/


}
