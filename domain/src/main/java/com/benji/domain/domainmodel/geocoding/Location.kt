package com.benji.domain.domainmodel.geocoding

import java.math.BigDecimal
import java.math.RoundingMode

data class Location(
     var y: Double,
     var x: Double
) {
    init {
        /**
         * Lat-long coordinates for cities in Sweden are in range:
         * - Latitude from 55.37514 to 67.85572
         * - Longitude from 11.1712 to 23.15645
         */
        require(!(y < 55.0 || y > 68.0)) { "Latitude coordinate not in a valid range (55-68). Latitude is: $y" }
        require(!(x < 10.0 || x > 24.0)) { "Longitude coordinate not in a valid range (10-24). Longitude is: $x" }
    }

}

fun Location.sixDecimals(): Location {
    val longitude = BigDecimal(x).setScale(5, RoundingMode.CEILING)
    val latitude = BigDecimal(y).setScale(5, RoundingMode.CEILING)
    return Location(latitude.toDouble(), longitude.toDouble())
}