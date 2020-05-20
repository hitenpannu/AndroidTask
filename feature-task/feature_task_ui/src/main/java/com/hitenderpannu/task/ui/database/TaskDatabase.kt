package com.hitenderpannu.task.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hitenderpannu.task.ui.database.dao.TaskDao
import com.hitenderpannu.task.ui.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun provideTaskDao(): TaskDao
}
