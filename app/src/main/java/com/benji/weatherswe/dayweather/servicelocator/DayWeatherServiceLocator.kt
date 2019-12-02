package com.benji.weatherswe.dayweather.servicelocator

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.datasource.remote.WeatherAPI
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.data.repository.WeatherRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.dayweather.DayWeatherViewModel
import com.benji.weatherswe.locationweather.servicelocator.LocationWeatherServiceLocator
import com.benji.weatherswe.utils.DispatcherProvider
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DayWeatherServiceLocator {
    private var weatherRepository: WeatherRepository? = null


    private fun provideWeatherRepository(): WeatherRepository {
        var weatherRepositoryTemp =
            weatherRepository
        if (weatherRepository == null) {
            weatherRepository = WeatherRepository(
                provideWeatherAPI()
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

    fun provideWeatherViewModel(fragment: Fragment): DayWeatherViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory {
                DayWeatherViewModel(
                    DispatcherProvider,
                    provideWeatherRepository(),
                    LocationWeatherServiceLocator.provideGeocodingRepository()
                )
            })
            .get(DayWeatherViewModel::class.java)
    }



}