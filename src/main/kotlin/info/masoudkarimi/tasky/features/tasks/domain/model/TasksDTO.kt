package info.masoudkarimi.tasky.features.tasks.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TasksDTO(
    @SerialName("tasks")
    val tasks: List<TaskDTO>
)