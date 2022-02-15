package info.masoudkarimi.tasky.features.tasks.domain

import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO

interface TaskRepository {
    suspend fun saveTask(userId: String?, taskDTO: TaskDTO): TaskDTO
    suspend fun findUserTasksById(userId: String?): List<TaskDTO>
}