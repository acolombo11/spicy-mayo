package eu.acolombo.work.calendar.events.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val status: String,
    val message: String?,
    val code: Int,
)
