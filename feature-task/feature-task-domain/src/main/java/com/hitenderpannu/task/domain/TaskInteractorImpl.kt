package com.hitenderpannu.task.domain

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import com.hitenderpannu.task.data.network.TaskRepo
import com.hitenderpannu.task.entity.Task

class TaskInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val taskRepo: TaskRepo
) : TaskInteractor {
    override suspend fun createTask(description: String, completed: Boolean?): Task {
        if (!networkConnectionChecker.isConnected()) throw NoInternetConnection
        return taskRepo.createTask(description, completed)
    }

    override suspend fun updateTask(taskId: String, description: String?, completed: Boolean?): Task {
        if (!networkConnectionChecker.isConnected()) throw NoInternetConnection
        return taskRepo.updateTask(taskId, description, completed)
    }

    override suspend fun getAllTasks(): List<Task> {
        if (!networkConnectionChecker.isConnected()) throw NoInternetConnection
        return taskRepo.getAllTasks()
    }

    override suspend fun getTask(taskId: String): Task {
        if (!networkConnectionChecker.isConnected()) throw NoInternetConnection
        return taskRepo.getTask(taskId)
    }
}
