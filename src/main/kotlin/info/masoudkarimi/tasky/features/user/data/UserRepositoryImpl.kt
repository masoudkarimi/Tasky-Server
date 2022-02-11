package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.data.routes.generateJwtToken
import info.masoudkarimi.tasky.ext.isEmailValid
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.features.user.exceptions.*
import info.masoudkarimi.tasky.utils.BcryptHasher

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun register(userRequest: UserRequestDTO): UserDTO {
        if (userRequest.email.isNullOrEmpty()) {
            throw RequiredFieldMissedException("email")
        }

        if (!userRequest.email.isEmailValid()) {
            throw EmailInvalidException
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
        if (userRequest.email.isNullOrEmpty()) {
            throw RequiredFieldMissedException("email")
        }

        if (!userRequest.email.isEmailValid()) {
            throw EmailOrPasswordInvalidException
        }

        if (userRequest.password.isNullOrEmpty()) {
            throw RequiredFieldMissedException("password")
        }

        val user = userDataSource.getUserByEmail(userRequest.email) ?: kotlin.run {
            throw EmailOrPasswordInvalidException
        }

        if (!BcryptHasher.checkPassword(userRequest.password, user.password!!)) {
            throw EmailOrPasswordInvalidException
        }

        val newToken = generateJwtToken(userRequest.email) ?: kotlin.run {
            throw UserGeneralException("Something went wrong, try again later!")
        }

        val result = userDataSource.updateUserTokenById(user._id!!, newToken)

        if (!result) {
            throw UserGeneralException("Oops! Something wen wrong. Try later")
        }

        return UserDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            token = newToken
        )
    }
}