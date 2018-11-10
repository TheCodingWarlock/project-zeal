package com.deedcorps.edward.project_zeal.api.model

/**
 * A successful response of the image recognition.
 *
 * @param success Whether the operation was successful or not
 * @param title Results of title analysis
 * @param content  Results of content analysis
 * @param domain Results of domain name analysis
 */
data class ZealResponse(
    val success: Boolean,
    val title: Title,
    val content: Content,
    val domain: Domain
)