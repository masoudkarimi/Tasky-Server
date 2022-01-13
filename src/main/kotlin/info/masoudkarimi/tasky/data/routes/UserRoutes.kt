package info.masoudkarimi.tasky.data.routes

import info.masoudkarimi.tasky.data.models.userStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRouting() {
    route("/user") {
        /**
         * List all users
         * */
        get {
            if (userStorage.isNotEmpty()) {
                call.respond(userStorage)
            } else {
                call.respondText("No user found", status = HttpStatusCode.NotFound)
            }
        }

        /**
         * Display specific user
         * */
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val user = userStorage.find { it.id == id } ?: return@get call.respondText(
                "No user with id $id",
                status = HttpStatusCode.NotFound
            )

            call.respond(user)
        }

        post {

        }

        delete("{id}") {

        }
    }
}