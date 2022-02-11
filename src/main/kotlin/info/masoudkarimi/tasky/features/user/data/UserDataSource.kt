package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO

interface UserDataSource {
    suspend fun saveUser(userRequestDTO: UserRequestDTO): UserDTO
    suspend fun getUserByEmail(email: String) : UserDAO?
    suspend fun updateUserTokenById(id: String, token: String): Boolean
}