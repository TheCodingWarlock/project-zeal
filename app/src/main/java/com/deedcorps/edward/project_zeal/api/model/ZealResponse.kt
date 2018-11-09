package com.deedcorps.edward.project_zeal.api.model

data class ZealResponse(
    val success: Boolean,
    val title: Title,
    val content: Content,
    val domain: Domain
)