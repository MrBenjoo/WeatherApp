package com.benji.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * from weather_forecast_table WHERE date = :date")
    fun getDayForecast(date : String): DayForecastEntityDB

    @Query("DELETE FROM weather_forecast_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dayForecast: DayForecastEntityDB)

}