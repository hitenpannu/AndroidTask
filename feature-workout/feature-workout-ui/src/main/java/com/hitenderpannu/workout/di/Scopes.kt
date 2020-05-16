package com.hitenderpannu.workout.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class WorkoutScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class DashboardScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class AddExerciseScope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class ExerciseFilter
