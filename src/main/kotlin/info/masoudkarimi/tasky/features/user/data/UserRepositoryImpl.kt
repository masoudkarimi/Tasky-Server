package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.ext.isEmailValid
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.features.user.exceptions.EmailInvalidExceptions
import info.masoudkarimi.tasky.features.user.exceptions.WeakPasswordException
import info.masoudkarimi.tasky.features.user.exceptions.RequiredFieldMissedException

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun register(userRequest: UserRequestDTO): UserDTO {
        if (userRequest.email.isNullOrEmpty()) {
            throw RequiredFieldMissedException("email")
        }

        if (!userRequest.email.isEmailValid()) {
            throw EmailInvalidExceptions
        }

        if (userRequest.password.isNullOrEmpty()) {
            throw RequiredFieldMissedException("password")
        }

        if (userRequest.password.length < 6 || !userRequest.password.matches("^[a-zA-Z0-9]*$".toRegex())) {
            throw WeakPasswordException
        }

        return userDataSource.saveUser(userRequest)
    }

    override suspend fun login(userRequest: UserRequestDTO): UserDTO {
        TODO("Not yet implemented")
    }
}