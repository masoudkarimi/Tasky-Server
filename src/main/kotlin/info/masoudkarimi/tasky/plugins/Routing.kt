package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.routes.rootRouting
import info.masoudkarimi.tasky.data.routes.taskRouting
import info.masoudkarimi.tasky.data.routes.userRouting
import io.ktor.application.*
import io.ktor.routing.*

const val API = "/api"
const val API_VERSION = "/v1"
const val USERS = "$API$API_VERSION/users"
const val USER = "$API$API_VERSION/user"
const val TASKS = "$API$API_VERSION/tasks"

fun Application.configureRouting() {
    routing {
        rootRouting()
        userRouting()
        taskRouting()
    }
}
