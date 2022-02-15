package info.masoudkarimi.tasky.di

import info.masoudkarimi.tasky.features.tasks.data.TaskDataSource
import info.masoudkarimi.tasky.features.tasks.data.TaskDataSourceImpl
import info.masoudkarimi.tasky.features.tasks.data.TaskRepositoryImpl
import info.masoudkarimi.tasky.features.tasks.data.dao.TaskDAO
import info.masoudkarimi.tasky.features.tasks.domain.TaskRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase

fun taskModule() = module {
    single<CoroutineCollection<TaskDAO>>(named("tasks")) {
        get<CoroutineDatabase>().getCollection("tasks")
    }

    single<TaskDataSource> {
        TaskDataSourceImpl(get(named("tasks")))
    }

    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}