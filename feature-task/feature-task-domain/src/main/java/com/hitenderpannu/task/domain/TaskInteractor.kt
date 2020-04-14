package com.hitenderpannu.task.domain

import com.hitenderpannu.task.entity.Task

interface TaskInteractor {

    suspend fun createTask(description: String, completed: Boolean?): Task

    suspend fun updateTask(taskId: String, description: String?, completed: Boolean?): Task

    suspend fun getAllTasks(): List<Task>

    suspend fun getTask(taskId: String): Task
}
