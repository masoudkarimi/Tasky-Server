package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

val userTasks = mutableListOf(
    Task(
        id = "1",
        userId = "0",
        title = "Send email to ramin",
        description = "Send email containing the project details to ramin"
    ),
    Task(
        id = "2",
        userId = "0",
        title = "Eat lunch",
        description = "Remember to eat lunch at 13:00 o'clock"
    ),
    Task(
        id = "3",
        userId = "1",
        title = "Read the tutorial about java",
        description = "Read this amazing tutorial about java synchronization"
    ),
)

@Serializable
data class TasksResponse(
    @SerialName("tasks")
    val tasks: List<Task>
)

@Serializable
data class Task(
    @SerialName("id")
    val id: String? = null,

    @SerialName("user_id")
    val userId: String,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,
)
