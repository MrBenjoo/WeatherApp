package com.benji.weatherswe.search

import android.content.Context
import android.location.Geocoder
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.location.ISearchCityGeocoding
import java.io.IOException

class SearchGeocoding(context: Context) : ISearchCityGeocoding {

    private val TAG = "SearchGeocoding"

    private var geocoder: Geocoder = Geocoder(context)
    private val MAX_RESULTS = 1

    override fun getFromLocation(location: Location): ResultWrapper<Exception, String> {
        return try {
            ResultWrapper.build {
                val listOfAddress = geocoder.getFromLocation(location.y, location.x, MAX_RESULTS)
                val address = listOfAddress.first().getAddressLine(0)
                getCityFromAddressLine(address)
            }
        } catch (exception: IOException) {
            ResultWrapper.build { throw exception }
        }
    }

    /**
     * Index for accessing the string city in listOfText is hardcoded to 1 because the structure of
     * the address string is: [0]street, [1]postcode + city, [2]country.
     */
    private fun getCityFromAddressLine(address: String): String {
        val stringBuilder = StringBuilder()
        val listOfText = address.split(",")
        listOfText[1].forEach { char -> if (Character.isLetter(char)) stringBuilder.append(char) }
        return stringBuilder.toString().trim()
    }

}