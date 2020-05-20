package com.hitenderpannu.task.data.network

import com.hitenderpannu.task.domain.repo.RemoteTaskRepo
import com.hitenderpannu.task.entity.Task

class TaskRepoImpl(private val taskApi: TaskApi) : RemoteTaskRepo {

    override suspend fun createTask(description: String, completed: Boolean?): Task {
        TODO()
    }

    override suspend fun getAllTasks(): List<Task> {
        TODO()
    }

    override suspend fun getTask(id: String): Task {
        TODO()
    }

    override suspend fun updateTask(id: String, description: String?, completed: Boolean?): Task {
        TODO()
    }
}
