package com.benji.weatherswe.searchcity.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.searchcity.SearchCityViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SearchCityServiceLocator {
    private var geocodingRepository: GeocodingRepository? = null


    private fun provideGeocodingRepository(): GeocodingRepository {
        var geocodingRepositoryTemp = geocodingRepository
        if (geocodingRepository == null) {
            geocodingRepository = GeocodingRepository(provideGeocodingAPI())
            geocodingRepositoryTemp = geocodingRepository
        }
        return geocodingRepositoryTemp!!
    }

    private fun provideGeocodingAPI(): GeocodingRemoteDataSource =
        GeocodingRemoteDataSource(provideRetrofit().create(GeocodingAPI::class.java))

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_GEOCODING_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun provideSearchCityViewModel(fragment: Fragment): SearchCityViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory {
                SearchCityViewModel(
                    provideGeocodingRepository()
                )
            })
            .get(SearchCityViewModel::class.java)
    }


}