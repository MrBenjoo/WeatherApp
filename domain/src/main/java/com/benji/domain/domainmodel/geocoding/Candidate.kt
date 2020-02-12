package com.benji.domain.domainmodel.geocoding

data class Candidate(
    val address: String,
    val location: Location,
    val score: Int
)


