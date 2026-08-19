package com.yourapp.knightgame;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        WebView myWebView = findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 允许JS运行
        webSettings.setDomStorageEnabled(true); // 允许本地存储

        // 重点：因为你的HTML存在 HTML/index.html，这里指向你项目里的本地路径
        myWebView.loadUrl("file:///android_asset/HTML/index.html"); 
        myWebView.setWebViewClient(new WebViewClient()); // 确保在应用内打开，不跳浏览器
    }
}