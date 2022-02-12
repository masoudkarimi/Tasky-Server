package info.masoudkarimi.tasky.plugins


import info.masoudkarimi.tasky.features.user.data.UserDataSource
import info.masoudkarimi.tasky.utils.JwtProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject


fun Application.configAuthentication() {
    val userDataSource by inject<UserDataSource>()

    install(Authentication) {
        jwt {
            verifier(JwtProvider.verifier)

            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                   userDataSource.getUserByEmail(credential.payload.claims["email"]?.asString() ?: return@validate null)
                } else {
                    null
                }
            }
        }
    }
}