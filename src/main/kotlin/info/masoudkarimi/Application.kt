package info.masoudkarimi

import io.ktor.server.netty.*
import info.masoudkarimi.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureRouting()
}
