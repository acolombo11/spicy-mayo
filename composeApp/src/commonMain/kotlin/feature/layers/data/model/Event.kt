package feature.layers.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("summary")
    val summary: String?,
    @SerialName("start")
    val start: Instant?,
    @SerialName("end")
    val end: Instant?,
    @SerialName("attendees")
    val attendees: List<String> = emptyList(),
    @SerialName("type")
    val type: String,
)