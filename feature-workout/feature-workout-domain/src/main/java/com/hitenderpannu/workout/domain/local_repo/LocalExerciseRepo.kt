package com.hitenderpannu.workout.domain.local_repo

import com.hitenderpannu.workout.entity.Exercise

interface LocalExerciseRepo {

    suspend fun getAllExercises(): List<Exercise>

    suspend fun insertExercises(list: List<Exercise>)

    suspend fun getExerciseWithBodyPartsAndEquipment(id: String): Exercise
}
