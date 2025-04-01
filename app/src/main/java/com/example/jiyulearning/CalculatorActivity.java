package com.example.jiyulearning;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;//设置为类中的全局变量
    private String firstNum = "";//第一个操作数
    private String operator = "";//运算符
    private String secondNum = "";//第二个操作数
    private String result = "";//当前的计算结果
    private String showText = "0";//显示在计算器上的文本内容，不一定是单个数字或符号，可以是数字与符号组成的一大串

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        tv_result = findViewById(R.id.tv_result);
        //下面给每个按钮控件都注册监听器（由于之后用不到按钮的变量，故无需再创建新的按钮控件变量）
        findViewById(R.id.btn_cancle).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_mutiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_sqrt).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String inputText;//按下按钮后输入的内容
        //将按钮对应的文本转化为字符串类型并赋值给inputText
        inputText = ((TextView) view).getText().toString();
            //用于未给按钮创建相应的变量，故需要R.id的方式
        if(view.getId()==R.id.btn_clear){//点击了清除按钮
            clear();
            }
        else if (view.getId()==R.id.btn_cancle) {//点击了回退按钮
            if (!showText.equals("") && !showText.equals("0")){
                if(operator.equals("")){//只有操作数1
                    firstNum=firstNum.substring(0,firstNum.length()-1);
                }
                else {
                    if(!secondNum.equals("")){
                        secondNum=secondNum.substring(0,secondNum.length()-1);
                    }
                }
                refreshText(showText.substring(0,showText.length()-1));
                }
        }
        else if (view.getId()==R.id.btn_sqrt) {//点击了开平方根按钮
            if (!firstNum.equals("")) {
                double calculate_result = Math.sqrt(Double.parseDouble(firstNum));
                refreshOperate(String.valueOf(calculate_result));
                refreshText(showText + "√=" + result);
            }
        }
        else if (view.getId()==R.id.btn_plus || view.getId()==R.id.btn_minus || view.getId()==R.id.btn_mutiply ||view.getId()==R.id.btn_divide) {
            //点击了加减乘除按钮
            if (!showText.equals("")) {
                operator = inputText;
                refreshText(showText + operator);
            }
        }
        else if(view.getId()==R.id.btn_reciprocal) {//点击了求倒数按钮
            if (!firstNum.equals("")) {
                double calculate_result = 1.0 / Double.parseDouble(firstNum);
                refreshOperate(String.valueOf(calculate_result));
                refreshText("1" + "/" + showText + "=" + result);
            }
        }
        else if (view.getId()==R.id.btn_equal) {//点击了等号按钮
            if (!showText.equals("")) {
                if(operator.equals("")){
                    refreshText(showText+"="+showText);
                }
                else {
                    double calculate_result = calculateFour();
                    refreshOperate(String.valueOf(calculate_result));
                    refreshText(showText + "=" + result);
                }
            }
        }
        else{//点击了数字或小数点
                if (operator.equals("")) {//无运算符，则继续拼接第一个操作数（可连续拼接）
                    if((firstNum.equals(".") ||firstNum.equals("0."))&& inputText.equals(".")){//避免同时输入多个小数点
                        inputText="";
                    }
                    else{
                        firstNum = firstNum + inputText;
                    }
                } else {//有运算符，继续拼接第二个操作数（可连续拼接）
                    if((secondNum.equals(".") ||secondNum.equals("0.")) && inputText.equals(".")){//避免同时输入多个小数点
                        inputText="";
                    }
                    else{
                        secondNum = secondNum + inputText;
                    }
                }
                if (showText.equals("0") && !inputText.equals(".")) {//若当前显示的数字只有0，则输入的数字应该覆盖此0；但如果输入的是小数点，
                    //则用户的目的可能是输入小数，此时不覆盖
                    refreshText(inputText);
                }
                else if (showText.equals("0") && inputText.equals(".")) {//一开始时就按下小数点
                    firstNum="0"+".";
                    refreshText(firstNum);
                } else {
                    refreshText(showText + inputText);//写成”showText + “才能实现拼接的效果：是因为前面可能已经有数据了，即showtext（为该类中的全局变量，可存储值）可能已经有值了
                }
        }
    }

    //清除文本显示
    private void clear() {
        refreshOperate("");//顺便借助一下该函数，能把几个参与运算的变量同时清零
        refreshText("");
    }

    //刷新运算结果，实现运算完之后的结果可以继续参与新的运算
    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum=result;
        secondNum="";//待输入，故为空
        operator="";//待输入，故为空
    }

    //更新文本显示
    private void refreshText(String text){
        showText = text;
        tv_result.setText(showText);
    }

    //进行四则运算
    private double calculateFour(){
        if(operator.equals("+")){//parseDouble:将字符串类型转化为double类型
            return Double.parseDouble(firstNum)+Double.parseDouble(secondNum);
        } else if (operator.equals("-")) {
            return Double.parseDouble(firstNum)-Double.parseDouble(secondNum);
        } else if (operator.equals("×")) {
            return Double.parseDouble(firstNum)*Double.parseDouble(secondNum);
        } else if (operator.equals("÷")) {
            return Double.parseDouble(firstNum)/Double.parseDouble(secondNum);
        }
        return 0;
    }
}
