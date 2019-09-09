package com.benji.weatherswe.main.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.remote.WeatherAPI
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.repository.WeatherRepository
import com.benji.domain.usecases.GetWeatherForecast
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.main.MainPageViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MainPageServiceLocator {
    private var weatherRepository: WeatherRepository? = null


    fun provideGetWeatherForecastUseCase(): GetWeatherForecast {
        return GetWeatherForecast(provideWeatherRepository())
    }

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

    fun provideMainPageViewModel(fragment: Fragment): MainPageViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory { MainPageViewModel(provideGetWeatherForecastUseCase()) })
            .get(MainPageViewModel::class.java)
    }

}