package info.masoudkarimi.tasky.data.routes

import info.masoudkarimi.tasky.data.db.database
import info.masoudkarimi.tasky.data.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.eq

val userCollection = database.getCollection<User>("users")

fun Route.userRouting() {
    route("/user") {
        /**
         * List all users
         * */
        get {
            val users = userCollection.find().toList()
            call.respond(successResponse(UsersResponse(users)))
        }

        /**
         * Display specific user
         * */
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Missing or malformed id"),
            )

            val user = userCollection.findOne(User::_id eq id) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                errorResponse("No user with id $id"),
            )

            call.respond(successResponse(user))
        }

        /**
         * Create new user and return it as response
         * */
        post {
            val userRequest = call.receive<User>()
            userCollection.findOne(User::email eq userRequest.email)?.let {
                return@post call.respond(
                    status = HttpStatusCode.Conflict,
                    errorResponse("Email already exists")
                )
            }
            val isSuccess = userCollection.insertOne(userRequest).wasAcknowledged()
            if (!isSuccess) {
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    errorResponse("Could not insert user")
                )
            }

            val insertedUser = userCollection.findOne(User::email eq userRequest.email)
            call.respond(
                status = HttpStatusCode.Created,
                successResponse(insertedUser)
            )
        }

        /**
         * Delete specific user by its id
         * */
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val deleteResult = userCollection.deleteOne(User::_id eq id)
            if (deleteResult.deletedCount > 0) {
                call.respond(status = HttpStatusCode.Accepted, successResponse("User removed correctly"))
            } else {
                call.respond(status = HttpStatusCode.NotFound, errorResponse("User not found"))
            }
        }
    }
}