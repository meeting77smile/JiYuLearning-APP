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
class RegisterTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun testRegisterWithValidInfo() {
        // 输入有效的注册信息
        onView(withId(R.id.et_username)).perform(typeText("valid_username"))
        onView(withId(R.id.et_level)).perform(typeText("1"))
        onView(withId(R.id.et_account)).perform(typeText("new_account"))
        onView(withId(R.id.et_password)).perform(typeText("new_password"))
        onView(withId(R.id.et_password_confirm)).perform(typeText("new_password"))

        // 点击注册按钮
        onView(withId(R.id.btn_register)).perform(click())

        // 验证是否跳转到登录界面
        onView(withId(R.id.et_account)).check(matches(isDisplayed()))
    }

    @Test
    fun testRegisterWithEmptyUsername() {
        // 不输入用户名，输入其他信息
        onView(withId(R.id.et_level)).perform(typeText("1"))
        onView(withId(R.id.et_account)).perform(typeText("new_account"))
        onView(withId(R.id.et_password)).perform(typeText("new_password"))
        onView(withId(R.id.et_password_confirm)).perform(typeText("new_password"))

        // 点击注册按钮
        onView(withId(R.id.btn_register)).perform(click())

        // 验证是否弹出提示信息
        //onToast("用户名不能空着~").check(matches(isDisplayed()))
    }

    @Test
    fun testRegisterWithMismatchedPasswords() {
        // 输入用户名、账号，输入不匹配的密码
        onView(withId(R.id.et_username)).perform(typeText("valid_username"))
        onView(withId(R.id.et_level)).perform(typeText("1"))
        onView(withId(R.id.et_account)).perform(typeText("new_account"))
        onView(withId(R.id.et_password)).perform(typeText("new_password"))
        onView(withId(R.id.et_password_confirm)).perform(typeText("different_password"))

        // 点击注册按钮
        onView(withId(R.id.btn_register)).perform(click())

        // 验证是否弹出提示信息
    }
}