package com.deedcorps.edward.project_zeal.api.model

/**
 * Domains have a category property which attempts to describe generally the kind of content that has been seen in the past at that domain.
 *
 * @param category The category of the domain extracted from the URL.
 */
data class Domain(val category: String)