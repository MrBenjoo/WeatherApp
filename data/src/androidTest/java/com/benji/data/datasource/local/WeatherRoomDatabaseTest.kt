package com.benji.data.datasource.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.benji.domain.domainmodel.weather.Hourly
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
internal class WeatherRoomDatabaseTest {

    private lateinit var database: WeatherRoomDatabase
    private lateinit var dao: WeatherDao
    private lateinit var dayForecast: DayForecastEntityDB

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, WeatherRoomDatabase::class.java).build()
        dao = database.weatherDao()

        val hourly =
            Moshi.Builder().build().adapter(Hourly::class.java).fromJson(Constants.HOURLY_JSON)!!

        val list = arrayListOf<Hourly>().also { it.add(hourly) }

        dayForecast = DayForecastEntityDB(
            "2019-09-30",
            "Måndag",
            "Malmö",
            "18C",
            list
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    @Test
    fun insert_DayForecast_ShouldEqualToSameDate() = runBlocking {
        dao.insert(dayForecast)
        val dayForecastDB = dao.getDayForecast("2019-09-30")
        assertEquals(dayForecast.date, dayForecastDB.date)
    }

    @Test
    fun insert_DayForecast_ShouldEqualToSameCity() = runBlocking {
        dao.insert(dayForecast)
        val dayForecastDB = dao.getDayForecast("2019-09-30")
        assertEquals(dayForecast.city, dayForecastDB.city)
    }

    @Test
    fun insert_DayForecast_ShouldEqualToSameDay() = runBlocking {
        dao.insert(dayForecast)
        val dayForecastDB = dao.getDayForecast("2019-09-30")
        assertEquals(dayForecast.day, dayForecastDB.day)
    }

    @Test
    fun insert_DayForecast_ShouldEqualToSameTemperature() = runBlocking {
        dao.insert(dayForecast)
        val dayForecastDB = dao.getDayForecast("2019-09-30")
        assertEquals(dayForecast.temperature, dayForecastDB.temperature)
    }

    @Test
    fun insert_DayForecast_ShouldEqualToHourlyList() = runBlocking {
        dao.insert(dayForecast)
        val dayForecastDB = dao.getDayForecast("2019-09-30")
        assertThat(dayForecast.listOfHourlyData, `is`(dayForecastDB.listOfHourlyData))
    }

    @Test
    fun deleteAll_DayForecast_ShouldReturnNullDayForecast() = runBlocking {
        dao.insert(dayForecast)
        dao.deleteAll()
        assertNull(dao.getDayForecast("2019-09-30"))
    }


}