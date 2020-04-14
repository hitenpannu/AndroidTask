package com.hitenderpannu.task.data.network

import com.hitenderpannu.common.entity.StatusCode
import com.hitenderpannu.task.data.network.entity.CreateTaskRequest
import com.hitenderpannu.task.data.network.entity.UpdateTaskRequest
import com.hitenderpannu.task.entity.Task

class TaskRepoImpl(private val taskApi: TaskApi) : TaskRepo {

    override suspend fun createTask(description: String, completed: Boolean?): Task {
        val networkResponse = taskApi.createTask(CreateTaskRequest(description, completed?:false))
        return when {
            networkResponse.status.code != StatusCode.SUCCESS.code -> {
                throw Throwable(networkResponse.status.message)
            }
            else -> {
                val response = networkResponse.data ?:throw Throwable("Something went wrong")
                Task(response.creatorId, response.description, response.completed)
            }
        }
    }

    override suspend fun getAllTasks(): List<Task> {
        val networkResponse = taskApi.getAllTasks()
        when {
            networkResponse.status.code != StatusCode.SUCCESS.code -> {
                throw Throwable(networkResponse.status.message)
            }
            else -> {
                val response = networkResponse.data ?:throw Throwable("Something went wrong")
                return response.map { Task(it.creatorId, it.description, it.completed) }
            }
        }
    }

    override suspend fun getTask(id: String): Task {
        val networkResponse = taskApi.getTask(id)
        return when {
            networkResponse.status.code != StatusCode.SUCCESS.code -> {
                throw Throwable(networkResponse.status.message)
            }
            else -> {
                val response = networkResponse.data ?:throw Throwable("Something went wrong")
                Task(response.creatorId, response.description, response.completed)
            }
        }
    }

    override suspend fun updateTask(id: String, description: String?, completed: Boolean?) : Task{
        val networkResponse = taskApi.updateTask(id, UpdateTaskRequest(description, completed))
        return when {
            networkResponse.status.code != StatusCode.SUCCESS.code -> {
                throw Throwable(networkResponse.status.message)
            }
            else -> {
                val response = networkResponse.data ?:throw Throwable("Something went wrong")
                Task(response.creatorId, response.description, response.completed)
            }
        }
    }
}
