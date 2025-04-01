package com.example.jiyulearning.entity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jiyulearning.SmartQaActivity;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeepSeekClient {
    private static final String TAG = "DeepSeekClient";
    private static final String BASE_URL = "https://api.deepseek.com/v1";
    private final OkHttpClient client;
    private final String apiKey;

    public interface ChatCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public DeepSeekClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new AuthInterceptor(apiKey))
                .build();
    }

    public void sendChatRequest(List<Message> history, ChatCallback callback) {
        try {
            JSONArray messages = buildMessageArray(history);
            Request request = buildRequest(messages);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(TAG, "API请求失败: " + e.getMessage());
                    callback.onFailure("网络错误: " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        handleErrorResponse(response, callback);
                        return;
                    }

                    try {
                        String responseBody = response.body().string();
                        String content = parseResponseContent(responseBody);
                        callback.onSuccess(content);
                    } catch (JSONException e) {
                        callback.onFailure("响应解析错误");
                    }
                }
            });

        } catch (JSONException e) {
            callback.onFailure("请求构造错误");
        }
    }

    private JSONArray buildMessageArray(List<Message> history) throws JSONException {
        JSONArray messages = new JSONArray();
        SmartQaActivity smartQaActivity = new SmartQaActivity();
        //在每条发送给ai的消息后面都加上备注
        String info = smartQaActivity.infoText;
        for (Message msg : history) {
            JSONObject message = new JSONObject();
            message.put("role", msg.getType() == Message.TYPE_USER ? "user" : "assistant");
            message.put("content", msg.getContent()+info);
            messages.put(message);
        }
        return messages;
    }

    private Request buildRequest(JSONArray messages) throws JSONException {
        JSONObject body = new JSONObject()
                .put("model", "deepseek-chat")
                .put("messages", messages)
                .put("temperature", 0.7);

        return new Request.Builder()
                .url(BASE_URL + "/chat/completions")
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();
    }

    private String parseResponseContent(String responseBody) throws JSONException {
        JSONObject json = new JSONObject(responseBody);
        return json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

    private void handleErrorResponse(Response response, ChatCallback callback) throws IOException {
        String errorBody = response.body().string();
        Log.e(TAG, "API错误响应: " + errorBody);
        try {
            JSONObject errorJson = new JSONObject(errorBody);
            String errorMsg = errorJson.optString("message", "未知错误");
            callback.onFailure("API错误: " + errorMsg);
        } catch (JSONException e) {
            callback.onFailure("HTTP错误: " + response.code());
        }
    }

    private static class AuthInterceptor implements Interceptor {
        private final String apiKey;

        AuthInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }
    }
}