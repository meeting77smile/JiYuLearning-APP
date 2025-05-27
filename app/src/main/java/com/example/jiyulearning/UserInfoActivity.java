package com.example.jiyulearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jiyulearning.database.UserDBHelper;
import com.example.jiyulearning.entity.UserInfo;
import com.example.jiyulearning.util.ToastUtil;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    //别在此处初始化赋值，因为先执行的是onCreate方法
    private UserDBHelper mDBHelper;
    private EditText et_update_level;
    private EditText et_pre_password;
    private EditText et_new_password;
    private EditText et_confirm_password;
    private EditText et_username;
    private LoginActivity loginActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //从LoginActivity类中获得当前账号所对应的用户信息
        loginActivity = new LoginActivity();
        //显示用户名、账号、水平等信息
        et_username = findViewById(R.id.et_username);
        et_username.setText(loginActivity.userInfo.username);
        TextView tv_account = findViewById(R.id.tv_account);
        tv_account.setText(loginActivity.userInfo.account);
        et_update_level = findViewById(R.id.et_update_level);
        et_update_level.setText(String.valueOf(loginActivity.userInfo.level));

        //当点击“保存更改”按钮
        findViewById(R.id.btn_save).setOnClickListener(this);
        //当点击“返回”按钮时:
        findViewById(R.id.btn_back).setOnClickListener(this);

        //数据库的连接：
        mDBHelper = UserDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save){
            //获取用户新输入的用户名
            String username = et_username.getText().toString();
            //获取用户新输入的水平
            String level = et_update_level.getText().toString();
            //获取用户新输入的密码
            et_pre_password = findViewById(R.id.et_pre_password);
            et_new_password = findViewById(R.id.et_new_password);
            et_confirm_password = findViewById(R.id.et_confirm_password);
            String pre_password = et_pre_password.getText().toString();
            String new_password = et_new_password.getText().toString();
            String confirm_password = et_confirm_password.getText().toString();
            if(username.length()==0){
                ToastUtil.show(this,"用户名不可为空~");
                return;
            }
            if(level.length()==0){
                ToastUtil.show(this,"用户水平不可为空~");
                return;
            }
            if(!new_password.equals(confirm_password)){
                ToastUtil.show(this,"两次密码输入不一致~");
                return;
            }
            if(new_password.length() != 0) {//当用户输入新密码时，才会修改密码
                if(!pre_password.equals(loginActivity.userInfo.password)){//原密码输入错误
                    ToastUtil.show(this,"原密码输入错误~");
                    return;
                }
                loginActivity.userInfo.password = new_password;
            }
            //修改无误，更新用户信息
            loginActivity.userInfo.username = username;
            loginActivity.userInfo.level = Integer.parseInt(level);


            if(mDBHelper.updata(loginActivity.userInfo)>0){
                ToastUtil.show(this,"用户信息已更新~");
            }
        }
        else if(view.getId() == R.id.btn_back){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }
}
