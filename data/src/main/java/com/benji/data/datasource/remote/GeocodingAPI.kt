package com.benji.data.datasource.remote

import com.benji.domain.domainmodel.geocoding.Candidates
import com.benji.domain.domainmodel.geocoding.Suggestions
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingAPI {

    @GET("suggest?")
    suspend fun getSuggestions(@Query("text") text: String,
                               @Query("countryCode") countryCode : String = "SE",
                               @Query("category") category : String = "City",
                               @Query("f") f : String = "pjson"): Suggestions

    @GET("findAddressCandidates?")
    suspend fun getCandidate(@Query("SingleLine") singleLine: String,
                             @Query("magicKey") magicKey : String,
                             @Query("f") f : String = "json"): Candidates



}