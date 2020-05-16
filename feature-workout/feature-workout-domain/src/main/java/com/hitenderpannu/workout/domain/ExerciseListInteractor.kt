package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.entity.Exercise

interface ExerciseListInteractor {

    suspend fun getListOfAllExercises(fetchFresh: Boolean = false): List<Exercise>

    suspend fun getListOfBodyParts(): List<BodyPart>

    suspend fun getListOfEquipments(): List<Equipment>
}
