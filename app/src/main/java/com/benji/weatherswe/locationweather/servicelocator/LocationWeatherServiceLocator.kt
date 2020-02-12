package com.benji.weatherswe.locationweather.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.locationweather.LocationWeatherViewModel
import com.benji.weatherswe.locationweather.ReveresedGeocoding
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.locationweather.LocationHandler
import com.benji.weatherswe.locationweather.PermissionManager
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LocationWeatherServiceLocator {

    val geoCodingRepository: GeocodingRepository by lazy {
        GeocodingRepository(provideGeoCodingAPI())
    }

    fun provideViewModel(fragment: Fragment): LocationWeatherViewModel =
        ViewModelProviders.of(
            fragment,
            BaseViewModelFactory { initLocationWeatherViewModel(fragment) }
        ).get(LocationWeatherViewModel::class.java)

    private fun provideGeoCodingAPI(): GeocodingRemoteDataSource =
        GeocodingRemoteDataSource(provideRetrofit().create(GeocodingAPI::class.java))

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_GEOCODING_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private fun initLocationWeatherViewModel(fragment: Fragment): LocationWeatherViewModel =
        LocationWeatherViewModel(
            DispatcherProvider,
            geoCodingRepository,
            LocationHandler(fragment),
            PermissionManager(fragment),
            ReveresedGeocoding(fragment.context!!)
        )

}


