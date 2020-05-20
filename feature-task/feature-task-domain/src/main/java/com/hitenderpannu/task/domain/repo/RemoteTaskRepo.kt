package com.hitenderpannu.task.domain.repo

import com.hitenderpannu.task.entity.Task

interface RemoteTaskRepo {

    suspend fun createTask(description: String, completed: Boolean? = false): Task

    suspend fun getAllTasks(): List<Task>

    suspend fun getTask(id: String): Task

    suspend fun updateTask(id: String, description: String? = null, completed: Boolean? = null): Task
}
