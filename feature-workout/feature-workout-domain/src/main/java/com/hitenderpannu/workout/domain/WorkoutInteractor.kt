package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises

interface WorkoutInteractor {

    suspend fun createNewWorkout(listOfExercises: List<Exercise>): Long

    suspend fun createNewSet(workoutId: Long, exerciseId: String): WorkoutExercise

    suspend fun addUpdateSet(set: ExerciseSet): WorkoutExercise

    suspend fun getWorkout(workoutId: Long): WorkoutWithExercises

    suspend fun getUnFinishedWorkout(): Workout?

    suspend fun updateRepCount(setId: Long, newRepCount: Int)

    suspend fun updateWeight(setId: Long, newWeight: Double)

    suspend fun finishWorkout(workoutId: Long)
}
