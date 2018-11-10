package com.deedcorps.edward.project_zeal.api.model

/**
 * Content analysis
 *
 * @param decision Whether the text is considered to contain bias or be impartial.
 * The API will return unsure if it is not confident either way
 * @param score How biased or impartial the content is with 0 being most biased, and 1 being most impartial
 * @param keywords A list of most representative key words from the content text ordered by
 * relevance (with the most relevant first)
 * @param entities A list of entities detected in the text
 */
data class Content(
    val decision: String,
    val score: Double,
    val keywords: List<Keyword>,
    val entities: List<Entity>
)