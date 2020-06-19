package com.hitenderpannu.dynamictaskfeature.data.database

import android.content.Context
import androidx.room.Room

object DatabaseManager {

    private var database: TaskDatabase? = null

    fun getDatabase(context: Context): TaskDatabase {
        synchronized(DatabaseManager) {
            if (database == null) {
                database = Room.databaseBuilder(context, TaskDatabase::class.java, "taskDatabase").build()
            }
        }
        return database!!;
    }
}
