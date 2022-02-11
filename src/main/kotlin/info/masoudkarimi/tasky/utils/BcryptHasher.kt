package info.masoudkarimi.tasky.utils

import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

    /**
     * Check if the password matches the User's password
     */
    fun checkPassword(attempt: String, current: String) = BCrypt.checkpw(attempt, current)

    /**
     * Returns the hashed version of the supplied password
     */
    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}