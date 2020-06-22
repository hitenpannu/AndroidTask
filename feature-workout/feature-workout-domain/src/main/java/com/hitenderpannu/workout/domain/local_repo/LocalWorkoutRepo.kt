package com.hitenderpannu.workout.domain.local_repo

import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface LocalWorkoutRepo {

    suspend fun createNewWorkout(): Long

    suspend fun addExerciseToWorkout(workoutId: Long, exerciseId: String)

    suspend fun getWorkoutWithAttachedExerciseList(workoutId: Long): Pair<WorkoutWithExercises, List<String>>

    suspend fun addOrUpdateSet(exerciseSet: ExerciseSet)

    suspend fun getSetsFor(workoutId: Long, exerciseId: String): List<ExerciseSet>

    suspend fun createNewSet(workoutId: Long, exerciseId: String)

    suspend fun getExerciseForWorkout(workoutId: Long, exerciseId: String): WorkoutExercise

    suspend fun updateRepCount(setId: Long, newRepCount: Int)

    suspend fun updateWeight(setId: Long, newWeight: Double)

    suspend fun finishWorkout(workoutId: Long)

    suspend fun getPreviousWorkout(): Flow<Workout?>

    suspend fun getTotalNumberOfWorkouts(): Flow<Int>

    suspend fun getTotalAmountOfWeightLifted(): Flow<BigDecimal>
}
