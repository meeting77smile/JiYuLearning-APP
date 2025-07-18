package com.example.jiyulearning;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebVersionActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_version);

        webView = findViewById(R.id.web_version);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript支持
        webView.setWebViewClient(new WebViewClient()); // 确保链接在WebView内部打开
        webView.loadUrl("http://118.178.229.132/JiYuLearning/index/chat1.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // 如果可以后退，则返回上一页
        } else {
            super.onBackPressed(); // 否则关闭应用
        }
    }
}
