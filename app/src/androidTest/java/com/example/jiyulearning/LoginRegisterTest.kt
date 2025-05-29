package com.example.jiyulearning

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginRegisterTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginRegisterFlow() {
        // 从登录页跳转到注册页
        onView(withId(R.id.rb_register)).perform(click())
        // 验证是否成功跳转到注册页
        onView(withId(R.id.et_username)).check(matches(isDisplayed()))

        // 在注册页输入注册信息
        val username = "testUser"
        val level = "1"
        val account = "testAccount"
        val password = "testPassword"

        onView(withId(R.id.et_username)).perform(typeText(username))
        onView(withId(R.id.et_level)).perform(typeText(level))
        onView(withId(R.id.et_account)).perform(typeText(account))
        onView(withId(R.id.et_password)).perform(typeText(password))
        onView(withId(R.id.et_password_confirm)).perform(typeText(password))

        // 点击注册按钮
        onView(withId(R.id.btn_register)).perform(click())

        // 跳转回登录页
        onView(withId(R.id.rb_login)).perform(click())

        // 验证是否成功跳转回登录页
        onView(withId(R.id.et_account)).check(matches(isDisplayed()))

        // 在登录页输入注册的账号和密码
        onView(withId(R.id.et_account)).perform(typeText(account))
        onView(withId(R.id.et_password)).perform(typeText(password))

        // 点击登录按钮
        onView(withId(R.id.btn_login)).perform(click())

        // 验证是否成功跳转到主界面
        onView(withId(R.id.tv_welcome)).check(matches(isDisplayed()))
    }
}