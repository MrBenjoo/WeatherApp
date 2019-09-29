package com.benji.weatherswe.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getCurrentTime(): String = inputFormat.format(Calendar.getInstance().time)

    fun getFormattedTime(time: String): String {
        val date = inputFormat.parse(time)
        return inputFormat.format(date)
    }

    fun getDay(time: String): String {
        var dayOfWeek = "Unknown"
        try {
            val date = inputFormat.parse(time)
            dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dayOfWeek
    }

}