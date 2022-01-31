package info.masoudkarimi.tasky.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JwtProvider {
    private const val expirationTime = 10 * 60 * 60 * 1000 // 10 hours
    val audience: String = System.getenv("JWT_AUDIENCE")
    private val issuer: String = System.getenv("JWT_ISSUER")

    val verifier: JWTVerifier = JWT
        .require(Cipher.algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun decodeJWT(token: String): DecodedJWT = JWT.require(Cipher.algorithm).build().verify(token)

    fun createJWT(userEmail: String): String? =
        JWT.create()
            .withIssuedAt(Date())
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("email", userEmail)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTime)).sign(Cipher.algorithm)
}