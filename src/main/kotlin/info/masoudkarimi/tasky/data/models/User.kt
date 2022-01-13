package info.masoudkarimi.tasky.data.models

import kotlinx.serialization.Serializable

val userStorage = mutableListOf<User>()

@Serializable
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
