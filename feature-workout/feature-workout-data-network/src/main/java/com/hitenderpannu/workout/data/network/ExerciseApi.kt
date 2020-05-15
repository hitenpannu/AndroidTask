package com.hitenderpannu.workout.data.network

import com.hitenderpannu.common.entity.NetworkResponse
import com.hitenderpannu.workout.data.network.network_entity.NetworkExercise
import retrofit2.http.GET

interface ExerciseApi {

    @GET("/exercise")
    suspend fun getAllExercises(): NetworkResponse<List<NetworkExercise>>
}
