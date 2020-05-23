package com.hitenderpannu.workout.entity

import com.hitenderpannu.common.entity.WeightUnit
import java.math.BigDecimal

data class ExerciseSet(
    val setId: Long,
    val workoutId: Long,
    val exerciseId: String,
    val repCount: Int,
    val weight: BigDecimal,
    val weightUnit: WeightUnit,
    val createdAt: Long,
    val duration: Long
)

data class WorkoutExercise(
    val workoutId: Long,
    val exercise: Exercise,
    val sets: List<ExerciseSet>
)

data class WorkoutWithExercises(
    val workoutId: Long,
    val createdAt: Long,
    val isFinished: Boolean,
    val exercises: List<WorkoutExercise>
)

data class Workout(
    val workoutId: Long,
    val createdAt: Long,
    val isFinished: Boolean
)
