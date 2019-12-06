package com.benji.domain.domainmodel.geocoding

import java.math.BigDecimal
import java.math.RoundingMode

data class Location(
    val x: Double,
    val y: Double
)

fun Location.sixDecimals(): Location {
    val longitude = BigDecimal(x).setScale(5, RoundingMode.CEILING)
    val latitude = BigDecimal(y).setScale(5, RoundingMode.CEILING)
    return Location(longitude.toDouble(), latitude.toDouble())
}