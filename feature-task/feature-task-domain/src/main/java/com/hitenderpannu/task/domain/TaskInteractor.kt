package com.hitenderpannu.task.domain

import com.hitenderpannu.task.entity.Task

interface TaskInteractor {

    suspend fun createTask(description: String, completed: Boolean?): Task

    suspend fun updateTaskDescription(task: Task, newDescription: String): Task

    suspend fun toggleCompletionStatus(task: Task): Task

    suspend fun getAllTasks(): List<Task>

    suspend fun getTask(taskId: String): Task

    suspend fun getAllPendingTasks(): List<Task>

    suspend fun deleteTask(task: Task)
}
