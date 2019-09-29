package com.benji.domain.domainmodel.weather

data class Hourly(
    val parameters: List<Parameter>,
    val validTime: String
)