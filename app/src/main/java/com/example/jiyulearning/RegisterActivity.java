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

public class RegisterActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private UserDBHelper mDBHelper;
    private EditText et_username;
    private EditText et_level;
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_confirm;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //给rg_login设置单选监听器
        RadioGroup rg_login = findViewById(R.id.rg_login);
        rg_login.setOnCheckedChangeListener(this);

        et_username = findViewById(R.id.et_username);
        et_level = findViewById(R.id.et_level);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_password_confirm = findViewById(R.id.et_password_confirm);

        //当点击"注册"时:
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        //数据库的连接：
        mDBHelper = UserDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if(checkedId == R.id.rb_login){
            //选择注册：
            //跳转到注册页面
            Intent intent = new Intent(this,LoginActivity.class);
            //防止重复跳转，采取栈顶清空方式
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        //当点击"注册"时:
        String username = et_username.getText().toString();
        String level = et_level.getText().toString();
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        String password_confirm = et_password_confirm.getText().toString();
        if(username.length()==0){
            ToastUtil.show(this,"用户名不能空着~");
            return;
        }
        if(account.length()==0){
            ToastUtil.show(this,"账号不能空着~");
            return;
        }
        if(password.length()==0){
            ToastUtil.show(this,"密码不能空着~");
            return;
        }
        if(!password.equals(password_confirm)){
            ToastUtil.show(this,"两次密码不一致,请重新输入!");
            return;
        }
        if(mDBHelper.accountExist(account)){
            ToastUtil.show(this,"账号已存在,换个账号名吧~");
            return;
        }
        //密码、账号均合法，可以创建新用户
        UserInfo user = new UserInfo();
        user.account = account;
        user.username = username;
        user.password = password;
        user.level = Integer.parseInt(level);
        if(mDBHelper.save(user) > 0){
            ToastUtil.show(this,"账号创建成功！");
        }
    }

    @Override
    protected void onDestroy() {
        //只在主界面写一次即可
        //覆写onDestroy方法
        super.onDestroy();
        //mDBHelper.closeLink();//关闭数据库的连接
    }
}
