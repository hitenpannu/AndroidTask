package com.hitenderpannu.dynamictaskfeature.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hitenderpannu.dynamictaskfeature.data.database.dao.TaskDao
import com.hitenderpannu.dynamictaskfeature.data.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun provideTaskDao(): TaskDao
}
