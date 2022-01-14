package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class UsersResponse(
    @SerialName("users")
    val users: List<User>
)

@Serializable
data class User(
    @BsonId
    val _id: String? = null,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("email")
    val email: String
)