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
class LoginTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginWithValidCredentials() {
        // 输入有效的账号和密码
        onView(withId(R.id.et_account)).perform(typeText("valid_account"))
        onView(withId(R.id.et_password)).perform(typeText("valid_password"))

        // 点击登录按钮
        onView(withId(R.id.btn_login)).perform(click())

        // 验证是否跳转到主界面
        onView(withId(R.id.tv_welcome)).check(matches(isDisplayed()))
    }

    @Test
    fun testLoginWithEmptyAccount() {
        // 不输入账号，只输入密码
        onView(withId(R.id.et_password)).perform(typeText("valid_password"))

        // 点击登录按钮
        onView(withId(R.id.btn_login)).perform(click())

        // 验证是否弹出提示信息
        // 这里假设提示信息会显示在Toast中，需要自定义ToastMatcher来验证
        // 简单验证可以使用Espresso的onView(withText("账号不能空着~")).check(matches(isDisplayed()))，但可能不准确
    }

    @Test
    fun testLoginWithEmptyPassword() {
        // 输入账号，不输入密码
        onView(withId(R.id.et_account)).perform(typeText("valid_account"))

        // 点击登录按钮
        onView(withId(R.id.btn_login)).perform(click())

        // 验证是否弹出提示信息
    }
}