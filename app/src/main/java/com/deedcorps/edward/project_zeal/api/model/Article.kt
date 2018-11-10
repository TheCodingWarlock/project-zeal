package com.deedcorps.edward.project_zeal.api.model

/**
 * This class represents the Article we're checking it's authenticity.
 *
 * @param url The URL of where the article is hosted
 * @param title The title of the article
 * @param content The entire content of the article
 */
data class Article(
    val url: String = "",
    val title: String = "",
    val content: String = ""
)