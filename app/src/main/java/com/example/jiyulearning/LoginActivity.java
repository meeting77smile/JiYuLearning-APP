package com.example.jiyulearning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jiyulearning.database.UserDBHelper;
import com.example.jiyulearning.entity.UserInfo;
import com.example.jiyulearning.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private UserDBHelper mDBHelper;
    private EditText et_account;
    private EditText et_password;
    public static String account;//设置为全局变量：加上static
    public static UserInfo userInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //给rg_login设置单选监听器
        RadioGroup rg_login = findViewById(R.id.rg_login);
        rg_login.setOnCheckedChangeListener(this);

        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);

        //当点击"登录"按钮时
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        //数据库的连接：
        mDBHelper = UserDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if(checkedId == R.id.rb_register){
            //选择注册：
            //跳转到注册页面
            Intent intent = new Intent(this,RegisterActivity.class);
            //防止重复跳转，采取栈顶清空方式
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        //当点击"登录"按钮时
        account = et_account.getText().toString();
        String password = et_password.getText().toString();
        if(account.length()==0){
            ToastUtil.show(this,"账号不能空着~");
            return;
        }
        if(password.length()==0){
            ToastUtil.show(this,"密码不能空着~");
            return;
        }
        if(!mDBHelper.accountExist(account)){
            ToastUtil.show(this,"账号不存在~");
            return;
        }
        userInfo = mDBHelper.queryByAccount(account);
        if(!password.equals(userInfo.password)){
            ToastUtil.show(this,"密码错误,请重新输入~");
            return;
        }
        else{
            ToastUtil.show(this,"登录成功！");
        }
        userInfo.account=account;
        //跳转到主界面
        Intent intent = new Intent(this,MainActivity.class);
        //防止重复跳转，采取栈顶清空方式
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mDBHelper.closeLink();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mDBHelper.closeLink();//关闭数据库的连接
    }
}
