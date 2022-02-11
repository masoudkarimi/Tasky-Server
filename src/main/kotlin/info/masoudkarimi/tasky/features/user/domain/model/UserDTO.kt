package info.masoudkarimi.tasky.features.user.domain.model

import io.ktor.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("token")
    val token: String? = null,
) : Principal