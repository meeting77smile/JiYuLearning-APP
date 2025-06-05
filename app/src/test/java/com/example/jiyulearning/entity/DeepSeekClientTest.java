package com.example.jiyulearning.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DeepSeekClientTest {

    private String testApiKey = "sk-521f6f1af82e497cac40afdf25757e89";

    @Test
    public void testSendChatRequest() throws Exception {
        // 实际调用被测试的方法
        DeepSeekClient client = new DeepSeekClient(testApiKey);
        client.sendChatRequest(Collections.singletonList(new Message("Hello! 如果你能做出回应，请只回复`Hello, world!`不要有其他多余的字符。", Message.TYPE_USER)), new DeepSeekClient.ChatCallback() {
            @Override
            public void onSuccess(String response) {
                assertEquals("Hello, world!", response);
            }

            @Override
            public void onFailure(String error) {
                fail("Request failed");
            }
        });

    }
}