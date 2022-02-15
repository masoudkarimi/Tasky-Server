package info.masoudkarimi.tasky.features.tasks.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    @SerialName("title")
    val title: String? = null,

    @SerialName("description")
    val description: String? = null
)