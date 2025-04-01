package com.example.jiyulearning;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jiyulearning.database.ListDBHelper;
import com.example.jiyulearning.entity.DeepSeekClient;
import com.example.jiyulearning.entity.ListInfo;
import com.example.jiyulearning.entity.Message;
import com.example.jiyulearning.entity.UserInfo;
import com.example.jiyulearning.util.DateUtil;
import com.example.jiyulearning.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_date_list;
    private TextView tv_date_list_2;
    private Calendar calendar;
    private EditText et_des_list;
    private EditText et_time;
    private ListDBHelper mDBHelper;

    private List<Message> messageList = new ArrayList<>();//存放发送给ai的问题
    private DeepSeekClient deepSeekClient;
    private UserInfo user;
    public static String infoText;

    private String infoToAi="请给我有且仅有一条精简且实际的今日学习事项规划,要求规划科学且创新，不包含类似‘今日学习规划：’的内容和与时间有关的内容，只包含规划的内容";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_title.setText("安排事项");
        tv_option.setText("待做事项");
        tv_date_list = findViewById(R.id.tv_date_list);
        tv_date_list_2 = findViewById(R.id.tv_date_list_2);
        et_des_list = findViewById(R.id.et_des_list);//事项描述
        et_time = findViewById(R.id.et_time);//所需时间
        findViewById(R.id.btn_save_list).setOnClickListener(this);
        findViewById(R.id.btn_delete_list).setOnClickListener(this);
        findViewById(R.id.btn_update_list).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        //显示当前日期
        calendar = Calendar.getInstance();
        tv_date_list.setText(DateUtil.getDate(calendar));
        //点击弹出日期对话框
        tv_date_list.setOnClickListener(this);//TextView也可以设置点击事件
        tv_date_list_2.setOnClickListener(this);//TextView也可以设置点击事件
        //当点击”待做事项”时：
        tv_option.setOnClickListener(this);
        //当点击返回图标时：
        findViewById(R.id.iv_back).setOnClickListener(this);
        //当点击个性化规划时:
        findViewById(R.id.btn_smart_plan).setOnClickListener(this);

        //获取用户水平
        LoginActivity loginActivity = new LoginActivity();
        user = loginActivity.userInfo;
        String userLevel="低";
        if(user.level == 2){
            userLevel="中等";
        }
        else if(user.level == 3){
            userLevel="高";
        }
        infoText = "。备注：我是一名大学生，我的高等数学水平较"+userLevel+"，请根据我的水平给出相应的回答";

        //数据库的连接：
        mDBHelper = ListDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

    }

    @Override
    public void onClick(View view) {
        String date = tv_date_list.getText().toString();
        String des = et_des_list.getText().toString();
        String time = et_time.getText().toString();
        if(view.getId() == R.id.tv_date_list || view.getId() == R.id.tv_date_list_2){//点击日期
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get((Calendar.DAY_OF_MONTH)));
            dialog.show();
        }
        else if(view.getId() == R.id.btn_save_list){//点击保存按钮
            if(des.length() == 0){
                ToastUtil.show(this,"事项内容不可为空~");
                return;
            }
            if(time.length() == 0){
                ToastUtil.show(this,"事项完成时间不可为空~");
                return;
            }
            ListInfo listInfo = new ListInfo();
            listInfo.date = tv_date_list.getText().toString();//此时日期用的是字符串类型的
            listInfo.description = et_des_list.getText().toString();
            listInfo.time = Double.parseDouble(et_time.getText().toString());
            if(mDBHelper.save(listInfo) > 0){
                ToastUtil.show(this,"添加事项成功！");
            }
        }
        else if(view.getId() == R.id.tv_option){//跳转到事项列表页面
            Intent intent = new Intent(this,ListPagerActivity.class);
            //防止重复跳转，采取栈顶清空方式
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(view.getId() == R.id.iv_back){//点击返回图片
            finish();//关闭当前界面
        }
        else if(view.getId() == R.id.btn_delete_list){//点击删除按钮:根据事项说明remark删除账单
            if(des.length() == 0){
                ToastUtil.show(this,"事项内容不可为空~");
                return;
            }
            long del_num = mDBHelper.deleteByDes(des);
            if(del_num > 0){//>0 表示至少一行被删去
                ToastUtil.show(this,"删除事项成功！");
                ListPagerActivity.finished_list+= (int)del_num;//计算已完成事项
            }
        }
        else if(view.getId() == R.id.btn_update_list){//点击修改按钮
            if(des.length() == 0){
                ToastUtil.show(this,"事项内容不可为空~");
                return;
            }
            if(time.length() == 0){
                ToastUtil.show(this,"事项完成时间不可为空~");
                return;
            }
            ListInfo listInfo = new ListInfo(date,Double.valueOf(time),des);
            if(mDBHelper.updata(listInfo) > 0){
                ToastUtil.show(this,"修改事项成功！");
            }
        }
        else if(view.getId() == R.id.btn_clear){//点击清空按钮
            long num = mDBHelper.deleteAll();
            if(num > 0){//>0 表示至少一行被删去
                ToastUtil.show(this,"清空事项成功！");
                ListPagerActivity.finished_list+= (int)num;//计算已完成事项
            }
        }
        else if(view.getId() == R.id.btn_smart_plan){//点击个性化规划按钮
            ToastUtil.show(ListAddActivity.this,"正在为您个性化定制，请稍后...");
            Message assistantMessage = new Message(infoText+infoToAi, Message.TYPE_ASSISTANT);
            messageList.add(assistantMessage);
            // 从安全存储获取API密钥
            String apiKey = "sk-521f6f1af82e497cac40afdf25757e89";
            deepSeekClient = new DeepSeekClient(apiKey);
            deepSeekClient.sendChatRequest(messageList, new DeepSeekClient.ChatCallback() {
                @Override
                public void onSuccess(String response) {
                    //在Android开发中，runOnUiThread是一个在Activity中使用的方法，它允许你在UI线程（主线程）中执行代码块。
                    //由于Android不允许在非UI线程中直接操作UI组件，runOnUiThread成为了在子线程中更新UI的一个简便方法。
                    //runOnUiThread()的常规写法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //response即为ai返回的对话
                            ListInfo listInfo = new ListInfo();
                            listInfo.date = tv_date_list.getText().toString();//此时日期用的是字符串类型的
                            listInfo.description = response;
                            if(response.length() >= 40){
                                listInfo.time = 60.0;
                            }else {
                                listInfo.time = 30.0;
                            }
                            if(mDBHelper.save(listInfo) > 0){
                                ToastUtil.show(ListAddActivity.this,"已添加个性化规划！");
                            }
                        }
                    });
                }

                //runOnUiThread()的Lambda表达式写法：
                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> showError(error));
                }
            });
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year,int month,int dayOfMonth) {
        //设置给文本显示（显示的是年月日）
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tv_date_list.setText(DateUtil.getDate(calendar));
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        // 可选：移除最后一条用户消息作为回滚
        if (!messageList.isEmpty() && messageList.get(messageList.size()-1).getType() == Message.TYPE_USER) {
            messageList.remove(messageList.size()-1);
        }
    }
/*    @Override
    protected void onDestroy() {
        //覆写onDestroy方法
        super.onDestroy();
        mDBHelper.closeLink();//关闭数据库的连接
    }*/
}
