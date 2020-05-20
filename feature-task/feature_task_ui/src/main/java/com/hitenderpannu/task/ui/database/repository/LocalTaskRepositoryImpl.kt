package com.hitenderpannu.task.ui.database.repository

import com.hitenderpannu.common.domain.Mapper
import com.hitenderpannu.task.domain.repo.LocalTaskRepo
import com.hitenderpannu.task.entity.Task
import com.hitenderpannu.task.ui.database.dao.TaskDao
import com.hitenderpannu.task.ui.database.entity.TaskEntity
import java.util.*

class LocalTaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val databaseMapper: Mapper<Task, TaskEntity>
) : LocalTaskRepo {

    override fun getTaskWhere(id: String): Task {
        val entity = taskDao.getTaskWhere(id)
        return databaseMapper.from(entity)
    }

    override fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { databaseMapper.from(it) }
    }

    override fun getAllPendingTasks(): List<Task> {
        return taskDao.getAllPendingTasks().map { databaseMapper.from(it) }
    }

    override fun createNewTask(description: String): Task {
        val time = System.currentTimeMillis()
        val task = TaskEntity(
            id = UUID.randomUUID().toString(),
            description = description,
            createdAt = time,
            isCompleted = false,
            updatedAt = time
        )
        taskDao.createTask(task)
        val data = taskDao.getTaskWhere(task.id)
        return databaseMapper.from(data)
    }

    override fun deleteTask(task: Task): String {
        taskDao.deleteTask(databaseMapper.to(task))
        return task.id
    }

    override fun updateTask(task: Task): Task {
        taskDao.updateTask(databaseMapper.to(task))
        val entity = taskDao.getTaskWhere(task.id)
        return databaseMapper.from(entity)
    }
}
