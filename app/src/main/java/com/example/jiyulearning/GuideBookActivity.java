package com.example.jiyulearning;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuideBookActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidebook);
        webView = findViewById(R.id.web_guidebood);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript支持
        webView.setWebViewClient(new WebViewClient()); // 确保链接在WebView内部打开
        webView.loadUrl("http://118.178.229.132/JiYuLearning/src/Guidebook.html");
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
