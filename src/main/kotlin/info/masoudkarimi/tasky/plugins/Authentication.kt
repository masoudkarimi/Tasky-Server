package info.masoudkarimi.tasky.plugins


import info.masoudkarimi.tasky.data.db.database
import info.masoudkarimi.tasky.data.models.UserDto
import info.masoudkarimi.tasky.utils.JwtProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.litote.kmongo.eq

val userCollection = database.getCollection<UserDto>("users")

fun Application.configAuthentication() {
    install(Authentication) {
        jwt {
            verifier(JwtProvider.verifier)

            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                    userCollection.findOne(UserDto::email eq credential.payload.claims["email"]?.asString())
                } else {
                    null
                }
            }
        }
    }
}