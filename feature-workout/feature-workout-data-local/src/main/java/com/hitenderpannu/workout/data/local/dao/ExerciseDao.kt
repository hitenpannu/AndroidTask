package com.hitenderpannu.workout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hitenderpannu.workout.data.local.entities.ExerciseBodyCrossRef
import com.hitenderpannu.workout.data.local.entities.ExerciseEntity
import com.hitenderpannu.workout.data.local.entities.ExerciseEquipmentCrossRef
import com.hitenderpannu.workout.data.local.entities.ExerciseWithBodyPartsAndEquipments
import com.hitenderpannu.workout.data.local.entities.TABLE_EXERCISE

@Dao
interface ExerciseDao {

    @Transaction
    @Query("SELECT * FROM $TABLE_EXERCISE")
    fun getAll(): List<ExerciseWithBodyPartsAndEquipments>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllBodyPartsReferences(exercises: ExerciseBodyCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEquipmentReferences(exercises: ExerciseEquipmentCrossRef)
}
