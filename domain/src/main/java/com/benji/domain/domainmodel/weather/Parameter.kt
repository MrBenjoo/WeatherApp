package com.benji.domain.domainmodel.weather

data class Parameter(
    val level: Int,
    val levelType: String,
    val name: String,
    val unit: String,
    val values: List<String>
)