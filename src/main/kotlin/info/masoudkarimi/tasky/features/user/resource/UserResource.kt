package info.masoudkarimi.tasky.features.user.resource

import info.masoudkarimi.tasky.data.models.errorResponse
import info.masoudkarimi.tasky.data.models.successResponse
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.features.user.exceptions.*
import info.masoudkarimi.tasky.plugins.USER
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Application.userRoutes() {
    install(StatusPages) {
        exception<RequiredFieldMissedException> { cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Missing required field: ${cause.fieldName}")
            )
        }

        exception<EmailInvalidExceptions> {
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Email is invalid!")
            )
        }

        exception<EmailAlreadyRegisteredExceptions> {
            call.respond(
                status = HttpStatusCode.Conflict,
                errorResponse("Email already registered!")
            )
        }

        exception<WeakPasswordException> {
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Your password is weak. " +
                        "the password should at least 6 characters and also contains letters and numbers")
            )
        }

        exception<UserRegistrationGeneralException> { cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                errorResponse(cause.message ?: "Couldn't process the request")
            )
        }
    }

    routing {
        userRegisterRoute()
        userLoginRoute()
    }
}

fun Route.userRegisterRoute() {
    val userRepository by inject<UserRepository>()

    post("$USER/auth/register") {
        val userRequest = call.receive<UserRequestDTO>()
        call.respond(HttpStatusCode.Created, successResponse(userRepository.register(userRequest)))
    }
}

fun Route.userLoginRoute() {
    val userRepository by inject<UserRepository>()

    post("$USER/auth/login") {
        val userRequest = call.receive<UserRequestDTO>()
        call.respond(userRepository.login(userRequest))
    }
}