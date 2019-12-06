package com.benji.domain.domainmodel.geocoding

data class Candidates(
    val candidates: List<Candidate>
)

fun Candidates.returnCandidateWithHighestScore() : Candidate {
    return this.candidates.maxBy { it.score }!!
}