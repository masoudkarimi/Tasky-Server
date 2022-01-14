package info.masoudkarimi.tasky.data.routes

import info.masoudkarimi.tasky.data.db.database
import info.masoudkarimi.tasky.data.models.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.and
import org.litote.kmongo.eq

val tasksCollection = database.getCollection<Task>("tasks")

fun Route.taskRouting() {
    route("/user/{user_id}/tasks") {
        /**
         * List all task for user with id [user_id]
         **/
        get {
            val userId = call.parameters["user_id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Field [user_id] required")
            )

            userCollection.findOne(User::_id eq userId) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                errorResponse("User not found")
            )

            val userTasks = tasksCollection.find(Task::userId eq userId).toList()

            call.respond(
                HttpStatusCode.OK,
                successResponse(TasksResponse(userTasks))
            )
        }

        /**
         * Creating a new task for corresponding user
         * */
        post {
            val userId = call.parameters["user_id"] ?: return@post call.respond(
                status = HttpStatusCode.BadRequest,
                errorResponse("Field [user_id] required")
            )

            val task = call.receive<Task>()
            userCollection.findOne(User::_id eq userId) ?: return@post call.respond(
                status = HttpStatusCode.NotFound,
                errorResponse("User not found")
            )

            task.userId = userId
            val isSuccess = tasksCollection.insertOne(task).wasAcknowledged()
            if (!isSuccess) {
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    errorResponse("Could not insert task")
                )
            }

            call.respond(
                status = HttpStatusCode.Created,
                successResponse(task)
            )
        }

        /**
         * Delete task user by its id
         * */
        delete("{id}") {
            val userId = call.parameters["user_id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                errorResponse("User id required")
            )
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                errorResponse("task id required")
            )

            val deleteResult = tasksCollection.deleteOne(and(Task::_id eq id, Task::userId eq userId))
            if (deleteResult.deletedCount > 0) {
                call.respond(status = HttpStatusCode.Accepted, successResponse("Task removed correctly"))
            } else {
                call.respond(status = HttpStatusCode.NotFound, errorResponse("Task not found for this user"))
            }
        }
    }
}