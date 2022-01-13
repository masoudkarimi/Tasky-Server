package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

val userStorage = mutableListOf(
    User(
        id = "0",
        firstName = "Masoud",
        lastName = "Karimi",
        email = "masoudkarimi@gmail.com"
    ),
    User(
        id = "1",
        firstName = "Saeed",
        lastName = "Karimi",
        email = "saeedkarimi@gmail.com"
    ),
    User(
        id = "2",
        firstName = "Ramin",
        lastName = "Karimi",
        email = "raminkarimi@gmail.com"
    )
)

@Serializable
data class UsersResponse(
    @SerialName("users")
    val users: List<User>
)

@Serializable
data class User(
    @SerialName("id")
    val id: String? = null,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("email")
    val email: String
)
