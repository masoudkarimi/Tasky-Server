package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.routes.rootRouting
import info.masoudkarimi.tasky.data.routes.taskRouting
import info.masoudkarimi.tasky.data.routes.userRouting
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        rootRouting()
        userRouting()
        taskRouting()
    }
}
