package com.example.jiyulearning;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jiyulearning.adapter.MessageAdapter;
import com.example.jiyulearning.entity.DeepSeekClient;
import com.example.jiyulearning.entity.Message;
import com.example.jiyulearning.entity.UserInfo;
import com.example.jiyulearning.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SmartQaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText etInput;
    private Button btnSend;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private DeepSeekClient deepSeekClient;
    private String InputText;
    private UserInfo user;
    public static String infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_qa);
        LoginActivity loginActivity = new LoginActivity();
        user = loginActivity.userInfo;
        String userLevel="低";
        if(user.level == 2){
            userLevel="中等";
        }
        else if(user.level == 3){
            userLevel="高";
        }
        infoText = "。备注：你的名字叫作”积语“，是陪伴用户学习高数，帮助用户个性化定制学习方案的助手。我是一名大学生，我的高等数学水平较"+userLevel+"，请根据我的水平给出相应的回答";
        initializeViews();
        setupRecyclerView();
        String helloText = "你好呀！我是您学习的得力助手——积语~  很高兴你能来找我聊天~  有什么问题都可以问我，我会根据您的实际情况个性化定制出您的专属答案。学习累了也可以找我谈心噢~";
        addAssistantMessage(helloText);
        setupDeepSeekClient();
        setupSendButton();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        etInput = findViewById(R.id.etInput);
        btnSend = findViewById(R.id.btnSend);
    }

    private void setupRecyclerView() {
        adapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                scrollToBottom());
    }

    private void setupDeepSeekClient() {
        // 从安全存储获取API密钥
        String apiKey = "sk-521f6f1af82e497cac40afdf25757e89";
        deepSeekClient = new DeepSeekClient(apiKey);
    }

    private void setupSendButton() {
        btnSend.setOnClickListener(v -> handleSendMessage());
    }

    private void handleSendMessage() {
        String inputText = etInput.getText().toString().trim();
        InputText = "以下是根据您的情况个性化定制的解决方案:\n";
        if (inputText.isEmpty()) {
            ToastUtil.show(this,"发送的消息不能为空~");
            return;
        }
        addUserMessage(inputText);
        sendToDeepSeek(inputText);
    }

    private void addUserMessage(String text) {
        Message userMessage = new Message(text, Message.TYPE_USER);
        messageList.add(userMessage);
        adapter.notifyItemInserted(messageList.size() - 1);
        //利用对话框来显示加载提示词
        etInput.setText("正在为您个性化定制解决方案...");
        scrollToBottom();
    }

    private void sendToDeepSeek(String input) {
        //在每条发送给ai的消息后面都加上备注
        //messageList.get(messageList.size()-1).setContent(input+infoText);
        deepSeekClient.sendChatRequest(messageList, new DeepSeekClient.ChatCallback() {
            @Override
            public void onSuccess(String response) {
                //在Android开发中，runOnUiThread是一个在Activity中使用的方法，它允许你在UI线程（主线程）中执行代码块。
                //由于Android不允许在非UI线程中直接操作UI组件，runOnUiThread成为了在子线程中更新UI的一个简便方法。
                //runOnUiThread()的常规写法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //情况对话框
                        etInput.setText("");
                        addAssistantMessage(InputText+response);
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

    private void addAssistantMessage(String content) {
        Message assistantMessage = new Message(content, Message.TYPE_ASSISTANT);
        messageList.add(assistantMessage);
        adapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        if (messageList.isEmpty()) return;
        recyclerView.smoothScrollToPosition(messageList.size() - 1);
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        // 可选：移除最后一条用户消息作为回滚
        if (!messageList.isEmpty() && messageList.get(messageList.size()-1).getType() == Message.TYPE_USER) {
            messageList.remove(messageList.size()-1);
        }
    }
}