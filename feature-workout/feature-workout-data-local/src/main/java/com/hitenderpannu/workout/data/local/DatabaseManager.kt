package com.hitenderpannu.workout.data.local

import android.content.Context
import androidx.room.Room
import com.hitenderpannu.workout.data.local.dao.BodyPartsDao
import com.hitenderpannu.workout.data.local.dao.EquipmentsDao
import com.hitenderpannu.workout.data.local.dao.ExerciseDao

class DatabaseManager(applicationContext: Context) {

    private val db = Room.databaseBuilder(applicationContext, WorkoutDatabase::class.java, "workout").build()

    fun provideExerciseDao(): ExerciseDao = db.exerciseDao()
    fun provideBodyPartsDao(): BodyPartsDao = db.bodyPartsDao()
    fun provideEquipmentsDao(): EquipmentsDao = db.equipmentsDao()
}
