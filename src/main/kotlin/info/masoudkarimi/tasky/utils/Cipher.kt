package info.masoudkarimi.tasky.utils

import com.auth0.jwt.algorithms.Algorithm

object Cipher {
    private val secret: String = System.getenv("JWT_SECRET")
    val algorithm: Algorithm = Algorithm.HMAC256(secret)
}
