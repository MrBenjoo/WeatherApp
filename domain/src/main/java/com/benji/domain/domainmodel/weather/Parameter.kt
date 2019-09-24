package com.benji.domain.domainmodel.weather

data class Parameter(
    var level: Int,
    val levelType: String,
    val name: String,
    val unit: String,
    val values: List<String>
)