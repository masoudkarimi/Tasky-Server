package info.masoudkarimi.tasky.di

import info.masoudkarimi.tasky.features.user.data.UserDataSource
import info.masoudkarimi.tasky.features.user.data.UserDataSourceImpl
import info.masoudkarimi.tasky.features.user.data.UserRepositoryImpl
import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase

fun userModule() = module {
    single<CoroutineCollection<UserDAO>>(named("users")) {
        get<CoroutineDatabase>().getCollection("users")
    }

    single<UserDataSource> {
        UserDataSourceImpl(get(named("users")))
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}