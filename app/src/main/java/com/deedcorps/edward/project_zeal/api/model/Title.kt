package com.deedcorps.edward.project_zeal.api.model

data class Title(
    val decision: String,
    val score: Double,
    val entities: List<Entity>
)