package info.masoudkarimi.tasky

import info.masoudkarimi.tasky.plugins.configureMonitoring
import info.masoudkarimi.tasky.plugins.configureRouting
import info.masoudkarimi.tasky.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureRouting()
    configureMonitoring()
    configureSerialization()
}
