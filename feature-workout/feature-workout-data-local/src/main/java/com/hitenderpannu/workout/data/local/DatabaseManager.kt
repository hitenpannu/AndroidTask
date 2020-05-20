package com.hitenderpannu.workout.data.local

import android.content.Context
import androidx.room.Room

object DatabaseManager {

    private var database: WorkoutDatabase? = null

    @Synchronized
    fun getDatabaseInstance(applicationContext: Context): WorkoutDatabase {
        if (database == null) {
            database = Room.databaseBuilder(applicationContext, WorkoutDatabase::class.java, "workout").build()
        }
        return database!!
    }
}
