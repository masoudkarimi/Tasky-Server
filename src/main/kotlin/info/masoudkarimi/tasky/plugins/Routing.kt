package info.masoudkarimi.tasky.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            System.out.println("Request received")
            call.respondText("Hello World!")
        }
    }
    routing {
    }
}
