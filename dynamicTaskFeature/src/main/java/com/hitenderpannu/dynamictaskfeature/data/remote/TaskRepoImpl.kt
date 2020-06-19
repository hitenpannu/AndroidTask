package com.hitenderpannu.dynamictaskfeature.data.remote

import com.hitenderpannu.dynamictaskfeature.domain.repo.RemoteTaskRepo
import com.hitenderpannu.dynamictaskfeature.entity.Task

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
