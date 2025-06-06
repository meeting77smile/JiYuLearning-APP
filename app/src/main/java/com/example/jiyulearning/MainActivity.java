package com.example.jiyulearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiyulearning.util.ToastUtil;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取用户名
        LoginActivity loginActivity = new LoginActivity();
        String username = loginActivity.userInfo.username;
        TextView tv_welcome = findViewById(R.id.tv_welcome);
        String welcome = tv_welcome.getText().toString();
        tv_welcome.setText("亲爱的"+username+", "+welcome);

        //别漏了设置点击事件之后，点击事件才能生效
        //点击"每日刷题"按钮
        MaterialCardView card_daily_practice = findViewById(R.id.card_daily_practice);
        card_daily_practice.setOnClickListener(this);
        //点击"账号信息"按钮
        Button btn_account_info = findViewById(R.id.btn_account_info);
        btn_account_info.setOnClickListener(this);
        //点击"退出登录"按钮
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        //点击"事项规划“按钮
        MaterialCardView card_daily_plan = findViewById(R.id.card_daily_plan);
        card_daily_plan.setOnClickListener(this);
        //点击"网页版“按钮
        MaterialCardView  card_web_link = findViewById(R.id.card_web_link);
        card_web_link.setOnClickListener(this);
        //点击"学习资料“按钮
        MaterialCardView  card_learn_res = findViewById(R.id.card_learn_res);
        card_learn_res.setOnClickListener(this);
        //点击"智能问答“按钮
        MaterialCardView  card_smart_qa = findViewById(R.id.card_smart_qa);
        card_smart_qa.setOnClickListener(this);
        //点击”计算器“按钮
        MaterialCardView  card_calculator = findViewById(R.id.card_calculator);
        card_calculator.setOnClickListener(this);
        //点击使用指南
        MaterialCardView  card_guidebood = findViewById(R.id.card_guidebook);
        card_guidebood.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.card_daily_practice) {//点击"每日刷题"按钮
            Intent intent = new Intent(MainActivity.this, DailyPracticeActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_account_info) {//点击"账号信息"按钮
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btn_logout){
            SharedPreferences spInfo = getSharedPreferences("Info", Context.MODE_PRIVATE);
            SharedPreferences.Editor spInfoEditor = spInfo.edit();
            spInfoEditor.remove("loggingInfo");
            spInfoEditor.apply();
            ToastUtil.show(this,"已退出登录~");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(view.getId() == R.id.card_daily_plan){
            Intent intent = new Intent(MainActivity.this, ListPagerActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.card_web_link){
            Intent intent = new Intent(MainActivity.this, WebVersionActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.card_learn_res){
            Intent intent = new Intent(MainActivity.this, LearningResourceActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.card_smart_qa){
            Intent intent = new Intent(MainActivity.this, SmartQaActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.card_calculator){
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.card_guidebook) {
            Intent intent = new Intent(MainActivity.this, GuideBookActivity.class);
            startActivity(intent);
        }
    }
}