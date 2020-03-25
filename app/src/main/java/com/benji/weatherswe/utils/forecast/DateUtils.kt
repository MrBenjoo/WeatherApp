package com.benji.weatherswe.utils.forecast

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getCurrentTime(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return inputFormat.format(Calendar.getInstance().time)
    }

    fun getFormattedTime(time: String): String {
        val date = inputFormat.parse(time)
        return outputFormat.format(date)
    }

    fun getHourlyTime(time: String): String {
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(time)
        return outputFormat.format(date)
    }

    fun getDay(time: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var dayOfWeek = "Unknown"
        try {
            val date = inputFormat.parse(time)
            dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dayOfWeek
    }

    /**
     * This method is almost the same as above except no argument is needed to be passed.
     * The above method is needed to get the weekday from the api while this one is needed
     * to get the current day without having to first call getCurrentTime and then pass it in to
     * getDay.
     */
    fun getDay(): String = getDay(getCurrentTime())

    fun getTodayDate(): String {
        val inputFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        return inputFormat.format(Calendar.getInstance().time)
    }
}