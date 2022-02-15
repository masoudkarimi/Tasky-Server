package info.masoudkarimi.tasky.features.tasks.data

import info.masoudkarimi.tasky.features.tasks.data.dao.TaskDAO
import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO

interface TaskDataSource {
    suspend fun saveTask(userId: String, taskDTO: TaskDTO): TaskDAO
    suspend fun findTasksByUserId(userId: String): List<TaskDAO>
}