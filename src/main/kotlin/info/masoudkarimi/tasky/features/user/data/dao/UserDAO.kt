package info.masoudkarimi.tasky.features.user.data.dao

import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import io.ktor.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class UserDAO(
    @BsonId
    val _id: String? = null,

    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("email")
    val email: String? = null,

    /**
     * User JWT token
     * **/
    @SerialName("token")
    val token: String? = null,

    /**
     * Hashed password
     * */
    @SerialName("password")
    val password: String? = null,
) : Principal

fun UserDAO.toUserDTO() = UserDTO(
    firstName = firstName,
    lastName = lastName,
    email = email,
    token = token,
)