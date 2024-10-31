package eu.acolombo.work.calendar.network.data

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val status: String,
    val message: String?,
    val code: Int,
)
