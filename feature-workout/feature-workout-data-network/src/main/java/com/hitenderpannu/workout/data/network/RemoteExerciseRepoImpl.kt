package com.hitenderpannu.workout.data.network

import com.hitenderpannu.common.entity.StatusCode.SUCCESS
import com.hitenderpannu.workout.domain.remote_repo.RemoteExerciseRepo
import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.FailedToGetExercises

class RemoteExerciseRepoImpl(private val api: ExerciseApi) : RemoteExerciseRepo {

    override suspend fun getAllExercise(): List<Exercise> {
        val networkResponse = api.getAllExercises()
        if (networkResponse.status.code != SUCCESS.code || networkResponse.data == null) {
            throw FailedToGetExercises(networkResponse.status.message)
        }

        return networkResponse.data!!.map { NetworkToDomainEntityMapper.map(it) }
    }
}
