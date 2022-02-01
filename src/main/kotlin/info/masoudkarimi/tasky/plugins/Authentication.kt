package info.masoudkarimi.tasky.plugins


import info.masoudkarimi.tasky.data.models.UserDto
import info.masoudkarimi.tasky.utils.JwtProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq


fun Application.configAuthentication() {
    val userCollection by inject<CoroutineCollection<UserDto>>(named("users"))

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