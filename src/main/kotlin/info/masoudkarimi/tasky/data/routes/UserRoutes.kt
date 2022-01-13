package info.masoudkarimi.tasky.data.routes

import info.masoudkarimi.tasky.data.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRouting() {
    route("/user") {
        /**
         * List all users
         * */
        get {
            if (userStorage.isNotEmpty()) {
                call.respond(successResponse(UsersResponse(userStorage)))
            } else {
                call.respond(status = HttpStatusCode.NotFound, errorResponse("User not found"))
            }
        }

        /**
         * Display specific user
         * */
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Missing or malformed id"),
            )

            val user = userStorage.find { it.id == id } ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                errorResponse("No user with id $id"),
            )

            call.respond(successResponse(user))
        }

        /**
         * Create new user and return it as response
         * */
        post {
            val user = call.receive<User>().copy(id = userStorage.lastIndex.inc().toString())
            userStorage.add(user)
            call.respond(status = HttpStatusCode.Created, successResponse(user))
        }

        /**
         * Delete specific user by its id
         * */
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (userStorage.removeIf { it.id == id }) {
                call.respond(status = HttpStatusCode.Accepted, successResponse("User removed correctly"))
            } else {
                call.respond(status = HttpStatusCode.NotFound, errorResponse("User not found"))
            }
        }
    }
}