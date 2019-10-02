package com.benji.data.datasource.local

import androidx.room.TypeConverter
import com.benji.domain.domainmodel.weather.Hourly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HorlyTypeConverters {

    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Hourly> {
        if (data == null) {
            return kotlin.collections.emptyList()
        }

        val listType = object : TypeToken<List<Hourly>>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Hourly>): String {
        return gson.toJson(someObjects)
    }
}