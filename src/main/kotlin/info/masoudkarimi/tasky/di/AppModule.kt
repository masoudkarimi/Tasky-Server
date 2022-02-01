package info.masoudkarimi.tasky.di


import info.masoudkarimi.tasky.config.AppConfig
import info.masoudkarimi.tasky.data.models.Task
import info.masoudkarimi.tasky.data.models.UserDto
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single {
        KMongo.createClient().coroutine
    }

    single {
        get<CoroutineClient>().getDatabase("tasky")
    }

    single<CoroutineCollection<UserDto>>(named("users")) {
        get<CoroutineDatabase>().getCollection("users")
    }

    single<CoroutineCollection<Task>>(named("tasks")) {
        get<CoroutineDatabase>().getCollection("tasks")
    }

    single {
        AppConfig()
    }
}