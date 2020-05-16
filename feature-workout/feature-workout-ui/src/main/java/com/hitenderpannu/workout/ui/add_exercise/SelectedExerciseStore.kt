package com.hitenderpannu.workout.ui.add_exercise

import com.hitenderpannu.workout.entity.Exercise

interface SelectedExerciseStore {

    fun addExercise(exercise: Exercise)

    fun isSelected(exercise: Exercise): Boolean

    fun removeExercise(exercise: Exercise)
}
