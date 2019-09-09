package com.benji.domain.domainmodel.geocoding

import java.util.*

data class Candidate(
    val address: String,
    val location: Location,
    val score: Int
)

class CompareScore : Comparator<Candidate> {
    override fun compare(a: Candidate, b: Candidate): Int {
        if (a.score > b.score)
            return -1 // highest value first
        return if (a.score == b.score) 0 else 1
    }
}