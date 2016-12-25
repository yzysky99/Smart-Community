package com.stev.smart_community.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stev.smart_community.Constants;
import com.stev.smart_community.R;

public class WebviewUI extends Activity {
    private static final String TAG = "WebviewUI";

    WebView webView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_webview_ui);
        Intent intent = getIntent();
        String detailUrl = intent.getStringExtra(Constants.CommonInfo.DETAIL_URL);
        webView = (WebView) findViewById(R.id.webView);

        Log.d(TAG,"detailUrl = " + detailUrl);
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
//        settings.setGeolocationEnabled(true);
//        String dir = mContext.getCacheDir()+"/baidudata";
//        settings.setGeolocationDatabasePath(dir);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);// 允许请求JS
        settings.setBuiltInZoomControls(true);
        webView.loadUrl(detailUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("http:") || url.startsWith("https:")) {
//                    view.loadUrl(url);
//                }
                Log.d(TAG, "stevyang " + url);
                if (url.startsWith("tel:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
