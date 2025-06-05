package com.example.jiyulearning

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jiyulearning.entity.UserInfo
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListAddTest {
    private lateinit var activityScenario: ActivityScenario<ListAddActivity>

    @Before
    fun setUp() {
        val testUserInfo = UserInfo()
        testUserInfo.username = "TestUser"
        testUserInfo.account = "testAccount"
        testUserInfo.password = "testPassword"
        testUserInfo.level = 1

        LoginActivity.userInfo = testUserInfo

        activityScenario = ActivityScenario.launch(ListAddActivity::class.java)
    }

    @Test
    fun testActivityIsDisplayed() {
        onView(withId(R.id.et_des_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testDatePickerDialogDisplayed() {
        onView(withId(R.id.tv_date_list)).perform(click())
        onView(withClassName(`is`("android.widget.DatePicker")))
            .check(matches(isDisplayed()))
        onView(withClassName(`is`("android.widget.DatePicker")))
            .perform(PickerActions.setDate(2025, 6, 1))
        onView(withText("确定")).perform(click())
        onView(withId(R.id.tv_date_list)).check(matches(withText("2025-06-01")))
    }

    @Test
    fun testSaveListWithValidInfo() {
        // 输入有效的事项描述和所需时间
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))
        onView(withId(R.id.et_time)).perform(typeText("30"))

        // 点击保存按钮
        onView(withId(R.id.btn_save_list)).perform(click())

        onView(withId(R.id.tv_option)).perform(click())
        onView(withText("Test Task")).check(matches(isDisplayed()))
    }

    @Test
    fun testSaveListWithEmptyDescription() {
        // 不输入事项描述，输入所需时间
        onView(withId(R.id.et_time)).perform(typeText("30"))

        // 点击保存按钮
        onView(withId(R.id.btn_save_list)).perform(click())

        // 验证是否弹出事项内容不可为空的提示信息
    }

    @Test
    fun testSaveListWithEmptyTime() {
        // 输入事项描述，不输入所需时间
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))

        // 点击保存按钮
        onView(withId(R.id.btn_save_list)).perform(click())

        // 验证是否弹出事项完成时间不可为空的提示信息
    }

    @Test
    fun testDeleteListWithValidDescription() {
        // 输入有效的事项描述
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))

        // 点击删除按钮
        onView(withId(R.id.btn_delete_list)).perform(click())

        // 验证是否弹出删除成功的提示信息
    }

    @Test
    fun testDeleteListWithEmptyDescription() {
        // 不输入事项描述
        // 点击删除按钮
        onView(withId(R.id.btn_delete_list)).perform(click())

        // 验证是否弹出事项内容不可为空的提示信息
    }

    @Test
    fun testUpdateListWithValidInfo() {
        // 输入有效的事项描述和所需时间
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))
        onView(withId(R.id.et_time)).perform(typeText("30"))

        // 点击修改按钮
        onView(withId(R.id.btn_update_list)).perform(click())

        // 验证是否弹出修改成功的提示信息
    }

    @Test
    fun testUpdateListWithEmptyDescription() {
        // 不输入事项描述，输入所需时间
        onView(withId(R.id.et_time)).perform(typeText("30"))

        // 点击修改按钮
        onView(withId(R.id.btn_update_list)).perform(click())

        // 验证是否弹出事项内容不可为空的提示信息
    }

    @Test
    fun testUpdateListWithEmptyTime() {
        // 输入事项描述，不输入所需时间
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))

        // 点击修改按钮
        onView(withId(R.id.btn_update_list)).perform(click())

        // 验证是否弹出事项完成时间不可为空的提示信息
    }

    @Test
    fun testClearAllLists() {
        // 输入有效的事项描述和所需时间
        onView(withId(R.id.et_des_list)).perform(typeText("Test Task"))
        onView(withId(R.id.et_time)).perform(typeText("30"))
        // 点击保存按钮
        onView(withId(R.id.btn_save_list)).perform(click())
        // 点击清空事项按钮
        onView(withId(R.id.btn_clear)).perform(click())

        // 验证是否弹出清空成功的提示信息
    }

    @Test
    fun testSmartPlanButtonClick() {
        // 点击个性化规划按钮
        onView(withId(R.id.btn_smart_plan)).perform(click())

        // 验证是否弹出正在个性化定制的提示信息
    }

    @Test
    fun testNavigateToListPagerActivity() {
        // 点击待做事项选项
        onView(withId(R.id.tv_option)).perform(click())

        // 验证是否跳转到 ListPagerActivity
        onView(withId(R.id.vp_list)).check(matches(isDisplayed()))
    }
}