package com.deedcorps.edward.project_zeal.api.model

/**
 * Entities detected in the text have a type property, which describes the class of the entity.
 *
 * @param text The text of the entity
 * @param start The absolute start position of the entity in the original text
 * @param end The absolute end position of the sentence in the original text
 * @param type The type of entity (e.g. person, organization, location, etc.)
 */
data class Entity(
    val text: String,
    val start: Int,
    val end: Int,
    val type: String
)