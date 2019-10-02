package com.benji.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(DayForecastEntityDB::class), version = 1)
@TypeConverters(HorlyTypeConverters::class)
abstract class WeatherRoomDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDatabase(context: Context): WeatherRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "weather_room_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}