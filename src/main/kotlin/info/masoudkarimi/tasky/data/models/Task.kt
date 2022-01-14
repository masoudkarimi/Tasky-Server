package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class TasksResponse(
    @SerialName("tasks")
    val tasks: List<Task>
)

@Serializable
data class Task(
    @BsonId
    @SerialName("_id")
    val _id: String? = null,

    @BsonId
    @SerialName("user_id")
    var userId: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,
)
