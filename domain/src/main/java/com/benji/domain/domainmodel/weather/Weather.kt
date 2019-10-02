package com.benji.domain.domainmodel.weather

data class Weather(
    val approvedTime: String,
    val referenceTime: String,
    val geometry: Geometry,
    val timeSeries: List<TimeSeries>
)