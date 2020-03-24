package com.benji.weatherswe.dayweather.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.WeatherAPI
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.repository.WeatherRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.dayweather.DayWeatherViewModel
import com.benji.weatherswe.utils.DispatcherProvider
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

object DayWeatherServiceLocator {
    private lateinit var cacheDir: File

    fun provideViewModel(fragment: Fragment): DayWeatherViewModel {
        this.cacheDir = fragment.context!!.cacheDir

        return ViewModelProvider(
            fragment,
            BaseViewModelFactory { initDayWeatherViewModel() }
        ).get(DayWeatherViewModel::class.java)
    }

    private fun initDayWeatherViewModel(): DayWeatherViewModel =
        DayWeatherViewModel(
            DispatcherProvider,
            weatherRepository
        )

    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(provideWeatherAPI())
    }

    private fun provideWeatherAPI(): WeatherRemoteDataSource =
        WeatherRemoteDataSource(
            provideRetrofit()
                .create(WeatherAPI::class.java)
        )

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_SMHI_FORECAST_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(provideOkHttpClient())
            .build()

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .cache(provideCache())
            .build()

    private fun provideCache(): Cache? {
        var cache: Cache? = null

        try {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            cache = Cache(cacheDir, cacheSize.toLong())
        } catch (exception: Exception) {

        }
        return cache
    }


}



