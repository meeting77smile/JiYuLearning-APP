package com.example.jiyulearning;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class DailyPracticeActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_practice);


        //根据用户账号拿到用户的水平信息：
        LoginActivity loginActivity = new LoginActivity();
        int level = loginActivity.userInfo.level;
        webView = findViewById(R.id.webview_practice);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript支持
        webView.setWebViewClient(new WebViewClient()); // 确保链接在WebView内部打开
        if(level == 2){//跳转到中等水平题库的页面
            webView.loadUrl("http://118.178.229.132/JiYuLearning/daily_homework/middle_question/middle_question.html"); // 中等题型的页面
        }
        else if(level == 3){//跳转到高手水平题库的页面
            webView.loadUrl("http://118.178.229.132/JiYuLearning/daily_homework//hard_question/hard_question.html"); // 困难题型的页面
        }
        else{//跳转到新手水平题库的页面
            webView.loadUrl("http://118.178.229.132/JiYuLearning/daily_homework//simple_question/simple_question.html"); // 默认为简单题型的页面
        }
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
