package com.hitenderpannu.workout.domain.remote_repo

import com.hitenderpannu.workout.entity.Exercise

interface RemoteExerciseRepo {
    suspend fun getAllExercise(): List<Exercise>
}
