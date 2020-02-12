package com.benji.weatherswe.locationweather


import com.benji.domain.location.IReversedGeocoding
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.*
import com.benji.domain.repository.IGeocodingRepository
import com.benji.weatherswe.InstantExecutorExtension
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.domain.location.ILocationHandler
import com.benji.domain.location.IPermissionManager
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
    private val locationHandler: ILocationHandler = mockk()
    private val permissionManager: IPermissionManager = mockk()
    private val reverseGeocoding: IReversedGeocoding = mockk()

    private val locationWeatherViewModel =
        LocationWeatherViewModel(
            dispatcher,
            geocodingRepository,
            locationHandler,
            permissionManager,
            reverseGeocoding
        )

    private val cityStockholm = "Stockholm"
    private val magicKey = "154asda5wd15as1x5awdas1xa"

    private val suggestions = Suggestions(
        listOf(
            Suggestion(
                magicKey,
                cityStockholm
            )
        )
    )

    private val candidateStockholm = Candidate(cityStockholm, Location(59.33, 18.06), 100)
    private val candidateMalmo = Candidate("Malmö", Location(55.60, 13.00), 100)
    private val listOfCandidates = Candidates(listOf(candidateStockholm, candidateMalmo))


    @Test
    fun `getCitySuggestions(Stockholm) should set value to citySuggestions`() = runBlocking {

        every {
            dispatcher.provideUIContext()
        } returns Dispatchers.Unconfined

        coEvery {
            geocodingRepository.getSuggestions(cityStockholm)
        } returns ResultWrapper.build { suggestions }

        locationWeatherViewModel.getCitySuggestions(cityStockholm)

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
            geocodingRepository.getSuggestions(cityStockholm)
        } returns ResultWrapper.build { suggestions }

        locationWeatherViewModel.getCitySuggestions(cityStockholm)

        coEvery {
            geocodingRepository.getCandidateLocation(cityStockholm, magicKey)
        } returns ResultWrapper.build { listOfCandidates }

        locationWeatherViewModel.onSuggestionClicked(0)

        assertEquals(cityStockholm, locationWeatherViewModel.candidate.value!!.address)

    }


}