package com.hitenderpannu.task.domain.repo

import com.hitenderpannu.task.entity.Task

interface LocalTaskRepo {
    fun getTaskWhere(id: String): Task

    fun getAllTasks(): List<Task>

    fun getAllPendingTasks(): List<Task>

    fun createNewTask(description: String): Task

    fun deleteTask(task: Task): String

    fun updateTask(task: Task): Task
}
