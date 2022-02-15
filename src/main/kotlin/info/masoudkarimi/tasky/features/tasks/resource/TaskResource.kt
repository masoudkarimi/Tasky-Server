package info.masoudkarimi.tasky.features.tasks.resource

import info.masoudkarimi.tasky.data.models.successResponse
import info.masoudkarimi.tasky.features.tasks.domain.TaskRepository
import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO
import info.masoudkarimi.tasky.features.tasks.domain.model.TasksDTO
import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.plugins.TASKS
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Application.taskRoutes() {
    routing {
        saveTaskRoute()
    }
}

fun Route.saveTaskRoute() {
    val taskRepository by inject<TaskRepository>()

    authenticate {
        post(TASKS) {
            val user = context.authentication.principal<UserDAO>() ?: kotlin.run {
                return@post call.respond(HttpStatusCode.Unauthorized)
            }

            val task = call.receive<TaskDTO>()

            return@post call.respond(
                status = HttpStatusCode.Created,
                successResponse(taskRepository.saveTask(userId = user._id, task))
            )
        }

        get(TASKS) {
            val user = context.authentication.principal<UserDAO>() ?: kotlin.run {
                return@get call.respond(HttpStatusCode.Unauthorized)
            }

            return@get call.respond(
                status = HttpStatusCode.OK,
                successResponse(TasksDTO(taskRepository.findUserTasksById(user._id)))
            )
        }
    }
}