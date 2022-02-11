package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.data.routes.generateJwtToken
import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.domain.model.UserDTO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.features.user.exceptions.EmailAlreadyRegisteredExceptions
import info.masoudkarimi.tasky.features.user.exceptions.UserRegistrationGeneralException
import info.masoudkarimi.tasky.utils.BcryptHasher

import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

class UserDataSourceImpl(
    private val userCollection: CoroutineCollection<UserDAO>
) : UserDataSource {

    override suspend fun saveUser(userRequestDTO: UserRequestDTO): UserDTO {
        userCollection.findOne(UserDAO::email eq userRequestDTO.email)?.let {
            throw EmailAlreadyRegisteredExceptions
        }

        val userDAO = UserDAO(
            firstName = userRequestDTO.firstName,
            lastName = userRequestDTO.lastName,
            email = userRequestDTO.email,
            password = BcryptHasher.hashPassword(userRequestDTO.password!!),
            token = generateJwtToken(userRequestDTO.email!!)
        )

        val insertedId = userCollection.insertOne(userDAO).insertedId ?: kotlin.run {
            throw UserRegistrationGeneralException("Could not create user!")
        }

        val insertedUser = userCollection.findOne(UserDAO::_id eq insertedId.asString().value) ?: kotlin.run {
            throw UserRegistrationGeneralException("Could not create user!")
        }

        return UserDTO(
            firstName = insertedUser.firstName,
            lastName = insertedUser.lastName,
            email = insertedUser.email,
            token = insertedUser.token
        )
    }

    override suspend fun getUserByEmail(email: String): UserDAO {
        TODO("Not yet implemented")
    }
}