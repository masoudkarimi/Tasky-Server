package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.models.errorResponse
import info.masoudkarimi.tasky.data.routes.rootRouting
import info.masoudkarimi.tasky.exceptions.*
import info.masoudkarimi.tasky.features.tasks.resource.taskRoutes
import info.masoudkarimi.tasky.features.user.resource.userRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

const val API = "/api"
const val API_VERSION = "/v1"
const val USER = "$API$API_VERSION/user"
const val TASKS = "$API$API_VERSION/tasks"

fun Application.configureRouting() {
    install(StatusPages) {
        exception<RequiredFieldMissedException> { cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Missing required field: ${cause.fieldName}")
            )
        }

        exception<EmailInvalidException> {
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Email is invalid!")
            )
        }

        exception<EmailOrPasswordInvalidException> {
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Email or password is invalid!")
            )
        }

        exception<EmailAlreadyRegisteredException> {
            call.respond(
                status = HttpStatusCode.Conflict,
                errorResponse("Email already registered!")
            )
        }

        exception<WeakPasswordException> {
            call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse(
                    "Your password is weak. " +
                            "the password should at least 6 characters and also contains letters and numbers"
                )
            )
        }

        exception<UserGeneralException> { cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                errorResponse(cause.message ?: "Couldn't process the request")
            )
        }

        exception<UnauthorizedException> {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                errorResponse("Token has expired")
            )
        }

        status(HttpStatusCode.Unauthorized) {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                errorResponse("Token has expired")
            )
        }
    }

    userRoutes()
    taskRoutes()

    routing {
        rootRouting()
    }
}
