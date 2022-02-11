package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.routes.rootRouting
import info.masoudkarimi.tasky.data.routes.taskRouting
import info.masoudkarimi.tasky.features.user.resource.userRoutes
import io.ktor.application.*
import io.ktor.routing.*

const val API = "/api"
const val API_VERSION = "/v1"
const val USER = "$API$API_VERSION/user"
const val TASKS = "$API$API_VERSION/tasks"

fun Application.configureRouting() {
    userRoutes()

    routing {
        rootRouting()
        taskRouting()
    }
}
