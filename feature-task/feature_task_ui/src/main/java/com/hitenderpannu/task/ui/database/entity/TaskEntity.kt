package com.hitenderpannu.task.ui.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = TaskEntity.TABLE_NAME,
    indices = [Index("id"), Index("isCompleted"), Index("updatedAt")]
)
data class TaskEntity(
    @PrimaryKey val id: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isCompleted: Boolean
) {
    companion object {
        const val TABLE_NAME = "tasks"
    }
}
