package com.benji.domain.domainmodel.weather

data class CurrentDayUntilMidnight(
    val parameters: List<Parameter>,
    val city : String,
    val validTime: String
)