package info.masoudkarimi.tasky.data.models

import io.ktor.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId



@Serializable
data class UserDto(
    @BsonId
    val _id: String? = null,

    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("token")
    val token: String? = null,

    @SerialName("password")
    val password: String? = null,
): Principal