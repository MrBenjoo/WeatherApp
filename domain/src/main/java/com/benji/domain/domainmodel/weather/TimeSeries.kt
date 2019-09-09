package com.benji.domain.domainmodel.weather

data class TimeSeries(
    val parameters: List<Parameter>,
    val validTime: String
)