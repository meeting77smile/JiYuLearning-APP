package com.example.jiyulearning.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtilTest {
    @Test
    fun testGetNowTime() {
        val nowTime = DateUtil.getNowTime()
        val sdf = SimpleDateFormat("HH:mm:ss")
        val currentTime = sdf.format(Date())
        assertEquals(currentTime, nowTime)
    }

    @Test
    fun testGetDate() {
        val calendar = Calendar.getInstance()
        val expectedDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        val actualDate = DateUtil.getDate(calendar)
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun testGetMonth() {
        val calendar = Calendar.getInstance()
        val expectedMonth = SimpleDateFormat("yyyy-MM").format(calendar.time)
        val actualMonth = DateUtil.getMonth(calendar)
        assertEquals(expectedMonth, actualMonth)
    }

    @Test
    fun testGetDay() {
        val calendar = Calendar.getInstance()
        val expectedDay = SimpleDateFormat("MM-dd").format(calendar.time)
        val actualDay = DateUtil.getDay(calendar)
        assertEquals(expectedDay, actualDay)
    }

    @Test
    fun testGetMonth_only() {
        val calendar = Calendar.getInstance()
        val expectedMonthOnly = SimpleDateFormat("MM").format(calendar.time)
        val actualMonthOnly = DateUtil.getMonth_only(calendar)
        assertEquals(expectedMonthOnly, actualMonthOnly)
    }
}