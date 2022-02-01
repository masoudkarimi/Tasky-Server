package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.config.AppConfig
import info.masoudkarimi.tasky.config.ServerConfig
import io.ktor.application.*
import org.koin.ktor.ext.inject


fun Application.setUpConfig() {
    val appConfig by inject<AppConfig>()

    // Server
    val serverObject = environment.config.config("ktor.server")
    val isProd = serverObject.property("isProd").getString().toBoolean()
    appConfig.serverConfig = ServerConfig(isProd = isProd)
}