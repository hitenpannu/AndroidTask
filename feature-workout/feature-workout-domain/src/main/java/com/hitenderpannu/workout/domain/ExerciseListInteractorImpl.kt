package com.hitenderpannu.workout.domain

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import com.hitenderpannu.workout.data.network.RemoteExerciseRepo
import com.hitenderpannu.workout.entity.Exercise

class ExerciseListInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteExerciseRepo: RemoteExerciseRepo
) : ExerciseListInteractor {

    override suspend fun getListOfAllExercises(): List<Exercise> {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        return remoteExerciseRepo.getAllExercise()
    }
}
