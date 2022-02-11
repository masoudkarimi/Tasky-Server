package info.masoudkarimi.tasky.data.routes


import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


fun Routing.rootRouting() {

    route("/") {
        get {
            call.respondText("Hello, world! Welcome to Tasky website!")
        }
    }
}