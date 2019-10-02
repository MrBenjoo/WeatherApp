package com.benji.domain.domainmodel.weather

data class Hourly(
    val validTime: String,
    val parameters: List<Parameter>
)