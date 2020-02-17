package com.benji.weatherswe.locationweather

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.location.IReversedGeocoding

class ReveresedGeocoding(context: Context) : IReversedGeocoding {

    private val TAG = "ReveresedGeocoding"

    private var geocoder: Geocoder = Geocoder(context)
    private val MAX_RESULTS = 1

    override fun getFromLocation(location: Location): String {
        val listOfAddress = geocoder.getFromLocation(location.y, location.x, MAX_RESULTS)
        val address = listOfAddress.first().getAddressLine(0)
        Log.d(TAG, "getFromLocation --> address:$address")
        return getCityFromAddressLine(address)
    }

    /**
     * Index for accessing the string city in listOfText is hardcoded to 1 because the structure of
     * the address string is: [0]street, [1]postcode + city, [2]country.
     */
    private fun getCityFromAddressLine(address: String): String {
        val stringBuilder = StringBuilder()
        val listOfText = address.split(",")
        listOfText[1].forEach { char -> if (Character.isLetter(char)) stringBuilder.append(char) }
        Log.d(TAG, "getCityFromAddressLine --> city:" + stringBuilder.toString().trim())
        return stringBuilder.toString().trim()
    }

}