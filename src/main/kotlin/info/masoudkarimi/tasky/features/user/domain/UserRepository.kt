package info.masoudkarimi.tasky.features.user.domain

import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO

interface UserRepository {
    suspend fun register(userRequest: UserRequestDTO): UserDTO
    suspend fun login(userRequest: UserRequestDTO): UserDTO
}