package com.benji.domain.domainmodel.weather

data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String
)