package com.benji.weatherswe.search.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.weatherswe.base.BaseViewModelFactory
import com.benji.weatherswe.search.SearchGeocoding
import com.benji.weatherswe.search.SearchViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SearchServiceLocator {

    val geoCodingRepository: GeocodingRepository by lazy {
        GeocodingRepository(provideGeoCodingAPI())
    }

    fun provideViewModel(fragment : Fragment) : SearchViewModel {
       return  ViewModelProvider(
            fragment,
           BaseViewModelFactory {
               initSearchCityViewModel(
                   fragment
               )
           }
        ).get(SearchViewModel::class.java)
    }

    private fun initSearchCityViewModel(fragment: Fragment): SearchViewModel {
        return SearchViewModel(
            geoCodingRepository,
            SearchGeocoding(fragment.context!!)
        )
    }

    private fun provideGeoCodingAPI(): GeocodingRemoteDataSource =
        GeocodingRemoteDataSource(provideRetrofit().create(GeocodingAPI::class.java))

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlManager.API_GEOCODING_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}