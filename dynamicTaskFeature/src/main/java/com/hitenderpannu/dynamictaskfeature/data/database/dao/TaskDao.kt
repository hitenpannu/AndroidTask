package com.hitenderpannu.dynamictaskfeature.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hitenderpannu.dynamictaskfeature.data.database.entity.TaskEntity

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME}")
    fun getAllTasks(): List<TaskEntity>

    @Transaction
    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME} WHERE isCompleted=0 ORDER BY createdAt DESC")
    fun getAllCompletedTasks(): List<TaskEntity>

    @Transaction
    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME} WHERE isCompleted=1 ORDER BY createdAt DESC")
    fun getAllPendingTasks(): List<TaskEntity>

    @Transaction
    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME} WHERE id=:id LIMIT 1")
    fun getTaskWhere(id: String): TaskEntity

    @Insert
    fun createTask(task: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)
}
