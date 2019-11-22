package com.benji.domain.domainmodel.weather

data class HourlyOverview (
    val validTime: String,
    val temp : String,
    val weatherSymbol : Int
)