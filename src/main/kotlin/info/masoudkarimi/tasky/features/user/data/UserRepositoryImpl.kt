package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.ext.isEmailValid
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.exceptions.*
import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.utils.BcryptHasher
import info.masoudkarimi.tasky.utils.JwtProvider

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun register(userRequest: UserRequestDTO): UserDAO {
        if (userRequest.email.isNullOrEmpty()) {
            throw RequiredFieldMissedException("email")
        }

        if (!userRequest.email.isEmailValid()) {
            throw EmailInvalidException
        }

        if (userRequest.password.isNullOrEmpty()) {
            throw RequiredFieldMissedException("password")
        }

        if (userRequest.password.length < 6) {
            throw WeakPasswordException
        }

        return userDataSource.saveUser(userRequest)
    }

    override suspend fun login(userRequest: UserRequestDTO): UserDAO {
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

        val newToken = JwtProvider.createJWT(userRequest.email) ?: kotlin.run {
            throw UserGeneralException("Something went wrong, try again later!")
        }

        val result = userDataSource.updateUserTokenById(user._id!!, newToken)

        if (!result) {
            throw UserGeneralException("Oops! Something wen wrong. Try later")
        }

        return UserDAO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            token = newToken
        )
    }
}