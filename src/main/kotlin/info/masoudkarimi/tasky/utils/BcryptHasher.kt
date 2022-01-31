package info.masoudkarimi.tasky.utils

import info.masoudkarimi.tasky.data.models.UserDto
import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

    /**
     * Check if the password matches the User's password
     */
    fun checkPassword(attempt: String, user: UserDto) = BCrypt.checkpw(attempt, user.password)

    /**
     * Returns the hashed version of the supplied password
     */
    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}