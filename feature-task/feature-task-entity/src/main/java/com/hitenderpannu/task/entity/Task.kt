package com.hitenderpannu.task.entity

data class Task(
    val id: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isCompleted: Boolean
)
