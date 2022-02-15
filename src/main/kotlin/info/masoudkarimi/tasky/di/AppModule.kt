package info.masoudkarimi.tasky.di


import info.masoudkarimi.tasky.config.AppConfig
import io.ktor.application.*
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun appModule(application: Application) = module {
    single {
        val uri = application.environment.config.propertyOrNull("ktor.db.connectionString")?.getString() ?: throw IllegalArgumentException("Please specify mongodb URI")
        KMongo.createClient(uri).coroutine
    }

    single {
        get<CoroutineClient>().getDatabase("tasky-db")
    }

    single {
        AppConfig()
    }
}