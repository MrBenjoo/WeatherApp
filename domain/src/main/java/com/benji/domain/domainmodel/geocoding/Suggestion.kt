package com.benji.domain.domainmodel.geocoding

data class Suggestion(
    val magicKey: String,
    val text: String
)

fun List<Suggestion>.returnCities(): List<String> {
    val listOfCities = mutableListOf<String>()
    forEach { suggestion -> listOfCities.add(suggestion.text) }
    return listOfCities
}