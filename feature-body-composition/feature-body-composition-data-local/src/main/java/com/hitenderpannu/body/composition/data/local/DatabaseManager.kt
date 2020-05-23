package com.hitenderpannu.body.composition.data.local

import android.content.Context
import androidx.room.Room

object DatabaseManager {

    private var database: BodyCompositionDatabase? = null

    @Synchronized
    fun getInstance(applicationContext: Context): BodyCompositionDatabase {
        if (database == null) {
            database = Room.databaseBuilder(applicationContext, BodyCompositionDatabase::class.java, "BodyCompositionDatabase").build()
        }
        return database!!
    }
}
