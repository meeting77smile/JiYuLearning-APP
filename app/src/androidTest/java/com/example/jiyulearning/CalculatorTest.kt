package com.example.jiyulearning

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(CalculatorActivity::class.java)

    @Test
    fun plusTest() {
        performCalculation("1", "+", "2", "1+2=3.0")
    }

    @Test
    fun minusTest() {
        performCalculation("5", "-", "4", "5-4=1.0")
    }

    @Test
    fun multiplyTest() {
        performCalculation("3", "×", "4", "3×4=12.0")
    }

    @Test
    fun divideTest() {
        performCalculation("8", "÷", "2", "8÷2=4.0")
    }

    @Test
    fun sqrtTest() {
        onView(withId(getButtonId("9"))).perform(click())
        onView(withId(R.id.btn_sqrt)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(withText("9√=3.0")))
    }

    @Test
    fun reciprocalTest() {
        onView(withId(getButtonId("2"))).perform(click())
        onView(withId(R.id.btn_reciprocal)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(withText("1/2=0.5")))
    }

    @Test
    fun clearTest() {
        onView(withId(getButtonId("1"))).perform(click())
        onView(withId(R.id.btn_clear)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(withText("")))
    }

    @Test
    fun cancelTest() {
        onView(withId(getButtonId("1"))).perform(click())
        onView(withId(getButtonId("2"))).perform(click())
        onView(withId(R.id.btn_cancle)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(withText("1")))
    }

    // 辅助方法：执行计算并验证结果
    private fun performCalculation(firstNum: String, operator: String, secondNum: String, expectedResult: String) {
        for (char in firstNum) {
            onView(withId(getButtonId(char.toString()))).perform(click())
        }
        onView(withId(getOperatorButtonId(operator))).perform(click())
        for (char in secondNum) {
            onView(withId(getButtonId(char.toString()))).perform(click())
        }
        onView(withId(R.id.btn_equal)).perform(click())
        onView(withId(R.id.tv_result)).check(matches(withText(expectedResult)))
    }

    // 辅助方法：根据数字获取按钮的 ID
    private fun getButtonId(num: String): Int {
        return when (num) {
            "0" -> R.id.btn_zero
            "1" -> R.id.btn_one
            "2" -> R.id.btn_two
            "3" -> R.id.btn_three
            "4" -> R.id.btn_four
            "5" -> R.id.btn_five
            "6" -> R.id.btn_six
            "7" -> R.id.btn_seven
            "8" -> R.id.btn_eight
            "9" -> R.id.btn_nine
            "." -> R.id.btn_dot
            else -> throw IllegalArgumentException("Invalid number: $num")
        }
    }

    // 辅助方法：根据运算符获取按钮的 ID
    private fun getOperatorButtonId(operator: String): Int {
        return when (operator) {
            "+" -> R.id.btn_plus
            "-" -> R.id.btn_minus
            "×" -> R.id.btn_mutiply
            "÷" -> R.id.btn_divide
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}