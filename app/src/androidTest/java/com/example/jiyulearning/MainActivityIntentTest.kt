package com.example.jiyulearning

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
class MainActivityIntentTest {
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        // 构建测试用的 UserInfo 对象
        val testUserInfo = UserInfo()
        testUserInfo.username = "TestUser"
        testUserInfo.account = "testAccount"
        testUserInfo.password = "testPassword"
        testUserInfo.level = 1

        LoginActivity.userInfo = testUserInfo

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun guideIntentTest() {
        onView(withId(R.id.card_guidebook)).perform(click())
        onView(withId(R.id.web_guidebood)).check(matches(isDisplayed()))
    }

    @Test
    fun dailyPracticeIntentTest() {
        onView(withId(R.id.card_daily_practice)).perform(click())
        onView(withId(R.id.webview_practice)).check(matches(isDisplayed()))
    }

    @Test
    fun qaIntentTest() {
        onView(withId(R.id.card_smart_qa)).perform(click())
        onView(withId(R.id.etInput)).check(matches(isDisplayed()))
    }

    @Test
    fun dailyPlanIntentTest() {
        onView(withId(R.id.card_daily_plan)).perform(click())
        onView(withId(R.id.tv_select)).check(matches(isDisplayed()))
    }

    @Test
    fun learnResIntentTest() {
        onView(withId(R.id.card_learn_res)).perform(click())
        onView(withId(R.id.webview_learning_res)).check(matches(isDisplayed()))
    }

    @Test
    fun webVersionIntentTest() {
        onView(withId(R.id.card_web_link)).perform(click())
        onView(withId(R.id.web_version)).check(matches(isDisplayed()))
    }

    @Test
    fun calculatorIntentTest() {
        onView(withId(R.id.card_calculator)).perform(click())
        onView(withText("简单计算器~")).check(matches(isDisplayed()))
    }

    @Test
    fun accountInfoIntentTest() {
        onView(withId(R.id.btn_account_info)).perform(click())
        onView(withId(R.id.btn_back)).check(matches(isDisplayed()))
    }

    @Test
    fun logoutTest() {
        onView(withId(R.id.btn_logout)).perform(click())
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}