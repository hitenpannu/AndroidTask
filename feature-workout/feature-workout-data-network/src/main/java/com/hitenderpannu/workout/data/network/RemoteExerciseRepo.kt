package com.hitenderpannu.workout.data.network

import com.hitenderpannu.workout.entity.Exercise

interface RemoteExerciseRepo {
    suspend fun getAllExercise(): List<Exercise>
}
