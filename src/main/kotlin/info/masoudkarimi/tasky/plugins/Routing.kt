package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.data.routes.userRouting
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        userRouting()
    }
}
