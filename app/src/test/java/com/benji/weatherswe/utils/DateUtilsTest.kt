package com.benji.weatherswe.utils

import com.benji.weatherswe.utils.DateUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DateUtilsTest {
    val dateUtils = DateUtils()

    val WEEK_DAY_MONDAY = "Monday"
    val WEEK_DAY_TUESDAY = "Tuesday"
    val WEEK_DAY_WEDNESDAY = "Wednesday"
    val WEEK_DAY_THURSDAY = "Thursday"
    val WEEK_DAY_FRIDAY = "Friday"
    val WEEK_DAY_SATURDAY = "Saturday"
    val WEEK_DAY_SUNDAY = "Sunday"

    @Test
    fun `getDay(2019-09-23) should return Monday`() {
        val inputDate = "2019-09-23"
        assertEquals(WEEK_DAY_MONDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-24) should return Tuesday`() {
        val inputDate = "2019-09-24"
        assertEquals(WEEK_DAY_TUESDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-25) should return Wednesday`() {
        val inputDate = "2019-09-25"
        assertEquals(WEEK_DAY_WEDNESDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-26) should return Thursday`() {
        val inputDate = "2019-09-26"
        assertEquals(WEEK_DAY_THURSDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-27) should return Friday`() {
        val inputDate = "2019-09-27"
        assertEquals(WEEK_DAY_FRIDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-28) should return Saturday`() {
        val inputDate = "2019-09-28"
        assertEquals(WEEK_DAY_SATURDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getDay(2019-09-29) should return Sunday`() {
        val inputDate = "2019-09-29"
        assertEquals(WEEK_DAY_SUNDAY, dateUtils.getDay(inputDate))
    }

    @Test
    fun `getFormattedTime(2019-01-01T000000Z) should return 2019-01-01`() {
        assertEquals("2019-01-01", dateUtils.getFormattedTime("2019-01-01T00:00:00Z"))
    }

    @Test
    fun `getHourlyTime(2019-01-01T130000Z) should return 1300`() {
        assertEquals("13:00", dateUtils.getHourlyTime("2019-01-01T13:00:00Z"))
    }


}