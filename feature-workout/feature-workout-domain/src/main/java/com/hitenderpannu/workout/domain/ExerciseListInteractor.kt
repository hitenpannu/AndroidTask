package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.entity.Exercise

interface ExerciseListInteractor {

    suspend fun getListOfAllExercises():List<Exercise>
}
