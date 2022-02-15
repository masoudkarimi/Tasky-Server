package info.masoudkarimi.tasky.features.tasks.data

import info.masoudkarimi.tasky.exceptions.UnauthorizedException
import info.masoudkarimi.tasky.features.tasks.data.dao.TaskDAO
import info.masoudkarimi.tasky.features.tasks.data.dao.toTaskDTO
import info.masoudkarimi.tasky.features.tasks.domain.TaskRepository
import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO

class TaskRepositoryImpl(
    private val taskDataSource: TaskDataSource
): TaskRepository {

    override suspend fun saveTask(userId: String?, taskDTO: TaskDTO): TaskDTO {
        if (userId.isNullOrEmpty()) {
            throw UnauthorizedException
        }

        return taskDataSource.saveTask(userId, taskDTO).toTaskDTO()
    }

    override suspend fun findUserTasksById(userId: String?): List<TaskDTO> {
        if (userId.isNullOrEmpty()) {
            throw UnauthorizedException
        }

        return taskDataSource.findTasksByUserId(userId).map(TaskDAO::toTaskDTO)
    }
}