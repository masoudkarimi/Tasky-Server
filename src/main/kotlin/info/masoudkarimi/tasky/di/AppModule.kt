package info.masoudkarimi.tasky.di


import info.masoudkarimi.tasky.config.AppConfig
import info.masoudkarimi.tasky.data.models.Task
import io.ktor.application.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
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


    single<CoroutineCollection<Task>>(named("tasks")) {
        get<CoroutineDatabase>().getCollection("tasks")
    }

    single {
        AppConfig()
    }
}