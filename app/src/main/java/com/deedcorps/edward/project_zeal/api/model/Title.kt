package com.deedcorps.edward.project_zeal.api.model

/**
 * Title analysis
 *
 * @param decision Whether the text is considered to contain **bias** or be **impartial**. The API will return **unsure**
 * if it is not confident either way
 * @param score How biased or impartial the content is with 0 being most biased and 1 being most impartial
 * @param entities A list of entities detected in the text
 */
data class Title(
    val decision: String,
    val score: Double,
    val entities: List<Entity>
)