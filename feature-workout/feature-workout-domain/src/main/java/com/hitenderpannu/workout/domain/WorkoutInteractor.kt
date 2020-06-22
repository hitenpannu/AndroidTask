package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface WorkoutInteractor {

    suspend fun createNewWorkout(listOfExercises: List<Exercise>): Long

    suspend fun createNewSet(workoutId: Long, exerciseId: String): WorkoutExercise

    suspend fun addUpdateSet(set: ExerciseSet): WorkoutExercise

    suspend fun getWorkout(workoutId: Long): WorkoutWithExercises

    suspend fun updateRepCount(setId: Long, newRepCount: Int)

    suspend fun updateWeight(setId: Long, newWeight: Double)

    suspend fun finishWorkout(workoutId: Long)

    suspend fun getPreviousWorkout(): Flow<WorkoutWithExercises?>

    suspend fun getTotalNumberOfWorkouts(): Flow<Int>

    suspend fun getTotalAmountOfWeightLifted(): Flow<BigDecimal>
}
