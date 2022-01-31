package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.models.errorResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureExceptionHandler() {
    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                errorResponse("Authentication error!")
            )
        }
    }
}