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
    @get: Rule
    val activityRule = ActivityScenarioRule(CalculatorActivity::class.java)

    @Test
    fun plusTest() {
        onView(withId(R.id.btn_one)).perform(click())
        onView(withId(R.id.btn_plus)).perform(click())
        onView(withId(R.id.btn_two)).perform(click())
        onView(withId(R.id.btn_equal)).perform(click())

        onView(withId(R.id.tv_result)).check(matches(withText("1+2=3.0")))
    }

    @Test
    fun minusTest() {
        onView(withId(R.id.btn_five)).perform(click())
        onView(withId(R.id.btn_minus)).perform(click())
        onView(withId(R.id.btn_four)).perform(click())
        onView(withId(R.id.btn_equal)).perform(click())

        onView(withId(R.id.tv_result)).check(matches(withText("5-4=1.0")))
    }
}