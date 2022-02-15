package info.masoudkarimi.tasky.plugins

import info.masoudkarimi.tasky.di.appModule
import info.masoudkarimi.tasky.di.taskModule
import info.masoudkarimi.tasky.di.userModule
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.KoinApplicationStarted
import org.koin.ktor.ext.KoinApplicationStopPreparing
import org.koin.ktor.ext.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }

    install(Koin) {
        slf4jLogger(level = Level.ERROR)
        modules(
            appModule(this@configureDI),
            userModule(),
            taskModule()
        )
    }

    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }

    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }
}