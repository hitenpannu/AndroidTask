package com.hitenderpannu.dynamictaskfeature.domain

import com.hitenderpannu.dynamictaskfeature.entity.Task
import com.hitenderpannu.dynamictaskfeature.domain.repo.LocalTaskRepo

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

    override suspend fun deleteTask(task: Task) {
        localTaskRepo.deleteTask(task)
    }
}
