package com.hitenderpannu.workout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import com.hitenderpannu.workout.data.local.entities.SetEntity
import com.hitenderpannu.workout.data.local.entities.WorkoutEntity
import com.hitenderpannu.workout.data.local.entities.WorkoutExerciseCrossRef
import com.hitenderpannu.workout.data.local.entities.WorkoutWithExercise
import com.hitenderpannu.workout.entity.Workout

@Dao
interface WorkoutDao {

    @Insert
    fun insertNewWorkout(workout: WorkoutEntity): Long

    @Insert
    fun insertWorkoutExerciseCross(vararg workoutExerciseCrossRef: WorkoutExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} WHERE id=:id LIMIT 1")
    fun getWorkoutWhere(id: Long): WorkoutEntity

    @Transaction
    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} WHERE id=:id LIMIT 1")
    fun getWorkoutWithExerciseWhere(id: Long): WorkoutWithExercise

    @Transaction
    @Query("SELECT * FROM ${SetEntity.TABLE_NAME} WHERE workoutId=:workoutId AND exerciseId=:exerciseId")
    fun getListOfSetsWhere(workoutId: Long, exerciseId: String): List<SetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateSet(setEntity: SetEntity)

    @Transaction
    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} WHERE isFinished=0 LIMIT 1")
    fun getUnfinishedWorkout(): WorkoutEntity?

    @RawQuery
    fun updateData(updateQuery: SimpleSQLiteQuery): Int

    @Transaction
    @Query("SELECT * FROM ${WorkoutEntity.TABLE_NAME} WHERE isFinished=1 ORDER BY createdAt DESC LIMIT 1")
    fun getPreviouslyClosedWorkout(): WorkoutEntity?
}
