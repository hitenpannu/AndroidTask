package com.hitenderpannu.task.domain

import com.hitenderpannu.task.domain.repo.LocalTaskRepo
import com.hitenderpannu.task.entity.Task

class TaskInteractorImpl(
    private val localTaskRepo: LocalTaskRepo
) : TaskInteractor {
    override suspend fun createTask(description: String, completed: Boolean?): Task {
        return localTaskRepo.createNewTask(description)
    }

    override suspend fun updateTaskDescription(task: Task, newDescription: String): Task {
        val newTask = task.copy(updatedAt = System.currentTimeMillis(), description = newDescription)
        return localTaskRepo.updateTask(newTask)
    }

    override suspend fun toggleCompletionStatus(task: Task): Task {
        val newTask = task.copy(isCompleted = !task.isCompleted, updatedAt = System.currentTimeMillis())
        return localTaskRepo.updateTask(newTask)
    }

    override suspend fun getAllTasks(): List<Task> {
        return localTaskRepo.getAllTasks()
    }

    override suspend fun getTask(taskId: String): Task {
        return localTaskRepo.getTaskWhere(taskId)
    }

    override suspend fun getAllPendingTasks(): List<Task> {
        return localTaskRepo.getAllPendingTasks()
    }
}
