package com.benji.weatherswe.weather.servicelocator

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.local.WeatherRoomDatabase
import com.benji.data.datasource.remote.WeatherAPI
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.repository.WeatherRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.weather.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherServiceLocator {
    private var weatherRepository: WeatherRepository? = null


    private fun provideWeatherRepository(context : Context): WeatherRepository {
        var weatherRepositoryTemp =
            weatherRepository
        if (weatherRepository == null) {
            weatherRepository = WeatherRepository(
                provideWeatherAPI(),
                WeatherRoomDatabase.getDatabase(context).weatherDao()
            )
            weatherRepositoryTemp =
                weatherRepository
        }
        return weatherRepositoryTemp!!
    }

    private fun provideWeatherAPI(): WeatherRemoteDataSource =
        WeatherRemoteDataSource(
            provideRetrofit().create(
                WeatherAPI::class.java
            )
        )

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_SMHI_FORECAST_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun provideWeatherViewModel(fragment: Fragment, context : Context): WeatherViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory { WeatherViewModel(DispatcherProvider, provideWeatherRepository(context), fragment.arguments!!["city"] as String) })
            .get(WeatherViewModel::class.java)
    }

}