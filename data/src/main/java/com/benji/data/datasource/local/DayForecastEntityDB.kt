package com.benji.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.benji.domain.domainmodel.weather.Hourly

@Entity(tableName = "weather_forecast_table")
class DayForecastEntityDB(
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "temperature") val temperature: String,
    @ColumnInfo(name = "listOfHourlyData") val listOfHourlyData: List<Hourly>
)