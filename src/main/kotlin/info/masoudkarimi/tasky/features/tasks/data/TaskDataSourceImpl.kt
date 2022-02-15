package info.masoudkarimi.tasky.features.tasks.data

import info.masoudkarimi.tasky.exceptions.RequiredFieldMissedException
import info.masoudkarimi.tasky.exceptions.UserGeneralException
import info.masoudkarimi.tasky.features.tasks.data.dao.TaskDAO
import info.masoudkarimi.tasky.features.tasks.domain.model.TaskDTO
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq


class TaskDataSourceImpl(
    private val taskCollection: CoroutineCollection<TaskDAO>
) : TaskDataSource {
    override suspend fun saveTask(userId: String, taskDTO: TaskDTO): TaskDAO {
        if (taskDTO.title.isNullOrEmpty()) {
            throw RequiredFieldMissedException("title")
        }

        val taskDAO = TaskDAO(
            title = taskDTO.title,
            description = taskDTO.description,
            userId = userId,
        )

        val insertedId = taskCollection.insertOne(taskDAO).insertedId ?: kotlin.run {
            throw UserGeneralException("Could not create task!")
        }

        return taskCollection.findOne(TaskDAO::_id eq insertedId.asString().value) ?: kotlin.run {
            throw UserGeneralException("Could not create task!")
        }
    }

    override suspend fun findTasksByUserId(userId: String): List<TaskDAO> {
        return taskCollection.find(TaskDAO::userId eq userId).toList()
    }
}