package com.hitenderpannu.dynamictaskfeature.data.remote.entity

data class CreateTaskRequest(val description: String, val completed: Boolean = false)

data class CreateTaskResponse(val creatorId: String, val description: String, val completed: Boolean)

data class UpdateTaskRequest(val description: String? = null, val completed: Boolean?)
