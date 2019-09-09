package com.benji.weatherswe.start.servicelocator

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.benji.data.datasource.UrlManager
import com.benji.data.datasource.remote.GeocodingAPI
import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.data.repository.GeocodingRepository
import com.benji.domain.usecases.GetLocationCandidate
import com.benji.domain.usecases.GetLocationSuggestions
import com.benji.weatherswe.BaseViewModelFactory
import com.benji.weatherswe.start.StartPageViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object StartServiceLocator {
    private var geocodingRepository: GeocodingRepository? = null

    fun provideGetLocationSuggestionsUseCase(): GetLocationSuggestions {
        return GetLocationSuggestions(provideGeocodingRepository())
    }

    private fun provideGetLocationCandidate(): GetLocationCandidate {
        return GetLocationCandidate(provideGeocodingRepository())
    }

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

    fun provideStartPageViewModel(fragment: Fragment): StartPageViewModel {
        return ViewModelProviders.of(
            fragment,
            BaseViewModelFactory {
                StartPageViewModel(
                    provideGetLocationSuggestionsUseCase(),
                    provideGetLocationCandidate()
                )
            })
            .get(StartPageViewModel::class.java)
    }


}