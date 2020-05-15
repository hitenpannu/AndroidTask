package com.hitenderpannu.workout.entity

data class FailedToGetExercises(val errorMessage: String) : Throwable(errorMessage)
