package info.masoudkarimi.tasky.features.user.data

import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.exceptions.EmailAlreadyRegisteredException
import info.masoudkarimi.tasky.exceptions.UserGeneralException
import info.masoudkarimi.tasky.utils.BcryptHasher
import info.masoudkarimi.tasky.utils.JwtProvider
import org.litote.kmongo.*

import org.litote.kmongo.coroutine.CoroutineCollection

class UserDataSourceImpl(
    private val userCollection: CoroutineCollection<UserDAO>
) : UserDataSource {

    override suspend fun saveUser(userRequestDTO: UserRequestDTO): UserDAO {
        userCollection.findOne(UserDAO::email eq userRequestDTO.email)?.let {
            throw EmailAlreadyRegisteredException
        }

        val userDAO = UserDAO(
            firstName = userRequestDTO.firstName,
            lastName = userRequestDTO.lastName,
            email = userRequestDTO.email,
            password = BcryptHasher.hashPassword(userRequestDTO.password!!),
            token = JwtProvider.createJWT(userRequestDTO.email!!)
        )

        val insertedId = userCollection.insertOne(userDAO).insertedId ?: kotlin.run {
            throw UserGeneralException("Could not create user!")
        }

        return userCollection.findOne(UserDAO::_id eq insertedId.asString().value) ?: kotlin.run {
            throw UserGeneralException("Could not create user!")
        }
    }

    override suspend fun getUserByEmail(email: String): UserDAO? {
        return userCollection.findOne(UserDAO::email eq email)
    }

    override suspend fun updateUserTokenById(id: String, token: String): Boolean {
        return userCollection.updateOne(UserDAO::_id eq id, setValue(UserDAO::token, token)).wasAcknowledged()
    }
}