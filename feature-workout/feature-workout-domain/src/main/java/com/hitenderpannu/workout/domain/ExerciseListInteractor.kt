package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.entity.Exercise

interface ExerciseListInteractor {

    suspend fun getListOfAllExercises(fetchFresh: Boolean = false):List<Exercise>
}
