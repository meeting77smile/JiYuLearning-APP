package com.example.jiyulearning.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message implements Serializable {//存储用户与大模型对话的消息类
    public static final int TYPE_USER = 0;//表示该对话为用户发送
    public static final int TYPE_ASSISTANT = 1;//表示该对话为大模型发送
    public static final int STATUS_SENDING = 0;//表示正在发送消息给大模型
    public static final int STATUS_SENT = 1;//表示发送消息给大模型成功
    public static final int STATUS_FAILED = 2;//表示发送消息给大模型失败

    private String content;//消息的内容
    private final int type;//消失的种类
    private final long timestamp;//表示发送/接收当前消息所对应的时间
    private int status;//消息的状态

    public Message(String content, int type) {
        this(content, type, System.currentTimeMillis(), type == TYPE_USER ? STATUS_SENDING : STATUS_SENT);
    }

    public Message(String content, int type, long timestamp, int status) {
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Getters and status methods
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getType() { return type; }
    public long getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public boolean isSending() { return status == STATUS_SENDING; }
    public boolean isSent() { return status == STATUS_SENT; }
    public boolean isFailed() { return status == STATUS_FAILED; }

    public String getFormattedTime() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(timestamp));
    }
}