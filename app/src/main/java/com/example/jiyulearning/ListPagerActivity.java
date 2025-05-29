package com.example.jiyulearning;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.jiyulearning.adapter.ListPagerAdapter;
import com.example.jiyulearning.database.ListDBHelper;
import com.example.jiyulearning.util.DateUtil;

import java.util.Calendar;

public class ListPagerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_day;
    private Calendar calendar;
    private ViewPager vp_list;
    private ListDBHelper mDBHelper;
    private TextView tv_finished;
    private TextView tv_todo;
    private TextView tv_sum_time;
    private TextView tv_date_list;
    private TextView tv_select;

    public static int finished_list=0;
    public static int todo_list=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pager);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_sum_time = findViewById(R.id.tv_sum_time);
        tv_date_list = findViewById(R.id.tv_date_list);
        tv_select = findViewById(R.id.tv_select);
        tv_title.setText("事项列表");
        tv_option.setText("管理事项");
        tv_finished = findViewById(R.id.tv_finished);
        tv_todo = findViewById(R.id.tv_todo);
        tv_day = findViewById(R.id.tv_day);
        //显示当前日期
        calendar = Calendar.getInstance();
        tv_day.setText(DateUtil.getMonth(calendar));
        tv_date_list.setText("日期："+DateUtil.getDay(calendar));
        //点击弹出日期对话框
        tv_day.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        tv_date_list.setOnClickListener(this);
        //当点击”添加账单”时：
        tv_option.setOnClickListener(this);
        //当点击返回图标时：
        findViewById(R.id.iv_back).setOnClickListener(this);
        //数据库的连接：
        mDBHelper = ListDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();
        //计算未完成事项（即当前页面的事项数）
        todo_list = mDBHelper.getListSize(DateUtil.getDate(calendar));
        tv_finished.setText("已完成："+String.valueOf(finished_list)+"项");
        tv_todo.setText("未完成："+String.valueOf(todo_list)+"项");
        double sum = mDBHelper.getAllTime(DateUtil.getDate(calendar));
        tv_sum_time.setText(String.format("还需：%d分钟",(int)sum));
        //初始化翻页视图
        initViewPager();
    }

    //初始化翻页视图
    private void initViewPager(){
        //从布局视图中获取名为pts_list的翻页标签栏
        PagerTabStrip pts_list = findViewById(R.id.pts_list);
        //设置翻页标签栏的文本大小
        pts_list.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        vp_list = findViewById(R.id.vp_list);
        //设置适配器
        String date=DateUtil.getMonth(calendar);
        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager(),date);
        vp_list.setAdapter(adapter);
        vp_list.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH)-1);//设置默认日期
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_day || view.getId() == R.id.tv_select){
            //弹出日期对话框
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get((Calendar.DAY_OF_MONTH)));
            dialog.show();
            double sum = mDBHelper.getAllTime(DateUtil.getDate(calendar));
            todo_list = mDBHelper.getListSize(DateUtil.getDate(calendar));
            tv_finished.setText("已完成："+String.valueOf(finished_list)+"项");
            tv_todo.setText("未完成："+String.valueOf(todo_list)+"项");
            tv_sum_time.setText("还需："+String.valueOf((sum))+"分钟");
            tv_date_list.setText("日期："+DateUtil.getDay(calendar));

        }
        else if(view.getId() == R.id.tv_option){
            //跳转到管理账单界面
            Intent intent = new Intent(this,ListAddActivity.class);
            //防止重复跳转，采取栈顶清空方式
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(view.getId() == R.id.iv_back){
            finish();//关闭当前界面
        }
//        else if(view.getId() == R.id.tv_date){
//            //弹出日期对话框
//            DatePickerDialog dialog = new DatePickerDialog(this, this,
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get((Calendar.DAY_OF_MONTH)));
//            dialog.show();
//            double income = mDBHelper.getIncomeSum(DateUtil.getMonth(calendar));
//            double cost = mDBHelper.getCostSum(DateUtil.getMonth(calendar));
//            double sum = income-cost;
//            tv_income.setText(String.format("收入：%d",(int)income));
//            tv_cost.setText(String.format("支出：%d",(int)cost));
//            tv_sum.setText(String.format("净收入：%d",(int)sum));
//            tv_date.setText(DateUtil.getMonth(calendar));
//        }
    }

    @Override
    public void onDateSet(DatePicker view, int year,int month,int dayOfMonth) {
        //设置给文本显示（显示的是年和月）
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tv_day.setText(DateUtil.getMonth(calendar));
        //设置翻页视图显示第几页
        vp_list.setCurrentItem(dayOfMonth-1);
    }
    @Override
    protected void onDestroy() {
        //只在主界面写一次即可
        //覆写onDestroy方法
        super.onDestroy();
        mDBHelper.closeLink();//关闭数据库的连接
    }
}
