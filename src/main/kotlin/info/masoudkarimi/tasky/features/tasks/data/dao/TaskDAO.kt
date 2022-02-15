package info.masoudkarimi.tasky.features.tasks.data.dao

import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO
import io.ktor.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class TaskDAO(
    @BsonId
    @SerialName("_id")
    val _id: String? = null,

    @BsonId
    @SerialName("user_id")
    var userId: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String?,
) : Principal

fun TaskDAO.toTaskDTO() = TaskDTO(
    title = title,
    description = description,
)