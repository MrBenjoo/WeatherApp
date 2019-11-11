package com.benji.domain.domainmodel.weather

data class Parameter(
    val name: String,
    val levelType: String,
    var level: Int,
    val unit: String,
    val values: List<String>
)