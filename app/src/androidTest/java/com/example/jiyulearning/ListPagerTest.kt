package com.example.jiyulearning

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jiyulearning.entity.UserInfo
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class ListPagerTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ListPagerActivity::class.java)

    @Before
    fun setUp() {
        val testUserInfo = UserInfo()
        testUserInfo.username = "TestUser"
        testUserInfo.account = "testAccount"
        testUserInfo.password = "testPassword"
        testUserInfo.level = 1

        LoginActivity.userInfo = testUserInfo
    }

    @Test
    fun testActivityDisplayed() {
        val calendar = Calendar.getInstance()
        val today = calendar.time
        val dateFormatYM = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val dateFormatDD = SimpleDateFormat("d日", Locale.getDefault())
        val dateFormatMD = SimpleDateFormat("日期：MM-dd", Locale.getDefault())
        onView(withId(R.id.tv_day)).check(matches(withText(dateFormatYM.format(today))))
        onView(withId(R.id.vp_list)).check(matches(isDisplayed()))
        onView(withText(dateFormatDD.format(today))).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_list)).check(matches(withText(dateFormatMD.format(today))))
    }

    @Test
    fun testClickDateButton_opensDatePicker() {
        // 点击日期选择按钮
        onView(withId(R.id.tv_day)).perform(click())

        // 使用espresso-contrib中的PickerActions验证日期选择器
        onView(withClassName(`is`("android.widget.DatePicker")))
            .check(matches(isDisplayed()))

        // 选择一个具体日期并验证
        onView(withClassName(`is`("android.widget.DatePicker")))
            .perform(PickerActions.setDate(2025, 6, 1))

        // 确认选择
        onView(withText("确定")).perform(click())

        // 验证日期是否正确显示在TextView上
        onView(withId(R.id.tv_day)).check(matches(withText("2025-06")))
    }

    @Test
    fun testClickManageOption_navigatesToListAddActivity() {
        onView(withId(R.id.tv_option)).perform(click())
        onView(withId(R.id.et_des_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testSwipeBetweenDates() {
        val calendar = Calendar.getInstance()

        // 测试普通日期的滑动
        testSwipeForDate(calendar.clone() as Calendar)

        // 测试月初（1号）的滑动
        val firstDayOfMonth = calendar.clone() as Calendar
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        setDateInApp(firstDayOfMonth)
        testSwipeForDate(firstDayOfMonth)

        // 测试月末的滑动
        val lastDayOfMonth = calendar.clone() as Calendar
        lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))
        setDateInApp(lastDayOfMonth)
        testSwipeForDate(lastDayOfMonth)
    }

    private fun testSwipeForDate(calendar: Calendar) {
        val originalDate = calendar.time
        val dateFormat = SimpleDateFormat("d日", Locale.getDefault())

        // 左滑应增加一天（如果不是月底）
        val expectedLeftSwipeDate = calendar.clone() as Calendar
        expectedLeftSwipeDate.add(Calendar.DAY_OF_MONTH, 1)

        // 右滑应减少一天（如果不是月初）
        val expectedRightSwipeDate = calendar.clone() as Calendar
        expectedRightSwipeDate.add(Calendar.DAY_OF_MONTH, -1)

        // 验证初始日期
        onView(withText(dateFormat.format(originalDate))).check(matches(isDisplayed()))

        // 左滑测试
        onView(withId(R.id.vp_list)).perform(swipeLeft())

        // 如果是月初，左滑后日期应保持不变；否则应更新为下一天
        val actualLeftSwipeDate = if (isLastDayOfMonth(calendar)) {
            originalDate
        } else {
            expectedLeftSwipeDate.time
        }

        onView(withText(dateFormat.format(actualLeftSwipeDate))).check(matches(isDisplayed()))

        // 恢复到原始日期
        setDateInApp(calendar)

        // 右滑测试
        onView(withId(R.id.vp_list)).perform(swipeRight())

        // 如果是月初，右滑后日期应保持不变；否则应更新为前一天
        val actualRightSwipeDate = if (isFirstDayOfMonth(calendar)) {
            originalDate
        } else {
            expectedRightSwipeDate.time
        }

        onView(withText(dateFormat.format(actualRightSwipeDate))).check(matches(isDisplayed()))

        // 恢复到原始日期
        setDateInApp(calendar)
    }

    private fun isFirstDayOfMonth(calendar: Calendar): Boolean {
        return calendar.get(Calendar.DAY_OF_MONTH) == 1
    }

    private fun isLastDayOfMonth(calendar: Calendar): Boolean {
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun setDateInApp(calendar: Calendar) {
        // 打开日期选择器
        onView(withId(R.id.tv_day)).perform(click())

        // 设置日期
        onView(withClassName(`is`("android.widget.DatePicker")))
            .perform(PickerActions.setDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // DatePicker月份从1开始
                calendar.get(Calendar.DAY_OF_MONTH)
            ))

        // 确认选择
        onView(withText("确定")).perform(click())
    }
}