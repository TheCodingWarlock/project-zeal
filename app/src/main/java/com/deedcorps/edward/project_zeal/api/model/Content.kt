package com.deedcorps.edward.project_zeal.api.model

data class Content(
    val decision: String,
    val score: Double,
    val keywords: List<Keyword>,
    val entities: List<Entity>
)