package com.benji.weatherswe.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.locationweather.servicelocator.LocationWeatherServiceLocator
import com.benji.weatherswe.utils.AutoCompleteCityAdapter
import com.benji.weatherswe.utils.extensions.mainActivity
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SearchCityServiceLocator {

    val geoCodingRepository: GeocodingRepository by lazy {
        GeocodingRepository(provideGeoCodingAPI())
    }

    fun provideViewModel(fragment : Fragment) : SearchCityViewModel {
       return  ViewModelProvider(
            fragment,
            BaseViewModelFactory { initSearchCityViewModel(fragment) }
        ).get(SearchCityViewModel::class.java)
    }

    private fun initSearchCityViewModel(fragment: Fragment): SearchCityViewModel {
        return SearchCityViewModel(geoCodingRepository)
    }

    fun provideAutoCompleteCityAdapter(fragment: Fragment) : AutoCompleteCityAdapter {
        return AutoCompleteCityAdapter(fragment.mainActivity().baseContext, emptyList())
    }

    private fun provideGeoCodingAPI(): GeocodingRemoteDataSource =
        GeocodingRemoteDataSource(provideRetrofit().create(GeocodingAPI::class.java))

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_GEOCODING_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

}