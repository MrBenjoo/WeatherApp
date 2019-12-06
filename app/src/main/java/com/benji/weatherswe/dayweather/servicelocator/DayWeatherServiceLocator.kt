package com.benji.weatherswe.dayweather.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.WeatherAPI
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.repository.WeatherRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.dayweather.DayWeatherViewModel
import com.benji.weatherswe.locationweather.servicelocator.LocationWeatherServiceLocator
import com.benji.weatherswe.utils.DispatcherProvider
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

object DayWeatherServiceLocator {
    private var weatherRepository: WeatherRepository? = null


    private fun provideWeatherRepository(fragment: Fragment): WeatherRepository {
        var weatherRepositoryTemp =
            weatherRepository
        if (weatherRepository == null) {
            weatherRepository = WeatherRepository(
                provideWeatherAPI(fragment.context!!.cacheDir)
            )
            weatherRepositoryTemp =
                weatherRepository
        }
        return weatherRepositoryTemp!!
    }

    private fun provideWeatherAPI(cache: File): WeatherRemoteDataSource =
        WeatherRemoteDataSource(
            provideRetrofit(cache).create(
                WeatherAPI::class.java
            )
        )

    private fun provideRetrofit(cache: File): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_SMHI_FORECAST_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(provideOkHttpClient(cache))
            .build()

    fun provideWeatherViewModel(fragment: Fragment): DayWeatherViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory {
                DayWeatherViewModel(
                    DispatcherProvider,
                    provideWeatherRepository(fragment),
                    LocationWeatherServiceLocator.provideGeoCodingRepository()
                )
            })
            .get(DayWeatherViewModel::class.java)
    }

    private fun provideCache(cachePath: File): Cache? {
        var cache: Cache? = null

        try {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            cache = Cache(cachePath, cacheSize.toLong())
        } catch (exception: Exception) {

        }
        return cache
    }

    private fun provideOkHttpClient(cache: File): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(provideCache(cache))
            .build()
    }


}