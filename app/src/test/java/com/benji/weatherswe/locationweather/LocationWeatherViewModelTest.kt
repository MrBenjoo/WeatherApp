package com.benji.weatherswe.locationweather


import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.*
import com.benji.domain.repository.IGeocodingRepository
import com.benji.weatherswe.InstantExecutorExtension
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.returnCities
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class LocationWeatherViewModelTest {

    private val dispatcher: DispatcherProvider = mockk()
    private val geocodingRepository: IGeocodingRepository = mockk()

    private val locationWeatherViewModel = LocationWeatherViewModel(dispatcher, geocodingRepository)


    private val city = "Stockholm"
    private val magicKey = "154asda5wd15as1x5awdas1xa"

    private val suggestions = Suggestions(
        listOf(
            Suggestion(
                magicKey,
                city
            )
        )
    )


    private val candidates = Candidates(listOf(Candidate("Malmö", Location(56.0, 13.0), 0),
        Candidate(city, Location(59.33, 18.06), 100)))

    @Test
    fun `getCitySuggestions(Stockholm) should set value to citySuggestions`() = runBlocking {

        every {
            dispatcher.provideUIContext()
        } returns Dispatchers.Unconfined

        coEvery {
            geocodingRepository.getSuggestions(city)
        } returns ResultWrapper.build { suggestions }

        locationWeatherViewModel.getCitySuggestions(city)

        assertEquals(
            locationWeatherViewModel.citySuggestions.value,
            suggestions.suggestions.returnCities()
        )
    }

    @Test
    fun `onSuggestionClicked(0) should return Stockholm`() = runBlocking {

        every {
            dispatcher.provideUIContext()
        } returns Dispatchers.Unconfined

        coEvery {
            geocodingRepository.getSuggestions(city)
        } returns ResultWrapper.build { suggestions }

        locationWeatherViewModel.getCitySuggestions(city)

        locationWeatherViewModel.onSuggestionClicked(0)

        coEvery {
            geocodingRepository.getCandidateLocation(city, magicKey)
        } returns ResultWrapper.build { candidates }


        assertEquals(
            locationWeatherViewModel.candidate.value!!.address,
            "Stockholm"
        )

        assertEquals(
            locationWeatherViewModel.candidate.value!!.location,
            Location(59.33, 18.06)
        )

        assertEquals(
            locationWeatherViewModel.candidate.value!!.score,
            100
        )
    }


}