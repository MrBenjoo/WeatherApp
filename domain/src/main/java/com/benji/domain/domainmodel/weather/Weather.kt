package com.benji.domain.domainmodel.weather

data class Weather(
    val approvedTime: String,
    val geometry: Geometry,
    val referenceTime: String,
    val timeSeries: List<TimeSeries>
)