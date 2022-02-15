package info.masoudkarimi.tasky.features.user.domain

import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO

interface UserRepository {
    suspend fun register(userRequest: UserRequestDTO): UserDAO
    suspend fun login(userRequest: UserRequestDTO): UserDAO
}