package com.example.jiyulearning

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jiyulearning.entity.UserInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmartQaTest {
    private lateinit var activityScenario: ActivityScenario<SmartQaActivity>

    @Before
    fun setUp() {
        val testUserInfo = UserInfo()
        testUserInfo.username = "TestUser"
        testUserInfo.account = "testAccount"
        testUserInfo.password = "testPassword"
        testUserInfo.level = 1

        LoginActivity.userInfo = testUserInfo

        activityScenario = ActivityScenario.launch(SmartQaActivity::class.java)
    }

    @Test
    fun testWelcomeMessageDisplayed() {
        // 验证欢迎消息是否显示
        onView(withText("你好呀！我是您学习的得力助手——积语~  很高兴你能来找我聊天~  有什么问题都可以问我，我会根据您的实际情况个性化定制出您的专属答案。学习累了也可以找我谈心噢~"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSendEmptyMessage() {
        // 点击发送按钮，不输入消息
        onView(withId(R.id.btnSend)).perform(click())

        // 验证是否弹出提示消息
        // onView(withText("发送的消息不能为空~")).check(matches(isDisplayed()))
    }

    @Test
    fun testSendMessage() {
        // 输入消息
        val testMessage = "这是一条测试消息"
        onView(withId(R.id.etInput)).perform(replaceText(testMessage))

        // 点击发送按钮
        onView(withId(R.id.btnSend)).perform(click())

        // 验证输入框替换为特定字符串
        onView(withId(R.id.etInput)).check(matches(withText("正在为您个性化定制解决方案...")))

        // 验证消息是否添加到列表中
        // 这里假设消息列表会显示发送的消息
        onView(withText(testMessage)).check(matches(isDisplayed()))
    }
}