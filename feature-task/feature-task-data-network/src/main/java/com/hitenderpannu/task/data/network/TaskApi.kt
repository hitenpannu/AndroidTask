package com.hitenderpannu.task.data.network

import com.hitenderpannu.common.entity.NetworkResponse
import com.hitenderpannu.task.data.network.entity.CreateTaskRequest
import com.hitenderpannu.task.data.network.entity.CreateTaskResponse
import com.hitenderpannu.task.data.network.entity.UpdateTaskRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {

    @POST("/tasks")
    suspend fun createTask(@Body createTaskRequest: CreateTaskRequest): NetworkResponse<CreateTaskResponse>

    @GET("/tasks")
    suspend fun getAllTasks(): NetworkResponse<List<CreateTaskResponse>>

    @GET("/tasks/{id}")
    suspend fun getTask(@Path("id") taskId: String): NetworkResponse<CreateTaskResponse>

    @PATCH("/tasks/{id}")
    suspend fun updateTask(@Path("id") taskId: String, @Body updateTaskRequest: UpdateTaskRequest): NetworkResponse<CreateTaskResponse>
}
